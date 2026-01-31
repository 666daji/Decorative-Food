package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.dfood.util.DFoodUtils;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModGlassBottleItem extends GlassBottleItem implements HaveBlock {
    private final Block block;

    public ModGlassBottleItem(Block block, Settings settings) {
        super(settings);
        this.block = block;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        PlayerEntity player = context.getPlayer();
        Item item = context.getStack().getItem();
        // 仅当父类方法失败时才尝试放置方块
        if (super.useOnBlock(context) != ActionResult.PASS || (player != null && !player.isSneaking() && DFoodUtils.isModFoodItem(item))){
            return ActionResult.PASS;
        }
        ActionResult actionResult = this.place(new ItemPlacementContext(context));
        if (!actionResult.isAccepted() && this.isFood()) {
            ActionResult actionResult2 = this.use(context.getWorld(), context.getPlayer(), context.getHand()).getResult();
            return actionResult2 == ActionResult.CONSUME ? ActionResult.CONSUME_PARTIAL : actionResult2;
        } else {
            return actionResult;
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        this.getBlock().appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Block getBlock() {
        return this.block;
    }

    @Override
    public FeatureSet getRequiredFeatures() {
        return this.getBlock().getRequiredFeatures();
    }
}
