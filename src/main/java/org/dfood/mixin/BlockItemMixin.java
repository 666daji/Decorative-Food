package org.dfood.mixin;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.dfood.util.ModUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin {

    /**
     *阻止玩家在站立状态下放置食物方块，
     * 解决了放置与食用的冲突问题。
     */
    @Inject(method = "useOnBlock", at = @At("HEAD"), cancellable = true)
    private static void useOnBlockMixin(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir){
        PlayerEntity player = context.getPlayer();
        Item item = context.getStack().getItem();
        BlockPos pos = context.getBlockPos();
        World world = context.getWorld();
        if (player != null && !player.isSneaking() && ModUtils.isModFoodItem(item) && !ModUtils.isCropCanPlaceAt(item, pos, world)) {
            cir.setReturnValue(ActionResult.PASS);
        }
    }
}
