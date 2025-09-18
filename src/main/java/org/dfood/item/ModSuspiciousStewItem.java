package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ModSuspiciousStewItem extends BlockItem {

    public ModSuspiciousStewItem(Block block, Settings settings) {
        super(block, settings);
    }

    private static void forEachEffect(ItemStack stew, Consumer<StatusEffectInstance> effectConsumer) {
        NbtCompound nbtCompound = stew.getNbt();
        if (nbtCompound != null && nbtCompound.contains("Effects", NbtElement.LIST_TYPE)) {
            NbtList nbtList = nbtCompound.getList("Effects", NbtElement.COMPOUND_TYPE);

            for (int i = 0; i < nbtList.size(); i++) {
                NbtCompound nbtCompound2 = nbtList.getCompound(i);
                int j;
                if (nbtCompound2.contains("EffectDuration", NbtElement.NUMBER_TYPE)) {
                    j = nbtCompound2.getInt("EffectDuration");
                } else {
                    j = 160;
                }

                StatusEffect statusEffect = StatusEffect.byRawId(nbtCompound2.getInt("EffectId"));
                if (statusEffect != null) {
                    effectConsumer.accept(new StatusEffectInstance(statusEffect, j));
                }
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        if (context.isCreative()) {
            List<StatusEffectInstance> list = new ArrayList<>();
            forEachEffect(stack, list::add);
            PotionUtil.buildTooltip(list, tooltip, 1.0F);
        }
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        ItemStack itemStack = super.finishUsing(stack, world, user);
        forEachEffect(itemStack, user::addStatusEffect);
        return user instanceof PlayerEntity && ((PlayerEntity)user).getAbilities().creativeMode ? itemStack : new ItemStack(Items.BOWL);
    }
}
