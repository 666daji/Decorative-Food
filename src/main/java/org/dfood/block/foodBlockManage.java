package org.dfood.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class foodBlockManage {
    private final AbstractBlock.Settings settings;
    private final int maxFood;
    private Block block;

    public foodBlockManage(AbstractBlock.Settings settings, int maxFood) {
        this.settings = settings;
        this.maxFood = maxFood;
    }

    public Block createBlock(String id) {
        if (this.block == null) {
            RegistryKey<Block> registryKey = keyOf(id);
            this.block = id.equals("chorus_fruit")?
                    new ChorusFruitBlock(settings.registryKey(registryKey), maxFood) :
                    new foodBlock(settings.registryKey(registryKey), maxFood);
        }
        return this.block;
    }

    public Block getBlock() {
        return this.block;
    }

    private static RegistryKey<Block> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.BLOCK, Identifier.ofVanilla(id));
    }
}
