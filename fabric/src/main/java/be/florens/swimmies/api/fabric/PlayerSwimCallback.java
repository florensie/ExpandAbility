package be.florens.swimmies.api.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public interface PlayerSwimCallback {

    Event<PlayerSwimCallback> EVENT = EventFactory.createArrayBacked(PlayerSwimCallback.class,
            (listeners) -> (player) -> {
                for (PlayerSwimCallback listener : listeners) {
                    InteractionResult result = listener.swim(player);

                    if (result.consumesAction() || result == InteractionResult.FAIL) {
                        return result;
                    }
                }
                return InteractionResult.PASS;
            });

    InteractionResult swim(Player player);
}
