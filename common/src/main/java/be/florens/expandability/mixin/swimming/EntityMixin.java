package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import be.florens.expandability.api.EventResult;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@ModifyExpressionValue(
			method = {
					"updateSwimming",
					"isVisuallyCrawling",
					"canSpawnSprintParticle",
					"applyMovementEmissionAndPlaySound",
					"checkFallDamage"
			},
			require = 5,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z")
	)
	private boolean setInWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	@ModifyExpressionValue(
			method = "updateSwimming",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z")
	)
	private boolean setUnderWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	@ModifyExpressionValue(
			method = "updateSwimming",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	private boolean setInFluidState(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	@WrapWithCondition(
			method = "baseTick",
			require = 1,
			at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/world/entity/Entity;fallDistance:D")
	)
	private boolean shouldReduceFallDamageInLava(Entity instance, double value) {
		return Util.shouldPlayerSwim(this, true);
	}

	/**
	 * Prevents the swimming sound from playing when non-vanilla swimming is enabled
	 */
	@WrapWithCondition(
			method = "applyMovementEmissionAndPlaySound",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;waterSwimSound()V")
	)
	private boolean shouldPlayWaterSwimSound(Entity entity) {
		if ((Object) this instanceof Avatar player) {
			// Repeat the isInWater check, so we don't cancel vanilla swimming sounds
			return !player.isInWater() && EventDispatcher.onPlayerSwim(player) == EventResult.SUCCESS;
		}
        return true;
	}

	/**
	 * Take fall damage when in water with water physics disabled
	 */
	@WrapWithCondition(
			method = {"move", "updateFluidInteraction"},
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
	)
	private boolean shouldResetFallDistance(Entity entity) {
		return Util.shouldPlayerSwim(this, true);
	}

	/**
	 * There's a check here for when there's water at the entity's feet. There aren't any vanilla blocks where this
	 * matters because honey and soul sand aren't full blocks. There is a block in the fabric test mod to test this
	 * behaviour.
	 */
	@ModifyExpressionValue(
			method = "getBlockSpeedFactor",
			require = 2,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z")
	)
	private boolean fixBlockSpeedFactor(boolean original) {
		if ((Object) this instanceof Avatar player) {
			BlockState block = player.level().getBlockState(player.blockPosition());

			if (block.is(Blocks.WATER) && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
				return true; // Makes condition return true
			}
		}

		return original; // Vanilla behaviour
	}

	@ModifyExpressionValue(
			method = "updateFluidInteraction",
			require = 2,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isPushedByFluid()Z")
	)
	private boolean isPushedByFluid(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}
}
