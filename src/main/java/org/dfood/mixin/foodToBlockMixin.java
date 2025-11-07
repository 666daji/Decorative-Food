package org.dfood.mixin;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ConsumableComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.block.FoodBlocks;
import org.dfood.item.HaveBlock;
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
     * <p>
     *     在Minecraft1.21.8版本中，物品的注册方法相比与之前的方法拥有更复杂的重载，
     *     所以本类注入了三个方法来拦截所有需要修改的内容
     * </p>
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
        else if (foodToBlocks.other.contains(id)) {
            cir.setReturnValue(
                    register(id, StewToBlocks.stewMap.get(id))
            );
        } else if (id.equals("milk_bucket")) {
            cir.setReturnValue(
                    register(id, new BlockItem(FoodBlocks.MILK_BUCKET, new Item.Settings().recipeRemainder(Items.BUCKET)
                            .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.MILK_BUCKET).useRemainder(Items.BUCKET).maxCount(1)
                            .useItemPrefixedTranslationKey().registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id)))))
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
                    register(id, new BlockItem(FoodBlocks.BOWL, new Item.Settings()
                            .useItemPrefixedTranslationKey()
                            .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id)))))
            );
        }
    }

    @Unique
    private static Item register(String id, Item item) {
        if (item instanceof BlockItem blockItem) {
            blockItem.appendBlocks(Item.BLOCK_ITEMS, item);
        } else if (item instanceof HaveBlock haveBlock) {
            haveBlock.appendBlocks(Item.BLOCK_ITEMS, item);
        }

        return Registry.register(Registries.ITEM, id, item);
    }
}
