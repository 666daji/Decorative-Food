package org.dfood.util;

import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.Item;
import org.dfood.block.foodBlocks;
import org.dfood.item.ModStewItem;

import java.util.HashMap;
import java.util.Map;

public class StewToBlocks {
    public static final Map<String, Item> stewMap = new HashMap<>();

    static {
        // 炖菜类
        stewMap.put("rabbit_stew", new ModStewItem(foodBlocks.RABBIT_STEW, new Item.Settings().food(FoodComponents.RABBIT_STEW)));
        stewMap.put("mushroom_stew", new ModStewItem(foodBlocks.MUSHROOM_STEW, new Item.Settings().food(FoodComponents.MUSHROOM_STEW)));
        stewMap.put("beetroot_soup", new ModStewItem(foodBlocks.BEETROOT_SOUP, new Item.Settings().food(FoodComponents.BEETROOT_SOUP)));
    }
}
