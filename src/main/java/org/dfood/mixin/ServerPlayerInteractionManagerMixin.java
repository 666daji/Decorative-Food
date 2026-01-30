package org.dfood.mixin;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.util.DFoodUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerInteractionManager.class)
public class ServerPlayerInteractionManagerMixin {

    /**
     *该类是为了优化食物放置手感而设计的，
     * 即使玩家下蹲也能与食物方块交互。
     */
    @Inject(method = "interactBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;copy()Lnet/minecraft/item/ItemStack;"), cancellable = true)
    private void interactBlockMixin(ServerPlayerEntity player, World world, ItemStack stack, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir){
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        boolean bl3 = DFoodUtils.isModFoodBlock(blockState.getBlock());
        if (bl3 && player.isSneaking()) {
            ActionResult actionResult = blockState.onUse(world, player, hand, hitResult);
            ItemStack itemStack = stack.copy();
            if (actionResult.isAccepted()) {
                Criteria.ITEM_USED_ON_BLOCK.trigger(player, blockPos, itemStack);
                cir.setReturnValue(actionResult);
            }
        }
    }
}
