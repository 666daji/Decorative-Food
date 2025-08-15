package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.ComponentMap;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;

import java.util.Objects;
import java.util.Optional;

public class PotionBlockEntity extends BlockEntity {
    private RegistryEntry<Potion> potion;

    public PotionBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public PotionBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.POTION_BLOCK_ENTITY, pos, state);
    }

    public void readCustomDataFromItem(PotionContentsComponent potionContents) {
        Optional<RegistryEntry<Potion>> potion = potionContents.potion();
        potion.ifPresent(potionRegistryEntry -> this.potion = potionRegistryEntry);
    }

    @Override
    protected void readData(ReadView view) {
        String potionId = view.getString("Potion","EMPTY");
        if (!Objects.equals(potionId, "EMPTY")){
            this.potion = byId(potionId);
        }
    }

    @Override
    protected void writeData(WriteView view) {
        if (potion != null) {
            view.putString("Potion", Objects.requireNonNull(Registries.POTION.getId(potion.value())).toString());
        }
    }

    public RegistryEntry<Potion> byId(String potionId) {
        return RegistryEntry.of(Registries.POTION.get(Identifier.tryParse(potionId)));
    }

    @Override
    protected void addComponents(ComponentMap.Builder builder) {
        if (potion != null) {
            builder.add(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potion));
        }
    }

    public RegistryEntry<Potion> getPotion() {
        return potion;
    }

    public PotionContentsComponent getPotionContents() {
        return potion != null ? new PotionContentsComponent(this.getPotion()) : new PotionContentsComponent(Potions.WATER);
    }

    public int getColor() {
        return potion != null
                ? this.getPotionContents().getColor()
                : 16253176;
    }

    @Override
    public NbtCompound toInitialChunkDataNbt(RegistryWrapper.WrapperLookup registries) {
        NbtCompound nbt = new NbtCompound();
        if (potion != null) {
            nbt.putString("Potion", Objects.requireNonNull(Registries.POTION.getId(potion.value())).toString());
        }
        return nbt;
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    public ItemStack getPotionStack() {
        ItemStack stack = new ItemStack(Items.POTION);
        if (potion != null) {
            stack.set(DataComponentTypes.POTION_CONTENTS, new PotionContentsComponent(potion));
        }
        return stack;
    }
}