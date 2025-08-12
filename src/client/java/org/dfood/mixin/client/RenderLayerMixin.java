package org.dfood.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderLayers;
import org.dfood.block.foodBlocks;
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
        BLOCKS.put(foodBlocks.APPLE, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.GOLDEN_APPLE, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.GOLDEN_CARROT, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.BEETROOT, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.COD, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.COOKED_COD, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.SALMON, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.COOKED_SALMON, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.CHORUS_FRUIT, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.SWEET_BERRIES, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.GLOW_BERRIES, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.CARROT, RenderLayer.getCutout());
        BLOCKS.put(foodBlocks.POTION, RenderLayer.getCutout());
    }
}
