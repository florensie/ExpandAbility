package be.florens.swimmies;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Function;

public class EventDispatcher {

	@ExpectPlatform
	public static List<Function<Player, Boolean>> getEventConsumers() {
		throw new IllegalStateException();
	}
}
