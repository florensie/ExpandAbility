package be.florens.swimmies.fabric;

import be.florens.swimmies.api.fabric.PlayerSwimCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class EventDispatcherImpl {

    public static InteractionResult onPlayerSwim(Player player) {
        return PlayerSwimCallback.EVENT.invoker().swim(player);
    }
}
