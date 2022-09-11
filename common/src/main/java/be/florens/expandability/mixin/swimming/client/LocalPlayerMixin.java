package be.florens.expandability.mixin.swimming.client;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

	@ModifyExpressionValue(method = "aiStep", require = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInWater()Z"))
	private boolean setInWater(boolean original) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), original);
	}

	@ModifyExpressionValue(method = {"aiStep", "hasEnoughImpulseToStartSprinting"}, require = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isUnderWater()Z"))
	private boolean setUnderWater(boolean original) {
		Player self = (Player) (Object) this;
		return Util.processEventResult(EventDispatcher.onPlayerSwim(self), original);
	}
}
