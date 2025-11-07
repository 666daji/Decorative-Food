package org.dfood.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
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
    protected void readData(ReadView view) {
        super.readData(view);

        EffectMap.clear();

        // 从ReadView读取效果数据
        if (view.getOptionalListReadView("Effects").isPresent()) {
            ReadView.ListReadView effectsList = view.getListReadView("Effects");

            for (ReadView effectView : effectsList) {
                if (effectView.getOptionalString("EffectId").isPresent() &&
                        effectView.getOptionalInt("EffectDuration").isPresent()) {

                    Identifier effectId = Identifier.tryParse(effectView.getString("EffectId", ""));
                    int duration = effectView.getInt("EffectDuration", 0);

                    if (effectId != null) {
                        EffectMap.put(effectId, duration);
                    }
                }
            }
        }
    }

    @Override
    protected void writeData(WriteView view) {
        super.writeData(view);

        // 将效果数据写入WriteView
        if (!EffectMap.isEmpty()) {
            WriteView.ListView effectsList = view.getList("Effects");

            for (Map.Entry<Identifier, Integer> entry : EffectMap.entrySet()) {
                WriteView effectView = effectsList.add();
                effectView.putString("EffectId", entry.getKey().toString());
                effectView.putInt("EffectDuration", entry.getValue());
            }
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
        return Registries.STATUS_EFFECT.getEntry(Registries.STATUS_EFFECT.get(effectId));
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