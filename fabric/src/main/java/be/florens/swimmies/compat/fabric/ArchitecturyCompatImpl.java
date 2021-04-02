package be.florens.swimmies.compat.fabric;

import be.florens.swimmies.api.fabric.PlayerSwimCallback;
import be.florens.swimmies.compat.ArchitecturyCompat;

public class ArchitecturyCompatImpl {

    public static void platformInit() {
        PlayerSwimCallback.EVENT.register(player -> ArchitecturyCompat.swimmie);
    }
}
