package org.dfood.mixin.client;

import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.render.model.UnbakedModel;
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
    public static Identifier MISSING_ID;

    @Inject(method = "getOrLoadModel", at = @At("HEAD"), cancellable = true)
    private void unLoadDeModel(Identifier id, CallbackInfoReturnable<UnbakedModel> cir) {
        // 检查是否满足条件：minecraft命名空间、以_de结尾
        boolean b2 = id.getNamespace().equals("minecraft");
        boolean b3 = id.getPath().endsWith("_de");

        if (b2 && b3) {
            // 直接从modelsToLoad中移除，避免进入加载流程
            this.modelsToLoad.remove(id);

            // 获取缺失模型（原版在异常处理中使用的方式）
            UnbakedModel missingModel = this.unbakedModels.get(MISSING_ID);

            // 将缺失模型放入unbakedModels
            this.unbakedModels.put(id, missingModel);

            // 直接返回缺失模型，不再执行原版逻辑
            cir.setReturnValue(missingModel);
            cir.cancel();
        }
    }
}