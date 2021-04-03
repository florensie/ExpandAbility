package be.florens.expandability;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class EventDispatcher {

    @ExpectPlatform
    public static InteractionResult onPlayerSwim(Player player) {
        return InteractionResult.PASS;
    }
}
