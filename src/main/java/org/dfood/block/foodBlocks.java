package org.dfood.block;

import net.minecraft.block.*;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.sound.BlockSoundGroup;
import org.dfood.sound.ModSoundGroups;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 关于原版的食物方块定义
 */
public class foodBlocks {
    public static final Map<String, Block> FOOD_BLOCK_REGISTRY = new HashMap<>();

    // 零食
    public static final Block COOKIE = createFoodBlock("cookie", MapColor.TERRACOTTA_YELLOW, 0.2F, null, 10);
    public static final Block APPLE = createFoodBlock("apple", MapColor.RED, 0.2F, null, 5);
    public static final Block MELON_SLICE = createFoodBlock("melon_slice", MapColor.LIME, 0.2F, null, 5);
    public static final Block BREAD = createFoodBlock("bread", MapColor.TERRACOTTA_YELLOW, 0.2F, ModSoundGroups.BREAD, 5);

    // 蔬菜类
    public static final Block BEETROOT = createFoodBlock("beetroot", MapColor.RED, 0.2F, BlockSoundGroup.CANDLE, 5);
    public static final Block POTATO = createFoodBlock("potato", MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE, 5, foodBlock.CROPS.POTATO);
    public static final Block BAKED_POTATO = createFoodBlock("baked_potato", MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE, 5);
    public static final Block CARROT = createFoodBlock("carrot", MapColor.YELLOW, 0.2F, BlockSoundGroup.CANDLE, 5, foodBlock.CROPS.CARROT);
    public static final Block SWEET_BERRIES = createFoodBlock("sweet_berries", MapColor.RED, 0.2F, BlockSoundGroup.SWEET_BERRY_BUSH, 5);
    public static final Block GLOW_BERRIES = registerFoodBlock("glow_berries", new foodBlock(AbstractBlock.Settings.create()
            .mapColor(MapColor.YELLOW).strength(0.2F, 0.2F).nonOpaque()
            .sounds(BlockSoundGroup.SWEET_BERRY_BUSH).pistonBehavior(PistonBehavior.DESTROY)
            .luminance(state -> state.get(foodBlock.NUMBER_OF_FOOD) + 3),
            12));

    // 金制食物
    public static final Block GOLDEN_APPLE = createFoodBlock("golden_apple", MapColor.GOLD, 0.2F, null, 5);
    public static final Block GOLDEN_CARROT = createFoodBlock("golden_carrot", MapColor.GOLD, 0.2F, BlockSoundGroup.CANDLE, 5);
    public static final Block GLISTERING_MELON_SLICE = createFoodBlock("glistering_melon_slice", MapColor.LIGHT_BLUE, 0.2F, null, 5);

    // 生熟肉类
    public static final Block CHICKEN = createFoodBlock("chicken", MapColor.LIGHT_GRAY, 0.2F, ModSoundGroups.MEAT, 1);
    public static final Block COOKED_CHICKEN = createFoodBlock("cooked_chicken", MapColor.TERRACOTTA_YELLOW, 0.2F, ModSoundGroups.MEAT, 1);
    public static final Block BEEF = createFoodBlock("beef", MapColor.BROWN, 0.3F, ModSoundGroups.MEAT, 3);
    public static final Block COOKED_BEEF = createFoodBlock("cooked_beef", MapColor.TERRACOTTA_BROWN, 0.3F, ModSoundGroups.MEAT, 3);
    public static final Block MUTTON = createFoodBlock("mutton", MapColor.PINK, 0.2F, ModSoundGroups.MEAT, 3);
    public static final Block COOKED_MUTTON = createFoodBlock("cooked_mutton", MapColor.TERRACOTTA_PINK, 0.2F, ModSoundGroups.MEAT, 3);
    public static final Block PORKCHOP = createFoodBlock("porkchop", MapColor.PINK, 0.2F, ModSoundGroups.MEAT, 3);
    public static final Block COOKED_PORKCHOP = createFoodBlock("cooked_porkchop", MapColor.TERRACOTTA_PINK, 0.2F, ModSoundGroups.MEAT, 3);
    public static final Block RABBIT = createFoodBlock("rabbit", MapColor.BROWN, 0.2F, ModSoundGroups.MEAT, 1);
    public static final Block COOKED_RABBIT = createFoodBlock("cooked_rabbit", MapColor.TERRACOTTA_BROWN, 0.2F, ModSoundGroups.MEAT, 1);

    // 鱼类
    public static final Block COD = createFoodBlock("cod", MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH, 3);
    public static final Block COOKED_COD = createFoodBlock("cooked_cod", MapColor.TERRACOTTA_LIGHT_BLUE, 0.2F, ModSoundGroups.FISH, 3);
    public static final Block SALMON = createFoodBlock("salmon", MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH, 3);
    public static final Block COOKED_SALMON = createFoodBlock("cooked_salmon", MapColor.TERRACOTTA_LIGHT_BLUE, 0.2F, ModSoundGroups.FISH, 3);
    public static final Block PUFFERFISH = createFoodBlock("pufferfish", MapColor.LIGHT_BLUE, 0.2F, ModSoundGroups.FISH, 1);

    // 炖菜
    public static final Block RABBIT_STEW = createFoodBlock("rabbit_stew", MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT, 1);
    public static final Block MUSHROOM_STEW = createFoodBlock("mushroom_stew", MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT, 1);
    public static final Block BEETROOT_SOUP = createFoodBlock("beetroot_soup", MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT, 1);
    public static final Block SUSPICIOUS_STEW = createSpecialFoodBlock("suspicious_stew", MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT, 1,
            settings -> new SuspiciousStewBlock(settings, 1));
    public static final Block BOWL = createFoodBlock("bowl", MapColor.BROWN, 0.1F, BlockSoundGroup.DECORATED_POT, 1);

    // 桶
    public static final Block BUCKET = createFoodBlock("bucket", MapColor.WHITE, 1.0F, ModSoundGroups.BUCKET, 1);
    public static final Block WATER_BUCKET = createFoodBlock("water_bucket", MapColor.BLUE, 1.0F, ModSoundGroups.BUCKET, 1);
    public static final Block MILK_BUCKET = createFoodBlock("milk_bucket", MapColor.WHITE, 1.0F, ModSoundGroups.BUCKET, 1);

    // 其他
    public static final Block PUMPKIN_PIE = createFoodBlock("pumpkin_pie", MapColor.TERRACOTTA_ORANGE, 0.2F, BlockSoundGroup.WOOL, 1);
    public static final Block CHORUS_FRUIT = createSpecialFoodBlock("chorus_fruit", MapColor.PURPLE, 0.2F, ModSoundGroups.CHORUS_FRUIT, 5,
            settings -> new ChorusFruitBlock(settings, 5));
    public static final Block EGG = createFoodBlock("egg", MapColor.WHITE, 0.2F, ModSoundGroups.EGG, 5);
    public static final Block TOTEM_OF_UNDYING = createSpecialFoodBlock("totem_of_undying", MapColor.YELLOW, 1.0F, ModSoundGroups.TOTEM, 1,
            ModTotemBlock::new);

    // 药水
    public static final Block POTION = createSpecialFoodBlock("potion", MapColor.PURPLE, 0.2F, ModSoundGroups.POTION, 3,
            settings -> new PotionBlock(settings.nonOpaque().solidBlock(Blocks::never), 3));

    /**
     * 注册食物方块到映射表中
     * @param id 方块ID
     * @param block 方块对象
     * @return 注册的方块
     */
    private static Block registerFoodBlock(String id, Block block) {
        FOOD_BLOCK_REGISTRY.put(id, block);
        return block;
    }

    /**
     * 创建普通食物方块的通用方法
     */
    private static Block createFoodBlock(String id, MapColor mapColor, float strength, BlockSoundGroup sound, int foodValue) {
        return createFoodBlock(id, mapColor, strength, sound, foodValue, null);
    }

    /**
     * 创建普通食物方块的通用方法（支持作物类型）
     */
    private static Block createFoodBlock(String id, MapColor mapColor, float strength, BlockSoundGroup sound,
                                         int foodValue, foodBlock.CROPS cropType) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .strength(strength, strength)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.DESTROY);

        if (sound != null) {
            settings = settings.sounds(sound);
        }

        Block block = cropType != null ?
                new foodBlock(settings, foodValue, cropType) :
                new foodBlock(settings, foodValue);

        return registerFoodBlock(id, block);
    }

    /**
     * 创建特殊食物方块的通用方法
     */
    private static Block createSpecialFoodBlock(String id, MapColor mapColor, float strength, BlockSoundGroup sound,
                                                int foodValue, Function<AbstractBlock.Settings, Block> blockCreator) {
        AbstractBlock.Settings settings = AbstractBlock.Settings.create()
                .mapColor(mapColor)
                .strength(strength, strength)
                .nonOpaque()
                .pistonBehavior(PistonBehavior.DESTROY);

        if (sound != null) {
            settings = settings.sounds(sound);
        }

        Block block = blockCreator.apply(settings);
        return registerFoodBlock(id, block);
    }
}