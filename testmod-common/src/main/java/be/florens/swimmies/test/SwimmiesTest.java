package be.florens.swimmies.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwimmiesTest {

	public static final String MOD_ID = "swimmies-test";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Swimmies testmod initializing");
	}
}
