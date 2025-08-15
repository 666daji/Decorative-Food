package org.dfood.block;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKey;

public class PotionBlockManage extends foodBlockManage{
    public PotionBlockManage(AbstractBlock.Settings settings, int maxFood) {
        super(settings, maxFood);
    }

    @Override
    public Block createBlock(String id) {
        if (this.getBlock() == null) {
            RegistryKey<Block> registryKey = keyOf(id);
            this.block = new PotionBlock(settings.registryKey(registryKey), maxFood);
        }
        return this.getBlock();
    }
}
