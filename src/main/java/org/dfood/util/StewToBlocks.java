package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import org.dfood.block.FoodBlocks;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class StewToBlocks {
    public static final Map<String, Item> stewMap = new HashMap<>();

    static {
        // 炖菜类
        put("rabbit_stew", FoodBlocks.RABBIT_STEW, new Item.Settings().food(FoodComponents.RABBIT_STEW));
        put("mushroom_stew", FoodBlocks.MUSHROOM_STEW, new Item.Settings().food(FoodComponents.MUSHROOM_STEW));
        put("beetroot_soup", FoodBlocks.BEETROOT_SOUP, new Item.Settings().food(FoodComponents.BEETROOT_SOUP));
        put("suspicious_stew", FoodBlocks.SUSPICIOUS_STEW, new Item.Settings()
                .food(FoodComponents.SUSPICIOUS_STEW)
                .component(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, SuspiciousStewEffectsComponent.DEFAULT));
    }

    private static void put(String id, Block block, Item.Settings settings){
        put(id, settings.useItemPrefixedTranslationKey(), settings1 -> new BlockItem(block, settings1));
    }

    private static void put(String id, Item.Settings settings, Function<Item.Settings, Item> creator){
        RegistryKey<Item> key = RegistryKey.of(RegistryKeys.ITEM, Identifier.ofVanilla(id));
        Item item = creator.apply(settings.useRemainder(Items.BOWL).maxCount(1).registryKey(key));

        stewMap.put(id, item);
    }
}
