package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.*;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModEnchantedGoldenAppleItem extends EnchantedGoldenAppleItem implements HaveBlock {
    private final Block block;

    public ModEnchantedGoldenAppleItem(Settings settings, Block block) {
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

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        appendBlockTooltip(stack, world, tooltip, context);
    }
}
