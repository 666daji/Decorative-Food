package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;

public class EnchantedGoldenAppleBlockEntity extends BlockEntity {
    public EnchantedGoldenAppleBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.ENCHANTED_GOLDEN_APPLE, pos, state);
    }
}
