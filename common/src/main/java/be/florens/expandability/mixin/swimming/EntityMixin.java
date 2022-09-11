package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.EventResult;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow public Level level;

	@Shadow public abstract boolean isInWater();

	@ModifyExpressionValue(method = {"updateSwimming", "isVisuallyCrawling", "canSpawnSprintParticle", "move"}, require = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
	private boolean setInWater(boolean original) {
		if ((Object) this instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
		}

		return original;
	}

	@ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z"))
	private boolean setUnderWater(boolean original) {
		if ((Object) this instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
		}

		return original;
	}

	/**
	 * Prevents the swimming sound from playing when non-vanilla swimming is enabled
	 */
	@WrapWithCondition(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSwimSound(F)V"))
	private boolean cancelPlaySwimSound(Entity entity, float volume) {
		// Re-check if we're in water first, so we don't cancel vanilla swimming sounds
		return this.isInWater() || !(entity instanceof Player player && EventDispatcher.onPlayerSwim(player) == EventResult.SUCCESS);
	}

	/**
	 * Take fall damage when in water with water physics disabled
	 */
	@WrapWithCondition(method = {"updateInWaterStateAndDoWaterCurrentPushing", "move"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V"))
	private boolean cancelResetFallDistance(Entity entity) {
		return !(entity instanceof Player player) || EventDispatcher.onPlayerSwim(player) != EventResult.FAIL;
	}

	/**
	 * There's a check here for when there's water at the entity's feet. There aren't any vanilla blocks where this
	 * matters because honey and soulsand aren't full blocks. There is a block in the fabric testmod to test this
	 * behaviour.
	 */
	// TODO: do we also need to change bubble column check here?
	@ModifyExpressionValue(method = "getBlockSpeedFactor", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/world/level/block/Block;)Z"))
	private boolean fixBlockSpeedFactor(boolean original) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player) {
			BlockState block = player.level.getBlockState(player.blockPosition());

			if (block.is(Blocks.WATER) && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
				return true; // Makes condition return true
			}
		}

		return original; // Vanilla behaviour
	}
}
