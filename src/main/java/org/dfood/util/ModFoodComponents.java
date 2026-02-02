package org.dfood.util;

import net.minecraft.component.type.FoodComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

/**
 * 包含模组中非炖菜类食物的食物组件定义
 */
public class ModFoodComponents {
    // 零食类
    public static final FoodComponent COOKIE = createFood(2, 0.1F);
    public static final FoodComponent APPLE = createFood(4, 0.3F);
    public static final FoodComponent MELON_SLICE = createFood(2, 0.3F);
    public static final FoodComponent BREAD = createFood(5, 0.6F);

    // 蔬菜类
    public static final FoodComponent BEETROOT = createFood(1, 0.6F);
    public static final FoodComponent POTATO = createFood(1, 0.3F);
    public static final FoodComponent BAKED_POTATO = createFood(5, 0.6F);
    public static final FoodComponent CARROT = createFood(3, 0.6F);
    public static final FoodComponent SWEET_BERRIES = createFood(2, 0.1F);
    public static final FoodComponent GLOW_BERRIES = createFood(2, 0.1F);

    // 金制食物
    public static final FoodComponent GOLDEN_APPLE = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 0), 1.0F)
            .alwaysEdible()
            .build();
    public static final FoodComponent ENCHANTED_GOLDEN_APPLE = new FoodComponent.Builder()
            .nutrition(4)
            .saturationModifier(1.2F)
            .statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 400, 1), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 6000, 0), 1.0F)
            .statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 2400, 3), 1.0F)
            .alwaysEdible()
            .build();
    public static final FoodComponent GOLDEN_CARROT = new FoodComponent.Builder().nutrition(6).saturationModifier(1.2F).build();

    // 生熟肉类
    public static final FoodComponent CHICKEN = createFoodWithEffect(
            2, 0.3F,
            new StatusEffectInstance(StatusEffects.HUNGER, 600, 0),
            0.3F
    );
    public static final FoodComponent COOKED_CHICKEN = createFood(6, 0.6F);
    public static final FoodComponent BEEF = createFood(3, 0.3F);
    public static final FoodComponent COOKED_BEEF = createFood(8, 0.8F);
    public static final FoodComponent MUTTON = createFood(2, 0.3F);
    public static final FoodComponent COOKED_MUTTON = createFood(6, 0.8F);
    public static final FoodComponent PORKCHOP = createFood(3, 0.3F);
    public static final FoodComponent COOKED_PORKCHOP = createFood(8, 0.8F);
    public static final FoodComponent RABBIT = createFood(3, 0.3F);
    public static final FoodComponent COOKED_RABBIT = createFood(5, 0.6F);

    // 鱼类
    public static final FoodComponent COD = createFood(2, 0.1F);
    public static final FoodComponent COOKED_COD = createFood(5, 0.6F);
    public static final FoodComponent SALMON = createFood(2, 0.1F);
    public static final FoodComponent COOKED_SALMON = createFood(6, 0.8F);
    public static final FoodComponent PUFFERFISH = createFoodWithEffects(
            1, 0.1F,
            new StatusEffectInstance(StatusEffects.POISON, 1200, 1),
            new StatusEffectInstance(StatusEffects.HUNGER, 300, 2),
            new StatusEffectInstance(StatusEffects.NAUSEA, 300, 0)
    );

    // 其他非炖菜类
    public static final FoodComponent PUMPKIN_PIE = createFood(8, 0.3F);
    public static final FoodComponent CHORUS_FRUIT = createAlwaysEdibleFood(4, 0.3F);
    public static final FoodComponent POISONOUS_POTATO = createFoodWithEffect(
            2, 0.3F,
            new StatusEffectInstance(StatusEffects.POISON, 100, 0),
            0.6F
    );
    public static final FoodComponent HONEY_BOTTLE = createFood(6, 0.1F);

    // 创建基本食物组件
    private static FoodComponent createFood(int nutrition, float saturationModifier) {
        return new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .build();
    }
    private static FoodComponent createAlwaysEdibleFood(int nutrition, float saturationModifier){
        return new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .alwaysEdible()
                .build();
    }

    // 创建带状态效果的食物组件
    private static FoodComponent createFoodWithEffect(int nutrition, float saturationModifier,
                                                      StatusEffectInstance effect, float probability) {
        return new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier)
                .statusEffect(effect, probability)
                .build();
    }

    // 创建带多个状态效果的食物组件
    private static FoodComponent createFoodWithEffects(int nutrition, float saturationModifier,
                                                       StatusEffectInstance... effects) {
        FoodComponent.Builder builder = new FoodComponent.Builder()
                .nutrition(nutrition)
                .saturationModifier(saturationModifier);

        for (StatusEffectInstance effect : effects) {
            builder.statusEffect(effect, 1.0F);
        }

        return builder.build();
    }
}