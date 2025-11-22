package org.dfood.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.dfood.block.FoodBlock;
import org.dfood.item.DoubleBlockItem;
import org.dfood.item.HaveBlock;

import java.util.function.BiFunction;

public class DFoodUtils {
    /**
     * 检查物品是否是模组添加的食物方块
     * @param item 要检查的物品
     * @return 是否是模组添加的食物方块
     */
    public static boolean isModFoodItem(Item item) {
        BlockState state = getBlockStateFromItem(item);
        if (state == null) return false;

        if (state.getBlock() instanceof FoodBlock block){
            return FoodBlock.FOOD_BLOCKS.contains(block);
        }

        return false;
    }

    /**
     * 检查物品是否是模组添加的食物方块
     * @param block 要检查的方块
     * @return 是否是模组添加的食物方块
     */
    public static boolean isModFoodBlock(Block block){
        if (block instanceof FoodBlock foodBlock){
            return FoodBlock.FOOD_BLOCKS.contains(foodBlock);
        }

        return false;
    }

    /**
     * 检查物品是否是模组添加的特殊方块物品
     * @param item 要检查的物品
     * @return 是否是模组添加的特殊方块物品
     */
    public static boolean isHaveBlock(Item item) {
        return item instanceof HaveBlock;
    }

    /**
     * 检查方块是否强制定义了asItem方法的返回值
     * @param state 要检查的方块状态
     * @return 方块是否强制定义了asItem方法的返回值
     */
    private static boolean HaveCItem(BlockState state) {
        return state.getBlock() instanceof FoodBlock foodBlock && foodBlock.haveCItem();
    }

    /**
     * 辅助方法
     * @param item 要转换的物品
     * @return 对应的默认方块状态
     */
    public static BlockState getBlockStateFromItem(Item item) {
        if (item instanceof DoubleBlockItem doubleBlockItem){
            return doubleBlockItem.getSecondBlock().getDefaultState();
        }
        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock().getDefaultState();
        }
        if (item instanceof HaveBlock haveBlock) {
            return haveBlock.getBlock().getDefaultState();
        }
        return null;
    }

    /**
     * 创建一个基本的食物方块
     * @param foodValue 食物方块的堆叠数量
     * @param settings 方块设置
     * @param blockCreator 食物方块的构造器
     * @return 一个新的食物方块
     */
    public static Block createFoodBlock(int foodValue, AbstractBlock.Settings settings,
                                        BiFunction<AbstractBlock.Settings, Integer, Block> blockCreator) {
        IntPropertyManager.preCache("number_of_food", foodValue);
        return blockCreator.apply(settings, foodValue);
    }
}