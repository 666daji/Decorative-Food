package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.PotionContentsComponent;
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
        foodMap.put("cookie", getItem(foodBlocks.COOKIE, ModFoodComponents.COOKIE));
        foodMap.put("apple", getItem(foodBlocks.APPLE, ModFoodComponents.APPLE));
        foodMap.put("melon_slice", getItem(foodBlocks.MELON_SLICE, ModFoodComponents.MELON_SLICE));
        foodMap.put("bread", getItem(foodBlocks.BREAD, ModFoodComponents.BREAD));

        // 蔬菜类
        foodMap.put("beetroot", getItem(foodBlocks.BEETROOT, ModFoodComponents.BEETROOT));
        foodMap.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(ModFoodComponents.POTATO), foodBlocks.POTATO));
        foodMap.put("baked_potato", getItem(foodBlocks.BAKED_POTATO, ModFoodComponents.BAKED_POTATO));
        foodMap.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(ModFoodComponents.CARROT), foodBlocks.CARROT));
        foodMap.put("sweet_berries", getItem(foodBlocks.SWEET_BERRIES, ModFoodComponents.SWEET_BERRIES));
        foodMap.put("glow_berries", getItem(foodBlocks.GLOW_BERRIES, ModFoodComponents.GLOW_BERRIES));

        // 金制食物
        foodMap.put("golden_apple", getItem(foodBlocks.GOLDEN_APPLE, ModFoodComponents.GOLDEN_APPLE));
        foodMap.put("golden_carrot", getItem(foodBlocks.GOLDEN_CARROT, ModFoodComponents.GOLDEN_CARROT));
        foodMap.put("glistering_melon_slice", new BlockItem(foodBlocks.GLISTERING_MELON_SLICE,  new Item.Settings()));

        // 生熟肉类
        foodMap.put("chicken", getItem(foodBlocks.CHICKEN, ModFoodComponents.CHICKEN));
        foodMap.put("cooked_chicken", getItem(foodBlocks.COOKED_CHICKEN, ModFoodComponents.COOKED_CHICKEN));
        foodMap.put("beef", getItem(foodBlocks.BEEF, ModFoodComponents.BEEF));
        foodMap.put("cooked_beef", getItem(foodBlocks.COOKED_BEEF, ModFoodComponents.COOKED_BEEF));
        foodMap.put("mutton", getItem(foodBlocks.MUTTON, ModFoodComponents.MUTTON));
        foodMap.put("cooked_mutton", getItem(foodBlocks.COOKED_MUTTON, ModFoodComponents.COOKED_MUTTON));
        foodMap.put("porkchop", getItem(foodBlocks.PORKCHOP, ModFoodComponents.PORKCHOP));
        foodMap.put("cooked_porkchop", getItem(foodBlocks.COOKED_PORKCHOP, ModFoodComponents.COOKED_PORKCHOP));
        foodMap.put("rabbit", getItem(foodBlocks.RABBIT, ModFoodComponents.RABBIT));
        foodMap.put("cooked_rabbit", getItem(foodBlocks.COOKED_RABBIT, ModFoodComponents.COOKED_RABBIT));

        // 鱼类
        foodMap.put("cod", getItem(foodBlocks.COD, ModFoodComponents.COD));
        foodMap.put("cooked_cod", getItem(foodBlocks.COOKED_COD, ModFoodComponents.COOKED_COD));
        foodMap.put("salmon", getItem(foodBlocks.SALMON, ModFoodComponents.SALMON));
        foodMap.put("cooked_salmon", getItem(foodBlocks.COOKED_SALMON, ModFoodComponents.COOKED_SALMON));
        foodMap.put("pufferfish", getItem(foodBlocks.PUFFERFISH, ModFoodComponents.PUFFERFISH));

        // 其他
        foodMap.put("pumpkin_pie", getItem(foodBlocks.PUMPKIN_PIE, ModFoodComponents.PUMPKIN_PIE));
        foodMap.put("chorus_fruit", new ModChorusFruitItem(foodBlocks.CHORUS_FRUIT,new Item.Settings().food(ModFoodComponents.CHORUS_FRUIT)));
        foodMap.put("egg", new ModEggItem(foodBlocks.EGG, new Item.Settings()));

        // 药水类
        foodMap.put("potion", new ModPotionItem(foodBlocks.POTION, new Item.Settings().maxCount(1)
                .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)));
    }

    public static BlockItem getItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }
}
