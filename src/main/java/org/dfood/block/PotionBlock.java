package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.entity.PotionBlockEntity;
import org.jetbrains.annotations.Nullable;

public class PotionBlock extends ComplexFoodBlock implements BlockEntityProvider {
    protected PotionBlock(Settings settings, int maxFood) {
        super(settings, maxFood, true, null, true, null);
    }

    public static class Builder extends FoodBlockBuilder<PotionBlock, Builder> {
        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        @Override
        protected PotionBlock createBlock() {
            return new PotionBlock(
                    this.settings, this.maxFood
            );
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PotionBlockEntity(pos, state);
    }
}
