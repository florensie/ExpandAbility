package be.florens.swimmies.mixin.client;

import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class ClientPlayerEntityMixin {

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
	private boolean setInWater(LocalPlayer clientPlayerEntity) {
		return true;
	}

	@Redirect(method = {"aiStep", "hasEnoughImpulseToStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
	private boolean setUnderWater(LocalPlayer clientPlayerEntity) {
		return true;
	}
}
