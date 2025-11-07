package org.dfood.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
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
    @Shadow @Final private static Map<Block, BlockRenderLayer> BLOCKS;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        BLOCKS.put(FoodBlocks.APPLE, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.GOLDEN_APPLE, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.GOLDEN_CARROT, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.BEETROOT, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.COD, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.COOKED_COD, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.SALMON, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.COOKED_SALMON, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.CHORUS_FRUIT, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.SWEET_BERRIES, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.GLOW_BERRIES, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.CARROT, BlockRenderLayer.CUTOUT);
        BLOCKS.put(FoodBlocks.POTION, BlockRenderLayer.CUTOUT);
    }
}
