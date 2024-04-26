package be.florens.expandability.api.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.entity.living.LivingEvent;
import net.neoforged.bus.api.Event;
import org.jetbrains.annotations.ApiStatus;

/**
 * Event that allows enabling solid collisions on top of fluids (aka "fluid walking").
 *
 * This event has the following {@link Event.Result}:
 * <ul>
 *     <li>{@link Event.Result#ALLOW}: Enable solid fluid collisions</li>
 *     <li>{@link Event.Result#DEFAULT}/{@link Event.Result#DENY}: Vanilla fluid behaviour</li>
 * </ul>
 */
@Event.HasResult
public class LivingFluidCollisionEvent extends LivingEvent {

	private final FluidState fluidState;

	public LivingFluidCollisionEvent(LivingEntity entity, FluidState fluidState) {
		super(entity);
		this.fluidState = fluidState;
	}

	/**
	 * @return The {@link FluidState} the entity is colliding with
	 */
	public FluidState getFluidState() {
		return this.fluidState;
	}
}
