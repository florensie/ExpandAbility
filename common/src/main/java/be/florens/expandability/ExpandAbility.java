package be.florens.expandability;

import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExpandAbility {

	public static final String MOD_ID = "expandability";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("ExpandAbility here, who dis?");
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}
}
