package org.dfood.mixin.client;

import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderLayer;
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
    @Shadow @Final private static Map<Block, BlockRenderLayer> BLOCKS;

    @Inject(method = "<clinit>", at = @At("RETURN"))
    private static void init(CallbackInfo ci) {
        BLOCKS.put(foodBlocks.APPLE.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.GOLDEN_APPLE.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.GOLDEN_CARROT.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.BEETROOT.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.COD.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.COOKED_COD.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.SALMON.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.COOKED_SALMON.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.CHORUS_FRUIT.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.SWEET_BERRIES.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.GLOW_BERRIES.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.CARROT.getBlock(), BlockRenderLayer.CUTOUT);
        BLOCKS.put(foodBlocks.POTION.getBlock(), BlockRenderLayer.CUTOUT);
    }
}
