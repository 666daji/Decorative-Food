package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.util.ActionResult;
import org.dfood.util.DFoodUtils;

public class ModChorusFruitItem extends ChorusFruitItem implements HaveBlock{
    private final Block block;

    public ModChorusFruitItem(Settings settings, Block block) {
        super(settings);
        this.block = block;
    }

    @Override
    public Block getBlock() {
        return block;
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
    public FeatureSet getRequiredFeatures() {
        return this.getBlock().getRequiredFeatures();
    }
}
