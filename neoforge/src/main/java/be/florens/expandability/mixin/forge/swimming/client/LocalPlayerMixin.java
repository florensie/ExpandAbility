package be.florens.expandability.mixin.forge.swimming.client;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    /**
     * Same as the common module mixin targeting the isInWater() call in the same method,
     * NeoForge patches an {@code || isInFluidMatching(...)} onto the isInWater() check
     */
    @ModifyExpressionValue(
            method = "shouldStopSwimSprinting",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;isInFluidMatching(Lnet/minecraft/world/entity/Entity;Lnet/neoforged/neoforge/fluids/InFluidPredicate;)Z")
    )
    private boolean setInFluidMatching(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }
}
