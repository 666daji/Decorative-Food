package org.dfood.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;
import org.dfood.block.ModTotemBlock;
import org.dfood.util.DFoodUtils;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

    public BlockItemMixin(Settings settings) {
        super(settings);
    }

    @Shadow
    @Nullable
    protected abstract BlockState getPlacementState(ItemPlacementContext context);

    @Shadow
    public abstract Block getBlock();

    /**
     *阻止玩家在站立状态下放置食物方块，
     * 解决了放置与食用的冲突问题。
     */
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        BlockItem mySelf = ((BlockItem)(Object)this);

        if (DFoodUtils.isModFoodItem(mySelf) || mySelf.equals(Items.TOTEM_OF_UNDYING)) {
            PlayerEntity player = context.getPlayer();
            ItemPlacementContext placementContext = new ItemPlacementContext(context);
            BlockState expectedState = getPlacementState(placementContext);

            if (expectedState != null && player != null &&
                    !player.isSneaking() && (DFoodUtils.isModFoodBlock(expectedState.getBlock()) || expectedState.getBlock() instanceof ModTotemBlock)) {
                cir.setReturnValue(ActionResult.PASS);
            }
        }
    }
}
