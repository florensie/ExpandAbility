package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	@Shadow protected abstract boolean isAffectedByFluids();

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/Tag;)D"))
	private double setFluidHeight(LivingEntity entity, Tag<Fluid> tag) {
		if (entity instanceof Player player) {
			InteractionResult shouldSwim = EventDispatcher.onPlayerSwim(player);
			return Util.processEventResult(shouldSwim, 1D, 0D, () -> player.getFluidHeight(tag));
		}

		return entity.getFluidHeight(tag); // Vanilla behaviour
	}

	@Redirect(method = {"aiStep", "checkFallDamage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
	private boolean setInWater(LivingEntity entity) {
		if (entity instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
		}

		return entity.isInWater(); // Vanilla behaviour
	}

	@Redirect(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isAffectedByFluids()Z"))
	private boolean setAffectedByFluids(LivingEntity entity) {
		if (entity instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), false, true, player::isAffectedByFluids);
		}

		//noinspection ConstantConditions
		return ((LivingEntityMixin) (Object) entity).isAffectedByFluids();
	}

	/**
	 * Reset the fall distance every tick when swimming is enabled
	 */
	@Inject(method = "checkFallDamage", at = @At("HEAD"))
	private void resetFallHeight(CallbackInfo info) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player && EventDispatcher.onPlayerSwim(player).consumesAction()) {
			this.fallDistance = 0;
		}
	}

	/**
	 * Cancel the small boost upward when leaving a fluid while against the side of a block when swimming is enabled
	 */
	@Redirect(method = "travel", allow = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isFree(DDD)Z"))
	private boolean cancelLeaveFluidAssist(LivingEntity entity, double x, double y, double z) {
		if (entity instanceof Player player) {
			if (EventDispatcher.onPlayerSwim(player).consumesAction()) {
				return false;
			}
		}

		return entity.isFree(x, y, z); // Vanilla behaviour
	}
}
