package org.dfood.util;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.*;
import net.minecraft.util.Rarity;
import org.dfood.block.FoodBlocks;
import org.dfood.item.*;
import org.dfood.mixin.FoodToBlockMixin;

import java.util.HashMap;
import java.util.Map;

/**
 * 一个映射类，使开发者可以高度自定义新的Item实例
 *
 * <p>该类通过映射表将原版食物物品替换为对应的方块物品，实现食物到方块的转换功能。</p>
 *
 * <h2>关于延时注册</h2>
 * <p>延时注册机制是为了解决物品注册时的依赖关系问题而设计的。在原版Minecraft中，
 * 某些物品的注册存在依赖顺序，例如蜂蜜瓶({@code honey_bottle})的注册依赖于玻璃瓶({@code glass_bottle})，
 * 因为蜂蜜瓶的配方剩余物设置为玻璃瓶。</p>
 *
 * <p>在食物到方块的替换过程中，如果直接在静态初始化阶段创建所有物品实例，可能会遇到以下问题：</p>
 * <ul>
 *   <li>当替换蜂蜜瓶时，需要引用{@linkplain  Items#GLASS_BOTTLE}作为配方剩余物</li>
 *   <li>但此时玻璃瓶可能尚未完成注册，导致{@linkplain  Items#GLASS_BOTTLE}为null</li>
 *   <li>这会导致替换的蜂蜜瓶错误地绑定空值，引发游戏崩溃</li>
 * </ul>
 *
 * <p>延时注册通过延迟实例化的方式，仅在真正需要时才创建这些有依赖关系的物品，
 * 确保所有依赖项都已正确初始化，从而避免了空引用和崩溃问题。</p>
 *
 * <p>对于有类似依赖关系的物品，都应使用延时注册机制进行处理。</p>
 *
 * @see FoodToBlockMixin
 * @see #getItem(String)
 */
public class FoodToBlocks {
    /**
     * @apiNote 需要延时注册的物品请在此Map中预留位置，值设为null。
     */
    public static final Map<String, Item> FOOD_MAP = new HashMap<>();

    static {
        // 零食类
        FOOD_MAP.put("cookie", createItem(FoodBlocks.COOKIE, FoodComponents.COOKIE));
        FOOD_MAP.put("apple", createItem(FoodBlocks.APPLE, FoodComponents.APPLE));
        FOOD_MAP.put("melon_slice", createItem(FoodBlocks.MELON_SLICE, FoodComponents.MELON_SLICE));
        FOOD_MAP.put("bread", createItem(FoodBlocks.BREAD, FoodComponents.BREAD));

        // 蔬菜类
        FOOD_MAP.put("beetroot", createItem(FoodBlocks.BEETROOT, FoodComponents.BEETROOT));
        FOOD_MAP.put("potato", new DoubleBlockItem(Blocks.POTATOES, new Item.Settings().food(FoodComponents.POTATO), FoodBlocks.POTATO));
        FOOD_MAP.put("baked_potato", createItem(FoodBlocks.BAKED_POTATO, FoodComponents.BAKED_POTATO));
        FOOD_MAP.put("carrot", new DoubleBlockItem(Blocks.CARROTS, new Item.Settings().food(FoodComponents.CARROT), FoodBlocks.CARROT));
        FOOD_MAP.put("sweet_berries", new DoubleBlockItem(Blocks.SWEET_BERRY_BUSH, new Item.Settings().food(FoodComponents.SWEET_BERRIES), FoodBlocks.SWEET_BERRIES));
        FOOD_MAP.put("glow_berries", new DoubleBlockItem(Blocks.CAVE_VINES, new Item.Settings().food(FoodComponents.GLOW_BERRIES), FoodBlocks.GLOW_BERRIES));

        // 金制食物
        FOOD_MAP.put("golden_apple", createItem(FoodBlocks.GOLDEN_APPLE, FoodComponents.GOLDEN_APPLE));
        FOOD_MAP.put("golden_carrot", createItem(FoodBlocks.GOLDEN_CARROT, FoodComponents.GOLDEN_CARROT));
        FOOD_MAP.put("glistering_melon_slice", new BlockItem(FoodBlocks.GLISTERING_MELON_SLICE, new Item.Settings()));
        FOOD_MAP.put("enchanted_golden_apple", new ModEnchantedGoldenAppleItem(new Item.Settings().rarity(Rarity.EPIC).food(FoodComponents.ENCHANTED_GOLDEN_APPLE), FoodBlocks.ENCHANTED_GOLDEN_APPLE));

        // 生熟肉类
        FOOD_MAP.put("chicken", createItem(FoodBlocks.CHICKEN, FoodComponents.CHICKEN));
        FOOD_MAP.put("cooked_chicken", createItem(FoodBlocks.COOKED_CHICKEN, FoodComponents.COOKED_CHICKEN));
        FOOD_MAP.put("beef", createItem(FoodBlocks.BEEF, FoodComponents.BEEF));
        FOOD_MAP.put("cooked_beef", createItem(FoodBlocks.COOKED_BEEF, FoodComponents.COOKED_BEEF));
        FOOD_MAP.put("mutton", createItem(FoodBlocks.MUTTON, FoodComponents.MUTTON));
        FOOD_MAP.put("cooked_mutton", createItem(FoodBlocks.COOKED_MUTTON, FoodComponents.COOKED_MUTTON));
        FOOD_MAP.put("porkchop", createItem(FoodBlocks.PORKCHOP, FoodComponents.PORKCHOP));
        FOOD_MAP.put("cooked_porkchop", createItem(FoodBlocks.COOKED_PORKCHOP, FoodComponents.COOKED_PORKCHOP));
        FOOD_MAP.put("rabbit", createItem(FoodBlocks.RABBIT, FoodComponents.RABBIT));
        FOOD_MAP.put("cooked_rabbit", createItem(FoodBlocks.COOKED_RABBIT, FoodComponents.COOKED_RABBIT));

        // 鱼类
        FOOD_MAP.put("cod", createItem(FoodBlocks.COD, FoodComponents.COD));
        FOOD_MAP.put("cooked_cod", createItem(FoodBlocks.COOKED_COD, FoodComponents.COOKED_COD));
        FOOD_MAP.put("salmon", createItem(FoodBlocks.SALMON, FoodComponents.SALMON));
        FOOD_MAP.put("cooked_salmon", createItem(FoodBlocks.COOKED_SALMON, FoodComponents.COOKED_SALMON));
        FOOD_MAP.put("pufferfish", createItem(FoodBlocks.PUFFERFISH, FoodComponents.PUFFERFISH));

        // 炖菜类
        FOOD_MAP.put("rabbit_stew", new ModStewItem(new Item.Settings().maxCount(1).food(FoodComponents.RABBIT_STEW), FoodBlocks.RABBIT_STEW));
        FOOD_MAP.put("mushroom_stew", new ModStewItem(new Item.Settings().maxCount(1).food(FoodComponents.MUSHROOM_STEW), FoodBlocks.MUSHROOM_STEW));
        FOOD_MAP.put("beetroot_soup", new ModStewItem(new Item.Settings().maxCount(1).food(FoodComponents.BEETROOT_SOUP), FoodBlocks.BEETROOT_SOUP));
        FOOD_MAP.put("suspicious_stew", new ModSuspiciousStewItem(new Item.Settings().maxCount(1).food(FoodComponents.SUSPICIOUS_STEW), FoodBlocks.SUSPICIOUS_STEW));
        FOOD_MAP.put("bowl", new BlockItem(FoodBlocks.BOWL, new Item.Settings()));

        // 桶
        FOOD_MAP.put("bucket", new ModBucketItem(Fluids.EMPTY, new Item.Settings().maxCount(16), FoodBlocks.BUCKET));
        FOOD_MAP.put("water_bucket", null);
        FOOD_MAP.put("milk_bucket", null);
        FOOD_MAP.put("lava_bucket", null);

        // 其他
        FOOD_MAP.put("pumpkin_pie", createItem(FoodBlocks.PUMPKIN_PIE, FoodComponents.PUMPKIN_PIE));
        FOOD_MAP.put("chorus_fruit", new ModChorusFruitItem(new Item.Settings().food(FoodComponents.CHORUS_FRUIT), FoodBlocks.CHORUS_FRUIT));
        FOOD_MAP.put("egg", new ModEggItem(new Item.Settings(), FoodBlocks.EGG));
        FOOD_MAP.put("totem_of_undying", new BlockItem(FoodBlocks.TOTEM_OF_UNDYING, new Item.Settings().maxCount(1).rarity(Rarity.UNCOMMON)));

        // 药水类
        FOOD_MAP.put("potion", new ModPotionItem(FoodBlocks.POTION, new Item.Settings().maxCount(1)));
        FOOD_MAP.put("glass_bottle", new ModGlassBottleItem(FoodBlocks.GLASS_BOTTLE, new Item.Settings()));
        FOOD_MAP.put("honey_bottle", null);
    }

    public static BlockItem createItem(Block foodBlock, FoodComponent foodComponent) {
        return new BlockItem(foodBlock, new Item.Settings().food(foodComponent));
    }

    /**
     * 通过id获取对应的物品。
     * @param id 要获取的物品
     * @return 对应的物品
     */
    public static Item getItem(String id){
        Item result = FOOD_MAP.get(id);
        if (result == null){
            return createDelayedItem(id);
        }
        return result;
    }

    /**
     * 创建需要延时注册的物品。
     * @param id 需要延时注册的物品id
     * @return 成功创建的物品，如果创建失败则返回null
     */
    private static Item createDelayedItem(String id) {
        synchronized (FOOD_MAP) {
            Item item = FOOD_MAP.get(id);
            if (item != null) {
                return item;
            }

            switch (id) {
                //桶
                case "milk_bucket":
                    item = new ModMilkBucketItem(new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1),
                            FoodBlocks.MILK_BUCKET);
                    break;
                case "water_bucket":
                    item = new ModBucketItem(Fluids.WATER,
                            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1), FoodBlocks.WATER_BUCKET);
                    break;
                case "lava_bucket":
                    item = new ModBucketItem(Fluids.LAVA,
                            new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1), FoodBlocks.LAVA_BUCKET);
                    break;

                // 药水类
                case "honey_bottle":
                    item = new ModHoneyBottleItem(FoodBlocks.HONEY_BOTTLE,
                            new Item.Settings().recipeRemainder(Items.GLASS_BOTTLE)
                                    .food(FoodComponents.HONEY_BOTTLE).maxCount(16));
                    break;

                default:
                    return null;
            }

            FOOD_MAP.put(id, item);
            return item;
        }
    }
}