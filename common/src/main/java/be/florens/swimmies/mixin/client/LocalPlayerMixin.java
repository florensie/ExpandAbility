package be.florens.swimmies.mixin.client;

import be.florens.swimmies.api.PlayerSwimEvent;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
	private boolean setInWater(LocalPlayer player) {
		return PlayerSwimEvent.EVENT.invoker().swim(player)
				|| player.isInWater(); // Vanilla behaviour
	}

	@Redirect(method = {"aiStep", "hasEnoughImpulseToStartSprinting"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
	private boolean setUnderWater(LocalPlayer player) {
		return PlayerSwimEvent.EVENT.invoker().swim(player)
				|| player.isUnderWater(); // Vanilla behaviour
	}
}
