package be.florens.expandability.mixin.forge.swimming.client;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @ModifyExpressionValue(
            method = "shouldStopSwimSprinting",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInFluidType(Ljava/util/function/BiPredicate;)Z")
    )
    private boolean setInFluidType(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }
}
