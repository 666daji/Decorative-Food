package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import org.dfood.block.entity.ComplexFoodBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 支持存储物品 NBT 数据的复杂食物方块。
 * <p>
 * 该方块允许将物品堆栈的 {@link NbtCompound} 存储于关联的 {@link ComplexFoodBlockEntity} 中，
 * 并在取出或破坏方块时恢复原样。存储机制为栈结构（先进后出）。
 *
 * @see ComplexFoodBlockEntity
 */
public class ComplexFoodBlock extends FoodBlock implements BlockEntityProvider {
    protected ComplexFoodBlock(Settings settings, int maxFood, boolean isFood,
                               @Nullable VoxelShape simpleShape, boolean useItemTranslationKey,
                               @Nullable EnforceAsItem cItem) {
        super(settings, maxFood, isFood, simpleShape, useItemTranslationKey, cItem);
    }

    public static class Builder extends FoodBlockBuilder<ComplexFoodBlock, Builder> {
        private Builder() {}

        public static Builder create() {
            return new Builder();
        }

        @Override
        protected ComplexFoodBlock createBlock() {
            return new ComplexFoodBlock(
                    this.settings,
                    this.maxFood,
                    this.isFood,
                    this.simpleShape,
                    this.useItemTranslationKey,
                    this.cItem
            );
        }
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ComplexFoodBlockEntity(pos, state);
    }

    /**
     * 默认不比较 NBT 数据，子类可重写以支持特定 NBT 匹配逻辑。
     *
     * @param stack 手持物品堆栈
     * @param state 对应的方块状态
     * @param blockEntity 对应的方块实体
     * @return 若匹配返回 {@code true}
     */
    @Override
    public boolean isSame(ItemStack stack, BlockState state, @Nullable BlockEntity blockEntity) {
        return super.isSame(stack, state, blockEntity);
    }

    /**
     * 尝试增加堆叠数量，并将物品 NBT 存入方块实体。
     *
     * @return {@code true} 如果操作成功
     */
    @Override
    protected boolean tryAdd(BlockState state, World world, BlockPos pos, PlayerEntity player,
                             ItemStack handStack, @Nullable BlockEntity blockEntity) {
        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            NbtCompound stackNbt = handStack.hasNbt() ? handStack.getNbt().copy() : new NbtCompound();
            complexFoodBlockEntity.pushNbt(stackNbt);
        }

        return super.tryAdd(state, world, pos, player, handStack, blockEntity);
    }

    /**
     * 创建物品堆栈，并从方块实体恢复对应的 NBT 数据。
     *
     * @param count 创建数量（通常为 1）
     * @param state 对应的方块状态
     * @param blockEntity 对应的方块实体
     * @return 带有原 NBT 数据的物品堆栈
     * @throws IllegalArgumentException 如果数量超出范围
     */
    @Override
    public ItemStack createStack(int count, BlockState state, @Nullable BlockEntity blockEntity) {
        if (count <= 0 || count > MAX_FOOD) {
            throw new IllegalArgumentException("Count must be between 1 and " + MAX_FOOD);
        }

        ItemStack stack = new ItemStack(this.asItem(), count);

        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            NbtCompound storedNbt = complexFoodBlockEntity.popNbt();
            if (storedNbt != null && !storedNbt.isEmpty()) {
                stack.setNbt(storedNbt);
            }
        }

        return stack;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state,
                         @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            NbtCompound stackNbt = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
            complexFoodBlockEntity.pushNbt(stackNbt);
            complexFoodBlockEntity.markDirty();
        }
    }

    /**
     * 获取掉落物列表，确保每个掉落物都带有正确的 NBT 数据。
     *
     * @return 按先进后出顺序恢复 NBT 的掉落物列表
     */
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        int foodCount = state.get(NUMBER_OF_FOOD);

        if (foodCount <= 0) {
            return Collections.emptyList();
        }

        BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);

        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            List<ItemStack> droppedStacks = new ArrayList<>();
            for (int i = 0; i < foodCount; i++) {
                droppedStacks.add(createStack(1, state, complexFoodBlockEntity));
            }
            return droppedStacks;
        }

        return Collections.singletonList(new ItemStack(this.asItem(), foodCount));
    }
}