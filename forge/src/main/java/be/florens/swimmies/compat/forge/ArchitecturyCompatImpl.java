package be.florens.swimmies.compat.forge;

import be.florens.swimmies.api.forge.PlayerSwimEvent;
import be.florens.swimmies.compat.ArchitecturyCompat;
import net.minecraft.world.InteractionResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class ArchitecturyCompatImpl {

    public static void platformInit() {
        MinecraftForge.EVENT_BUS.addListener(ArchitecturyCompatImpl::onPlayerSwim);
    }

    @SubscribeEvent
    public static void onPlayerSwim(PlayerSwimEvent playerSwimEvent) {
        playerSwimEvent.setResult(getEventResult(ArchitecturyCompat.swimmie));
    }

    private static Event.Result getEventResult(InteractionResult interactionResult) {
        switch (ArchitecturyCompat.swimmie) {
            case SUCCESS:
                return Event.Result.ALLOW;
            case FAIL:
                return Event.Result.DENY;
            default:
                return Event.Result.DEFAULT;
        }
    }
}
