package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.component.ComponentChanges;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.potion.Potion;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import org.dfood.block.PotionBlock;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * 药水方块对应的方块实体（适用于1.21.1+版本）。
 * <p>
 * 该实体用于存储药水类型和颜色等数据，并支持在方块和物品之间转换。
 * 继承自复杂食物方块实体以利用其组件数据存储机制。
 *
 * @see PotionBlock
 */
public class PotionBlockEntity extends ComplexFoodBlockEntity {
    public PotionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POTION_BLOCK_ENTITY, pos, state);
    }

    /**
     * 获取指定索引处的药水注册表条目。
     *
     * @param index 索引位置
     * @return 指定位置的药水注册表条目，若索引越界或无药水数据则返回null
     */
    @Nullable
    public RegistryEntry<Potion> getPotionAtIndex(int index) {
        PotionContentsComponent potionContents = getPotionContentsAtIndex(index);
        if (potionContents != null) {
            return potionContents.potion().orElse(null);
        }
        return null;
    }

    /**
     * 获取指定索引处的药水内容组件。
     *
     * @param index 索引位置，0为栈底，size-1为栈顶
     * @return 指定位置的药水内容组件，若索引越界或无药水数据则返回null
     */
    @Nullable
    public PotionContentsComponent getPotionContentsAtIndex(int index) {
        // 获取指定索引处的组件更改
        ComponentChanges changes = getComponentChangesAt(index);
        if (changes != null && !changes.isEmpty()) {
            // 从组件更改中获取药水内容组件
            Optional<? extends PotionContentsComponent> potionContents = changes.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContents != null && potionContents.isPresent()) {
                return potionContents.get();
            }
        }
        return null;
    }

    /**
     * 获取指定索引处的药水颜色。
     *
     * @param index 索引位置
     * @return 药水的颜色值，如果无药水则返回默认颜色
     */
    public int getColor(int index) {
        PotionContentsComponent potionContents = getPotionContentsAtIndex(index);
        if (potionContents != null) {
            return potionContents.getColor();
        }
        return 16253176; // 默认颜色
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registryLookup) {
        return this.createNbt(registryLookup);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}