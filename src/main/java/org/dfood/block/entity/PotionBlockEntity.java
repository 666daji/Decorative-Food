package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.listener.ClientPlayPacketListener;

public class PotionBlockEntity extends BlockEntity {
    private Potion potion;

    public PotionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PotionBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.POTION_BLOCK_ENTITY, pos, state);
    }

    public void readCustomDataFromItem(NbtCompound nbt) {
        if (nbt.contains("Potion")) {
            this.potion = getPotionFromNbt(nbt);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.potion = getPotionFromNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (potion != null && potion != Potions.EMPTY) {
            nbt.putString("Potion", Registries.POTION.getId(this.potion).toString());
        } else {
            nbt.remove("Potion");
        }
    }

    public Potion getPotion() {
        return potion;
    }

    public int getColor() {
        if (this.potion != null) {
            return PotionUtil.getColor(this.potion);
        }
        return 16253176;
    }

    private Potion getPotionFromNbt(NbtCompound nbt) {
        if (nbt.contains("Potion")) {
            return Potion.byId(nbt.getString("Potion"));
        }
        return Potions.EMPTY;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return this.createNbt();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public ItemStack getPotionStack() {
        if (this.potion != null && this.potion != Potions.EMPTY) {
            return PotionUtil.setPotion(new ItemStack(Items.POTION), this.potion);
        }
        return new ItemStack(Items.POTION);
    }
}
