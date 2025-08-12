package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import org.dfood.block.foodBlocks;
import org.dfood.item.*;
import org.dfood.mixin.foodToBlockMixin;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个映射类，使开发者可以高度自定义新的Item实例
 * @see foodToBlockMixin
 */
public class foodToBlocks {
    public static final Map<String, Item> foodMap = new HashMap<>();

    static {
        // 零食类
        foodMap.put("cookie", getItem(foodBlocks.COOKIE, FoodComponents.COOKIE));
        foodMap.put("apple", getItem(foodBlocks.APPLE, FoodComponents.APPLE));
        foodMap.put("melon_slice", getItem(foodBlocks.MELON_SLICE, FoodComponents.MELON_SLICE));
        foodMap.put("bread", getItem(foodBlocks.BREAD, FoodComponents.BREAD));

        // 蔬菜类
        foodMap.put("beetroot", getItem(foodBlocks.BEETROOT, FoodComponents.BEETROOT));
        foodMap.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(FoodComponents.POTATO), foodBlocks.POTATO));
        foodMap.put("baked_potato", getItem(foodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO));
        foodMap.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(FoodComponents.CARROT), foodBlocks.CARROT));
        foodMap.put("sweet_berries", getItem(foodBlocks.SWEET_BERRIES, FoodComponents.SWEET_BERRIES));
        foodMap.put("glow_berries", getItem(foodBlocks.GLOW_BERRIES, FoodComponents.GLOW_BERRIES));

        // 金制食物
        foodMap.put("golden_apple", getItem(foodBlocks.GOLDEN_APPLE, FoodComponents.GOLDEN_APPLE));
        foodMap.put("golden_carrot", getItem(foodBlocks.GOLDEN_CARROT, FoodComponents.GOLDEN_CARROT));
        foodMap.put("glistering_melon_slice", new BlockItem(foodBlocks.GLISTERING_MELON_SLICE, new Item.Settings()));

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
        foodMap.put("bowl", new BlockItem(foodBlocks.BOWL, new Item.Settings()));

        // 其他
        foodMap.put("pumpkin_pie", getItem(foodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE));
        foodMap.put("chorus_fruit", new ModChorusFruitItem(foodBlocks.CHORUS_FRUIT,new Item.Settings().food(FoodComponents.CHORUS_FRUIT)));
        foodMap.put("egg", new ModEggItem(foodBlocks.EGG, new Item.Settings()));

        // 药水类
        foodMap.put("potion", new ModPotionItem(foodBlocks.POTION, new Item.Settings().maxCount(1)));
    }

    public static BlockItem getItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }
}
