package be.florens.expandability.api.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

/**
 * Event that allows enabling/disabling the vanilla swimming behaviour even when not in a fluid.
 * Fired multiple times per tick on the client and server.
 */
public interface PlayerSwimCallback {

    Event<PlayerSwimCallback> EVENT = EventFactory.createArrayBacked(PlayerSwimCallback.class,
            (listeners) -> (player) -> {
                for (PlayerSwimCallback listener : listeners) {
                    InteractionResult result = listener.swim(player);

                    if (result.consumesAction() || result == InteractionResult.FAIL) {
                        return result;
                    }
                }
                return InteractionResult.PASS;
            });

    /**
     * @param player The player that is trying to swim
     * @return {@link InteractionResult#SUCCESS}/{@link InteractionResult#CONSUME} to enable swimming,
     *         {@link InteractionResult#FAIL} to disable and {@link InteractionResult#PASS} for vanilla behaviour
     */
    InteractionResult swim(Player player);
}
