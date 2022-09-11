package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {

	/**
	 * <ul>
	 *     <li>{@link Player#attack}: makes it so you can land critical hits while in water with fluid physics disabled</li>
	 *     <li>{@link Player#checkMovementStatistics}: makes sure the correct hunger is applied</li>
	 * </ul>
	 */
	@ModifyExpressionValue(method = {"attack", "checkMovementStatistics", "tryToStartFallFlying"}, require = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWater()Z"))
	private boolean setInWater(boolean original) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), original);
	}

	/**
	 * Makes sure the correct hunger is applied
	 */
	@ModifyExpressionValue(method = "checkMovementStatistics", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isEyeInFluid(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean setEyeInFluid(boolean original) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), original);
	}

	/**
	 * Vanilla checks the if the block above the player is fluid and prevents swimming up by look direction
	 * This cancels the check if we have swimming enabled
	 */
	@ModifyExpressionValue(method = "travel", allow = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private boolean cancelSurfaceCheck(boolean original) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), false, true, original);
	}
}
