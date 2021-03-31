package be.florens.swimmies.api;

import be.florens.swimmies.EventFactory;
import me.shedaniel.architectury.event.Event;
import net.minecraft.world.entity.player.Player;

/**
 * Event that allows enabling the vanilla swimming behaviour even when not in a fluid
 */
public interface PlayerSwimEvent {

	Event<PlayerSwimEvent> EVENT = EventFactory.createBoolean();

	/**
	 * @param player The player that is trying to swim
	 * @return return true to enable swimming, false to default to vanilla behaviour
	 */
	boolean swim(Player player);
}
