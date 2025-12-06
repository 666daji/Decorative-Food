package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.ComplexFoodBlock;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 复杂食物方块的方块实体，负责存储物品堆栈的NBT数据。
 * 使用栈结构（先进后出）保存NBT数据，确保取出顺序与放置顺序相反。
 *
 * @see ComplexFoodBlock
 */
public class ComplexFoodBlockEntity extends BlockEntity {

    /**
     * 存储NBT数据的栈结构
     * 使用NbtList作为底层存储，便于序列化和反序列化
     */
    private final Stack<NbtCompound> nbtStack = new Stack<>();

    /**
     * 最大容量，由对应的ComplexFoodBlock定义
     */
    private int maxCapacity;

    public ComplexFoodBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.COMPLEX_FOOD, pos, state);

        // 从方块状态中获取最大容量
        if (state.getBlock() instanceof ComplexFoodBlock complexFoodBlock) {
            this.maxCapacity = complexFoodBlock.MAX_FOOD;
        }
    }

    /**
     * 将物品堆栈的NBT数据压入栈中
     * @param nbt 要保存的NBT数据，如果为null则保存空的NbtCompound
     * @return 如果成功压入返回true，如果栈已满返回false
     */
    public boolean pushNbt(@Nullable NbtCompound nbt) {
        if (nbtStack.size() >= maxCapacity) {
            return false; // 栈已满
        }

        if (nbt == null) {
            nbt = new NbtCompound();
        }

        nbtStack.push(nbt);
        markDirty(); // 标记数据已更改
        return true;
    }

    /**
     * 从栈中弹出NBT数据（先进后出）
     * @return 栈顶的NBT数据，如果栈为空则返回null
     */
    @Nullable
    public NbtCompound popNbt() {
        if (nbtStack.isEmpty()) {
            return null;
        }

        NbtCompound popped = nbtStack.pop();
        markDirty(); // 标记数据已更改
        return popped;
    }

    /**
     * 查看栈顶的NBT数据但不移除
     * @return 栈顶的NBT数据，如果栈为空则返回null
     */
    @Nullable
    public NbtCompound peekNbt() {
        return nbtStack.isEmpty() ? null : nbtStack.peek();
    }

    /**
     * 获取当前栈中NBT数据的数量
     * @return 栈中元素的数量
     */
    public int getNbtCount() {
        return nbtStack.size();
    }

    /**
     * 检查栈是否已满
     * @return 如果栈已满返回true，否则返回false
     */
    public boolean isFull() {
        return nbtStack.size() >= maxCapacity;
    }

    /**
     * 检查栈是否为空
     * @return 如果栈为空返回true，否则返回false
     */
    public boolean isEmpty() {
        return nbtStack.isEmpty();
    }

    /**
     * 清空栈中的所有NBT数据
     */
    public void clearNbt() {
        nbtStack.clear();
        markDirty();
    }

    /**
     * 序列化方块实体数据到NBT
     * @param nbt 要写入的NBT标签
     */
    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);

        // 将NBT栈序列化为NbtList
        NbtList nbtList = new NbtList();
        nbtList.addAll(nbtStack);

        nbt.put("NbtStack", nbtList);
        nbt.putInt("MaxCapacity", maxCapacity);
    }

    /**
     * 从NBT反序列化方块实体数据
     * @param nbt 要读取的NBT标签
     */
    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        // 清空当前栈
        nbtStack.clear();

        // 从NbtList反序列化NBT栈
        if (nbt.contains("NbtStack", 9)) {
            NbtList nbtList = nbt.getList("NbtStack", 10);

            for (int i = 0; i < nbtList.size(); i++) {
                NbtCompound compound = nbtList.getCompound(i);
                nbtStack.push(compound);
            }
        }

        // 读取最大容量
        if (nbt.contains("MaxCapacity", 3)) {
            maxCapacity = nbt.getInt("MaxCapacity");
        }
    }

    /**
     * 获取栈中所有NBT数据的副本
     * @return 包含所有NBT数据的列表（从栈底到栈顶）
     */
    public List<NbtCompound> getAllNbt() {
        return new ArrayList<>(nbtStack);
    }

    /**
     * 获取指定索引处的NBT数据（0为栈底，size-1为栈顶）
     * @param index 索引位置
     * @return 指定位置的NBT数据，如果索引越界则返回null
     */
    @Nullable
    public NbtCompound getNbtAt(int index) {
        if (index < 0 || index >= nbtStack.size()) {
            return null;
        }

        return nbtStack.get(index);
    }
}