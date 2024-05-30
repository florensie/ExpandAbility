package be.florens.expandability.api.fabric;

import be.florens.expandability.EventResult;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.player.Player;

/**
 * Event that allows enabling/disabling the vanilla swimming behaviour even when not in a fluid.
 * Fired multiple times per tick on the client and server.
 */
public interface PlayerSwimCallback {

    Event<PlayerSwimCallback> EVENT = EventFactory.createArrayBacked(PlayerSwimCallback.class,
            (listeners) -> (player) -> {
                for (PlayerSwimCallback listener : listeners) {
                    EventResult result = listener.swim(player);

                    if (result != EventResult.PASS) {
                        return result;
                    }
                }
                return EventResult.PASS;
            });

    /**
     * @param player The player that is trying to swim
     * @return {@link EventResult#SUCCESS} to enable swimming, {@link EventResult#FAIL} to disable
     *         and {@link EventResult#PASS} for vanilla behaviour
     */
    EventResult swim(Player player);
}
