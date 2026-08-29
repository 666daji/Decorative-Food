package org.dfood.shape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.Identifier;
import net.minecraft.util.shape.VoxelShape;
import org.dfood.DecorativeFood;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 食物方块形状处理器，用于根据食物数量动态选择对应的形状。
 * 支持使用自定义的形状枚举类来扩展形状定义。
 * <p>
 * 这套形状区间表在{@linkplain Shapes#shapeMap}中，以方块 id 为键、以
 * {@code [数量下界, 数量上界, 形状ID]} 三元组为值。本类对外只暴露按方块状态取形状的
 * 接口，内部会把「方块 → 区间表」与「形状ID → 具体形状」分别缓存，避免在渲染热路径上
 * 每帧重复执行字符串拼接与枚举线性查找。
 *
 * @see shapes
 * @see ShapeConvertible
 */
public class FoodShapeHandle {
    private static final Logger LOGGER = DecorativeFood.LOGGER;

    /**
     * 食物方块体素形状的区间表（见{@linkplain Shapes#shapeMap}）。
     */
    private static final Map<String, int[][]> shapeMap = Shapes.shapeMap;
    /** 方块 → 区间表的延迟缓存，加快渲染热路径上的形状查找。 */
    private static final Map<Block, int[][]> BLOCK_SHAPE_MAP = new ConcurrentHashMap<>();
    /**
     * 形状枚举类 → 其「形状ID → 具体形状」的查找表。
     * 用{@link ClassValue}保证线程安全，并让每个枚举类只构建一次。
     */
    private static final ClassValue<Map<Integer, VoxelShape>> SHAPE_ID_CACHE = new ClassValue<>() {
        @Override
        protected Map<Integer, VoxelShape> computeValue(Class<?> type) {
            Map<Integer, VoxelShape> byId = new HashMap<>();
            Object[] constants = type.getEnumConstants();
            if (constants != null) {
                for (Object constant : constants) {
                    if (constant instanceof ShapeConvertible convertible) {
                        byId.put(convertible.getId(), convertible.getShape());
                    }
                }
            }
            return Collections.unmodifiableMap(byId);
        }
    };

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
        Block block = state.getBlock();
        int[][] ranges = BLOCK_SHAPE_MAP.computeIfAbsent(block, FoodShapeHandle::resolveRanges);
        if (ranges != null) {
            int count = state.get(number);
            for (int[] range : ranges) {
                if (count >= range[0] && count <= range[1]) {
                    return getShapeById(range[2], shapeEnum);
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
     * 解析指定方块对应的形状区间表。
     * 结果可为 {@code null}，表示该方块没有注册过任何形状区间。
     * <p>把这段逻辑单独抽出来，是为了配合{@link #BLOCK_SHAPE_MAP}做延迟缓存。</p>
     *
     * @param block 目标方块
     * @return 对应的形状区间表，若未注册则返回 {@code null}
     */
    private static int[][] resolveRanges(Block block) {
        Identifier blockId = Registries.BLOCK.getId(block);
        return shapeMap.get(blockId.toString());
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
        VoxelShape shape = SHAPE_ID_CACHE.get(shapeEnum).get(id);
        if (shape != null) {
            return shape;
        }
        LOGGER.warn("No shape definition with ID {} was found, using the default shape", id);
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
        FLAT_SMALL(5, Block.createCuboidShape(5, 0, 5, 11, 1, 11)),
        /** 中等扁平方块形状 (10x1x10) */
        FLAT_MEDIUM(6, Block.createCuboidShape(3, 0, 3, 13, 1, 13)),
        /** 小型完整方块形状 (6x8x6) */
        ALL_SMALL(7, Block.createCuboidShape(5, 0, 5, 11, 8, 11)),
        /** 中等完整方块形状 (10x8x10) */
        ALL_MEDIUM(8, Block.createCuboidShape(3, 0, 3, 13, 8, 13)),
        /** 小型四分之一高方块形状 (6x2x6) */
        DOUBLE_HALF_SMALL(9, Block.createCuboidShape(5, 0, 5, 11, 2, 11)),
        /** 中等四分之一高方块形状 (10x2x10) */
        DOUBLE_HALF_MEDIUM(10, Block.createCuboidShape(3, 0, 3, 13, 2, 13)),
        /** 小型半高方块形状 (6x4x6) */
        HALF_SMALL(11, Block.createCuboidShape(5, 0, 5, 11, 4, 11)),
        /** 中等半高方块形状 (10x4x10) */
        HALF_MEDIUM(12, Block.createCuboidShape(3, 0, 3, 13, 4, 13));

        private static final Map<Integer, VoxelShape> BY_ID;

        static {
            Map<Integer, VoxelShape> byId = new HashMap<>();
            for (shapes shape : values()) {
                byId.put(shape.id, shape.shape);
            }
            BY_ID = Collections.unmodifiableMap(byId);
        }

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
            return BY_ID.getOrDefault(id, ALL.shape);
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
