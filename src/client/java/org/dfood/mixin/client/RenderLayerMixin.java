package org.dfood.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.dfood.block.FoodBlocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(RenderLayers.class)
public class RenderLayerMixin {
    @Shadow @Final private static Map<Block, RenderLayer> BLOCKS;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        BLOCKS.put(FoodBlocks.APPLE, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.GOLDEN_APPLE, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.BEETROOT, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.COD, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.COOKED_COD, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.SALMON, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.COOKED_SALMON, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.CHORUS_FRUIT, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.SWEET_BERRIES, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.GLOW_BERRIES, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.CARROT, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.GOLDEN_CARROT, RenderLayer.getCutout());
        BLOCKS.put(FoodBlocks.POTION, RenderLayer.getCutout());
    }
}
