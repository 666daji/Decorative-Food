package org.dfood.render;

import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import org.dfood.block.FoodBlocks;

public class ModRenderLayers {
    private static final BlockRenderLayerMap instance = BlockRenderLayerMap.INSTANCE;

    public static void registryRenderLayer() {
        instance.putBlock(FoodBlocks.APPLE, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.GOLDEN_APPLE, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.GOLDEN_CARROT, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.BEETROOT, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.COD, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.COOKED_COD, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.SALMON, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.COOKED_SALMON, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.CHORUS_FRUIT, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.SWEET_BERRIES, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.GLOW_BERRIES, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.CARROT, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.POTION, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.GLASS_BOTTLE, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.HONEY_BOTTLE, RenderLayer.getCutout());
        instance.putBlock(FoodBlocks.SUSPICIOUS_STEW, RenderLayer.getCutout());
    }
}
