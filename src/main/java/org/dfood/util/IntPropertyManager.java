package org.dfood.util;

import net.minecraft.state.property.IntProperty;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于管理和创建自定义范围的 {@link IntProperty} 属性的工具类。
 *
 * <p>该类提供了两种主要机制来管理方块属性：
 * <ol>
 *   <li><strong>多属性缓存</strong> - 避免重复创建相同范围的属性实例，提高性能</li>
 *   <li><strong>预缓存机制</strong> - 解决方块构造函数中的时序问题，允许在构造函数之前指定属性范围</li>
 * </ol>
 *
 * <p><strong>使用示例：</strong>
 *
 * <p><strong>1. 直接创建属性（常规用法）：</strong>
 * <pre>{@code
 * // 在方块类中直接创建属性
 * public class FoodBlock extends Block {
 *     private final IntProperty NUMBER_OF_FOOD = IntPropertyManager.create("number_of_food", 1, 8);
 *
 *     public FoodBlock(Settings settings) {
 *         super(settings);
 *         this.setDefaultState(this.getStateManager().getDefaultState()
 *                 .with(FACING, Direction.NORTH)
 *                 .with(NUMBER_OF_FOOD, 1));
 *     }
 *
 *     @Override
 *     protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
 *         builder.add(FACING, NUMBER_OF_FOOD);
 *     }
 * }
 * }</pre>
 *
 * <p><strong>2. 使用预缓存机制（解决时序问题）：</strong>
 * <pre>{@code
 * // 在注册方块前预缓存属性信息
 * IntPropertyManager.preCache("number_of_food", 1, 12);
 *
 * // 然后创建方块实例
 * FoodBlock customFoodBlock = new FoodBlock(
 *     FabricBlockSettings.create().hardness(0.5f).resistance(0.5f)
 * );
 *
 * // 在方块的 appendProperties 方法中取用预缓存的属性
 * public class FoodBlock extends Block {
 *     private final IntProperty NUMBER_OF_FOOD;
 *
 *     public FoodBlock(Settings settings) {
 *         super(settings);
 *         this.NUMBER_OF_FOOD = null; // 将在 appendProperties 中初始化
 *     }
 *
 *     @Override
 *     protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
 *         if (this.NUMBER_OF_FOOD == null) {
 *             this.NUMBER_OF_FOOD = IntPropertyManager.take();
 *         }
 *         builder.add(FACING, NUMBER_OF_FOOD);
 *     }
 * }
 * }</pre>
 *
 * <p><strong>注意事项：</strong>
 * <ul>
 *   <li>预缓存机制每次只能存储一个属性信息，新的预缓存会覆盖之前的</li>
 *   <li>取用预缓存的属性后，缓存会被自动清除</li>
 *   <li>相同名称和范围的属性只会被创建一次，后续请求会返回缓存的实例</li>
 *   <li>Minecraft 允许重名的不同属性，因此不同范围的同名属性会被视为不同的属性</li>
 * </ul>
 *
 * @see IntProperty
 */
public class IntPropertyManager {
    // 多属性缓存，避免重复创建相同范围的属性
    private static final Map<String, IntProperty> PROPERTY_CACHE = new HashMap<>();

    // 预缓存信息
    private static PendingPropertyInfo pendingInfo = null;

    /**
     * 预缓存属性信息（不立即创建属性实例）
     *
     * @param name 属性名称
     * @param max 最大值
     */
    public static void preCache(String name, int max) {
        preCache(name, 1, max);
    }

    /**
     * 预缓存属性信息（不立即创建属性实例）
     *
     * @param name 属性名称
     * @param min 最小值
     * @param max 最大值
     */
    public static void preCache(String name, int min, int max) {
        if (max == min) {
            min -= 1;
        }
        pendingInfo = new PendingPropertyInfo(name, min, max);
    }

    /**
     * 取走预缓存的属性
     * 会检查多属性缓存，避免重复创建相同范围的属性
     *
     * @return 对应的 IntProperty 实例
     * @throws IllegalStateException 如果没有预缓存的属性信息
     */
    public static IntProperty take() {
        if (pendingInfo == null) {
            throw new IllegalStateException("No pre-cached property found");
        }

        // 生成缓存键
        String key = generateKey(pendingInfo.name, pendingInfo.min, pendingInfo.max);

        // 检查是否已存在相同范围的属性
        if (PROPERTY_CACHE.containsKey(key)) {
            IntProperty property = PROPERTY_CACHE.get(key);
            clearPending(); // 清除预缓存信息
            return property;
        }

        // 创建新属性并缓存
        IntProperty property = IntProperty.of(pendingInfo.name, pendingInfo.min, pendingInfo.max);
        PROPERTY_CACHE.put(key, property);
        clearPending(); // 清除预缓存信息
        return property;
    }

    /**
     * 直接创建或获取一个从1到指定最大值的 IntProperty
     *
     * @param name 属性名称
     * @param max 最大值
     * @return 对应的 IntProperty 实例
     */
    public static IntProperty create(String name, int max) {
        return create(name, 1, max);
    }

    /**
     * 直接创建或获取一个指定范围的 IntProperty
     *
     * @param name 属性名称
     * @param min 最小值
     * @param max 最大值
     * @return 对应的 IntProperty 实例
     */
    public static IntProperty create(String name, int min, int max) {
        if (max == min) {
            min -= 1;
        }
        String key = generateKey(name, min, max);

        // 检查是否已存在相同范围的属性
        if (PROPERTY_CACHE.containsKey(key)) {
            return PROPERTY_CACHE.get(key);
        }

        // 创建新属性并缓存
        IntProperty property = IntProperty.of(name, min, max);
        PROPERTY_CACHE.put(key, property);
        return property;
    }

    /**
     * 生成用于缓存的唯一键
     */
    private static String generateKey(String name, int min, int max) {
        return name + ":" + min + ":" + max;
    }

    /**
     * 清除预缓存信息
     */
    private static void clearPending() {
        pendingInfo = null;
    }

    /**
     * 检查是否有预缓存的属性信息
     *
     * @return 如果有预缓存的属性信息返回 true，否则返回 false
     */
    public static boolean hasPending() {
        return pendingInfo != null;
    }

    /**
     * 获取当前预缓存的属性信息（用于调试）
     *
     * @return 预缓存的属性信息，如果没有则返回 null
     */
    public static String getPendingInfo() {
        return pendingInfo != null ? pendingInfo.toString() : null;
    }

    /**
     * 清空所有缓存（主要用于测试或重新加载时）
     */
    public static void clearAll() {
        PROPERTY_CACHE.clear();
        clearPending();
    }

    /**
     * 获取当前多属性缓存的大小
     */
    public static int getCacheSize() {
        return PROPERTY_CACHE.size();
    }

    /**
     * 内部类，用于存储预缓存的属性信息
     */
    private record PendingPropertyInfo(String name, int min, int max) {
        @Override
        public @NotNull String toString() {
            return "PendingPropertyInfo{name='" + name + "', min=" + min + ", max=" + max + "}";
        }
    }
}