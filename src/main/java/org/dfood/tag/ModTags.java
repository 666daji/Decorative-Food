package org.dfood.tag;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;

public class ModTags {
    public static final TagKey<Block> FOOD_PLACE = ofBlock("food_place");
    public static final TagKey<Block> FOOD_BLOCK = ofBlock("food_block");

    public static final TagKey<Item> SEEDS = ofItem("seeds");

    private static TagKey<Block> ofBlock(String id) {
        return TagKey.of(RegistryKeys.BLOCK, Identifier.of(ThreedFood.MOD_ID, id));
    }

    private static TagKey<Item> ofItem(String id) {
        return TagKey.of(RegistryKeys.ITEM, Identifier.of(ThreedFood.MOD_ID, id));
    }
}
