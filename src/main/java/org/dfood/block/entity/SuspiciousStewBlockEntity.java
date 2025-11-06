package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class SuspiciousStewBlockEntity extends BlockEntity {
    protected final Map<Identifier, Integer> EffectMap = new HashMap<>();

    public SuspiciousStewBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public SuspiciousStewBlockEntity(BlockPos pos, BlockState state) {
        this(ModBlockEntityTypes.SUSPICIOUS_STEW_BLOCK_ENTITY, pos, state);
    }

    private void readEffectsFromComponent(SuspiciousStewEffectsComponent component) {
        EffectMap.clear();

        // 如果组件存在且包含效果，则处理每个效果
        if (component != null && component.effects() != null) {
            for (SuspiciousStewEffectsComponent.StewEffect stewEffect : component.effects()) {
                // 获取效果的注册表ID作为键
                RegistryEntry<StatusEffect> effectEntry = stewEffect.effect();
                if (effectEntry.getKey().isPresent()) {
                    Identifier effectId = effectEntry.getKey().get().getValue();
                    int duration = stewEffect.duration();
                    EffectMap.put(effectId, duration);
                }
            }
        }
    }

    public void readCustomDataFromItem(SuspiciousStewEffectsComponent component) {
        readEffectsFromComponent(component);
        this.markDirty();
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.readNbt(nbt, registryLookup);

        EffectMap.clear();

        // 从NBT读取效果数据
        if (nbt.contains("Effects", NbtElement.LIST_TYPE)) {
            NbtList effectsList = nbt.getList("Effects", NbtElement.COMPOUND_TYPE);

            for (int i = 0; i < effectsList.size(); i++) {
                NbtCompound effectTag = effectsList.getCompound(i);
                if (effectTag.contains("EffectId", NbtElement.STRING_TYPE) &&
                        effectTag.contains("EffectDuration", NbtElement.INT_TYPE)) {

                    Identifier effectId = Identifier.tryParse(effectTag.getString("EffectId"));
                    int duration = effectTag.getInt("EffectDuration");

                    if (effectId != null) {
                        EffectMap.put(effectId, duration);
                    }
                }
            }
        }
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup) {
        super.writeNbt(nbt, registryLookup);

        // 将效果数据写入NBT
        if (!EffectMap.isEmpty()) {
            NbtList effectsList = new NbtList();

            for (Map.Entry<Identifier, Integer> entry : EffectMap.entrySet()) {
                NbtCompound effectTag = new NbtCompound();
                effectTag.putString("EffectId", entry.getKey().toString());
                effectTag.putInt("EffectDuration", entry.getValue());
                effectsList.add(effectTag);
            }

            nbt.put("Effects", effectsList);
        }
    }

    public SuspiciousStewEffectsComponent createStewEffectsComponent() {
        if (EffectMap.isEmpty()) {
            return SuspiciousStewEffectsComponent.DEFAULT;
        }

        // 将EffectMap转换为StewEffect列表
        var effects = EffectMap.entrySet().stream()
                .map(entry -> {
                    // 通过注册表查找StatusEffect
                    RegistryEntry<StatusEffect> effectEntry = getRegistryEntry(entry.getKey());
                    return new SuspiciousStewEffectsComponent.StewEffect(
                            effectEntry,
                            entry.getValue()
                    );
                })
                .toList();

        return new SuspiciousStewEffectsComponent(effects);
    }

    // 辅助方法：通过Identifier获取StatusEffect的注册表条目
    private RegistryEntry<StatusEffect> getRegistryEntry(Identifier effectId) {
        if (this.world != null) {
            var registry = this.world.getRegistryManager().get(net.minecraft.registry.RegistryKeys.STATUS_EFFECT);
            return registry.getEntry(registry.get(effectId));
        }
        return null;
    }

    public ItemStack getStewStack() {
        ItemStack stack = new ItemStack(Items.SUSPICIOUS_STEW);

        // 设置组件
        SuspiciousStewEffectsComponent component = createStewEffectsComponent();
        if (component != SuspiciousStewEffectsComponent.DEFAULT) {
            stack.set(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS, component);
        }

        return stack;
    }

    public Map<Identifier, Integer> getEffectMap() {
        return new HashMap<>(EffectMap);
    }

    public void addEffect(Identifier effectId, int duration) {
        EffectMap.put(effectId, duration);
        this.markDirty();
    }

    public void clearEffects() {
        EffectMap.clear();
        this.markDirty();
    }
}