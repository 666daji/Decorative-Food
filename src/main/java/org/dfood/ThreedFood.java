package org.dfood;

import net.fabricmc.api.ModInitializer;

import org.dfood.block.entity.ModBlockEntityTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreedFood implements ModInitializer {
	public static final String MOD_ID = "dfood";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
        ModBlockEntityTypes.register();
		LOGGER.info("Hello Fabric world!");
	}
}