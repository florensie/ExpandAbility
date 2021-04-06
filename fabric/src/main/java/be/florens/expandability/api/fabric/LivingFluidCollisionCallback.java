package be.florens.expandability.api.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event that allows enabling solid collisions on fluids.
 * Fired multiple times per tick on the client and server.
 */
@ApiStatus.Experimental
public interface LivingFluidCollisionCallback {

	Event<LivingFluidCollisionCallback> EVENT = EventFactory.createArrayBacked(LivingFluidCollisionCallback.class,
			(listeners) -> (player, fluidState) -> {
				for (LivingFluidCollisionCallback listener : listeners) {
					if (listener.collide(player, fluidState)) {
						return true;
					}
				}
				return false;
			});

	/**
	 * @param entity The {@link LivingEntity} that is colliding
	 * @param fluidState The {@link FluidState} the entity is collding with
	 * @return true to enable solid fluid collisions and false for vanilla behaviour
	 */
	boolean collide(LivingEntity entity, FluidState fluidState);
}
