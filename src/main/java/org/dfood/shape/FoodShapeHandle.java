package org.dfood.shape;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.shape.VoxelShape;

import java.util.Map;

public class FoodShapeHandle {
    /**
     表示食物方块体素的映射。
     在二维数组的一个元素中，该元素的前两个数字表示食物数量的范围，第三个数字表示对应的形状ID。
     @see Shapes
     */
    private static final Map<String, int[][]> shapeMap = Shapes.shapeMap;
    private static final FoodShapeHandle INSTANCE = new FoodShapeHandle();

    private FoodShapeHandle() {}

    public static FoodShapeHandle getInstance() {
        return INSTANCE;
    }

    public VoxelShape getShape(BlockState state, IntProperty number) {
        String blockId = Registries.BLOCK.getId(state.getBlock()).toString();
        if (shapeMap.containsKey(blockId)){
            for (int[] sha : shapeMap.get(blockId)){
                int i = state.get(number); // 获取食物数量
                if (i >= sha[0] && i <= sha[1]) {
                    return shapes.getShape(sha[2]); // 返回对应的形状
                }
            }
        }
        return shapes.ALL.getShape(); // 未找到时返回默认形状
    }

    public enum shapes{
        ALL(1, Block.createCuboidShape(0, 0, 0, 16, 8, 16)),
        HALF(2, Block.createCuboidShape(0, 0, 0, 16, 4, 16)),
        DOUBLE_HALF(3, Block.createCuboidShape(0, 0, 0, 16, 2, 16)),
        FLAT(4, Block.createCuboidShape(0, 0, 0, 16, 1, 16)),
        FLAT_SMAIL(5, Block.createCuboidShape(5, 0, 5, 11, 1, 11)),
        FLAT_ZH(6, Block.createCuboidShape(3, 0, 3, 13, 1, 13)),
        ALL_SMAIL(7, Block.createCuboidShape(5, 0, 5, 11, 8, 11)),
        ALL_ZH(8, Block.createCuboidShape(3, 0, 3, 13, 8, 13)),
        DOUBLE_HEAL_SMAIL(9, Block.createCuboidShape(5, 0, 5, 11, 2, 11)),
        DOUBLE_HEAL_ZH(10, Block.createCuboidShape(3, 0, 3, 13, 2, 13)),
        HALF_SMAIL(11, Block.createCuboidShape(5, 0, 5, 11, 4, 11)),
        HALF_ZH(12, Block.createCuboidShape(3, 0, 3, 13, 4, 13));

        private final VoxelShape shape;
        private final int id;

        shapes(int id, VoxelShape shape) {
            this.shape = shape;
            this.id = id;
        }

        public VoxelShape getShape() {
            return shape;
        }

        public static VoxelShape getShape(int id) {
            for (shapes s : values()) {
                if (s.id == id) {
                    return s.shape;
                }
            }
            return ALL.shape;
        }
    }
}
