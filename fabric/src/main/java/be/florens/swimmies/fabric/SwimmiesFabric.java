package be.florens.swimmies.fabric;

import be.florens.swimmies.Swimmies;
import be.florens.swimmies.compat.ArchitecturyCompat;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class SwimmiesFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Swimmies.init();
        if (FabricLoader.getInstance().isModLoaded("architectury")) {
            ArchitecturyCompat.init();
        }
    }
}
