package be.florens.swimmies.forge;

import net.minecraft.world.entity.player.Player;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.function.Function;

public class EventDispatcherImpl {

	public static List<Function<Player, Boolean>> getEventConsumers() {
		throw new NotImplementedException();
	}
}
