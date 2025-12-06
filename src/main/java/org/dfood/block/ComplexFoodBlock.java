package org.dfood.block;

import net.minecraft.block.Block;
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
import net.minecraft.world.World;
import org.dfood.block.entity.ComplexFoodBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 表示可以将原本物品堆栈中的{@linkplain net.minecraft.nbt.NbtCompound}写入方块实体中保存
 * 并在方块被破坏或者拿取的时候返回原本的物品堆栈的复杂食物方块。
 * <h2>Nbt的保存</h2>
 * <p>由于食物方块的堆叠通常是一个一个的，所以会在{@linkplain ComplexFoodBlockEntity}
 * 中保存一个长度等于{@linkplain FoodBlock#MAX_FOOD}的{@linkplain net.minecraft.nbt.NbtCompound}的有序集合，
 * 每个索引处对应的Nbt数据表示为放置时对应的物品堆栈的Nbt数据，
 * 这些数据会在取出物品时原封不动地返还，Nbt集合是先进后出的。
 * 例如：放置一个最大堆叠数为3的复杂食物方块，有三个物品堆栈，保存的Nbt数据数据分别为{t:1}，{y:2}，{z:3}
 * （无论物品堆栈的数量如何，因为一般情况下每次只会放入堆栈中的1个物品），
 * 依次堆叠进去，当取出时会先取出Nbt为{z:3}的物品。</p>
 * @see ComplexFoodBlockEntity
 */
public class ComplexFoodBlock extends FoodBlock implements BlockEntityProvider {
    public ComplexFoodBlock(Settings settings, int max_food, boolean isFood) {
        super(settings, max_food, isFood);
    }

    public ComplexFoodBlock(Settings settings, int max_food){
        super(settings, max_food);
    }

    public ComplexFoodBlock(Settings settings, int max_food, @Nullable EnforceAsItem cItem){
        super(settings, max_food, cItem);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ComplexFoodBlockEntity(pos, state);
    }

    /**
     * 因为该方块的堆叠允许不同的{@linkplain net.minecraft.nbt.NbtCompound},
     * 所以本类不会对比Nbt数据来判断是否匹配，子类可以重写该方块以检查特定的Nbt数据是否相同。
     * @param stack 手持物品堆栈
     * @param blockEntity 对应的方块实体
     * @return 匹配返回true,否则返回false
     */
    @Override
    public boolean isSame(ItemStack stack, @Nullable BlockEntity blockEntity) {
        return super.isSame(stack, blockEntity);
    }

    /**
     * 重写尝试增加堆叠数量的方法，同时保存物品的NBT数据
     * @param state 当前的方块状态
     * @param world 当前世界
     * @param pos 方块的位置
     * @param player 执行操作的玩家
     * @param handStack 尝试添加的物品堆栈
     * @param blockEntity 对应的方块实体
     * @return 操作是否成功
     */
    @Override
    protected boolean tryAdd(BlockState state, World world, BlockPos pos, PlayerEntity player,
                             ItemStack handStack, @Nullable BlockEntity blockEntity) {
        int currentCount = state.get(NUMBER_OF_FOOD);

        if (currentCount < MAX_FOOD) {
            // 保存物品的NBT数据到方块实体
            if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
                // 复制物品堆栈的NBT数据
                NbtCompound stackNbt = handStack.hasNbt() ? handStack.getNbt().copy() : new NbtCompound();
                complexFoodBlockEntity.pushNbt(stackNbt);
            }

            // 更新方块状态
            BlockState newState = state.with(NUMBER_OF_FOOD, currentCount + 1);
            return world.setBlockState(pos, newState, Block.NOTIFY_ALL);
        }

        return false;
    }

    /**
     * 重写尝试减少方块堆叠数量的方法，同时恢复物品的NBT数据
     * @param state 当前的方块状态
     * @param world 当前世界
     * @param pos 方块的位置
     * @param player 执行操作的玩家
     * @param blockEntity 对应的方块实体
     * @return 操作是否成功
     */
    @Override
    protected boolean tryRemove(BlockState state, World world, BlockPos pos,
                                PlayerEntity player, @Nullable BlockEntity blockEntity) {
        int currentCount = state.get(NUMBER_OF_FOOD);

        if (currentCount > 0) {
            int newCount = currentCount - 1;

            if (newCount > 0) {
                // 更新方块状态
                BlockState newState = state.with(NUMBER_OF_FOOD, newCount);
                world.setBlockState(pos, newState, Block.NOTIFY_ALL);
            } else {
                // 如果数量为0，破坏方块
                world.breakBlock(pos, false);
            }

            // 给予玩家物品（非创造模式）
            if (!player.isCreative()) {
                ItemStack foodItem = createStack(1, blockEntity);
                if (!player.giveItemStack(foodItem)) {
                    player.dropItem(foodItem, false);
                }
            }

            return true;
        }

        return false;
    }

    /**
     * 重写创建物品堆栈的方法，从方块实体中恢复NBT数据
     * @param count 数量（对于复杂食物方块，通常为1）
     * @param blockEntity 对应的方块实体
     * @return 创建成功的物品堆栈
     */
    @Override
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        if (count <= 0 || count > MAX_FOOD) {
            throw new IllegalArgumentException("Count must be between 1 and " + MAX_FOOD);
        }

        ItemStack stack = new ItemStack(this.asItem(), count);

        // 从方块实体中获取并恢复NBT数据
        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            NbtCompound storedNbt = complexFoodBlockEntity.popNbt();
            if (storedNbt != null && !storedNbt.isEmpty()) {
                stack.setNbt(storedNbt);
            }
        }

        return stack;
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);

        // 首次放置方块时，将手持物品的NBT数据保存到方块实体中
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            // 复制物品堆栈的NBT数据
            NbtCompound stackNbt = itemStack.hasNbt() ? itemStack.getNbt().copy() : new NbtCompound();
            complexFoodBlockEntity.pushNbt(stackNbt);

            // 标记方块实体数据已更改
            complexFoodBlockEntity.markDirty();
        }
    }

    /**
     * 重写获取掉落物的方法，确保每个掉落物都带有正确的NBT数据
     * @param state 方块状态
     * @param builder 战利品上下文参数构建器
     * @return 掉落物列表
     */
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        int foodCount = state.get(NUMBER_OF_FOOD);

        // 当数量为0时，不返回任何物品
        if (foodCount <= 0) {
            return Collections.emptyList();
        }

        // 获取方块实体
        BlockEntity blockEntity = builder.get(LootContextParameters.BLOCK_ENTITY);

        if (blockEntity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            List<ItemStack> droppedStacks = new ArrayList<>();

            // 根据先进后出的顺序获取所有NBT数据并创建物品堆栈
            for (int i = 0; i < foodCount; i++) {
                ItemStack stack = createStack(1, complexFoodBlockEntity);
                droppedStacks.add(stack);
            }

            return droppedStacks;
        }

        // 如果没有方块实体，返回默认物品
        return Collections.singletonList(new ItemStack(this.asItem(), foodCount));
    }
}