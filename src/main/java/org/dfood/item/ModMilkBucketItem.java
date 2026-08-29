package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.item.*;
import net.minecraft.util.ActionResult;

public class ModMilkBucketItem extends MilkBucketItem implements HaveBlock {
    private final Block block;

    public ModMilkBucketItem(Settings settings, Block block) {
        super(settings);
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (super.useOnBlock(context) != ActionResult.PASS || shouldPassToVanilla(context)) {
            return ActionResult.PASS;
        }
        return placeOrConsume(context, this::isFood, c -> this.use(c.getWorld(), c.getPlayer(), c.getHand()));
    }
}
