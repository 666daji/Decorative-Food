package org.dfood.render;

import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.dfood.block.FoodBlocks;
import org.dfood.block.entity.PotionBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ModBlockColors {
    public static void registryColors() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) -> tintIndex != -1? 4159204: -1, FoodBlocks.WATER_BUCKET);
        ColorProviderRegistry.BLOCK.register(ModBlockColors::getPotionColor, FoodBlocks.POTION);
    }

    private static int getPotionColor(BlockState state, @Nullable BlockRenderView world, @Nullable BlockPos pos, int tintIndex) {
        if (world != null && tintIndex != -1) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
                return potionBlockEntity.getColor(tintIndex);
            }
        }
        return -1;
    }
}
