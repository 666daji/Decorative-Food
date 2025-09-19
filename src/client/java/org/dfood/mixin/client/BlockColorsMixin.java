package org.dfood.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.dfood.block.FoodBlocks;
import org.dfood.block.entity.PotionBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BlockColors.class)
public class BlockColorsMixin {
    @ModifyVariable(method = "create", at = @At("RETURN"))
    private static BlockColors registerBlockColor(BlockColors blockColors) {
        blockColors.registerColorProvider(BlockColorsMixin::getPotionColor, FoodBlocks.POTION);
        return blockColors;
    }

    @Unique
    private static int getPotionColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex){
        if (world != null && tintIndex != -1) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
                return potionBlockEntity.getColor();
            }
        }
        return -1;
    }
}
