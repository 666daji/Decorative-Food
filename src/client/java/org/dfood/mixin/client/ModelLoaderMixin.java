package org.dfood.mixin.client;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Set;

@Mixin(ModelLoader.class)
public class ModelLoaderMixin {

    @Shadow
    @Final
    private Map<Identifier, UnbakedModel> unbakedModels;

    @Shadow
    @Final
    private Set<Identifier> modelsToLoad;

    @Shadow
    @Final
    public static ModelIdentifier MISSING_ID;

    @Inject(method = "getOrLoadModel", at = @At("HEAD"), cancellable = true)
    private void unLoadDeModel(Identifier id, CallbackInfoReturnable<UnbakedModel> cir) {
        if (id instanceof ModelIdentifier modelId) {
            // 检查是否满足条件：物品模型、minecraft命名空间、以_de结尾
            boolean b1 = modelId.getVariant().equals("inventory");
            boolean b2 = modelId.getNamespace().equals("minecraft");
            boolean b3 = modelId.getPath().endsWith("_de");

            if (b1 && b2 && b3) {
                // 直接从modelsToLoad中移除，避免进入加载流程
                this.modelsToLoad.remove(id);

                // 获取缺失模型（原版在异常处理中使用的方式）
                UnbakedModel missingModel = this.unbakedModels.get(MISSING_ID);

                // 将缺失模型放入unbakedModels（与原版异常处理一致）
                this.unbakedModels.put(id, missingModel);

                // 直接返回缺失模型，不再执行原版逻辑
                cir.setReturnValue(missingModel);
                cir.cancel();
            }
        }
    }
}