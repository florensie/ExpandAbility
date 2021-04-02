package be.florens.effectful.api.forge;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Event;

@Event.HasResult
public class PlayerSwimEvent extends PlayerEvent {

    public PlayerSwimEvent(Player player) {
        super(player);
    }
}
