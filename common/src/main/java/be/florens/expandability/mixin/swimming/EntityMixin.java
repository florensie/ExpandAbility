package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.EventResult;
import be.florens.expandability.Util;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow public Level level;

	@Shadow public abstract boolean isInWater();
	@Shadow protected abstract void playSwimSound(float f);

	@Redirect(method = {"updateSwimming", "isVisuallyCrawling", "canSpawnSprintParticle", "move"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
	private boolean setInWater(Entity entity) {
		if (entity instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
		}

		return entity.isInWater(); // Vanilla behaviour
	}

	@Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z"))
	private boolean setUnderWater(Entity entity) {
		if (entity instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isUnderWater);
		}

		return entity.isUnderWater(); // Vanilla behaviour
	}

	// Require = 0, because this is 1.17.1+
	@Redirect(method = "updateSwimming", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/Tag;)Z"))
	private boolean setInFluidState(FluidState fluidState, Tag<Fluid> tag) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player && tag == FluidTags.WATER) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), () -> fluidState.is(tag));
		}

		return fluidState.is(tag);
	}

	/**
	 * Prevents the swimming sound from playing when non-vanilla swimming is enabled
	 */
	@Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSwimSound(F)V"))
	private void cancelPlaySwimSound(Entity entity, float volume) {
		// Re-check if we're in water first, so we don't cancel vanilla swimming sounds
		if (!this.isInWater() && entity instanceof Player player && EventDispatcher.onPlayerSwim(player) == EventResult.SUCCESS) {
			return;
		}

		// Vanilla behaviour
		this.playSwimSound(volume);
	}

	/**
	 * Take fall damage when in water with water physics disabled
	 */
	@Redirect(method = "updateInWaterStateAndDoWaterCurrentPushing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V"))
	private void cancelSetFallDistance(Entity entity) {
		if (entity instanceof Player player && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
			return;
		}

		// Vanilla behaviour
		entity.resetFallDistance();
	}

	/**
	 * There's a check here for when there's water at the entity's feet. There aren't any vanilla blocks where this
	 * matters because honey and soulsand aren't full blocks. There is a block in the fabric testmod to test this
	 * behaviour.
	 */
	@Redirect(method = "getBlockSpeedFactor", at = @At(value = "FIELD", opcode = Opcodes.GETSTATIC, target = "Lnet/minecraft/world/level/block/Blocks;WATER:Lnet/minecraft/world/level/block/Block;"))
	private Block fixBlockSpeedFactor() {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player) {
			Block block = player.level.getBlockState(player.blockPosition()).getBlock();

			if (block == Blocks.WATER && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
				return Blocks.AIR; // Makes condition return true
			}
		}

		return Blocks.WATER; // Vanilla behaviour
	}
}
