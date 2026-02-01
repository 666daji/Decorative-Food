package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.ComplexFoodBlock;
import org.dfood.util.DFoodUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 复杂食物方块对应的方块实体，用于按栈结构（先进后出）存储物品堆栈的 NBT 数据。
 * <p>
 * 每个 {@link ComplexFoodBlock} 实例对应一个此实体，负责在放置和取出物品时保持 NBT 一致性。
 *
 * @see ComplexFoodBlock
 */
public class ComplexFoodBlockEntity extends BlockEntity {

    /**
     * 存储 NBT 数据的栈结构，底层使用 {@link NbtList} 便于序列化。
     */
    protected final Stack<NbtCompound> nbtStack = new Stack<>();

    /**
     * 最大存储容量，由对应的 {@link ComplexFoodBlock} 定义。
     */
    protected int maxCapacity;

    public ComplexFoodBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.COMPLEX_FOOD, pos, state);
    }

    public ComplexFoodBlockEntity(BlockEntityType<? extends ComplexFoodBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        // 从方块状态中获取最大容量
        if (state.getBlock() instanceof ComplexFoodBlock complexFoodBlock) {
            this.maxCapacity = complexFoodBlock.MAX_FOOD;
        }
    }

    /**
     * 将物品堆栈的 NBT 数据压入栈中。
     *
     * @param nbt 要保存的 NBT 数据，若为 {@code null} 则保存空的 {@link NbtCompound}
     * @return {@code true} 如果成功压入，{@code false} 如果栈已满
     */
    public boolean pushNbt(@Nullable NbtCompound nbt) {
        if (nbtStack.size() >= maxCapacity) {
            return false;
        }

        if (nbt == null) {
            nbt = new NbtCompound();
        }

        nbtStack.push(nbt);
        markDirty();
        return true;
    }

    /**
     * 从栈中弹出 NBT 数据（先进后出）。
     *
     * @return 栈顶的 NBT 数据，若栈为空则返回 {@code null}
     */
    @Nullable
    public NbtCompound popNbt() {
        if (nbtStack.isEmpty()) {
            return null;
        }

        NbtCompound popped = nbtStack.pop();
        markDirty();
        return popped;
    }

    /**
     * 查看栈顶的 NBT 数据但不移除。
     *
     * @return 栈顶的 NBT 数据，若栈为空则返回 {@code null}
     */
    @Nullable
    public NbtCompound peekNbt() {
        return nbtStack.isEmpty() ? null : nbtStack.peek();
    }

    /**
     * 获取当前栈中 NBT 数据的数量。
     */
    public int getNbtCount() {
        return nbtStack.size();
    }

    /**
     * 检查栈是否已满。
     */
    public boolean isFull() {
        return nbtStack.size() >= maxCapacity;
    }

    /**
     * 检查栈是否为空。
     */
    public boolean isEmpty() {
        return nbtStack.isEmpty();
    }

    /**
     * 清空栈中的所有 NBT 数据。
     */
    public void clearNbt() {
        nbtStack.clear();
        markDirty();
    }

    /**
     * 序列化方块实体数据到 NBT。
     */
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        NbtList nbtList = new NbtList();
        nbtList.addAll(nbtStack);

        nbt.put("NbtStack", nbtList);
        nbt.putInt("MaxCapacity", maxCapacity);
    }

    /**
     * 从 NBT 反序列化方块实体数据。
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        nbtStack.clear();

        if (nbt.contains("NbtStack", 9)) {
            NbtList nbtList = nbt.getList("NbtStack", 10);
            for (int i = 0; i < nbtList.size(); i++) {
                nbtStack.push(nbtList.getCompound(i));
            }
        }

        if (nbt.contains("MaxCapacity", 3)) {
            maxCapacity = nbt.getInt("MaxCapacity");
        }
    }

    /**
     * 获取栈中所有 NBT 数据的副本（从栈底到栈顶）。
     */
    public List<NbtCompound> getAllNbt() {
        return new ArrayList<>(nbtStack);
    }

    /**
     * 获取用于预览的所有存储物品堆栈，每个物品堆栈数量为1.
     *
     * @return 添加的所有原物品堆栈
     */
    public List<ItemStack> getAllStack() {
        int count = DFoodUtils.getFoodBlockCount(getCachedState());
        List<ItemStack> stacks = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            ItemStack stack = new ItemStack(getCachedState().getBlock().asItem());
            stack.setNbt(getNbtAt(i));
            stacks.add(stack);
        }

        return stacks;
    }

    /**
     * 获取指定索引处的 NBT 数据。
     *
     * @param index 索引位置，0 为栈底，size-1 为栈顶
     * @return 指定位置的 NBT 数据，若索引越界则返回 {@code null}
     */
    @Nullable
    public NbtCompound getNbtAt(int index) {
        if (index < 0 || index >= nbtStack.size()) {
            return null;
        }
        return nbtStack.get(index);
    }
}