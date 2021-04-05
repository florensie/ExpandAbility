package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Player.class)
public abstract class PlayerMixin {

	/**
	 * Vanilla checks the if the block above the player is fluid and prevents swimming up by look direction
	 * This cancel the check if we have swimming enabled
	 */
	@Redirect(method = "travel", allow = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z"))
	private boolean cancelSurfaceCheck(FluidState fluidState) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), false, true, fluidState::isEmpty);
	}

	@Redirect(method = "tryToStartFallFlying", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWater()Z"))
	private boolean setInWater(Player player) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
	}
}
