package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.api.EventResult;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

	public LivingEntityMixin(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	// TODO: baseTick stopRiding

	@ModifyExpressionValue(
			method = "aiStep",
			require = 2, // TODO: do we want to target lava check?
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/TagKey;)D")
	)
	private double setFluidHeight(double original) {
		if ((Object) this instanceof Player player) {
			EventResult shouldSwim = EventDispatcher.onPlayerSwim(player);
			return Util.processEventResult(shouldSwim, 1D, 0D, original);
		}

		return original;
	}

	@ModifyExpressionValue(
			method = {
					"shouldTravelInFluid",
					"aiStep",
					"checkFallDamage"
			},
			require = 3,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z")
	)
	private boolean setInWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	/**
	 * Reset the fall distance every tick when swimming is enabled
	 */
	@Inject(method = "checkFallDamage", at = @At("HEAD"))
	private void resetFallHeight(CallbackInfo info) {
		if (Util.shouldPlayerSwim(this, false)) {
			this.fallDistance = 0;
		}
	}

	/**
	 * Cancel the small boost upward when leaving a fluid while against the side of a block when swimming is enabled
	 */
	@Inject(
			method = "jumpOutOfFluid",
			at = @At("HEAD"),
			cancellable = true
	)
	private void cancelJumpOutOfFluid(double d, CallbackInfo ci) {
		if (Util.shouldPlayerSwim(this, false)) {
			ci.cancel(); // Early return intended!
		}
	}
}
