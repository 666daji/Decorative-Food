package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import org.jetbrains.annotations.Nullable;

/**
 * 用于{@link org.dfood.block.FoodBlock}的双方块物品，
 * 在不和原版冲突的情况下，使玩家能够通过原版的物品放置出食物方块
 */
public class DoubleBlockItem extends BlockItem {
    private final Block block2;

    public DoubleBlockItem(Block block, Settings settings, Block block2) {
        super(block, settings);
        this.block2 = block2;
    }

    @Override
    protected @Nullable BlockState getPlacementState(ItemPlacementContext context) {
        Block actualBlock = getActualBlock(context);
        BlockState state = actualBlock.getPlacementState(context);
        return (state != null && canPlace(context, state)) ? state : null;
    }

    private Block getActualBlock(ItemPlacementContext context) {
        BlockState block = this.getBlock().getDefaultState();
        if (context.getPlayer() != null && context.getPlayer().isSneaking()){
            return block2;
        }

        if (block.canPlaceAt(context.getWorld(), context.getBlockPos())) {
            return this.getBlock();
        } else {
            return this.block2;
        }
    }

    public Block getSecondBlock() {
        return this.block2;
    }
}
