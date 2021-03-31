package be.florens.swimmies.fabric;

import be.florens.swimmies.Swimmies;
import net.fabricmc.api.ModInitializer;

public class SwimmiesFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		Swimmies.init();
	}
}
