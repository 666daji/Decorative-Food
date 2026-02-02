package org.dfood.block.entity;

import com.mojang.serialization.DataResult;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentChanges;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.ComplexFoodBlock;
import org.dfood.util.DFoodUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 复杂食物方块对应的方块实体，用于按栈结构（先进后出）存储物品堆栈的组件更改数据（适用于1.21.1+版本）。
 * <p>
 * 每个 {@link ComplexFoodBlock} 实例对应一个此实体，负责在放置和取出物品时保持组件数据一致性。
 * 使用 {@link ComponentChanges} 代替传统的 NBT 数据存储。
 *
 * @see ComplexFoodBlock
 */
public class ComplexFoodBlockEntity extends BlockEntity {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComplexFoodBlockEntity.class);

    /**
     * 存储组件更改数据的栈结构。
     */
    protected final Stack<ComponentChanges> componentChangesStack = new Stack<>();

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
     * 将物品堆栈的组件更改数据压入栈中。
     *
     * @param componentChanges 要保存的组件更改数据，若为 {@code null} 则保存空的 {@link ComponentChanges#EMPTY}
     * @return {@code true} 如果成功压入，{@code false} 如果栈已满
     */
    public boolean pushComponentChanges(@Nullable ComponentChanges componentChanges) {
        if (componentChangesStack.size() >= maxCapacity) {
            return false;
        }

        if (componentChanges == null) {
            componentChanges = ComponentChanges.EMPTY;
        }

        componentChangesStack.push(componentChanges);
        markDirty();
        return true;
    }

    /**
     * 从栈中弹出组件更改数据（先进后出）。
     *
     * @return 栈顶的组件更改数据，若栈为空则返回 {@code null}
     */
    @Nullable
    public ComponentChanges popComponentChanges() {
        if (componentChangesStack.isEmpty()) {
            return null;
        }

        ComponentChanges popped = componentChangesStack.pop();
        markDirty();
        return popped;
    }

    /**
     * 查看栈顶的组件更改数据但不移除。
     *
     * @return 栈顶的组件更改数据，若栈为空则返回 {@code null}
     */
    @Nullable
    public ComponentChanges peekComponentChanges() {
        return componentChangesStack.isEmpty() ? null : componentChangesStack.peek();
    }

    /**
     * 获取当前栈中组件更改数据的数量。
     */
    public int getComponentChangesCount() {
        return componentChangesStack.size();
    }

    /**
     * 检查栈是否已满。
     */
    public boolean isFull() {
        return componentChangesStack.size() >= maxCapacity;
    }

    /**
     * 检查栈是否为空。
     */
    public boolean isEmpty() {
        return componentChangesStack.isEmpty();
    }

    /**
     * 清空栈中的所有组件更改数据。
     */
    public void clearComponentChanges() {
        componentChangesStack.clear();
        markDirty();
    }

    /**
     * 序列化方块实体数据到 NBT。
     * 使用 {@link ComponentChanges#CODEC} 将组件更改数据编码为 NBT。
     */
    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        NbtList componentChangesList = new NbtList();
        for (ComponentChanges changes : componentChangesStack) {
            // 使用ComponentChanges.CODEC编码为NBT
            DataResult<NbtElement> result = ComponentChanges.CODEC.encodeStart(NbtOps.INSTANCE, changes);

            result.result().ifPresent(componentChangesList::add);

            // 处理错误情况
            result.error().ifPresent(error -> {
                LOGGER.error("Failed to encode ComponentChanges: {}", error.message());
            });
        }

        nbt.put("ComponentChangesStack", componentChangesList);
        nbt.putInt("MaxCapacity", maxCapacity);
    }

    /**
     * 从 NBT 反序列化方块实体数据。
     * 使用 {@link ComponentChanges#CODEC} 将 NBT 解码为组件更改数据。
     */
    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        componentChangesStack.clear();

        if (nbt.contains("ComponentChangesStack", NbtElement.LIST_TYPE)) {
            NbtList componentChangesList = nbt.getList("ComponentChangesStack", NbtElement.COMPOUND_TYPE);
            for (int i = 0; i < componentChangesList.size(); i++) {
                DataResult<ComponentChanges> result = ComponentChanges.CODEC.parse(NbtOps.INSTANCE, componentChangesList.getCompound(i));

                result.result().ifPresent(componentChangesStack::push);

                // 处理错误情况
                result.error().ifPresent(error -> {
                    LOGGER.error("Failed to parse ComponentChanges: {}", error.message());
                });
            }
        }

        if (nbt.contains("MaxCapacity", NbtElement.INT_TYPE)) {
            maxCapacity = nbt.getInt("MaxCapacity");
        }
    }

    /**
     * 获取栈中所有组件更改数据的副本（从栈底到栈顶）。
     */
    public List<ComponentChanges> getAllComponentChanges() {
        return new ArrayList<>(componentChangesStack);
    }

    /**
     * 获取用于预览的所有存储物品堆栈，每个物品堆栈数量为1。
     *
     * @return 添加的所有原物品堆栈
     */
    public List<ItemStack> getAllStack() {
        int count = DFoodUtils.getFoodBlockCount(getCachedState());
        List<ItemStack> stacks = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            ItemStack stack = new ItemStack(getCachedState().getBlock().asItem());
            ComponentChanges changes = getComponentChangesAt(i);
            if (changes != null && !changes.isEmpty()) {
                stack.applyUnvalidatedChanges(changes);
            }
            stacks.add(stack);
        }

        return stacks;
    }

    /**
     * 获取指定索引处的组件更改数据。
     *
     * @param index 索引位置，0 为栈底，size-1 为栈顶
     * @return 指定位置的组件更改数据，若索引越界则返回 {@code null}
     */
    @Nullable
    public ComponentChanges getComponentChangesAt(int index) {
        if (index < 0 || index >= componentChangesStack.size()) {
            return null;
        }
        return componentChangesStack.get(index);
    }
}