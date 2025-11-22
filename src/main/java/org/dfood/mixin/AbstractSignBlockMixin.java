package org.dfood.mixin;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.block.ModAbstractSignBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * 告示牌物品悬挂功能混入类
 */
@Mixin(AbstractSignBlock.class)
public abstract class AbstractSignBlockMixin extends BlockWithEntity implements Waterloggable {

    protected AbstractSignBlockMixin(Settings settings) {
        super(settings);
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        if (!ModAbstractSignBlock.isValidSign(state)) {
            super.onBreak(world, pos, state, player);
            return;
        }

        if (!world.isClient) {
            ModAbstractSignBlock.handleSignBreak(world, pos, player);
        }

        super.onBreak(world, pos, state, player);
    }

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void dFood$onUse(BlockState state, World world, BlockPos pos,
                             PlayerEntity player, Hand hand, BlockHitResult hit,
                             CallbackInfoReturnable<ActionResult> cir) {

        if (!ModAbstractSignBlock.isValidSign(state) || world.isClient) {
            return;
        }

        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (!(blockEntity instanceof SignBlockEntity signBlockEntity)) {
            return;
        }

        // 尝试悬挂物品
        if (ModAbstractSignBlock.tryHangItem(world, pos, state, player, signBlockEntity)) {
            cir.setReturnValue(ActionResult.SUCCESS);
        }
    }
}