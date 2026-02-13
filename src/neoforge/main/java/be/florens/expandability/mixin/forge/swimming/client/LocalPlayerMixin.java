package be.florens.expandability.mixin.forge.swimming.client;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    public LocalPlayerMixin(ClientLevel arg, GameProfile gameProfile) {
        super(arg, gameProfile);
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/fluids/FluidType;isAir()Z"))
    private boolean setIsAir(boolean original) {
        return Util.processEventResult(EventDispatcher.onPlayerSwim(this), false, true, original);
    }

    @ModifyExpressionValue(method = "aiStep", require = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;isInFluidType(Ljava/util/function/BiPredicate;)Z"))
    private boolean setInFluidType(boolean original) {
        return Util.processEventResult(EventDispatcher.onPlayerSwim(this), original);
    }

    @ModifyExpressionValue(method = "aiStep", require = 3, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canStartSwimming()Z"))
    private boolean setCanStartSwimming(boolean original) {
        return Util.processEventResult(EventDispatcher.onPlayerSwim(this), original);
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canSwimInFluidType(Lnet/neoforged/neoforge/fluids/FluidType;)Z"))
    private boolean setCanSwimInFluidType(boolean original) {
        return Util.processEventResult(EventDispatcher.onPlayerSwim(this), original);
    }
}
