package org.dfood.block;

import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.block.entity.PotionBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class PotionBlock extends FoodBlock implements BlockEntityProvider {
    public PotionBlock(Settings settings, int max_food) {
        super(settings, max_food);
    }

    @Override
    public @Nullable BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new PotionBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack itemStack) {
        super.onPlaced(world, pos, state, placer, itemStack);
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
            PotionContentsComponent potionContents = itemStack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContents != null) {
                potionBlockEntity.readCustomDataFromItem(potionContents);
            }
        }
    }

    @Override
    public boolean isSame(ItemStack stack, BlockEntity blockEntity) {
        if (blockEntity instanceof PotionBlockEntity potionBlockEntity && super.isSame(stack, blockEntity)) {
            PotionContentsComponent potion = potionBlockEntity.getPotionStack().get(DataComponentTypes.POTION_CONTENTS);
            PotionContentsComponent stackComponent = stack.get(DataComponentTypes.POTION_CONTENTS);
            if (stackComponent != null && potion != null) {
                return Objects.equals(potion, stackComponent);
            }
        }
        return false;
    }

    @Override
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        ItemStack stack = new ItemStack(this.asItem(), count);
        if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
            stack.set(DataComponentTypes.POTION_CONTENTS, potionBlockEntity.getPotionContents());
            return stack;
        }
        return stack;
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContextParameterSet.Builder builder) {
        BlockEntity blockEntity = builder.getOptional(LootContextParameters.BLOCK_ENTITY);

        if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
            int count = state.get(NUMBER_OF_FOOD);
            ItemStack baseStack = potionBlockEntity.getPotionStack();
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
