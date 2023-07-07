package be.florens.expandability.api.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.player.Player;

/**
 * Event that allows enabling/disabling the vanilla swimming behaviour even when not in a fluid.
 * Fired multiple times per tick on the client and server.
 */
public interface PlayerSwimCallback {

    Event<PlayerSwimCallback> EVENT = EventFactory.createArrayBacked(PlayerSwimCallback.class,
            (listeners) -> (player) -> {
                for (PlayerSwimCallback listener : listeners) {
                    TriState result = listener.swim(player);

                    if (result == TriState.TRUE || result == TriState.FALSE) {
                        return result;
                    }
                }
                return TriState.DEFAULT;
            });

    /**
     * @param player The player that is trying to swim
     * @return {@link TriState#TRUE} to enable swimming, {@link TriState#FALSE} to disable
     *         and {@link TriState#DEFAULT} for vanilla behaviour
     */
    TriState swim(Player player);
}
