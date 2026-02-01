package org.dfood.jade;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec2f;
import org.dfood.ThreedFood;
import org.dfood.block.entity.ComplexFoodBlockEntity;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;

import java.util.List;

public class ComplexFoodBlockComponentProvider implements IBlockComponentProvider {
    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        BlockEntity entity = blockAccessor.getBlockEntity();
        if (entity instanceof ComplexFoodBlockEntity complexFoodBlockEntity) {
            List<ItemStack> content = complexFoodBlockEntity.getAllStack();
            IElementHelper elements = IElementHelper.get();

            // 显示详细的物品列表
            content.forEach(stack -> {
                IElement icon = elements.item(stack, 0.5f).size(new Vec2f(10, 10)).translate(new Vec2f(0, -1));
                icon.message(null);
                iTooltip.add(icon);
                iTooltip.append(stack.getName());
            });
        }
    }

    @Override
    public Identifier getUid() {
        return new Identifier(ThreedFood.MOD_ID, "complex_food_block");
    }
}
