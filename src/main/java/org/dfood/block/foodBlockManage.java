package org.dfood.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class foodBlockManage {
    private final AbstractBlock.Settings settings;
    private final int maxFood;
    private Block block;
    @Nullable
    private foodBlock.CROPS crops;

    public foodBlockManage(AbstractBlock.Settings settings, int maxFood) {
        this.settings = settings;
        this.maxFood = maxFood;
    }

    public foodBlockManage(AbstractBlock.Settings settings, int maxFood, @Nullable foodBlock.CROPS crops) {
        this(settings, maxFood);
        this.crops = crops;
    }

    public Block createBlock(String id) {
        if (this.block == null) {
            RegistryKey<Block> registryKey = keyOf(id);
            if (this.crops != null) {
                this.block = new foodBlock(settings.registryKey(registryKey), maxFood, this.crops);
            }else {
                this.block = id.equals("chorus_fruit")?
                        new ChorusFruitBlock(settings.registryKey(registryKey), maxFood) :
                        new foodBlock(settings.registryKey(registryKey), maxFood);
            }
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
