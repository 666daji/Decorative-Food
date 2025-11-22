package org.dfood.util.mixin;

import net.minecraft.item.ItemStack;

/**
 * 告示牌物品悬挂功能接口 - 简化版
 * 为告示牌添加正反两面物品悬挂能力，每面1个槽位，最大堆叠数为1
 */
public interface dFoodSignBlockEntity {

    /**
     * 获取指定面的物品
     * @param front true=正面, false=背面
     * @return 对应面的物品堆叠
     */
    default ItemStack dFood$getItem(boolean front) {
        return ItemStack.EMPTY;
    }

    /**
     * 设置指定面的物品
     * @param front true=正面, false=背面
     * @param stack 要设置的物品堆叠
     */
    default void dFood$setItem(boolean front, ItemStack stack) {
        throw new UnsupportedOperationException();
    }

    /**
     * 尝试添加物品到指定面
     * @param front true=正面, false=背面
     * @param stack 要添加的物品堆叠
     * @return 是否添加成功
     */
    default boolean dFood$tryAddItem(boolean front, ItemStack stack) {
        return false;
    }

    /**
     * 获取指定面是否有物品
     * @param front true=正面, false=背面
     * @return 是否有物品
     */
    default boolean dFood$hasItem(boolean front) {
        return false;
    }

    /**
     * 获取需要返还的铁粒总数（每个悬挂的物品对应1个铁粒）
     * @return 需要返还的铁粒数量
     */
    default int dFood$getIronNuggetsToReturn() {
        return 0;
    }
}