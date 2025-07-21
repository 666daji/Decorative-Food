package org.dfood;

import net.fabricmc.api.ModInitializer;

import org.dfood.item.ModItemGroups;
import org.dfood.item.Seeds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreedFood implements ModInitializer {
	public static final String MOD_ID = "dfood";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		Seeds.registerItems();
		ModItemGroups.registerItemGroups();
		LOGGER.info("Hello Fabric world!");
	}
}