package org.dfood.mixin;

import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;
import org.dfood.item.ModPotionItem;
import org.dfood.util.FoodToBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Items.class)
public abstract class foodToBlockMixin {

    /**
     *该Mixin类通过掉换注册方法的Item实例来实现将食物物品转换为对应的方块。
     * 原本的Item实例被更改了标识符变为了被遗弃的物品
     */
    @ModifyVariable(method = "register(Ljava/lang/String;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("HEAD"), argsOnly = true)
    private static Item modifyItem(Item item, String value) {
        if (FoodToBlocks.foodMap.containsKey(value)) {
            Registry.register(Registries.ITEM, new Identifier(value + "_de"), item);
            return FoodToBlocks.foodMap.get(value);
        } else {
            return item;
        }
    }

    @Inject(method = "register(Lnet/minecraft/registry/RegistryKey;Lnet/minecraft/item/Item;)Lnet/minecraft/item/Item;", at = @At("RETURN"))
    private static void registerBlock(RegistryKey<Item> key, Item item, CallbackInfoReturnable<Item> cir) {
        if (item instanceof ModPotionItem) {
            ((ModPotionItem)item).appendBlocks(Item.BLOCK_ITEMS, item);
        }
    }
}
