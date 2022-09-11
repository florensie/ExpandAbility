package be.florens.expandability.mixin.swimming.client;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin {

	@ModifyExpressionValue(method = "setupRotations", require = 0 /* rendering only */, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;isInWater()Z"))
	private boolean setInWater(boolean original, AbstractClientPlayer player) {
		return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
	}
}
