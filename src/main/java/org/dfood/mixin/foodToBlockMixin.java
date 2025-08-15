package org.dfood.mixin;

import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.block.foodBlocks;
import org.dfood.item.ModPotionItem;
import org.dfood.util.StewToBlocks;
import org.dfood.util.foodToBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Function;

@Mixin(Items.class)
public abstract class foodToBlockMixin {

    /**
     * 该Mixin类通过掉换注册方法的Item实例来实现将食物物品转换为对应的方块。
     * 原本的Item实例被更改了标识符变为了被遗弃的物品
     */
    @Inject(method = "register(Ljava/lang/String;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;",
            at = @At("HEAD"),
            cancellable = true)
    private static void modifyItem(String id, Item.Settings settings, CallbackInfoReturnable<Item> cir){
        if (foodToBlocks.foodMap.containsKey(id)) {
            cir.setReturnValue(
                    register(id, foodToBlocks.foodMap.get(id))
            );
        }
        else if (id.equals("rabbit_stew") || id.equals("mushroom_stew") || id.equals("beetroot_soup")) {
            cir.setReturnValue(
                    register(id, StewToBlocks.stewMap.get(id))
            );
        }
    }
    @Inject(method = "register(Ljava/lang/String;Ljava/util/function/Function;Lnet/minecraft/item/Item$Settings;)Lnet/minecraft/item/Item;",at = @At("HEAD"),cancellable = true)
    private static void modifyBlockItem(String id, Function<Item.Settings, Item> factory, Item.Settings settings, CallbackInfoReturnable<Item> cir){
        if (foodToBlocks.foodMap.containsKey(id)) {
            cir.setReturnValue(
                    register(id, foodToBlocks.foodMap.get(id))
            );
        }
    }

    @Inject(method = "register(Ljava/lang/String;)Lnet/minecraft/item/Item;",at = @At("HEAD"),cancellable = true)
    private static void modifyItem(String id, CallbackInfoReturnable<Item> cir){
        if (foodToBlocks.foodMap.containsKey(id)) {
            cir.setReturnValue(
                    register(id, foodToBlocks.foodMap.get(id))
            );
        }
        else if (id.equals("bowl")) {
            cir.setReturnValue(
                    register(id, new BlockItem(foodBlocks.BOWL.getBlock(), new Item.Settings()
                            .useItemPrefixedTranslationKey()
                            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id)))))
            );
        }
    }

    @Unique
    private static Item register(String id, Item item) {
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        } else if (item instanceof ModPotionItem potionItem) {
            potionItem.appendBlocks(Item.BLOCK_ITEMS, item);
        }
        return Registry.register(Registries.ITEM, id, item);
    }
}
