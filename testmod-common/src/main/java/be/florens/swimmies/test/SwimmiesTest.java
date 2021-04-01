package be.florens.swimmies.test;

import be.florens.swimmies.api.PlayerSwimEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SwimmiesTest {

	public static final String MOD_ID = "swimmies-test";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

	public static void init() {
		LOGGER.info("Swimmies testmod initialized");
		PlayerSwimEvent.EVENT.register(event -> {
			Item heldItem = event.getPlayer().getMainHandItem().getItem();
			return heldItem == Items.DEBUG_STICK ? InteractionResult.SUCCESS
					: heldItem == Items.BARRIER ? InteractionResult.FAIL
					: InteractionResult.PASS;
		});
	}
}
