package be.florens.swimmies.mixin.common.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
	private boolean setInWater(LocalPlayer player) {
		return true;
	}

	@Redirect(method = {"aiStep", "hasEnoughImpulseToStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
	private boolean setUnderWater(LocalPlayer player) {
		return true;
	}
}
