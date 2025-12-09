package org.dfood.mixin.client;

import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import org.dfood.util.DFoodUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {

    /**
     *与服务端的ServerPlayerInteractionManagerMixin同步
     * @see org.dfood.mixin.ServerPlayerInteractionManagerMixin
     */
    @Inject(method = "interactBlockInternal", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;shouldCancelInteraction()Z"), cancellable = true)
    private void interactBlockMixin(ClientPlayerEntity player, Hand hand, BlockHitResult hitResult, CallbackInfoReturnable<ActionResult> cir) {
        BlockPos blockPos = hitResult.getBlockPos();
        BlockState blockState = Objects.requireNonNull(((ClientPlayerInteractionManagerAccessor) this).getClient().world).getBlockState(blockPos);
        boolean bl3 = DFoodUtils.isModFoodBlock(blockState.getBlock());
        if (bl3) {
            ActionResult actionResult = blockState.onUse(((ClientPlayerInteractionManagerAccessor) this).getClient().world, player, hand, hitResult);
            if (actionResult.isAccepted()) {
                cir.setReturnValue(actionResult);
            }
        }
    }
}
