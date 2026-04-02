package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.api.EventResult;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@ModifyExpressionValue(
			method = {
					"updateSwimming",
					"isVisuallyCrawling",
					"canSpawnSprintParticle",
					"applyMovementEmissionAndPlaySound"
			},
			require = 4,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z")
	)
	private boolean setInWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	/**
	 * Prevents the swimming sound from playing when non-vanilla swimming is enabled
	 */
	@WrapWithCondition(
			method = "applyMovementEmissionAndPlaySound",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;waterSwimSound()V")
	)
	private boolean shouldPlayWaterSwimSound(Entity entity) {
		if ((Object) this instanceof Player player) {
			// Repeat the isInWater check, so we don't cancel vanilla swimming sounds
			return !player.isInWater() && EventDispatcher.onPlayerSwim(player) == EventResult.SUCCESS;
		}
        return true;
	}

	/**
	 * Take fall damage when in water with water physics disabled
	 */
	@WrapWithCondition(
			method = {"updateInWaterStateAndDoWaterCurrentPushing", "move"},
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
	)
	private boolean shouldResetFallDistance(Entity entity) {
		return Util.shouldPlayerSwim(this, false);
	}

	/**
	 * There's a check here for when there's water at the entity's feet. There aren't any vanilla blocks where this
	 * matters because honey and soul sand aren't full blocks. There is a block in the fabric test mod to test this
	 * behaviour.
	 */
	// TODO: do we also need to change bubble column check here?
	@ModifyExpressionValue(
			method = "getBlockSpeedFactor",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z")
	)
	private boolean fixBlockSpeedFactor(boolean original) {
		if ((Object) this instanceof Player player) {
			BlockState block = player.level().getBlockState(player.blockPosition());

			if (block.is(Blocks.WATER) && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
				return true; // Makes condition return true
			}
		}

		return original; // Vanilla behaviour
	}
}
