package be.florens.swimmies.mixin.client;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

	@Redirect(method = "setupRotations", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isInWater()Z"))
	private boolean setInWater(AbstractClientPlayer player) {
		return true;
	}
}
