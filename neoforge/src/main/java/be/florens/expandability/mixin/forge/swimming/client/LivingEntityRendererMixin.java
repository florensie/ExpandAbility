package be.florens.expandability.mixin.forge.swimming.client;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyExpressionValue(
            method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V",
            require = 0, // rendering only
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInFluidType(Ljava/util/function/BiPredicate;)Z")
    )
    private boolean setInFluidType(boolean original, LivingEntity entity) {
        return Util.shouldPlayerSwim(this, original);
    }
}
