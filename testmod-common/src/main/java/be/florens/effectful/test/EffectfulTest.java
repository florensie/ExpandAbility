package be.florens.effectful.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EffectfulTest {

	public static final String MOD_ID = "effectful-test";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Effectful testmod initializing");
	}
}
