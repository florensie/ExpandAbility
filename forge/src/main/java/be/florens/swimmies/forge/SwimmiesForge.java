package be.florens.swimmies.forge;

import be.florens.swimmies.Swimmies;
import be.florens.swimmies.compat.ArchitecturyCompat;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod(Swimmies.MOD_ID)
public class SwimmiesForge {

	public SwimmiesForge() {
		Swimmies.init();
		if (ModList.get().isLoaded("architectury")) {
			ArchitecturyCompat.init();
		}
	}
}
