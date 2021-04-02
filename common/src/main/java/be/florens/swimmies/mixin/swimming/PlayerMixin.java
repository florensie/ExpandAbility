package be.florens.swimmies.mixin.swimming;

import be.florens.swimmies.EventDispatcher;
import be.florens.swimmies.Util;
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
		return !Util.processEventResult(EventDispatcher.onPlayerSwim(self), self::isInWater);
	}
}
