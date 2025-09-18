package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SuspiciousStewBlockEntity extends BlockEntity {
    protected final Map<Integer, Integer> EffectMap = new HashMap<>();

    public SuspiciousStewBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SuspiciousStewBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.SUSPICIOUS_STEW_BLOCK_ENTITY, pos, state);
    }

    private void readEffectsFromNbt(NbtCompound nbt) {
        EffectMap.clear();
        // 检查NBT中是否包含效果列表
        if (nbt != null && nbt.contains("Effects", NbtElement.LIST_TYPE)) {
            NbtList effectsList = nbt.getList("Effects", NbtElement.COMPOUND_TYPE);
            // 获取效果ID
            for (int i = 0; i < effectsList.size(); i++) {
                NbtCompound effectTag = effectsList.getCompound(i);
                if (effectTag.contains("EffectId", NbtElement.NUMBER_TYPE)) {
                    int effectId = effectTag.getInt("EffectId");
                    // 获取持续时间，如果不存在则使用默认值160
                    int duration = effectTag.contains("EffectDuration", NbtElement.NUMBER_TYPE)
                            ? effectTag.getInt("EffectDuration")
                            : 160;

                    EffectMap.put(effectId, duration);
                }
            }
        }
    }

    public void readCustomDataFromItem(NbtCompound nbt) {
        readEffectsFromNbt(nbt);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        readEffectsFromNbt(nbt);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        writeCustomDataToItem(nbt);
    }

    public void writeCustomDataToItem(NbtCompound nbt) {
        if (!EffectMap.isEmpty()) {
            NbtList effectsList = new NbtList();

            for (Map.Entry<Integer, Integer> entry : EffectMap.entrySet()) {
                NbtCompound effectTag = new NbtCompound();
                effectTag.putInt("EffectId", entry.getKey());
                effectTag.putInt("EffectDuration", entry.getValue());
                effectsList.add(effectTag);
            }

            nbt.put("Effects", effectsList);
        }
    }

    public ItemStack getStewStack() {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW);
        writeCustomDataToItem(stack.getOrCreateNbt());
        return stack;
    }

    public Map<Integer, Integer> getEffectMap() {
        return new HashMap<>(EffectMap);
    }

    public void addEffect(int effectId, int duration) {
        EffectMap.put(effectId, duration);
        this.markDirty();
    }

    public void clearEffects() {
        EffectMap.clear();
        this.markDirty();
    }
}
