package be.florens.expandability.forge;

import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class EventDispatcherImpl {

    public static InteractionResult onPlayerSwim(Player player) {
        PlayerSwimEvent event = new PlayerSwimEvent(player);
        MinecraftForge.EVENT_BUS.post(event);
        return getEventResult(event);
    }

    private static InteractionResult getEventResult(Event event) {
        switch (event.getResult()) {
            case ALLOW:
                return InteractionResult.SUCCESS;
            case DENY:
                return InteractionResult.FAIL;
            default:
                return InteractionResult.PASS;
        }
    }
}
