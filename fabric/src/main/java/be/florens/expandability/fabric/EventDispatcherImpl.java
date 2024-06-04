package be.florens.expandability.fabric;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class EventDispatcherImpl {

    public static EventResult onPlayerSwim(Player player) {
        return PlayerSwimCallback.EVENT.invoker().swim(player);
    }

	public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
		return LivingFluidCollisionCallback.EVENT.invoker().collide(entity, fluidState);
	}
}
