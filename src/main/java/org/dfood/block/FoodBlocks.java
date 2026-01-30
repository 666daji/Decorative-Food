package org.dfood.block;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import org.dfood.sound.ModSoundGroups;
import org.dfood.util.DFoodUtils;
import org.dfood.util.IntPropertyManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 关于原版的食物方块定义
 */
public class FoodBlocks {
    public static final Map<String, Block> FOOD_BLOCK_REGISTRY = new HashMap<>();

    // 零食
    public static final Block COOKIE = registerFoodBlock("cookie", 10,
            MapColor.TERRACOTTA_YELLOW, BlockSoundGroup.STONE);
    public static final Block APPLE = registerFoodBlock("apple", 5,
            MapColor.RED, BlockSoundGroup.STONE);
    public static final Block MELON_SLICE = registerFoodBlock("melon_slice", 5,
            MapColor.LIME, BlockSoundGroup.STONE);
    public static final Block BREAD = registerFoodBlock("bread", 5,
            MapColor.TERRACOTTA_YELLOW, ModSoundGroups.BREAD);

    // 蔬菜类
    public static final Block BEETROOT = registerFoodBlock("beetroot", 5,
            MapColor.RED, BlockSoundGroup.CANDLE);
    public static final Block POTATO = registerFoodBlock("potato", 5,
            MapColor.GOLD, BlockSoundGroup.CANDLE, EnforceAsItems.POTATO);
    public static final Block BAKED_POTATO = registerFoodBlock("baked_potato", 5,
            MapColor.GOLD, BlockSoundGroup.CANDLE);
    public static final Block CARROT = registerFoodBlock("carrot", 5,
            MapColor.YELLOW, BlockSoundGroup.CANDLE, EnforceAsItems.CARROT);
    public static final Block SWEET_BERRIES = registerFoodBlock("sweet_berries", 5,
            MapColor.RED, BlockSoundGroup.SWEET_BERRY_BUSH, EnforceAsItems.SWEET_BERRIES);
    public static final Block GLOW_BERRIES = registerFoodBlock("glow_berries",
            FoodBlock.Builder.create()
                    .maxFood(12)
                    .cItem(EnforceAsItems.GLOW_BERRIES)
                    .settings(DFoodUtils.getFoodBlockSettings()
                            .mapColor(MapColor.YELLOW).sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                            .luminance(state -> state.getBlock() instanceof FoodBlock ?
                                    state.get(IntPropertyManager.create("number_of_food", 12)) + 3 : 0))
                    .build());

    // 金制食物
    public static final Block GOLDEN_APPLE = registerFoodBlock("golden_apple", 5,
            MapColor.GOLD, null);
    public static final Block GOLDEN_CARROT = registerFoodBlock("golden_carrot", 5,
            MapColor.GOLD, BlockSoundGroup.CANDLE);
    public static final Block GLISTERING_MELON_SLICE = registerFoodBlock("glistering_melon_slice", 5,
            MapColor.LIGHT_BLUE, null);

    // 生熟肉类
    public static final Block CHICKEN = registerFoodBlock("chicken", 1,
            MapColor.LIGHT_GRAY, ModSoundGroups.MEAT);
    public static final Block COOKED_CHICKEN = registerFoodBlock("cooked_chicken", 1,
            MapColor.TERRACOTTA_YELLOW, ModSoundGroups.MEAT);
    public static final Block BEEF = registerFoodBlock("beef", 3,
            MapColor.BROWN, ModSoundGroups.MEAT);
    public static final Block COOKED_BEEF = registerFoodBlock("cooked_beef", 3,
            MapColor.TERRACOTTA_BROWN, ModSoundGroups.MEAT);
    public static final Block MUTTON = registerFoodBlock("mutton", 3,
            MapColor.PINK, ModSoundGroups.MEAT);
    public static final Block COOKED_MUTTON = registerFoodBlock("cooked_mutton", 3,
            MapColor.TERRACOTTA_PINK, ModSoundGroups.MEAT);
    public static final Block PORKCHOP = registerFoodBlock("porkchop", 3,
            MapColor.PINK, ModSoundGroups.MEAT);
    public static final Block COOKED_PORKCHOP = registerFoodBlock("cooked_porkchop", 3,
            MapColor.TERRACOTTA_PINK, ModSoundGroups.MEAT);
    public static final Block RABBIT = registerFoodBlock("rabbit", 1,
            MapColor.BROWN, ModSoundGroups.MEAT);
    public static final Block COOKED_RABBIT = registerFoodBlock("cooked_rabbit", 1,
            MapColor.TERRACOTTA_BROWN, ModSoundGroups.MEAT);

    // 鱼类
    public static final Block COD = registerFoodBlock("cod", 3,
            MapColor.LIGHT_BLUE, ModSoundGroups.FISH);
    public static final Block COOKED_COD = registerFoodBlock("cooked_cod", 3,
            MapColor.TERRACOTTA_LIGHT_BLUE, ModSoundGroups.FISH);
    public static final Block SALMON = registerFoodBlock("salmon", 3,
            MapColor.LIGHT_BLUE, ModSoundGroups.FISH);
    public static final Block COOKED_SALMON = registerFoodBlock("cooked_salmon", 3,
            MapColor.TERRACOTTA_LIGHT_BLUE, ModSoundGroups.FISH);
    public static final Block PUFFERFISH = registerFoodBlock("pufferfish", 1,
            MapColor.LIGHT_BLUE, ModSoundGroups.FISH);

    // 炖菜
    public static final Block RABBIT_STEW = registerFoodBlock("rabbit_stew", 1,
            MapColor.BROWN, BlockSoundGroup.DECORATED_POT);
    public static final Block MUSHROOM_STEW = registerFoodBlock("mushroom_stew", 1,
            MapColor.BROWN, BlockSoundGroup.DECORATED_POT);
    public static final Block BEETROOT_SOUP = registerFoodBlock("beetroot_soup", 1,
            MapColor.BROWN, BlockSoundGroup.DECORATED_POT);
    public static final Block SUSPICIOUS_STEW = registerFoodBlock("suspicious_stew",
            SuspiciousStewBlock.Builder.create()
                    .maxFood(1)
                    .settings(DFoodUtils.getFoodBlockSettings()
                            .mapColor(MapColor.BROWN)
                            .sounds(BlockSoundGroup.DECORATED_POT))
                    .build());
    public static final Block BOWL = registerFoodBlock("bowl", 1,
            MapColor.BROWN, BlockSoundGroup.DECORATED_POT);

    // 桶
    public static final Block BUCKET = registerFoodBlock("bucket", 1,
            MapColor.WHITE, ModSoundGroups.BUCKET);
    public static final Block WATER_BUCKET = registerFoodBlock("water_bucket", 1,
            MapColor.BLUE, ModSoundGroups.WATER_BUCKET);
    public static final Block MILK_BUCKET = registerFoodBlock("milk_bucket", 1,
            MapColor.WHITE, ModSoundGroups.WATER_BUCKET);
    public static final Block LAVA_BUCKET = registerFoodBlock("lava_bucket", 1,
            DFoodUtils.getFoodBlockSettings()
                    .sounds(ModSoundGroups.LAVA_BUCKET)
                    .mapColor(MapColor.ORANGE)
                    .luminance(state -> 15));

    // 其他
    public static final Block PUMPKIN_PIE = registerFoodBlock("pumpkin_pie", 1,
            MapColor.TERRACOTTA_ORANGE, BlockSoundGroup.WOOL);
    public static final Block CHORUS_FRUIT = registerFoodBlock("chorus_fruit",
            ChorusFruitBlock.Builder.create()
                    .maxFood(5)
                    .settings(DFoodUtils.getFoodBlockSettings()
                            .mapColor(MapColor.PURPLE)
                            .sounds(ModSoundGroups.CHORUS_FRUIT))
                    .build());
    public static final Block EGG = registerFoodBlock("egg", 5, MapColor.WHITE, ModSoundGroups.EGG);
    public static final Block TOTEM_OF_UNDYING = registerFoodBlock("totem_of_undying",
            new ModTotemBlock(DFoodUtils.getFoodBlockSettings()
                    .mapColor(MapColor.YELLOW)
                    .sounds(ModSoundGroups.EGG)));

    // 药水
    public static final Block POTION = registerFoodBlock("potion",
            PotionBlock.Builder.create()
                    .maxFood(3)
                    .settings(DFoodUtils.getFoodBlockSettings()
                            .mapColor(MapColor.PURPLE)
                            .sounds(ModSoundGroups.POTION))
                    .build());
    public static final Block GLASS_BOTTLE = registerFoodBlock("glass_bottle", 3,
            MapColor.WHITE, ModSoundGroups.GLASS_BOTTLE);
    public static final Block HONEY_BOTTLE = registerFoodBlock("honey_bottle", 3,
            MapColor.ORANGE, ModSoundGroups.POTION);

    /**
     * 直接注册已创建的方块
     */
    private static Block registerFoodBlock(String id, Block block) {
        FOOD_BLOCK_REGISTRY.put(id, block);
        return block;
    }

    private static Block registerFoodBlock(String id, int maxFood, AbstractBlock.Settings settings){
        Block block = FoodBlock.Builder.create()
                .maxFood(maxFood)
                .settings(settings)
                .build();

        return registerFoodBlock(id, block);
    }

    /**
     * 使用构建器创建普通食物方块
     */
    private static Block registerFoodBlock(String id, int maxFood, MapColor mapColor, BlockSoundGroup sound){
        return registerFoodBlock(id, maxFood, mapColor, sound, null);
    }

    /**
     * 使用构建器创建普通食物方块（带强制物品类型）
     */
    private static Block registerFoodBlock(String id, int maxFood, MapColor mapColor,
                                           BlockSoundGroup sound, FoodBlock.EnforceAsItem cropType){
        BlockSoundGroup finalSound = (sound == null) ? BlockSoundGroup.STONE : sound;

        Block block = FoodBlock.Builder.create()
                .maxFood(maxFood)
                .cItem(cropType)
                .settings(DFoodUtils.getFoodBlockSettings()
                        .mapColor(mapColor).sounds(finalSound))
                .build();

        return registerFoodBlock(id, block);
    }
}