package org.dfood.item;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import java.util.Map;

public interface HaveBlock {
    Block getBlock();

    default void appendBlocks(Map<Block, Item> map, Item item) {
        map.put(this.getBlock(), item);
    }
}
