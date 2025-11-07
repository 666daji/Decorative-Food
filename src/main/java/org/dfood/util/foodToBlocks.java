package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.dfood.block.FoodBlock;
import org.dfood.block.FoodBlocks;
import org.dfood.item.*;
import org.dfood.mixin.foodToBlockMixin;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

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
        put("cookie", FoodBlocks.COOKIE, FoodComponents.COOKIE);
        put("apple", FoodBlocks.APPLE, FoodComponents.APPLE);
        put("melon_slice", FoodBlocks.MELON_SLICE, FoodComponents.MELON_SLICE);
        put("bread", FoodBlocks.BREAD, FoodComponents.BREAD);

        // 蔬菜类
        put("beetroot", FoodBlocks.BEETROOT, FoodComponents.BEETROOT);
        put("potato", new Item.Settings().food(FoodComponents.POTATO),
                settings -> new DoubleBlockItem(Blocks.POTATOES, settings, FoodBlocks.POTATO));
        put("baked_potato", FoodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO);
        put("carrot", new Item.Settings().food(FoodComponents.CARROT),
                settings -> new DoubleBlockItem(Blocks.CARROTS, settings, FoodBlocks.CARROT));
        put("sweet_berries", new Item.Settings().food(FoodComponents.SWEET_BERRIES),
                settings -> new DoubleBlockItem(Blocks.SWEET_BERRY_BUSH, settings, FoodBlocks.SWEET_BERRIES));
        put("glow_berries", new Item.Settings().food(FoodComponents.GLOW_BERRIES),
                settings -> new DoubleBlockItem(Blocks.CAVE_VINES, settings, FoodBlocks.GLOW_BERRIES));

        // 金制食物
        put("golden_apple", FoodBlocks.GOLDEN_APPLE, FoodComponents.GOLDEN_APPLE);
        put("golden_carrot", FoodBlocks.GOLDEN_CARROT, FoodComponents.GOLDEN_CARROT);
        put("glistering_melon_slice", FoodBlocks.GLISTERING_MELON_SLICE, new Item.Settings());

        // 生熟肉类
        put("chicken", FoodBlocks.CHICKEN, new Item.Settings().food(FoodComponents.CHICKEN, ConsumableComponents.RAW_CHICKEN));
        put("cooked_chicken", FoodBlocks.COOKED_CHICKEN, FoodComponents.COOKED_CHICKEN);
        put("beef", FoodBlocks.BEEF, FoodComponents.BEEF);
        put("cooked_beef", FoodBlocks.COOKED_BEEF, FoodComponents.COOKED_BEEF);
        put("mutton", FoodBlocks.MUTTON, FoodComponents.MUTTON);
        put("cooked_mutton", FoodBlocks.COOKED_MUTTON, FoodComponents.COOKED_MUTTON);
        put("porkchop", FoodBlocks.PORKCHOP, FoodComponents.PORKCHOP);
        put("cooked_porkchop", FoodBlocks.COOKED_PORKCHOP, FoodComponents.COOKED_PORKCHOP);
        put("rabbit", FoodBlocks.RABBIT, FoodComponents.RABBIT);
        put("cooked_rabbit", FoodBlocks.COOKED_RABBIT, FoodComponents.COOKED_RABBIT);

        // 鱼类
        put("cod", FoodBlocks.COD, FoodComponents.COD);
        put("cooked_cod", FoodBlocks.COOKED_COD, FoodComponents.COOKED_COD);
        put("salmon", FoodBlocks.SALMON, FoodComponents.SALMON);
        put("cooked_salmon", FoodBlocks.COOKED_SALMON, FoodComponents.COOKED_SALMON);
        put("pufferfish", FoodBlocks.PUFFERFISH, new Item.Settings().food(FoodComponents.PUFFERFISH, ConsumableComponents.PUFFERFISH).useCooldown(1.0F));

        // 桶
        put("bucket", new Item.Settings().maxCount(16).useItemPrefixedTranslationKey(),
                settings -> new ModBucketItem(Fluids.EMPTY, settings, FoodBlocks.BUCKET));
        put("water_bucket", new Item.Settings().maxCount(1).useItemPrefixedTranslationKey(),
                settings -> new ModBucketItem(Fluids.WATER, settings, FoodBlocks.WATER_BUCKET));

        // 其他
        put("pumpkin_pie", FoodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE);
        put("chorus_fruit", FoodBlocks.CHORUS_FRUIT, new Item.Settings().food(FoodComponents.CHORUS_FRUIT, ConsumableComponents.CHORUS_FRUIT).useCooldown(1.0F));
        put("egg", new Item.Settings(), settings -> new ModEggItem(FoodBlocks.EGG, settings));
        put("totem_of_undying", FoodBlocks.TOTEM_OF_UNDYING, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)
                .component(DataComponentTypes.DEATH_PROTECTION, DeathProtectionComponent.TOTEM_OF_UNDYING)
                .useItemPrefixedTranslationKey());

        // 药水类
        put("potion",new Item.Settings().maxCount(1)
                .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
                .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK)
                .useItemPrefixedTranslationKey(),
                settings -> new ModPotionItem(FoodBlocks.POTION, settings));
    }

    public static void put(String id, Block block, Item.Settings settings) {
        put(id, settings.useItemPrefixedTranslationKey(), settings1 -> new BlockItem(block, settings));
    }

    public static void put(String id, Block block, FoodComponent foodComponent) {
        put(id, block, new Item.Settings().food(foodComponent).useItemPrefixedTranslationKey());
    }

    /**
     * 基本的定义物品的方法
     * @param id 物品的 id
     * @param settings 物品设置
     * @param creator 物品的构造器
     */
    private static void put(String id, Item.Settings settings, Function<Item.Settings, Item> creator){
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id));
        Item item = creator.apply(settings.registryKey(key));

        foodMap.put(id, item);
    }
}
