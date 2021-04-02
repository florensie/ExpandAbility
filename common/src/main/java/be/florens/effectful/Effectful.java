package be.florens.effectful;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Effectful {

	public static final String MOD_ID = "effectful";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Effectful here, who dis?");
	}
}
