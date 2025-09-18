package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.block.entity.SuspiciousStewBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class SuspiciousStewBlock extends foodBlock implements BlockEntityProvider {

    public SuspiciousStewBlock(Settings settings, int max_food) {
        super(settings, max_food);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new SuspiciousStewBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        if (!world.isClient) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity) {
                NbtCompound stackNbt = itemStack.getNbt();
                if (stackNbt != null) {
                    suspiciousStewBlockEntity.readCustomDataFromItem(stackNbt);
                }
            }
        }
    }

    @Override
    public boolean isSame(ItemStack stack, BlockEntity blockEntity) {
        if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity && super.isSame(stack, blockEntity)) {
            NbtCompound stewStackNbt = suspiciousStewBlockEntity.getStewStack().getNbt();
            NbtCompound stackNbt = stack.getNbt();

            // 检查两个NBT是否都包含效果列表
            if (stackNbt != null && stewStackNbt != null &&
                    stackNbt.contains("Effects") && stewStackNbt.contains("Effects")) {
                // 比较效果列表是否相同
                return Objects.equals(stewStackNbt.get("Effects"), stackNbt.get("Effects"));
            }
            // 如果其中一个没有效果，则认为它们不匹配
            return false;
        }
        return false;
    }

    @Override
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW, count);

        if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity) {
            // 将方块实体中的效果写入物品NBT
            NbtCompound nbt = stack.getOrCreateNbt();
            suspiciousStewBlockEntity.writeCustomDataToItem(nbt);
        }

        return stack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);

        if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity) {
            int count = state.get(NUMBER_OF_FOOD);
            ItemStack baseStack = suspiciousStewBlockEntity.getStewStack();
            List<ItemStack> drops = new java.util.ArrayList<>();

            for (int i = 0; i < count; i++) {
                drops.add(baseStack.copy());
            }

            return drops;
        }

        // 没有方块实体时，返回空列表
        return List.of();
    }
}