package be.florens.expandability.api.forge;

import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.bus.api.Event;

/**
 * Event that allows enabling/disabling the vanilla swimming behaviour even when not in a fluid.
 * Fired multiple times per tick on the client and server.
 *
 * This event has the following {@link Event.Result}:
 * <ul>
 *     <li>{@link Event.Result#DEFAULT}: Vanilla swimming behaviour</li>
 *     <li>{@link Event.Result#ALLOW}: Always swim, even when not in a fluid</li>
 *     <li>{@link Event.Result#DENY}: Never swim, even when in a fluid</li>
 * </ul>
 */
@Event.HasResult
public class PlayerSwimEvent extends PlayerEvent {

    public PlayerSwimEvent(Player player) {
        super(player);
    }
}
