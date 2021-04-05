package be.florens.expandability.fabric;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class EventDispatcherImpl {

    public static InteractionResult onPlayerSwim(Player player) {
        return PlayerSwimCallback.EVENT.invoker().swim(player);
    }

	public static boolean onLivingFluidCollision(LivingEntity entity) {
    	return LivingFluidCollisionCallback.EVENT.invoker().collide(entity);
	}
}
