package org.dfood;

import net.fabricmc.api.ClientModInitializer;
import org.dfood.render.ModBlockColors;
import org.dfood.render.ModRenderLayers;

public class ThreedfoodClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
        ModBlockColors.registryColors();
        ModRenderLayers.registryRenderLayer();
	}
}