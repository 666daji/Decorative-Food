package org.dfood.item;

import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;

public class Seeds {
    public static final Item SWEET_BERRIES_SEED = register("sweet_berries_seed", new BlockItem(Blocks.SWEET_BERRY_BUSH, new Item.Settings().useItemPrefixedTranslationKey().registryKey(keyOf("sweet_berries_seed"))));
    public static final Item GLOW_BERRIES_SEED = register("glow_berries_seed", new BlockItem(Blocks.CAVE_VINES, new Item.Settings().useItemPrefixedTranslationKey().registryKey(keyOf("glow_berries_seed"))));

    private static Item register(String id, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(ThreedFood.MOD_ID, id), item);
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.of(ThreedFood.MOD_ID, id));
    }

    public static void registerItems() {
        // This method can be used to trigger the registration of items if needed
        // Currently, items are registered statically in the class.
    }
}
