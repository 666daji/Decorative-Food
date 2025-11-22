package org.dfood.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.util.mixin.dFoodSignBlockEntity;

/**
 * 告示牌物品悬挂功能管理器 - 简化版
 * 处理告示牌上物品的悬挂和破坏掉落逻辑
 */
public class ModAbstractSignBlock {

    /**
     * 处理告示牌破坏时的物品掉落
     */
    public static void handleSignBreak(World world, BlockPos pos, PlayerEntity player) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof dFoodSignBlockEntity signEntity)) {
            return;
        }

        // 掉落正面和背面的物品
        dropAllItems(world, pos, signEntity);

        // 返还铁粒
        returnIronNuggets(world, pos, signEntity);

        // 清空数据
        clearSignData(signEntity);
    }

    /**
     * 掉落告示牌上的所有物品
     */
    private static void dropAllItems(World world, BlockPos pos, dFoodSignBlockEntity signEntity) {
        DefaultedList<ItemStack> items = DefaultedList.of();

        ItemStack frontItem = signEntity.dFood$getItem(true);
        if (!frontItem.isEmpty()) {
            items.add(frontItem.copy());
        }

        ItemStack backItem = signEntity.dFood$getItem(false);
        if (!backItem.isEmpty()) {
            items.add(backItem.copy());
        }

        if (!items.isEmpty()) {
            ItemScatterer.spawn(world, pos, items);
        }
    }

    /**
     * 返还铁粒
     */
    private static void returnIronNuggets(World world, BlockPos pos, dFoodSignBlockEntity signEntity) {
        int ironNuggetsToReturn = signEntity.dFood$getIronNuggetsToReturn();

        if (ironNuggetsToReturn > 0) {
            DefaultedList<ItemStack> ironStacks = DefaultedList.ofSize(1,
                    new ItemStack(Items.IRON_NUGGET, ironNuggetsToReturn));
            ItemScatterer.spawn(world, pos, ironStacks);
        }
    }

    /**
     * 清空告示牌数据
     */
    private static void clearSignData(dFoodSignBlockEntity signEntity) {
        signEntity.dFood$setItem(true, ItemStack.EMPTY);
        signEntity.dFood$setItem(false, ItemStack.EMPTY);
    }

    /**
     * 检查是否为有效的告示牌
     */
    public static boolean isValidSign(BlockState state) {
        return state.isIn(BlockTags.WALL_SIGNS);
    }

    /**
     * 尝试悬挂物品到告示牌
     */
    public static boolean tryHangItem(World world, BlockPos pos, BlockState state,
                                      PlayerEntity player, SignBlockEntity signBlockEntity) {

        ItemStack mainHandStack = player.getMainHandStack();
        ItemStack offHandStack = player.getOffHandStack();

        // 检查悬挂条件：主手有物品且副手有铁粒
        if (mainHandStack.isEmpty() || !offHandStack.isOf(Items.IRON_NUGGET)) {
            return false;
        }

        boolean isFront = signBlockEntity.isPlayerFacingFront(player);

        // 检查对应面是否有空间
        if (signBlockEntity.dFood$hasItem(isFront)) {
            return false;
        }

        // 检查墙上告示牌的背面限制
        if (state.getBlock() instanceof WallSignBlock && !isFront) {
            // 墙上告示牌不能在背面悬挂物品
            return false;
        }

        // 尝试添加物品
        ItemStack stackToAdd = mainHandStack.copy();
        if (!signBlockEntity.dFood$tryAddItem(isFront, stackToAdd)) {
            return false;
        }

        // 处理悬挂成功逻辑
        processSuccessfulHang(world, pos, state, player, stackToAdd);
        return true;
    }

    /**
     * 处理悬挂成功后的逻辑
     */
    private static void processSuccessfulHang(World world, BlockPos pos, BlockState state,
                                              PlayerEntity player, ItemStack stackToAdd) {

        // 消耗铁粒
        if (!player.isCreative()) {
            ItemStack offHandStack = player.getOffHandStack();
            offHandStack.decrement(1);
        }

        // 更新玩家物品栏
        if (!player.isCreative()) {
            player.setStackInHand(Hand.MAIN_HAND, stackToAdd);
        }

        // 播放效果
        world.syncWorldEvent(2001, pos, Block.getRawIdFromState(state));

        // 播放音效
        world.playSound(null, pos, SoundEvents.BLOCK_CHAIN_PLACE, SoundCategory.BLOCKS, 0.5F, 1.0F);

        // 强制更新方块状态
        world.updateListeners(pos, state, state, 3);
    }
}