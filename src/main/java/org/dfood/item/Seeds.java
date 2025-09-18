package org.dfood.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;

public class Seeds {
    public static final Item SWEET_BERRIES_SEED = register("sweet_berries_seed", new AliasedBlockItem(Blocks.SWEET_BERRY_BUSH, new Item.Settings()));
    public static final Item GLOW_BERRIES_SEED = register("glow_berries_seed", new AliasedBlockItem(Blocks.CAVE_VINES, new Item.Settings()));

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(ThreedFood.MOD_ID, id), item);
    }

    public static void registerItems() {
        // This method can be used to trigger the registration of items if needed
        // Currently, items are registered statically in the class.
    }
}
