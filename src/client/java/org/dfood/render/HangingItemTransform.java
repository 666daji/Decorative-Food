package org.dfood.render;

import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public record HangingItemTransform(Consumer<MatrixStack> transformFunction) {
    private static final Map<Item, HangingItemTransform> HANGING_ITEM_TRANSFORMS = new HashMap<>();

    /**
     * 应用物品的特殊变换
     * @param item 对应的物品堆栈
     * @param matrixStack 初始矩阵堆栈
     * @param signState 对应的告示牌方块状态
     * @param itemFacing 方块朝向
     * @apiNote 如果没有对应的变化则不进行任何操作
     */
    public static void applyTransformation(ItemStack item, MatrixStack matrixStack, BlockState signState, Direction itemFacing) {
        if (HANGING_ITEM_TRANSFORMS.containsKey(item.getItem())){
            HANGING_ITEM_TRANSFORMS.get(item.getItem()).transformFunction().accept(matrixStack);
        }
    }

    static {
        HANGING_ITEM_TRANSFORMS.put(Items.COD, new HangingItemTransform(matrixStack -> {
            matrixStack.translate(-0.44f,  -0.2f, 0.55f);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
        }));
        HANGING_ITEM_TRANSFORMS.put(Items.COOKED_COD, new HangingItemTransform(matrixStack -> {
            matrixStack.translate(-0.44f,  -0.2f, 0.55f);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
        }));
        HANGING_ITEM_TRANSFORMS.put(Items.SALMON, new HangingItemTransform(matrixStack -> {
             matrixStack.translate(-0.44f, -0.2f, 0.65f);
             matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
        }));
        HANGING_ITEM_TRANSFORMS.put(Items.COOKED_SALMON, new HangingItemTransform(matrixStack -> {
            matrixStack.translate(-0.44f, -0.2f, 0.65f);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90f));
        }));
    }
}
