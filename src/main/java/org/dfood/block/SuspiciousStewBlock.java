package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootWorldContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.block.entity.SuspiciousStewBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuspiciousStewBlock extends FoodBlock implements BlockEntityProvider {

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
                SuspiciousStewEffectsComponent stewEffect = itemStack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
                if (stewEffect != null) {
                    suspiciousStewBlockEntity.readCustomDataFromItem(stewEffect);
                }
            }
        }
    }

    @Override
    public boolean isSame(ItemStack stack, BlockEntity blockEntity) {
        if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity && super.isSame(stack, blockEntity)) {
            SuspiciousStewEffectsComponent blockEntityEffects = suspiciousStewBlockEntity.createStewEffectsComponent();
            SuspiciousStewEffectsComponent stackEffects = stack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);

            // 如果都没有效果，认为是相同的
            if (blockEntityEffects == SuspiciousStewEffectsComponent.DEFAULT && stackEffects == null) {
                return true;
            }

            // 如果只有一个有效果，认为不相同
            if (blockEntityEffects == SuspiciousStewEffectsComponent.DEFAULT || stackEffects == null) {
                return false;
            }

            // 比较效果列表是否相同
            return blockEntityEffects.equals(stackEffects);
        }
        return false;
    }

    @Override
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW, count);

        if (blockEntity instanceof SuspiciousStewBlockEntity suspiciousStewBlockEntity) {
            // 将方块实体中的效果写入物品组件
            SuspiciousStewEffectsComponent component = suspiciousStewBlockEntity.createStewEffectsComponent();
            if (component != SuspiciousStewEffectsComponent.DEFAULT) {
                stack.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, component);
            }
        }

        return stack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootWorldContext.Builder builder) {
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