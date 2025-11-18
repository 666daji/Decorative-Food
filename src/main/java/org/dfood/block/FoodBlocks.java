package org.dfood.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.dfood.sound.ModSoundGroups;
import org.dfood.util.IntPropertyManager;
import org.dfood.util.DFoodUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 关于原版的食物方块定义
 */
public class FoodBlocks {
    public static final Map<String, Block> FOOD_BLOCK_REGISTRY = new HashMap<>();

    // 零食
    public static final Block COOKIE = createFoodBlock("cookie", 10,
            MapColor.TERRACOTTA_YELLOW, 0.2F, null);
    public static final Block APPLE = createFoodBlock("apple", 5,
            MapColor.RED, 0.2F, null);
    public static final Block MELON_SLICE = createFoodBlock("melon_slice", 5,
            MapColor.LIME, 0.2F, null);
    public static final Block BREAD = createFoodBlock("bread", 5,
            MapColor.TERRACOTTA_YELLOW, 0.2F, ModSoundGroups.BREAD);

    // 蔬菜类
    public static final Block BEETROOT = createFoodBlock("beetroot", 5,
            MapColor.RED, 0.2F, BlockSoundGroup.CANDLE);
    public static final Block POTATO = createFoodBlock("potato",
            MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE, 5, EnforceAsItems.POTATO);
    public static final Block BAKED_POTATO = createFoodBlock("baked_potato", 5,
            MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE);
    public static final Block CARROT = createFoodBlock("carrot",
            MapColor.YELLOW, 0.2F, BlockSoundGroup.CANDLE, 5, EnforceAsItems.CARROT);
    public static final Block SWEET_BERRIES = createFoodBlock("sweet_berries",
            MapColor.RED, 0.2F, BlockSoundGroup.SWEET_BERRY_BUSH, 5, EnforceAsItems.SWEET_BERRIES);
    public static final Block GLOW_BERRIES = registerFoodBlock("glow_berries", 12,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(0.2F, 0.2F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.SWEET_BERRY_BUSH)
                    .pistonBehavior(PistonBehavior.DESTROY)
                    .luminance(state -> {
                        if (state.getBlock() instanceof FoodBlock) {
                            return state.get(IntPropertyManager.create("number_of_food", 12)) + 3;
                        }
                        return 0;
                    }),
            ((settings, integer) -> new FoodBlock(settings, integer, EnforceAsItems.GLOW_BERRIES)));

    // 金制食物
    public static final Block GOLDEN_APPLE = createFoodBlock("golden_apple", 5,
            MapColor.GOLD, 0.2F, null);
    public static final Block GOLDEN_CARROT = createFoodBlock("golden_carrot", 5,
            MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE);
    public static final Block GLISTERING_MELON_SLICE = createFoodBlock("glistering_melon_slice", 5,
            MapColor.LIGHT_BLUE, 0.2F, null);

    // 生熟肉类
    public static final Block CHICKEN = createFoodBlock("chicken", 1,
            MapColor.LIGHT_GRAY, 0.2F, ModSoundGroups.MEAT);
    public static final Block COOKED_CHICKEN = createFoodBlock("cooked_chicken", 1,
            MapColor.TERRACOTTA_YELLOW, 0.2F, ModSoundGroups.MEAT);
    public static final Block BEEF = createFoodBlock("beef", 3,
            MapColor.BROWN, 0.3F, ModSoundGroups.MEAT);
    public static final Block COOKED_BEEF = createFoodBlock("cooked_beef", 3,
            MapColor.TERRACOTTA_BROWN, 0.3F, ModSoundGroups.MEAT);
    public static final Block MUTTON = createFoodBlock("mutton", 3,
            MapColor.PINK, 0.2F, ModSoundGroups.MEAT);
    public static final Block COOKED_MUTTON = createFoodBlock("cooked_mutton", 3,
            MapColor.TERRACOTTA_PINK, 0.2F, ModSoundGroups.MEAT);
    public static final Block PORKCHOP = createFoodBlock("porkchop", 3,
            MapColor.PINK, 0.2F, ModSoundGroups.MEAT);
    public static final Block COOKED_PORKCHOP = createFoodBlock("cooked_porkchop", 3,
            MapColor.TERRACOTTA_PINK, 0.2F, ModSoundGroups.MEAT);
    public static final Block RABBIT = createFoodBlock("rabbit", 1,
            MapColor.BROWN, 0.2F, ModSoundGroups.MEAT);
    public static final Block COOKED_RABBIT = createFoodBlock("cooked_rabbit", 1,
            MapColor.TERRACOTTA_BROWN, 0.2F, ModSoundGroups.MEAT);

    // 鱼类
    public static final Block COD = createFoodBlock("cod", 3,
            MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH);
    public static final Block COOKED_COD = createFoodBlock("cooked_cod", 3,
            MapColor.TERRACOTTA_LIGHT_BLUE, 0.2F, ModSoundGroups.FISH);
    public static final Block SALMON = createFoodBlock("salmon", 3,
            MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH);
    public static final Block COOKED_SALMON = createFoodBlock("cooked_salmon", 3,
            MapColor.TERRACOTTA_LIGHT_BLUE, 0.2F, ModSoundGroups.FISH);
    public static final Block PUFFERFISH = createFoodBlock("pufferfish", 1,
            MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH);

    // 炖菜
    public static final Block RABBIT_STEW = createFoodBlock("rabbit_stew", 1,
            MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT);
    public static final Block MUSHROOM_STEW = createFoodBlock("mushroom_stew", 1,
            MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT);
    public static final Block BEETROOT_SOUP = createFoodBlock("beetroot_soup", 1,
            MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT);
    public static final Block SUSPICIOUS_STEW = registerFoodBlock("suspicious_stew", 1,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.BROWN)
                    .strength(0.1F, 0.1F)
                    .nonOpaque()
                    .sounds(BlockSoundGroup.DECORATED_POT)
                    .pistonBehavior(PistonBehavior.DESTROY),
            SuspiciousStewBlock::new);
    public static final Block BOWL = createFoodBlock("bowl", 1,
            MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT);

    // 桶
    public static final Block BUCKET = createFoodBlock("bucket", 1,
            MapColor.WHITE, 0.2F, ModSoundGroups.BUCKET);
    public static final Block WATER_BUCKET = createFoodBlock("water_bucket", 1,
            MapColor.BLUE, 0.2F, ModSoundGroups.WATER_BUCKET);
    public static final Block MILK_BUCKET = createFoodBlock("milk_bucket", 1,
            MapColor.WHITE, 0.2F, ModSoundGroups.WATER_BUCKET);
    public static final Block LAVA_BUCKET = createFoodBlock("lava_bucket", 1,
            MapColor.ORANGE, 0.2F, ModSoundGroups.LAVA_BUCKET);

    // 其他
    public static final Block PUMPKIN_PIE = createFoodBlock("pumpkin_pie", 1,
            MapColor.TERRACOTTA_ORANGE, 0.2F, BlockSoundGroup.WOOL);
    public static final Block CHORUS_FRUIT = registerFoodBlock("chorus_fruit", 5,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PURPLE)
                    .strength(0.2F, 0.2F)
                    .nonOpaque()
                    .sounds(ModSoundGroups.CHORUS_FRUIT)
                    .pistonBehavior(PistonBehavior.DESTROY),
            ChorusFruitBlock::new);
    public static final Block EGG = createFoodBlock("egg", 5, MapColor.WHITE, 0.2F, ModSoundGroups.EGG);
    public static final Block TOTEM_OF_UNDYING = registerFoodBlock("totem_of_undying", 1,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.YELLOW)
                    .strength(0.2F, 0.2F)
                    .nonOpaque()
                    .sounds(ModSoundGroups.EGG)
                    .pistonBehavior(PistonBehavior.DESTROY),
            (settings, foodVal) -> new ModTotemBlock(settings));

    // 药水
    public static final Block POTION = registerFoodBlock("potion", 3,
            AbstractBlock.Settings.create()
                    .mapColor(MapColor.PURPLE)
                    .strength(0.2F, 0.2F)
                    .nonOpaque()
                    .solidBlock(Blocks::never)
                    .sounds(ModSoundGroups.POTION)
                    .pistonBehavior(PistonBehavior.DESTROY),
            PotionBlock::new);
    public static final Block GLASS_BOTTLE = createFoodBlock("glass_bottle", 3,
            MapColor.WHITE, 0.2F, ModSoundGroups.POTION);
    public static final Block HONEY_BOTTLE = createFoodBlock("honey_bottle", 3,
            MapColor.ORANGE, 0.2F, ModSoundGroups.POTION);

    /**
     * 基础方法：注册食物方块
     *
     * @param id 方块ID
     * @param foodValue 食物值（用于确定最大数量）
     * @param settings 方块设置
     * @param blockCreator 创建方块的函数
     * @return 创建的方块
     */
    private static Block registerFoodBlock(String id, int foodValue, AbstractBlock.Settings settings,
                                           BiFunction<AbstractBlock.Settings, Integer, Block> blockCreator) {
        Block block = DFoodUtils.createFoodBlock(foodValue, settings, blockCreator);
        FOOD_BLOCK_REGISTRY.put(id, block);
        return block;
    }

    /**
     * 创建普通食物方块的通用方法
     */
    private static Block createFoodBlock(String id, int foodValue, MapColor mapColor, float strength, BlockSoundGroup sound) {
        return createFoodBlock(id, mapColor, strength, sound, foodValue, null);
    }

    /**
     * 创建普通食物方块的通用方法（作物类型）
     */
    private static Block createFoodBlock(String id, MapColor mapColor, float strength, BlockSoundGroup sound,
                                         int foodValue, FoodBlock.EnforceAsItem cropType) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .strength(strength, strength)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.DESTROY);

        if (sound != null) {
            settings = settings.sounds(sound);
        }

        if (cropType != null) {
            return registerFoodBlock(id, foodValue, settings, (s, foodVal) -> new FoodBlock(s, foodVal, cropType));
        } else {
            return registerFoodBlock(id, foodValue, settings, FoodBlock::new);
        }
    }
}