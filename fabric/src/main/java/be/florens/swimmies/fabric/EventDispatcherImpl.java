package be.florens.swimmies.fabric;

import be.florens.swimmies.fabric.api.SwimmiesAPI;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.entity.player.Player;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EventDispatcherImpl {

	public static List<Function<Player, Boolean>> getEventConsumers() {
		return FabricLoader.getInstance().getEntrypoints("swimmies", SwimmiesAPI.class).stream()
				.map(entrypoint -> (Function<Player, Boolean>) entrypoint::playerShouldSwim)
				.collect(Collectors.toList());
	}
}
