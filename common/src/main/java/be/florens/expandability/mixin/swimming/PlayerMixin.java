package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

	/**
	 * <ul>
	 *     <li>{@link Player#attack}: makes it so you can land critical hits while in water with fluid physics disabled</li>
	 *     <li>{@link Player#checkMovementStatistics}: makes sure the correct hunger is applied</li>
	 * </ul>
	 */
	@Redirect(method = {"attack", "checkMovementStatistics", "tryToStartFallFlying"}, require = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWater()Z"))
	private boolean setInWater(Player player) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
	}

	/**
	 * Makes sure the correct hunger is applied
	 */
	@Redirect(method = "checkMovementStatistics", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/Tag;)Z"))
	private boolean setEyeInFluid(Player player, Tag<Fluid> tag) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), () -> player.isEyeInFluid(tag));
	}

	/**
	 * Vanilla checks the if the block above the player is fluid and prevents swimming up by look direction
	 * This cancels the check if we have swimming enabled
	 */
	// TODO: WrapWithCondition (with slice and ordinal)
	@Redirect(method = "travel", allow = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private boolean cancelSurfaceCheck(FluidState fluidState) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), false, true, fluidState::isEmpty);
	}
}
