package org.dfood.item;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;

public class ModItemGroups {
    public static void registerItemGroups() {
        Registry.register(Registries.ITEM_GROUP,new Identifier(ThreedFood.MOD_ID,"dfoodseed"),
                ItemGroup.create(ItemGroup.Row.TOP,-1)
                .displayName(Text.translatable("itemGroup.dfoodseed"))
                .icon(() -> new ItemStack(Seeds.SWEET_BERRIES_SEED))
                .entries((displayContext, entries) ->{
                    entries.add(Seeds.GLOW_BERRIES_SEED);
                    entries.add(Seeds.SWEET_BERRIES_SEED);
            }).build()
        );
    }
}
