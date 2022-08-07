package be.florens.expandability.mixin.swimming.client;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

	// TODO: isInFluidType might need to be patched for start-sprinting here on forge
	// TODO: isInFluidType WILL need to be patched for end-sprinting here on forge
	@Redirect(method = "aiStep", require = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
	private boolean setInWater(LocalPlayer player) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
	}

	// TODO: looks ok
	@Redirect(method = {"aiStep", "hasEnoughImpulseToStartSprinting"}, require = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
	private boolean setUnderWater(LocalPlayer player) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isUnderWater);
	}
}
