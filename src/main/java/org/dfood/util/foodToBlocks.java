package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.FoodComponents;
import net.minecraft.item.Item;
import net.minecraft.item.BlockItem;
import org.dfood.block.foodBlocks;
import org.dfood.item.ModStewItem;

import java.util.HashMap;
import java.util.Map;

public class foodToBlocks {
    public static final Map<String, Item> foodMap = new HashMap<>();

    static {
        // 零食类
        foodMap.put("cookie", getItem(foodBlocks.COOKIE, FoodComponents.COOKIE));
        foodMap.put("apple", getItem(foodBlocks.APPLE, FoodComponents.APPLE));
        foodMap.put("bread", getItem(foodBlocks.BREAD, FoodComponents.BREAD));
        foodMap.put("beetroot", getItem(foodBlocks.BEETROOT, FoodComponents.BEETROOT));
        foodMap.put("baked_potato", getItem(foodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO));

        // 生熟肉类
        foodMap.put("chicken", getItem(foodBlocks.CHICKEN, FoodComponents.CHICKEN));
        foodMap.put("cooked_chicken", getItem(foodBlocks.COOKED_CHICKEN, FoodComponents.COOKED_CHICKEN));
        foodMap.put("beef", getItem(foodBlocks.BEEF, FoodComponents.BEEF));
        foodMap.put("cooked_beef", getItem(foodBlocks.COOKED_BEEF, FoodComponents.COOKED_BEEF));
        foodMap.put("mutton", getItem(foodBlocks.MUTTON, FoodComponents.MUTTON));
        foodMap.put("cooked_mutton", getItem(foodBlocks.COOKED_MUTTON, FoodComponents.COOKED_MUTTON));
        foodMap.put("porkchop", getItem(foodBlocks.PORKCHOP, FoodComponents.PORKCHOP));
        foodMap.put("cooked_porkchop", getItem(foodBlocks.COOKED_PORKCHOP, FoodComponents.COOKED_PORKCHOP));
        foodMap.put("rabbit", getItem(foodBlocks.RABBIT, FoodComponents.RABBIT));
        foodMap.put("cooked_rabbit", getItem(foodBlocks.COOKED_RABBIT, FoodComponents.COOKED_RABBIT));

        // 鱼类
        foodMap.put("cod", getItem(foodBlocks.COD, FoodComponents.COD));
        foodMap.put("cooked_cod", getItem(foodBlocks.COOKED_COD, FoodComponents.COOKED_COD));
        foodMap.put("salmon", getItem(foodBlocks.SALMON, FoodComponents.SALMON));
        foodMap.put("cooked_salmon", getItem(foodBlocks.COOKED_SALMON, FoodComponents.COOKED_SALMON));
        foodMap.put("pufferfish", getItem(foodBlocks.PUFFERFISH, FoodComponents.PUFFERFISH));

        // 炖菜类
        foodMap.put("rabbit_stew", new ModStewItem(foodBlocks.RABBIT_STEW, new Item.Settings().food(FoodComponents.RABBIT_STEW)));
        foodMap.put("mushroom_stew", new ModStewItem(foodBlocks.MUSHROOM_STEW, new Item.Settings().food(FoodComponents.MUSHROOM_STEW)));
        foodMap.put("beetroot_soup", new ModStewItem(foodBlocks.BEETROOT_SOUP, new Item.Settings().food(FoodComponents.BEETROOT_SOUP)));

        // 其他
        foodMap.put("pumpkin_pie", getItem(foodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE));
    }

    public static BlockItem getItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }
}
