package be.florens.swimmies.api;

import be.florens.swimmies.EventFactory;
import me.shedaniel.architectury.event.Event;
import net.minecraft.world.entity.player.Player;

public interface PlayerSwimEvent {

	Event<PlayerSwimEvent> EVENT = EventFactory.createBoolean();

	boolean swim(Player player);
}
