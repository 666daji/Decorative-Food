package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.listener.ClientPlayPacketListener;
import org.jetbrains.annotations.Nullable;

public class PotionBlockEntity extends ComplexFoodBlockEntity {
    public PotionBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.POTION_BLOCK_ENTITY, pos, state);
    }

    /**
     * 获取指定索引处的药水
     */
    @Nullable
    public Potion getPotionAtIndex(int index) {
        NbtCompound nbt = getNbtAt(index);
        return nbt != null ? getPotionFromNbt(nbt) : null;
    }

    /**
     * 从NBT中读取药水数据
     */
    private Potion getPotionFromNbt(NbtCompound nbt) {
        if (nbt.contains("Potion")) {
            return Potion.byId(nbt.getString("Potion"));
        }
        return Potions.EMPTY;
    }

    public int getColor(int index) {
        NbtCompound nbt = getNbtAt(index);
        if (nbt != null && !nbt.isEmpty()) {
            Potion potion = getPotionFromNbt(nbt);
            return potion != null ? PotionUtil.getColor(potion) : 16253176;
        }
        return 16253176; // 默认颜色
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }
}
