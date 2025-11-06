package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class ModSuspiciousItem extends BlockItem {

    public ModSuspiciousItem(Block block, Settings settings) {
        super(block, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip, TooltipType type) {
        super.appendTooltip(stack, context, tooltip, type);
        if (type.isCreative()) {
            List<StatusEffectInstance> list = new ArrayList();
            SuspiciousStewEffectsComponent suspiciousStewEffectsComponent = stack.getOrDefault(
                    DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffectsComponent.DEFAULT
            );

            for (SuspiciousStewEffectsComponent.StewEffect stewEffect : suspiciousStewEffectsComponent.effects()) {
                list.add(stewEffect.createStatusEffectInstance());
            }

            PotionContentsComponent.buildTooltip(list, tooltip::add, 1.0F, context.getUpdateTickRate());
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        SuspiciousStewEffectsComponent suspiciousStewEffectsComponent = stack.getOrDefault(
                DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffectsComponent.DEFAULT
        );

        for (SuspiciousStewEffectsComponent.StewEffect stewEffect : suspiciousStewEffectsComponent.effects()) {
            user.addStatusEffect(stewEffect.createStatusEffectInstance());
        }

        return super.finishUsing(stack, world, user);
    }
}
