package org.dfood.shape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;
import org.dfood.ThreedFood;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Map;

/**
 * 食物方块形状处理器，用于根据食物数量动态选择对应的形状。
 * 支持使用自定义的形状枚举类来扩展形状定义。
 *
 * @see shapes
 * @see ShapeConvertible
 */
public class FoodShapeHandle {
    private static final Logger LOGGER = ThreedFood.LOGGER;

    /**
     * 表示食物方块体素的映射。
     * 在二维数组的每个元素中，前两个数值定义食物数量的区间，第三个数值对应形状ID。
     */
    private static final Map<String, int[][]> shapeMap = Shapes.shapeMap;
    private static final FoodShapeHandle INSTANCE = new FoodShapeHandle();

    private FoodShapeHandle() {}

    /**
     * 获取食物形状处理器的单例实例。
     *
     * @return 食物形状处理器的单例实例
     */
    public static FoodShapeHandle getInstance() {
        return INSTANCE;
    }

    /**
     * 根据方块状态和食物数量获取对应的形状。
     * 使用自定义的形状枚举类来定义形状。
     *
     * @param <T>       形状枚举类型，必须实现 ShapeConvertible 接口
     * @param state     方块状态
     * @param number    食物数量属性
     * @param shapeEnum 形状枚举类
     * @return 对应的形状，如果未找到匹配则返回默认形状
     */
    public <T extends Enum<T> & ShapeConvertible> VoxelShape getShape(@NotNull BlockState state, IntProperty number, Class<T> shapeEnum) {
        String blockId = Registries.BLOCK.getId(state.getBlock()).toString();
        if (shapeMap.containsKey(blockId)) {
            for (int[] sha : shapeMap.get(blockId)) {
                int i = state.get(number); // 获取食物数量
                if (i >= sha[0] && i <= sha[1]) {
                    return getShapeById(sha[2], shapeEnum);
                }
            }
        }
        return getDefaultShape(shapeEnum);
    }

    /**
     * 根据方块状态和食物数量获取对应的形状。
     * 使用默认的 shapes 枚举类来定义形状。
     *
     * @param state  方块状态
     * @param number 食物数量属性
     * @return 对应的形状，如果未找到匹配则返回默认形状
     */
    public VoxelShape getShape(BlockState state, IntProperty number) {
        return getShape(state, number, shapes.class);
    }

    /**
     * 根据形状ID从自定义枚举类获取对应的形状。
     *
     * @param <T>       形状枚举类型，必须实现 ShapeConvertible 接口
     * @param id        形状ID
     * @param shapeEnum 形状枚举类
     * @return 对应的形状，如果未找到则返回默认形状
     */
    private <T extends Enum<T> & ShapeConvertible> VoxelShape getShapeById(int id, Class<T> shapeEnum) {
        try {
            T[] enumConstants = shapeEnum.getEnumConstants();
            for (T shape : enumConstants) {
                if (shape.getId() == id) {
                    return shape.getShape();
                }
            }
            LOGGER.warn("No shape definition with ID {} was found, using the default shape", id);
        } catch (Exception e) {
            LOGGER.error("An error occurred when getting a shape based on ID，ID: {}", id, e);
        }
        return getDefaultShape(shapeEnum);
    }

    /**
     * 从自定义枚举类获取默认的形状。
     *
     * @param <T>       形状枚举类型，必须实现 ShapeConvertible 接口
     * @param shapeEnum 形状枚举类
     * @return 默认的形状
     */
    private <T extends Enum<T> & ShapeConvertible> VoxelShape getDefaultShape(Class<T> shapeEnum) {
        try {
            T[] enumConstants = shapeEnum.getEnumConstants();
            if (enumConstants.length > 0) {
                return enumConstants[0].getShape();
            }
        } catch (Exception e) {
            LOGGER.error("An error occurred while getting the default shape", e);
        }
        LOGGER.warn("Unable to get a default shape from an enum class, use an alternate default shape");
        return shapes.ALL.getShape();
    }

    /**
     * 预定义的形状枚举，包含常用的食物形状。
     */
    public enum shapes implements ShapeConvertible {
        /** 完整方块形状 (16x8x16) */
        ALL(1, Block.createCuboidShape(0, 0, 0, 16, 8, 16)),
        /** 半高方块形状 (16x4x16) */
        HALF(2, Block.createCuboidShape(0, 0, 0, 16, 4, 16)),
        /** 四分之一高方块形状 (16x2x16) */
        DOUBLE_HALF(3, Block.createCuboidShape(0, 0, 0, 16, 2, 16)),
        /** 扁平方块形状 (16x1x16) */
        FLAT(4, Block.createCuboidShape(0, 0, 0, 16, 1, 16)),
        /** 小型扁平方块形状 (6x1x6) */
        FLAT_SMAIL(5, Block.createCuboidShape(5, 0, 5, 11, 1, 11)),
        /** 中等扁平方块形状 (10x1x10) */
        FLAT_ZH(6, Block.createCuboidShape(3, 0, 3, 13, 1, 13)),
        /** 小型完整方块形状 (6x8x6) */
        ALL_SMAIL(7, Block.createCuboidShape(5, 0, 5, 11, 8, 11)),
        /** 中等完整方块形状 (10x8x10) */
        ALL_ZH(8, Block.createCuboidShape(3, 0, 3, 13, 8, 13)),
        /** 小型四分之一高方块形状 (6x2x6) */
        DOUBLE_HEAL_SMAIL(9, Block.createCuboidShape(5, 0, 5, 11, 2, 11)),
        /** 中等四分之一高方块形状 (10x2x10) */
        DOUBLE_HEAL_ZH(10, Block.createCuboidShape(3, 0, 3, 13, 2, 13)),
        /** 小型半高方块形状 (6x4x6) */
        HALF_SMAIL(11, Block.createCuboidShape(5, 0, 5, 11, 4, 11)),
        /** 中等半高方块形状 (10x4x10) */
        HALF_ZH(12, Block.createCuboidShape(3, 0, 3, 13, 4, 13));

        private final VoxelShape shape;
        private final int id;

        /**
         * 创建形状枚举实例。
         *
         * @param id    形状ID
         * @param shape 形状
         */
        shapes(int id, VoxelShape shape) {
            this.shape = shape;
            this.id = id;
        }

        /**
         * 获取形状ID。
         *
         * @return 形状ID
         */
        @Override
        public int getId() {
            return this.id;
        }

        /**
         * 获取形状。
         *
         * @return 形状
         */
        @Override
        public VoxelShape getShape() {
            return shape;
        }

        /**
         * 根据形状ID获取对应的形状。
         *
         * @param id 形状ID
         * @return 对应的形状，如果未找到则返回默认形状
         */
        public static VoxelShape getShape(int id) {
            for (shapes s : values()) {
                if (s.id == id) {
                    return s.shape;
                }
            }
            LOGGER.warn("未找到ID为 {} 的形状，使用默认形状", id);
            return ALL.shape;
        }
    }

    /**
     * 形状可转换接口，用于定义自定义形状枚举类必须实现的方法。
     */
    public interface ShapeConvertible {
        /**
         * 获取形状ID。
         *
         * @return 形状ID
         */
        int getId();

        /**
         * 获取形状。
         *
         * @return 形状
         */
        VoxelShape getShape();
    }
}