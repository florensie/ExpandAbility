package be.florens.swimmies.test.forge;

import be.florens.swimmies.test.SwimmiesTest;
import net.minecraftforge.fml.common.Mod;

@Mod(SwimmiesTest.MOD_ID)
public class SwimmiesTestForge {

	public SwimmiesTestForge() {
		SwimmiesTest.init();
	}
}
