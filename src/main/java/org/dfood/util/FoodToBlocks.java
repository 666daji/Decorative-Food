package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import org.dfood.block.FoodBlocks;
import org.dfood.item.*;
import org.dfood.mixin.foodToBlockMixin;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个映射类，使开发者可以高度自定义新的Item实例
 * @see foodToBlockMixin
 */
public class FoodToBlocks {
    public static final Map<String, Item> foodMap = new HashMap<>();

    static {
        // 零食类
        foodMap.put("cookie", getItem(FoodBlocks.COOKIE, FoodComponents.COOKIE));
        foodMap.put("apple", getItem(FoodBlocks.APPLE, FoodComponents.APPLE));
        foodMap.put("melon_slice", getItem(FoodBlocks.MELON_SLICE, FoodComponents.MELON_SLICE));
        foodMap.put("bread", getItem(FoodBlocks.BREAD, FoodComponents.BREAD));

        // 蔬菜类
        foodMap.put("beetroot", getItem(FoodBlocks.BEETROOT, FoodComponents.BEETROOT));
        foodMap.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(FoodComponents.POTATO), FoodBlocks.POTATO));
        foodMap.put("baked_potato", getItem(FoodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO));
        foodMap.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(FoodComponents.CARROT), FoodBlocks.CARROT));
        foodMap.put("sweet_berries", getItem(FoodBlocks.SWEET_BERRIES, FoodComponents.SWEET_BERRIES));
        foodMap.put("glow_berries", getItem(FoodBlocks.GLOW_BERRIES, FoodComponents.GLOW_BERRIES));

        // 金制食物
        foodMap.put("golden_apple", getItem(FoodBlocks.GOLDEN_APPLE, FoodComponents.GOLDEN_APPLE));
        foodMap.put("golden_carrot", getItem(FoodBlocks.GOLDEN_CARROT, FoodComponents.GOLDEN_CARROT));
        foodMap.put("glistering_melon_slice", new BlockItem(FoodBlocks.GLISTERING_MELON_SLICE, new Item.Settings()));

        // 生熟肉类
        foodMap.put("chicken", getItem(FoodBlocks.CHICKEN, FoodComponents.CHICKEN));
        foodMap.put("cooked_chicken", getItem(FoodBlocks.COOKED_CHICKEN, FoodComponents.COOKED_CHICKEN));
        foodMap.put("beef", getItem(FoodBlocks.BEEF, FoodComponents.BEEF));
        foodMap.put("cooked_beef", getItem(FoodBlocks.COOKED_BEEF, FoodComponents.COOKED_BEEF));
        foodMap.put("mutton", getItem(FoodBlocks.MUTTON, FoodComponents.MUTTON));
        foodMap.put("cooked_mutton", getItem(FoodBlocks.COOKED_MUTTON, FoodComponents.COOKED_MUTTON));
        foodMap.put("porkchop", getItem(FoodBlocks.PORKCHOP, FoodComponents.PORKCHOP));
        foodMap.put("cooked_porkchop", getItem(FoodBlocks.COOKED_PORKCHOP, FoodComponents.COOKED_PORKCHOP));
        foodMap.put("rabbit", getItem(FoodBlocks.RABBIT, FoodComponents.RABBIT));
        foodMap.put("cooked_rabbit", getItem(FoodBlocks.COOKED_RABBIT, FoodComponents.COOKED_RABBIT));

        // 鱼类
        foodMap.put("cod", getItem(FoodBlocks.COD, FoodComponents.COD));
        foodMap.put("cooked_cod", getItem(FoodBlocks.COOKED_COD, FoodComponents.COOKED_COD));
        foodMap.put("salmon", getItem(FoodBlocks.SALMON, FoodComponents.SALMON));
        foodMap.put("cooked_salmon", getItem(FoodBlocks.COOKED_SALMON, FoodComponents.COOKED_SALMON));
        foodMap.put("pufferfish", getItem(FoodBlocks.PUFFERFISH, FoodComponents.PUFFERFISH));

        // 炖菜类
        foodMap.put("rabbit_stew", new ModStewItem(FoodBlocks.RABBIT_STEW, new Item.Settings().maxCount(1).food(FoodComponents.RABBIT_STEW)));
        foodMap.put("mushroom_stew", new ModStewItem(FoodBlocks.MUSHROOM_STEW, new Item.Settings().maxCount(1).food(FoodComponents.MUSHROOM_STEW)));
        foodMap.put("beetroot_soup", new ModStewItem(FoodBlocks.BEETROOT_SOUP, new Item.Settings().maxCount(1).food(FoodComponents.BEETROOT_SOUP)));
        foodMap.put("suspicious_stew", new ModSuspiciousStewItem(FoodBlocks.SUSPICIOUS_STEW, new Item.Settings().maxCount(1).food(FoodComponents.SUSPICIOUS_STEW)));
        foodMap.put("bowl", new BlockItem(FoodBlocks.BOWL, new Item.Settings()));

        // 桶
        foodMap.put("bucket", new ModBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16), FoodBlocks.BUCKET));
        foodMap.put("water_bucket", new ModBucketItem(Fluids.WATER, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1), FoodBlocks.WATER_BUCKET));
        foodMap.put("milk_bucket", new ModMilkBucketItem(FoodBlocks.MILK_BUCKET, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));

        // 其他
        foodMap.put("pumpkin_pie", getItem(FoodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE));
        foodMap.put("chorus_fruit", new ModChorusFruitItem(FoodBlocks.CHORUS_FRUIT,new Item.Settings().food(FoodComponents.CHORUS_FRUIT)));
        foodMap.put("egg", new ModEggItem(FoodBlocks.EGG, new Item.Settings()));
        foodMap.put("totem_of_undying", new BlockItem(FoodBlocks.TOTEM_OF_UNDYING, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));

        // 药水类
        foodMap.put("potion", new ModPotionItem(FoodBlocks.POTION, new Item.Settings().maxCount(1)));
    }

    public static BlockItem getItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }
}
