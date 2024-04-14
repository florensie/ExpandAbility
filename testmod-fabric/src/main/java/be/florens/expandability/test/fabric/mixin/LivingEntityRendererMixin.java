package be.florens.expandability.test.fabric.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntityRenderer.class)
public class LivingEntityRendererMixin {

    @ModifyReturnValue(method = "shouldShowName(Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("RETURN"))
    private boolean hidePlayerNames(boolean original, LivingEntity entity) {
        return false;
    }
}
