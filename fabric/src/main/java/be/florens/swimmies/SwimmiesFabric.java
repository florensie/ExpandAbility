package be.florens.swimmies;

import net.fabricmc.api.ModInitializer;

public class SwimmiesFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		Swimmies.init();
	}
}
