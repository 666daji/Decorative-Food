package org.dfood.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.util.shape.VoxelShape;
import org.dfood.util.IntPropertyManager;
import org.jetbrains.annotations.Nullable;

/**
 * FoodBlock构建器，支持链式调用配置参数，支持创建 FoodBlock 子类
 *
 * @param <T> 要构建的 FoodBlock 子类类型
 * @param <B> FoodBlockBuilder 自身的类型（用于实现流畅接口）
 * @apiNote 子类应该提供创建构建器的静态方法。
 * 例如：
 * <pre>{@code
 *      public static Builder create() {
 *          return new Builder();
 *      }
 * }</pre>
 */
public abstract class FoodBlockBuilder<T extends FoodBlock, B extends FoodBlockBuilder<T, B>> {
    protected AbstractBlock.Settings settings;
    protected int maxFood = 1;
    protected boolean isFood = true;
    @Nullable
    protected FoodBlock.EnforceAsItem cItem = null;
    protected boolean useItemTranslationKey = true;
    @Nullable
    protected VoxelShape simpleShape = null;

    /**
     * 构造函数，仅供子类使用
     */
    protected FoodBlockBuilder() {}

    /**
     * 设置方块的基本设置
     */
    public B settings(AbstractBlock.Settings settings) {
        this.settings = settings;
        return self();
    }

    /**
     * 设置最大食物堆叠数量
     */
    public B maxFood(int maxFood) {
        this.maxFood = maxFood;
        return self();
    }

    /**
     * 设置是否为食物方块（控制是否加入注册集合）
     */
    public B isFood(boolean isFood) {
        this.isFood = isFood;
        return self();
    }

    /**
     * 设置强制关联的物品
     */
    public B cItem(@Nullable FoodBlock.EnforceAsItem cItem) {
        this.cItem = cItem;
        return self();
    }

    /**
     * 设置是否使用物品翻译键
     */
    public B useItemTranslationKey(boolean useItemTranslationKey) {
        this.useItemTranslationKey = useItemTranslationKey;
        return self();
    }

    /**
     * 设置简化的固定形状
     */
    public B simpleShape(@Nullable VoxelShape simpleShape) {
        this.simpleShape = simpleShape;
        return self();
    }

    /**
     * 构建 FoodBlock 实例
     * @throws IllegalStateException 如果未指定方块设置
     */
    public T build() {
        validateSettings();
        preCacheIntProperty();

        return createBlock();
    }

    /**
     * 获取当前 FoodBlockBuilder 实例（用于实现流畅接口）
     */
    @SuppressWarnings("unchecked")
    protected B self() {
        return (B) this;
    }

    /**
     * 创建 FoodBlock 实例的工厂方法
     * @apiNote 子类必须重写此方法来创建自定义的 FoodBlock 子类
     */
    protected abstract T createBlock();

    /**
     * 验证必需的设置
     */
    protected void validateSettings() {
        if (this.settings == null) {
            throw new IllegalStateException("Settings must be provided");
        }
        if (this.maxFood < 1) {
            throw new IllegalStateException("Max food count must be greater than 0");
        }
    }

    /**
     * 预缓存 IntProperty
     */
    protected void preCacheIntProperty() {
        IntPropertyManager.preCache("number_of_food", this.maxFood);
    }
}