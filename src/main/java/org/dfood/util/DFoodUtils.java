package org.dfood.util;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.item.ModPotionItem;
import org.dfood.tag.ModTags;

import java.util.function.Function;

public class DFoodUtils {

    /**
     * 检查物品是否是该模组添加的食物方块
     */
    public static boolean isModFoodItem(Item item) {
        BlockState state = getBlockStateFromItem(item);
        if (state == null) return false;

        if (item instanceof BlockItem && isPotatoOrCarrot(state)) {
            return true;
        }
        return state.isIn(ModTags.FOOD_BLOCK);
    }

    /**
     * 检查作物能否放置在指定位置
     */
    public static boolean isCropCanPlaceAt(Item item, BlockPos pos, World world) {
        if (!(item instanceof BlockItem)) return false;

        BlockState state = getBlockStateFromItem(item);
        return state != null
                && isPotatoOrCarrot(state)
                && state.canPlaceAt(world, pos.up());
    }

    /**
     * 辅助方法
     * @param item
     * @return 对应的默认方块状态
     */
    private static BlockState getBlockStateFromItem(Item item) {
        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock().getDefaultState();
        }
        if (item instanceof ModPotionItem potionItem) {
            return potionItem.getBlock().getDefaultState();
        }
        return null;
    }

    private static boolean isPotatoOrCarrot(BlockState state) {
        return state.equals(Blocks.POTATOES.getDefaultState())
                || state.equals(Blocks.CARROTS.getDefaultState());
    }

    public static Block createFoodBlock(int foodValue, AbstractBlock.Settings settings,
                                            Function<AbstractBlock.Settings, Block> blockCreator) {
        IntPropertyManager.preCache("number_of_food", foodValue);
        return blockCreator.apply(settings);
    }
}