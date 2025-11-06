package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import org.dfood.block.FoodBlocks;
import org.dfood.item.*;
import org.dfood.mixin.foodToBlockMixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 一个映射类，使开发者可以高度自定义新的Item实例
 * @see foodToBlockMixin
 */
public class foodToBlocks {
    public static final Map<String, Item> foodMap = new HashMap<>();
    /**
     * 为了防止在碗物品之前加载炖菜类物品，
     * 所以使用字符串来判断炖菜类物品
     */
    public static final Set<String> other = Set.of("rabbit_stew", "mushroom_stew", "beetroot_soup", "suspicious_stew");

    static {
        // 零食类
        foodMap.put("cookie", getItem(FoodBlocks.COOKIE, ModFoodComponents.COOKIE));
        foodMap.put("apple", getItem(FoodBlocks.APPLE, ModFoodComponents.APPLE));
        foodMap.put("melon_slice", getItem(FoodBlocks.MELON_SLICE, ModFoodComponents.MELON_SLICE));
        foodMap.put("bread", getItem(FoodBlocks.BREAD, ModFoodComponents.BREAD));

        // 蔬菜类
        foodMap.put("beetroot", getItem(FoodBlocks.BEETROOT, ModFoodComponents.BEETROOT));
        foodMap.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(ModFoodComponents.POTATO), FoodBlocks.POTATO));
        foodMap.put("baked_potato", getItem(FoodBlocks.BAKED_POTATO, ModFoodComponents.BAKED_POTATO));
        foodMap.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(ModFoodComponents.CARROT), FoodBlocks.CARROT));
        foodMap.put("sweet_berries", new DoubleBlockItem(Blocks.SWEET_BERRY_BUSH, new Item.Settings().food(ModFoodComponents.SWEET_BERRIES), FoodBlocks.SWEET_BERRIES));
        foodMap.put("glow_berries", new DoubleBlockItem(Blocks.CAVE_VINES, new Item.Settings().food(ModFoodComponents.GLOW_BERRIES), FoodBlocks.GLOW_BERRIES));

        // 金制食物
        foodMap.put("golden_apple", getItem(FoodBlocks.GOLDEN_APPLE, ModFoodComponents.GOLDEN_APPLE));
        foodMap.put("golden_carrot", getItem(FoodBlocks.GOLDEN_CARROT, ModFoodComponents.GOLDEN_CARROT));
        foodMap.put("glistering_melon_slice", new BlockItem(FoodBlocks.GLISTERING_MELON_SLICE,  new Item.Settings()));

        // 生熟肉类
        foodMap.put("chicken", getItem(FoodBlocks.CHICKEN, ModFoodComponents.CHICKEN));
        foodMap.put("cooked_chicken", getItem(FoodBlocks.COOKED_CHICKEN, ModFoodComponents.COOKED_CHICKEN));
        foodMap.put("beef", getItem(FoodBlocks.BEEF, ModFoodComponents.BEEF));
        foodMap.put("cooked_beef", getItem(FoodBlocks.COOKED_BEEF, ModFoodComponents.COOKED_BEEF));
        foodMap.put("mutton", getItem(FoodBlocks.MUTTON, ModFoodComponents.MUTTON));
        foodMap.put("cooked_mutton", getItem(FoodBlocks.COOKED_MUTTON, ModFoodComponents.COOKED_MUTTON));
        foodMap.put("porkchop", getItem(FoodBlocks.PORKCHOP, ModFoodComponents.PORKCHOP));
        foodMap.put("cooked_porkchop", getItem(FoodBlocks.COOKED_PORKCHOP, ModFoodComponents.COOKED_PORKCHOP));
        foodMap.put("rabbit", getItem(FoodBlocks.RABBIT, ModFoodComponents.RABBIT));
        foodMap.put("cooked_rabbit", getItem(FoodBlocks.COOKED_RABBIT, ModFoodComponents.COOKED_RABBIT));

        // 鱼类
        foodMap.put("cod", getItem(FoodBlocks.COD, ModFoodComponents.COD));
        foodMap.put("cooked_cod", getItem(FoodBlocks.COOKED_COD, ModFoodComponents.COOKED_COD));
        foodMap.put("salmon", getItem(FoodBlocks.SALMON, ModFoodComponents.SALMON));
        foodMap.put("cooked_salmon", getItem(FoodBlocks.COOKED_SALMON, ModFoodComponents.COOKED_SALMON));
        foodMap.put("pufferfish", getItem(FoodBlocks.PUFFERFISH, ModFoodComponents.PUFFERFISH));

        // 桶
        foodMap.put("bucket", new ModBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16), FoodBlocks.BUCKET));
        foodMap.put("water_bucket", new ModBucketItem(Fluids.WATER, new Item.Settings().maxCount(16), FoodBlocks.WATER_BUCKET));
        foodMap.put("milk_bucket", new ModMilkBucketItem(FoodBlocks.MILK_BUCKET, new Item.Settings().maxCount(16)));

        // 其他
        foodMap.put("pumpkin_pie", getItem(FoodBlocks.PUMPKIN_PIE, ModFoodComponents.PUMPKIN_PIE));
        foodMap.put("chorus_fruit", new ModChorusFruitItem(FoodBlocks.CHORUS_FRUIT,new Item.Settings().food(ModFoodComponents.CHORUS_FRUIT)));
        foodMap.put("egg", new ModEggItem(FoodBlocks.EGG, new Item.Settings()));
        foodMap.put("totem_of_undying", new BlockItem(FoodBlocks.TOTEM_OF_UNDYING, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));

        // 药水类
        foodMap.put("potion", new ModPotionItem(FoodBlocks.POTION, new Item.Settings().maxCount(1)
                .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)));
    }

    public static BlockItem getItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }
}
