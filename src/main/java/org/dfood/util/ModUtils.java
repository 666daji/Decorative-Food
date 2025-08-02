package org.dfood.util;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.tag.ModTags;

public class ModUtils {

    /**
     * 检查一个物品是否是该模组添加的食物方块
     * @param item
     * @return 如果是食物方块则返回true，否则返回false
     */
    public static boolean isModFoodItem (Item item){
        if (item instanceof BlockItem) {
            BlockState blockState = ((BlockItem) item).getBlock().getDefaultState();
            if (blockState.equals(Blocks.POTATOES.getDefaultState()) || blockState.equals(Blocks.CARROTS.getDefaultState())) {
                return true;
            }
            return blockState.isIn(ModTags.FOOD_BLOCK);
        }
        return false;
    }

    public static boolean isCropCanPlaceAt(Item item, BlockPos pos, World world) {
        if (item instanceof BlockItem) {
            BlockState blockState = ((BlockItem) item).getBlock().getDefaultState();
            if (blockState.equals(Blocks.POTATOES.getDefaultState()) || blockState.equals(Blocks.CARROTS.getDefaultState())) {
                return blockState.canPlaceAt(world, pos.up());
            }
        }
        return false;
    }
}
