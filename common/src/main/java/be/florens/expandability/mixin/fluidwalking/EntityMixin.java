package be.florens.expandability.mixin.fluidwalking;

import be.florens.expandability.EventDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Mixin(Entity.class)
public class EntityMixin {

	/**
	 * Adjust's an entity's movement to account for fluid walking.
	 *
	 * @param originalDisplacement The entity's proposed displacement accounting for collisions.
	 * @return A new Vec3d representing the displacement after fluid walking is accounted for, which the vanilla code
	 * will use to update the player's position later in the method. This ensures that the player never actually touches
	 * any of the fluids they can walk on.
	 */
	@ModifyVariable(method = "move", ordinal = 1, index = 3, name = "vec32", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"))
	private Vec3 fluidCollision(Vec3 originalDisplacement) {
		Entity potentialEntity = (Entity) (Object) this;

		// AFAIK this has to be done because the relevant movement code is only in Entity, but all of this really only
		// applies to living entities
		//noinspection ConstantConditions
		if (!(potentialEntity instanceof LivingEntity)) {
			return originalDisplacement;
		}

		LivingEntity entity = (LivingEntity) potentialEntity;

		boolean canWalkOnFluids = EventDispatcher.onLivingFluidCollision(entity);

		// Building the list of valid fluid tags. Probably a better way to do this
		// TODO: add to event
		ArrayList<Tag<Fluid>> validFluidTags = new ArrayList<>();
		validFluidTags.add(FluidTags.WATER);
		validFluidTags.add(FluidTags.LAVA);

		// A bunch of checks to see if fluid walking is even possible
		if (canWalkOnFluids && originalDisplacement.y <= 0.0 && !entity.isShiftKeyDown() && !isTouchingFluidInTags(entity, entity.getBoundingBox().deflate(0.001D), validFluidTags)) {
			HashMap<Vec3, Double> points = findFluidDistances(entity, originalDisplacement, validFluidTags);
			// A value of null for an entry means that no valid fluid block was found within range
			Double highestDistance = null;

			for (Map.Entry<Vec3, Double> point : points.entrySet()) {
				if (highestDistance == null || (point.getValue() != null && point.getValue() > highestDistance)) {
					highestDistance = point.getValue();
				}
			}

			if (highestDistance != null) {
				Vec3 finalDisplacement = new Vec3(originalDisplacement.x, highestDistance, originalDisplacement.z);
				AABB finalBox = entity.getBoundingBox().move(finalDisplacement).deflate(0.001D);
				if (isTouchingFluidInTags(entity, finalBox, validFluidTags)) {
					return originalDisplacement;
				} else {
					entity.fallDistance = 0.0F;
					entity.setOnGround(true);
					return finalDisplacement;
				}
			}
		}

		return originalDisplacement;
	}

	/**
	 * Gives the entity's distance to various fluids underneath (and above) it, in terms of the four bottom points of its
	 * bounding box.
	 *
	 * @param entity               The entity to check for fluid walking.
	 * @param originalDisplacement The entity's proposed displacement after checking for collisions.
	 * @param validFluidTags       A list of fluid tags that the given entity can walk on.
	 * @return A hashmap containing each bottom corner of the entity's original displaced bounding box, and the distance
	 * between that corner and a given fluid, with a value of null for points with no fluid in range.
	 */
	@Unique
	private static HashMap<Vec3, Double> findFluidDistances(LivingEntity entity, Vec3 originalDisplacement, ArrayList<Tag<Fluid>> validFluidTags) {
		AABB box = entity.getBoundingBox().move(originalDisplacement);

		HashMap<Vec3, Double> points = new HashMap<>();
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

				if (checkFluidInTags(landingState, validFluidTags)) {
					entry.setValue(distanceToFluidSurface);
					break;
				}
			}
		}
		return points;
	}

	/**
	 * Checks if a given fluid is in a list of fluid tags.
	 *
	 * @param fluid          The fluid to check.
	 * @param validFluidTags A list of fluid tags.
	 * @return A boolean representing whether the given fluid is in the list of fluid tags.
	 */
	@Unique
	private static boolean checkFluidInTags(FluidState fluid, ArrayList<Tag<Fluid>> validFluidTags) {
		for (Tag<Fluid> tag : validFluidTags) {
			if (fluid.is(tag)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a given entity's bounding box is touching any of the fluids in validFluidTags. This is modified
	 * vanilla code that works for any fluid, since vanilla only has one for water.
	 *
	 * @param entity         The entity to check for fluid walking.
	 * @param box            The entity's proposed bounding box to check.
	 * @param validFluidTags A list of fluid tags to check against.
	 * @return A boolean representing whether the entity's proposed bounding box will touch any of the given fluids given
	 * in validFluidTags.
	 */
	@Unique
	private static boolean isTouchingFluidInTags(LivingEntity entity, AABB box, ArrayList<Tag<Fluid>> validFluidTags) {
		int minX = Mth.floor(box.minX);
		int maxX = Mth.ceil(box.maxX);
		int minY = Mth.floor(box.minY);
		int maxY = Mth.ceil(box.maxY);
		int minZ = Mth.floor(box.minZ);
		int maxZ = Mth.ceil(box.maxZ);
		Level world = entity.getCommandSenderWorld();

		if (world.hasChunksAt(minX, minY, minZ, maxX, maxY, maxZ)) {
			BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

			for (int p = minX; p < maxX; ++p) {
				for (int q = minY; q < maxY; ++q) {
					for (int r = minZ; r < maxZ; ++r) {
						mutable.set(p, q, r);
						FluidState fluidState = world.getFluidState(mutable);

						if (checkFluidInTags(fluidState, validFluidTags)) {
							double surfaceY = fluidState.getHeight(world, mutable) + q;

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
