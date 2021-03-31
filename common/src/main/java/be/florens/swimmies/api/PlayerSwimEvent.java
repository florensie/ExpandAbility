package be.florens.swimmies.api;

import me.shedaniel.architectury.event.Event;
import me.shedaniel.architectury.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface PlayerSwimEvent {

	Event<PlayerSwimEvent> EVENT = EventFactory.createInteractionResult();

	InteractionResult swim(Player player);
}
