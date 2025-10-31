package org.dfood.tag;

import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.dfood.ThreedFood;

public class ModTags {
    public static final TagKey<Block> FOOD_PLACE = of("food_place");

    private static TagKey<Block> of(String id) {
        return TagKey.of(RegistryKeys.BLOCK, new Identifier(ThreedFood.MOD_ID, id));
    }
}
