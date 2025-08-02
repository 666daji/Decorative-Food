package org.dfood.util;

import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.block.foodBlockManage;
import org.dfood.block.foodBlocks;

import java.util.HashMap;
import java.util.Map;

public class StewToBlocks {
    public static final Map<String, Item> stewMap = new HashMap<>();

    static {
        // 炖菜类
        put("rabbit_stew", foodBlocks.RABBIT_STEW, new Item.Settings().food(FoodComponents.RABBIT_STEW).useRemainder(Items.BOWL));
        put("mushroom_stew", foodBlocks.MUSHROOM_STEW, new Item.Settings().food(FoodComponents.MUSHROOM_STEW).useRemainder(Items.BOWL));
        put("beetroot_soup", foodBlocks.BEETROOT_SOUP, new Item.Settings().food(FoodComponents.BEETROOT_SOUP).useRemainder(Items.BOWL));
    }

    public static void put(String id, foodBlockManage block, Item.Settings settings) {
        Item item = new BlockItem(block.getBlock(), settings.useItemPrefixedTranslationKey().registryKey(keyOf(id)));
        stewMap.put(id, item);
    }

    private static RegistryKey<Item> keyOf(String id) {
        return RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id));
    }
}
