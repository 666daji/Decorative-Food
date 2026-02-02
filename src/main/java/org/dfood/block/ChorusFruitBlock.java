package org.dfood.block;

import net.minecraft.block.BlockState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class ChorusFruitBlock extends FoodBlock {
    public ChorusFruitBlock(Settings settings, int maxFood) {
        super(settings, maxFood, true, null, true, null);
    }

    public static class Builder extends FoodBlockBuilder<ChorusFruitBlock, Builder> {
        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        @Override
        protected ChorusFruitBlock createBlock() {
            return new ChorusFruitBlock(
                    this.settings, this.maxFood
            );
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound(
                    pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false
            );
        }
    }
}
