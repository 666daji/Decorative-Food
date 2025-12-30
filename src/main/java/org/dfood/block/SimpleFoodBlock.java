package org.dfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateManager;
import net.minecraft.util.shape.VoxelShape;
import org.dfood.util.IntPropertyManager;
import org.jetbrains.annotations.Nullable;

/**
 * 表示堆叠数为1的{@link FoodBlock}，
 * 不需要在{@link IntPropertyManager}中缓存属性即可创建实例
 */
public class SimpleFoodBlock extends FoodBlock {
    public SimpleFoodBlock(Settings settings, boolean isFood, @Nullable VoxelShape simpleShape, boolean useItemTranslationKey, @Nullable EnforceAsItem cItem) {
        super(settings, 1, isFood, simpleShape, useItemTranslationKey, cItem);
    }

    /**
     * 可以直接访问的构造函数
     * @param simpleShape 简单形状
     * @param useItemTranslationKey 是否使用物品的翻译键作为方块的翻译键
     */
    public SimpleFoodBlock(Settings settings, boolean isFood, @Nullable VoxelShape simpleShape, boolean useItemTranslationKey){
        this(settings, isFood, simpleShape, useItemTranslationKey, null);
    }

    public SimpleFoodBlock(Settings settings){
        this(settings, true, null, false, null);
    }

    public SimpleFoodBlock(Settings settings, VoxelShape shape){
        this(settings, true, shape, false, null);
    }

    public SimpleFoodBlock(Settings settings, boolean isFood, VoxelShape shape) {
        this(settings, isFood, shape, false, null);
    }

    /**
     * 全参数构建器
     */
    public static class Builder extends FoodBlockBuilder<SimpleFoodBlock, Builder> {
        private Builder () {}

        public static Builder create() {
            return new Builder();
        }

        @Override
        public SimpleFoodBlock build() {
            validateSettings();

            return createBlock();
        }

        @Override
        protected SimpleFoodBlock createBlock() {
            return new SimpleFoodBlock(
                    this.settings,
                    this.isFood,
                    this.simpleShape,
                    this.useItemTranslationKey,
                    this.cItem
            );
        }
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(IntPropertyManager.create("number_of_food", 1));
    }
}
