package be.florens.effectful.forge;

import be.florens.effectful.Effectful;
import net.minecraftforge.fml.common.Mod;

@Mod(Effectful.MOD_ID)
public class EffectfulForge {

	public EffectfulForge() {
		Effectful.init();
	}
}
