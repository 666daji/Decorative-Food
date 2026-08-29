package org.dfood.replace;

import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import org.dfood.block.*;
import org.dfood.sound.ModSoundGroups;
import org.dfood.util.DFoodUtils;
import org.dfood.util.IntPropertyManager;

import java.util.HashMap;
import java.util.Map;

/**
 * <h1>原版食物 → 可放置的食物方块</h1>
 *
 * <p>本模组让原本只能食用的原版食物物品，也能像方块一样被放置到地面上，并以 3D 形态堆叠起来。
 * 实现上并不新增一个平行物品，而是把<b>原版物品本身</b>替换成「既是食物、又能放置成方块」的方块物品：
 * 对着地面右键时，食物不会直接掉进快捷栏或被吃掉，而是会在表面摆成一叠。</p>
 *
 * <h2>实现机制</h2>
 * <p>「放置」能力由几条相互配合的路径共同构成：</p>
 * <ol>
 *   <li><b>声明方块（本类）</b>：为每种参与的食物声明一个 {@link FoodBlock}（或其子类）实例，
 *       放进 {@link #FOOD_BLOCK_REGISTRY}。方块本体负责承载堆叠数量与摆放造型，见
 *       {@link FoodBlock}。</li>
 *   <li><b>注册方块（{@link org.dfood.mixin.BlocksMixin}）</b>：在原版 {@code Blocks} 类的
 *       静态初始化（{@code <clinit>} 的 HEAD）里，把 {@link #FOOD_BLOCK_REGISTRY} 中的方块按对应
 *       食物的 id 注册进 {@code Registries.BLOCK}，使方块在游戏启动时就已存在。</li>
 *   <li><b>替换物品（{@link org.dfood.replace.FoodToBlocks} +
 *       {@link org.dfood.mixin.FoodToBlockMixin}）</b>：{@code FoodToBlocks.FOOD_MAP} 保存
 *       「食物 id → 模组方块物品」的映射。{@code FoodToBlockMixin} 在
 *       {@code Items.register(String, Item)} 的头部拦截注册：先把<b>原版</b>物品以
 *       {@code <id>_de} 的新标识符注册进 {@code Registries.ITEM}（原物品自此被「遗弃」，不再占用
 *       原 id），再用映射里的<b>模组</b>物品顶替原 id，于是诸如 {@code cookie} 这样的 id 现在指向的
 *       是可放置的模组方块物品。</li>
 *   <li><b>包装特殊物品（{@link org.dfood.item.HaveBlock}）</b>：<b>桶、蛋、药水、炖菜</b>等原版
 *       物品类自带额外行为（盛放流体、投掷、喝下等），无法用普通 {@code BlockItem} 顶替。于是让这些
 *       物品<b>继续继承原版物品类</b>、同时实现 {@code HaveBlock} 接口，在原版行为之外多出一条放置路径；
 *       注册完成后（{@code registerBlock} 注入点）再把这些物品补进 {@code Item.BLOCK_ITEMS}，
 *       维持「方块 → 物品」的反向映射。</li>
 *   <li><b>化解「放置 vs 食用」冲突（{@link org.dfood.mixin.BlockItemMixin}）</b>：端着食物右键
 *       既可能被当成放置，也可能被当成食用。{@code BlockItemMixin} 在 {@code useOnBlock} 的头部拦截：
 *       目标方块是模组食物方块（或不死图腾）且玩家<b>未潜行</b>时返回 {@code PASS}，把处理权交还
 *       「使用物品」逻辑，于是普通右键是<b>吃</b>，<b>潜行</b>右键才是<b>放置</b>。</li>
 * </ol>
 *
 * <h2>放置后的行为</h2>
 * <p>方块被放置后是可堆叠的 {@link FoodBlock}：再往上放同种食物会使 {@link FoodBlock#NUMBER_OF_FOOD}
 * 加一（上限为 {@link FoodBlock#MAX_FOOD}），右键可取出 1 份，破坏则按堆叠数量掉落对应物品。大多数食物
 * 用普通 {@code BlockItem}（{@code createItem}）承载；少数（马铃薯、胡萝卜、甜浆果、发光浆果）因要绑定
 * <b>农作物的成熟产物</b>，通过 {@code cItem} 与 {@link EnforceAsItems} 强制指定方块对应的物品。</p>
 *
 * <h2>关联模块与一致性约束</h2>
 * <p>一条食物链的对应关系散落在三处，必须保持一致：{@link #FOOD_BLOCK_REGISTRY}（方块）、
 * {@link org.dfood.replace.FoodToBlocks#FOOD_MAP}（物品映射）与 {@code Item.BLOCK_ITEMS}（反向映射）。</p>
 *
 * <p>存在注册顺序依赖的食物（如蜂蜜瓶的配方剩余物依赖玻璃瓶、各桶依赖空桶）采用<b>延时注册</b>，
 * 逻辑见 {@link org.dfood.replace.FoodToBlocks#getItem(String)}。</p>
 *
 * @see org.dfood.block.FoodBlock
 * @see org.dfood.replace.FoodToBlocks
 * @see org.dfood.mixin.FoodToBlockMixin
 * @see org.dfood.mixin.BlocksMixin
 * @see org.dfood.mixin.BlockItemMixin
 * @see org.dfood.item.HaveBlock
 */
public class FoodBlocks {
    /**
     * 方块注册表：键为原版食物物品的 id，值为本模组对应的可放置方块。
     */
    public static final Map<String, Block> FOOD_BLOCK_REGISTRY = new HashMap<>();

    // 零食
    public static final Block COOKIE = registerFoodBlock("cookie", 7,
            MapColor.TERRACOTTA_YELLOW, BlockSoundGroup.STONE);
    public static final Block APPLE = registerFoodBlock("apple", 5,
            MapColor.RED, BlockSoundGroup.STONE);
    public static final Block MELON_SLICE = registerFoodBlock("melon_slice", 5,
            MapColor.LIME, BlockSoundGroup.STONE);
    public static final Block BREAD = registerFoodBlock("bread", 5,
            MapColor.TERRACOTTA_YELLOW, ModSoundGroups.BREAD);
    public static final Block DRIED_KELP = registerFoodBlock("dried_kelp", 4,
            MapColor.GREEN, BlockSoundGroup.GRASS);

    // 蔬菜类
    public static final Block BEETROOT = registerFoodBlock("beetroot", 5,
            MapColor.RED, BlockSoundGroup.CANDLE);
    public static final Block POTATO = registerFoodBlock("potato", 5,
            MapColor.GOLD, BlockSoundGroup.CANDLE, EnforceAsItems.POTATO);
    public static final Block POISONOUS_POTATO = registerFoodBlock("poisonous_potato", 5,
            MapColor.GOLD, BlockSoundGroup.CANDLE);
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
            MapColor.GOLD, null);
    public static final Block ENCHANTED_GOLDEN_APPLE = registerFoodBlock("enchanted_golden_apple",
            EnchantedGoldenAppleBlock.Builder.create()
                    .maxFood(5)
                    .settings(DFoodUtils.getFoodBlockSettings().mapColor(MapColor.GOLD))
                    .build());

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
    public static final Block TROPICAL_FISH = registerFoodBlock("tropical_fish", 4,
            MapColor.RED, ModSoundGroups.FISH);

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

    // 怪物
    public static final Block SPIDER_EYE = registerFoodBlock("spider_eye", 6,
            MapColor.RED, ModSoundGroups.MEAT);

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