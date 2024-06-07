package be.florens.expandability.api.forge;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.event.entity.living.LivingEvent;

/**
 * Event that allows enabling solid collisions on top of fluids (aka "fluid walking").
 *
 * This event has the following result:
 * <ul>
 *     <li>true: Enable solid fluid collisions</li>
 *     <li>false: Vanilla fluid behaviour</li>
 * </ul>
 */
public class LivingFluidCollisionEvent extends LivingEvent {

	private final FluidState fluidState;

	private boolean shouldCollide = false;

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

	public boolean shouldCollide() {
		return shouldCollide;
	}

	public void setColliding(boolean shouldCollide) {
		this.shouldCollide = shouldCollide;
	}
}
