package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.entity.EnchantedGoldenAppleBlockEntity;
import org.jetbrains.annotations.Nullable;

public class EnchantedGoldenAppleBlock extends FoodBlock implements BlockEntityProvider {

    protected EnchantedGoldenAppleBlock(Settings settings, int maxFood) {
        super(settings, maxFood, true, null, true, null);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    public static class Builder extends FoodBlockBuilder<EnchantedGoldenAppleBlock, Builder> {
        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        @Override
        protected EnchantedGoldenAppleBlock createBlock() {
            return new EnchantedGoldenAppleBlock(
                    this.settings, this.maxFood
            );
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new EnchantedGoldenAppleBlockEntity(pos, state);
    }
}
