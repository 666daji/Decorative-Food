package org.dfood.util;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.*;
import net.minecraft.item.*;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.block.foodBlock;
import org.dfood.block.foodBlockManage;
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
        put("cookie", foodBlocks.COOKIE, FoodComponents.COOKIE);
        put("apple", foodBlocks.APPLE, FoodComponents.APPLE);
        put("melon_slice", foodBlocks.MELON_SLICE, FoodComponents.MELON_SLICE);
        put("bread", foodBlocks.BREAD, FoodComponents.BREAD);

        // 蔬菜类
        put("beetroot", foodBlocks.BEETROOT, FoodComponents.BEETROOT);
        foodMap.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(FoodComponents.POTATO).useItemPrefixedTranslationKey().registryKey(keyOf("potato")), foodBlocks.POTATO.getBlock()));
        put("baked_potato", foodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO);
        foodMap.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(FoodComponents.CARROT).useItemPrefixedTranslationKey().registryKey(keyOf("carrot")), foodBlocks.CARROT.getBlock()));
        put("sweet_berries", foodBlocks.SWEET_BERRIES, FoodComponents.SWEET_BERRIES);
        put("glow_berries", foodBlocks.GLOW_BERRIES, FoodComponents.GLOW_BERRIES);

        // 金制食物
        put("golden_apple", foodBlocks.GOLDEN_APPLE, FoodComponents.GOLDEN_APPLE);
        put("golden_carrot", foodBlocks.GOLDEN_CARROT, FoodComponents.GOLDEN_CARROT);
        put("glistering_melon_slice", foodBlocks.GLISTERING_MELON_SLICE, new Item.Settings());

        // 生熟肉类
        put("chicken", foodBlocks.CHICKEN, new Item.Settings().food(FoodComponents.CHICKEN, ConsumableComponents.RAW_CHICKEN));
        put("cooked_chicken", foodBlocks.COOKED_CHICKEN, FoodComponents.COOKED_CHICKEN);
        put("beef", foodBlocks.BEEF, FoodComponents.BEEF);
        put("cooked_beef", foodBlocks.COOKED_BEEF, FoodComponents.COOKED_BEEF);
        put("mutton", foodBlocks.MUTTON, FoodComponents.MUTTON);
        put("cooked_mutton", foodBlocks.COOKED_MUTTON, FoodComponents.COOKED_MUTTON);
        put("porkchop", foodBlocks.PORKCHOP, FoodComponents.PORKCHOP);
        put("cooked_porkchop", foodBlocks.COOKED_PORKCHOP, FoodComponents.COOKED_PORKCHOP);
        put("rabbit", foodBlocks.RABBIT, FoodComponents.RABBIT);
        put("cooked_rabbit", foodBlocks.COOKED_RABBIT, FoodComponents.COOKED_RABBIT);

        // 鱼类
        put("cod", foodBlocks.COD, FoodComponents.COD);
        put("cooked_cod", foodBlocks.COOKED_COD, FoodComponents.COOKED_COD);
        put("salmon", foodBlocks.SALMON, FoodComponents.SALMON);
        put("cooked_salmon", foodBlocks.COOKED_SALMON, FoodComponents.COOKED_SALMON);
        put("pufferfish", foodBlocks.PUFFERFISH, new Item.Settings().food(FoodComponents.PUFFERFISH, ConsumableComponents.PUFFERFISH).useCooldown(1.0F));

        // 其他
        put("pumpkin_pie", foodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE);
        put("chorus_fruit", foodBlocks.CHORUS_FRUIT, new Item.Settings().food(FoodComponents.CHORUS_FRUIT, ConsumableComponents.CHORUS_FRUIT).useCooldown(1.0F));
        put("egg", foodBlocks.EGG, new Item.Settings());

        // 药水类
        foodMap.put("potion", new ModPotionItem(foodBlocks.POTION.getBlock(), new Item.Settings().maxCount(1)
                .component(DataComponentTypes.POTION_CONTENTS, PotionContentsComponent.DEFAULT)
                .component(DataComponentTypes.CONSUMABLE, ConsumableComponents.DRINK)
                .useItemPrefixedTranslationKey().registryKey(keyOf("potion"))));
    }

    public static void put(String id, foodBlockManage block, Item.Settings settings) {
        Item item;
        if (id.equals("egg")){
            item = new ModEggItem(block.getBlock(), settings.useItemPrefixedTranslationKey().registryKey(keyOf(id)));
        } else {
            item = new BlockItem(block.getBlock(), settings.useItemPrefixedTranslationKey().registryKey(keyOf(id)));
        }
        foodMap.put(id, item);
    }

    public static void put(String id, foodBlockManage block, FoodComponent foodComponent) {
        put(id, block, new Item.Settings().food(foodComponent).useItemPrefixedTranslationKey());
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id));
    }
}
