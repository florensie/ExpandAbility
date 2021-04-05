package be.florens.expandability.api.fabric;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.world.entity.LivingEntity;

/**
 * Event that allows enabling solid collisions on fluids.
 * Fired multiple times per tick on the client and server.
 */
public interface LivingFluidCollisionCallback {

	Event<LivingFluidCollisionCallback> EVENT = EventFactory.createArrayBacked(LivingFluidCollisionCallback.class,
			(listeners) -> (player) -> {
				for (LivingFluidCollisionCallback listener : listeners) {
					if (listener.collide(player)) {
						return true;
					}
				}
				return false;
			});

	/**
	 * @param entity The {@link LivingEntity} that is colliding
	 * @return true to enable solid fluid collisions and false for vanilla behaviour
	 */
	boolean collide(LivingEntity entity);
}
