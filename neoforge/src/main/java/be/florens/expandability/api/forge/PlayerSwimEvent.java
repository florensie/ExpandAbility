package be.florens.expandability.api.forge;

import be.florens.expandability.api.EventResult;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

/**
 * Event that allows enabling/disabling the vanilla swimming behaviour even when not in a fluid.
 * Fired multiple times per tick on the client and server.
 *
 * This event has the following {@link EventResult}:
 * <ul>
 *     <li>{@link EventResult#PASS}: Vanilla swimming behaviour</li>
 *     <li>{@link EventResult#SUCCESS}: Always swim, even when not in a fluid</li>
 *     <li>{@link EventResult#FAIL}: Never swim, even when in a fluid</li>
 * </ul>
 */
public class PlayerSwimEvent extends PlayerEvent {

    private EventResult result = EventResult.PASS;

    public PlayerSwimEvent(Player player) {
        super(player);
    }

    public EventResult getResult() {
        return result;
    }

    public void setResult(EventResult result) {
        this.result = result;
    }
}
