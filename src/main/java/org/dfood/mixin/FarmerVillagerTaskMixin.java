package org.dfood.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.ai.brain.task.FarmerVillagerTask;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.event.GameEvent;
import org.dfood.ThreedFood;
import org.dfood.item.Seeds;
import org.dfood.tag.ModTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmerVillagerTask.class)
public class FarmerVillagerTaskMixin {

    @Shadow private @Nullable BlockPos currentTarget;

    @Inject(method = "keepRunning(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/entity/passive/VillagerEntity;J)V",
            at = @At("HEAD"))
    private void FoodToSeed(ServerWorld serverWorld, VillagerEntity villagerEntity, long l, CallbackInfo ci) {
        SimpleInventory simpleInventory = villagerEntity.getInventory();
        for (int i = 0; i < simpleInventory.size(); i++) {
            ItemStack itemStack = simpleInventory.getStack(i);
            ThreedFood.LOGGER.info(itemStack.getItem().toString());
        if (itemStack.getItem().equals(Items.CARROT)) {
                ItemStack itemStack1 = new ItemStack(Seeds.CARROT_SEED, itemStack.getCount());
                simpleInventory.setStack(i, itemStack1);
                ThreedFood.LOGGER.info("替换成功");
            }
        if (itemStack.getItem().equals(Items.POTATO)) {
                ItemStack itemStack1 = new ItemStack(Items.POISONOUS_POTATO, itemStack.getCount());
                simpleInventory.setStack(i, itemStack1);
            }
        }
        for (int i = 0; i < simpleInventory.size(); i++) {
            ItemStack itemStack = simpleInventory.getStack(i);
            boolean bl = false;
            if (!itemStack.isEmpty() && itemStack.isIn(ModTags.SEEDS) && itemStack.getItem() instanceof BlockItem blockItem) {
                BlockState blockState2 = blockItem.getBlock().getDefaultState();
                serverWorld.setBlockState(currentTarget, blockState2);
                serverWorld.emitGameEvent(GameEvent.BLOCK_PLACE, this.currentTarget, GameEvent.Emitter.of(villagerEntity, blockState2));
                bl = true;
            }
            if (bl) {
                serverWorld.playSound(
                        null, this.currentTarget.getX(), this.currentTarget.getY(), this.currentTarget.getZ(), SoundEvents.ITEM_CROP_PLANT, SoundCategory.BLOCKS, 1.0F, 1.0F
                );
                itemStack.decrement(1);
                if (itemStack.isEmpty()) {
                    simpleInventory.setStack(i, ItemStack.EMPTY);
                }
                break;
            }
        }
    }
}
