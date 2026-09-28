package be.florens.expandability.mixin.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Player.class)
public abstract class PlayerMixin {

	/// makes it such that you can land critical hits while in water with fluid physics disabled
	@ModifyExpressionValue(
			method = "canCriticalAttack",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWater()Z")
	)
	private boolean setInWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	@ModifyExpressionValue(
			method = "tryToStartFallFlying",
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInLiquid()Z")
	)
	private boolean setInLiquid(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	/// Vanilla checks if the block above the player is fluid and prevents swimming up by look direction
	/// This cancels the check if we have swimming enabled
	@ModifyExpressionValue(
			method = "travel",
			allow = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;isEmpty()Z")
	)
	private boolean cancelSurfaceCheck(boolean original) {
		return !Util.shouldPlayerSwim(this, !original);
	}

	@ModifyReturnValue(
			method = "isPushedByFluid", at = @At(value = "RETURN")
	)
	private boolean isPushedByFluid(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}
}
