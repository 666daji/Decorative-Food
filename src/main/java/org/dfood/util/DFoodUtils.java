package org.dfood.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.dfood.block.FoodBlock;
import org.dfood.item.DoubleBlockItem;
import org.dfood.item.HaveBlock;

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
            return FoodBlock.getRegisteredFoodBlocks().contains(block);
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
            return FoodBlock.getRegisteredFoodBlocks().contains(foodBlock);
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
    public static boolean HaveCItem(BlockState state) {
        return state.getBlock() instanceof FoodBlock foodBlock && foodBlock.haveCItem();
    }

    /**
     * 获取方块物品的对应默认方块状态
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
     * 获取食物方块的基础设置。
     * @return 食物方块的基础设置。
     * @apiNote 这不是必须的，但是推荐使用这个方法来创建食物方块
     */
    public static AbstractBlock.Settings getFoodBlockSettings() {
        return AbstractBlock.Settings.create()
                .strength(0.1F, 0.1F)
                .nonOpaque()
                .solidBlock(Blocks::never)
                .pistonBehavior(PistonBehavior.DESTROY);
    }
}