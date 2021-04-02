package be.florens.effectful.fabric;

import be.florens.effectful.api.fabric.PlayerSwimCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class EventDispatcherImpl {

    public static InteractionResult onPlayerSwim(Player player) {
        return PlayerSwimCallback.EVENT.invoker().swim(player);
    }
}
