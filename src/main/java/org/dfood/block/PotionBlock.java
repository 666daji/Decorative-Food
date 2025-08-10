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
import net.minecraft.potion.PotionUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.block.entity.PotionBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class PotionBlock extends foodBlock implements BlockEntityProvider {
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
            NbtCompound stackNbt = itemStack.getNbt();
            if (stackNbt != null) {
                potionBlockEntity.readCustomDataFromItem(stackNbt);
            }
        }
    }

    @Override
    public boolean isSame(ItemStack stack, BlockEntity blockEntity) {
        if (blockEntity instanceof PotionBlockEntity potionBlockEntity && super.isSame(stack, blockEntity)) {
            NbtCompound potionStackNbt = potionBlockEntity.getPotionStack().getNbt();
            NbtCompound stackNbt = stack.getNbt();
            if (stackNbt != null && potionStackNbt != null && stackNbt.contains("Potion") && potionStackNbt.contains("Potion")) {
                return Objects.equals(potionStackNbt.getString("Potion"), stackNbt.getString("Potion"));
            }
        }
        return false;
    }

    @Override
    public ItemStack createStack(int count, @Nullable BlockEntity blockEntity) {
        ItemStack stack = new ItemStack(Items.POTION, count);
        if (blockEntity instanceof PotionBlockEntity potionBlockEntity) {
            return PotionUtil.setPotion(stack, potionBlockEntity.getPotion());
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
