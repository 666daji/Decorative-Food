package org.dfood.shape;

import java.util.HashMap;
import java.util.Map;

/**
 * @see FoodShapeHandle
 */
public class Shapes {
    public static Map<String,int[][]> shapeMap = registryShapeMap(new HashMap<>());

    /**
     * 注册形状示例
     *<pre>{@code
     *      shapeMap.put("minecraft:cookie",new int[][]{
     *                 {1, 1, 5}, {2, 3, 6}, {4, 10, 4}
     *         });
     *}</pre>
     */
    public static Map<String, int[][]> registryShapeMap(Map<String, int[][]> shapeMap) {
        // 零食
        shapeMap.put("minecraft:cookie",new int[][]{
                {1, 1, 5}, {2, 3, 6}, {4, 10, 4}
        });
        shapeMap.put("minecraft:apple", new int[][]{
                {1, 1, 11}, {2, 5, 8}
        });
        shapeMap.put("minecraft:melon_slice", new int[][]{
                {1, 1, 11}, {2, 3, 12}, {4, 5, 2}
        });
        shapeMap.put("minecraft:bread", new int[][]{
                {1, 2, 3}, {3, 4, 2}, {5, 5, 1}
        });
        // 蔬菜类
        shapeMap.put("minecraft:beetroot", new int[][]{
                {1, 1, 11}, {2, 5, 12}
        });
        shapeMap.put("minecraft:potato", new int[][]{
                {1, 1, 11}, {2, 4, 2}, {5, 5, 1}
        });
        shapeMap.put("minecraft:baked_potato", new int[][]{
                {1, 1, 11}, {2, 4, 2}, {5, 5, 1}
        });
        shapeMap.put("minecraft:carrot", new int[][]{
                {1, 3, 2}, {4, 5, 1}
        });
        shapeMap.put("minecraft:sweet_berries", new int[][]{
                {1, 1, 11}, {2, 2, 12}, {3, 5, 2}
        });
        shapeMap.put("minecraft:glow_berries", new int[][]{
                {1, 3, 11}, {4, 7, 12}, {8, 12, 2}
        });
        // 金制食物
        shapeMap.put("minecraft:golden_apple", new int[][]{
                {1, 1, 11}, {2, 5, 8}
        });
        shapeMap.put("minecraft:enchanted_golden_apple", new int[][]{
                {1, 1, 11}, {2, 5, 8}
        });
        shapeMap.put("minecraft:golden_carrot", new int[][]{
                {1, 3, 2}, {4, 5, 1}
        });
        shapeMap.put("minecraft:glistering_melon_slice", new int[][]{
                {1, 1, 11}, {2, 3, 12}, {4, 5, 2}
        });
        // 生熟肉类
        shapeMap.put("minecraft:chicken", new int[][]{
                {1, 1, 12}
        });
        shapeMap.put("minecraft:cooked_chicken", new int[][]{
                {1, 1, 12}
        });
        shapeMap.put("minecraft:beef", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:cooked_beef", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:mutton", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:cooked_mutton", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:porkchop", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:cooked_porkchop", new int[][]{
                {1, 1, 3}, {2, 2, 2}, {3, 3, 1}
        });
        shapeMap.put("minecraft:rabbit", new int[][]{
                {1, 1, 12}
        });
        shapeMap.put("minecraft:cooked_rabbit", new int[][]{
                {1, 1, 12}
        });
        // 鱼类
        shapeMap.put("minecraft:cod", new int[][]{
                {1, 3, 2}
        });
        shapeMap.put("minecraft:cooked_cod", new int[][]{
                {1, 3, 2}
        });
        shapeMap.put("minecraft:salmon", new int[][]{
                {1, 3, 1}
        });
        shapeMap.put("minecraft:cooked_salmon", new int[][]{
                {1, 3, 1}
        });
        shapeMap.put("minecraft:pufferfish", new int[][]{
                {1, 1, 7}
        });
        shapeMap.put("minecraft:tropical_fish", new int[][]{
                {1, 1, 11}, {2, 3, 12}, {4, 4, 2}
        });
        // 炖菜
        shapeMap.put("minecraft:beetroot_soup", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:mushroom_stew", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:rabbit_stew", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:bowl", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:suspicious_stew", new int[][]{
                {1, 1, 8}
        });
        // 桶
        shapeMap.put("minecraft:bucket", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:milk_bucket", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:water_bucket", new int[][]{
                {1, 1, 8}
        });
        shapeMap.put("minecraft:lava_bucket", new int[][]{
                {1, 1, 8}
        });
        // 其他
        shapeMap.put("minecraft:pumpkin_pie", new int[][]{
                {1, 1, 1}
        });
        shapeMap.put("minecraft:chorus_fruit", new int[][]{
                {1, 1, 11}, {2, 5, 8}
        });
        shapeMap.put("minecraft:egg", new int[][]{
                {1, 1, 11}, {2, 5, 12}
        });
        // 药水
        shapeMap.put("minecraft:potion", new int[][]{
                {1, 2, 8}, {3, 3, 1}
        });
        shapeMap.put("minecraft:glass_bottle", new int[][]{
                {1, 2, 8}, {3, 3, 1}
        });
        shapeMap.put("minecraft:honey_bottle", new int[][]{
                {1, 2, 8}, {3, 3, 1}
        });

        return shapeMap;
    }
}
