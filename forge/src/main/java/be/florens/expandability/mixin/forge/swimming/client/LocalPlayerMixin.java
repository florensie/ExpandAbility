package be.florens.expandability.mixin.forge.swimming.client;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.ProfilePublicKey;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {

    private LocalPlayerMixin(ClientLevel arg, GameProfile gameProfile, @Nullable ProfilePublicKey arg2) {
        super(arg, gameProfile, arg2);
    }

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraftforge/fluids/FluidType;isAir()Z"))
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

    @ModifyExpressionValue(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;canSwimInFluidType(Lnet/minecraftforge/fluids/FluidType;)Z"))
    private boolean setCanSwimInFluidType(boolean original) {
        return Util.processEventResult(EventDispatcher.onPlayerSwim(this), original);
    }
}
