package org.dfood.util;

import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import org.dfood.block.FoodBlocks;
import org.dfood.item.ModSuspiciousItem;

import java.util.HashMap;
import java.util.Map;

public class StewToBlocks {
    public static final Map<String, Item> stewMap = new HashMap<>();

    static {
        // 炖菜类
        stewMap.put("rabbit_stew", new BlockItem(FoodBlocks.RABBIT_STEW, new Item.Settings().food(FoodComponents.RABBIT_STEW).maxCount(1)));
        stewMap.put("mushroom_stew", new BlockItem(FoodBlocks.MUSHROOM_STEW, new Item.Settings().food(FoodComponents.MUSHROOM_STEW).maxCount(1)));
        stewMap.put("beetroot_soup", new BlockItem(FoodBlocks.BEETROOT_SOUP, new Item.Settings().food(FoodComponents.BEETROOT_SOUP).maxCount(1)));
        stewMap.put("suspicious_stew", new ModSuspiciousItem(FoodBlocks.SUSPICIOUS_STEW, new Item.Settings().food(FoodComponents.SUSPICIOUS_STEW).maxCount(1)));
    }
}
