package be.florens.expandability.api.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.Event;

/**
 *
 */
@Event.HasResult
public class LivingFluidCollisionEvent extends LivingEvent {

	public LivingFluidCollisionEvent(LivingEntity entity) {
		super(entity);
	}
}
