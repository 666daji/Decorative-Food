package org.dfood.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dfood.block.FoodBlocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Blocks.class)
public class BlocksMixin {

    @Inject(method = "<clinit>", at = @At("HEAD"), remap = false)
    private static void registerFoodBlocks(CallbackInfo ci) {
        // 遍历映射表中的所有方块并进行注册
        FoodBlocks.FOOD_BLOCK_REGISTRY.forEach(BlocksMixin::register);
    }

    @Unique
    private static void register(String id, Block block) {
        Registry.register(Registries.BLOCK, Identifier.of(id), block);
    }
}