package be.florens.expandability.mixin.fluidcollision;

import be.florens.expandability.EventDispatcher;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public class EntityMixin {

	/**
	 * Adjusts an entity's movement to account for fluid walking. This ensures that the player never actually touches
	 * any of the fluids they can walk on.
	 *
	 * @param originalDisplacement the entity's proposed displacement accounting for collisions
	 * @return a new Vec3d representing the displacement after fluid walking is accounted for
	 */
	@ModifyExpressionValue(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"))
	private Vec3 fluidCollision(Vec3 originalDisplacement) {
		// We only support living entities
		//noinspection ConstantConditions
		if (!((Object) this instanceof LivingEntity entity)) {
			return originalDisplacement;
		}

		// A bunch of checks to see if fluid walking is even possible
		if (originalDisplacement.y <= 0.0 && !isTouchingFluid(entity,entity.getBoundingBox().deflate(0.001D))) {
			Map<Vec3, Double> points = findFluidDistances(entity, originalDisplacement);
			Double highestDistance = null;

			for (Map.Entry<Vec3, Double> point : points.entrySet()) {
				if (highestDistance == null || (point.getValue() != null && point.getValue() > highestDistance)) {
					highestDistance = point.getValue();
				}
			}

			if (highestDistance != null) {
				Vec3 finalDisplacement = new Vec3(originalDisplacement.x, highestDistance, originalDisplacement.z);
				AABB finalBox = entity.getBoundingBox().move(finalDisplacement).deflate(0.001D);
				if (!isTouchingFluid(entity, finalBox)) {
					entity.fallDistance = 0.0F;
					entity.setOnGround(true);
					return finalDisplacement;
				}
			}
		}

		return originalDisplacement;
	}

	/**
	 * Gives the entity's distance to various fluids underneath (and above) it, in terms of the four bottom points
	 * of its bounding box.
	 *
	 * @param entity the entity to check for fluid walking.
	 * @param originalDisplacement the entity's proposed displacement after checking for collisions.
	 * @return a map containing each bottom corner of the entity's original displaced bounding box, and the distance
	 * between that corner and a given fluid, with a value of null for points with no fluid in range.
	 */
	@Unique
	private static Map<Vec3, Double> findFluidDistances(LivingEntity entity, Vec3 originalDisplacement) {
		AABB box = entity.getBoundingBox().move(originalDisplacement);

		Map<Vec3, Double> points = new HashMap<>();
		points.put(new Vec3(box.minX, box.minY, box.minZ), null);
		points.put(new Vec3(box.minX, box.minY, box.maxZ), null);
		points.put(new Vec3(box.maxX, box.minY, box.minZ), null);
		points.put(new Vec3(box.maxX, box.minY, box.maxZ), null);

		double fluidStepHeight = entity.isOnGround() ? Math.max(1.0, entity.maxUpStep) : 0.0;

		for (Map.Entry<Vec3, Double> entry : points.entrySet()) {
			for (int i = 0; ; i--) { // Check successive blocks downward
				// Auto step is essentially just shifting the fall adjustment up by the step height
				BlockPos landingPos = new BlockPos(entry.getKey()).offset(0.0, i + fluidStepHeight, 0.0);
				FluidState landingState = entity.getCommandSenderWorld().getFluidState(landingPos);

				double distanceToFluidSurface = landingPos.getY() + landingState.getOwnHeight() - entity.getY();
				double limitingVelocity = originalDisplacement.y;

				if (distanceToFluidSurface < limitingVelocity || distanceToFluidSurface > fluidStepHeight) {
					break;
				}

				if (!landingState.isEmpty() && EventDispatcher.onLivingFluidCollision(entity, landingState)) {
					entry.setValue(distanceToFluidSurface);
					break;
				}
			}
		}

		return points;
	}

	/**
	 * Checks if a given entity's bounding box is touching any fluids. This is modified vanilla code that works for
	 * any fluid, since vanilla only has one for water.
	 *
	 * @param entity the entity to check for fluid walking
	 * @param box the entity's proposed bounding box to check
	 * @return whether the entity's proposed bounding box will touch any fluids
	 */
	@Unique
	private static boolean isTouchingFluid(LivingEntity entity, AABB box) {
		int minX = Mth.floor(box.minX);
		int maxX = Mth.ceil(box.maxX);
		int minY = Mth.floor(box.minY);
		int maxY = Mth.ceil(box.maxY);
		int minZ = Mth.floor(box.minZ);
		int maxZ = Mth.ceil(box.maxZ);
		Level world = entity.getCommandSenderWorld();

		//noinspection deprecation
		if (world.hasChunksAt(minX, minY, minZ, maxX, maxY, maxZ)) {
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

			// Loop over coords in bounding box
			for (int x = minX; x < maxX; ++x) {
				for (int y = minY; y < maxY; ++y) {
					for (int z = minZ; z < maxZ; ++z) {
						mutable.set(x, y, z);
						FluidState fluidState = world.getFluidState(mutable);

						if (!fluidState.isEmpty()) {
							double surfaceY = fluidState.getHeight(world, mutable) + y;

							if (surfaceY >= box.minY) {
								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}
}
