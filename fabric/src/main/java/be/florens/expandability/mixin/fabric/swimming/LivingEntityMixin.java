package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    // Fabric-only: removed by NeoForge patch
    @ModifyExpressionValue(
            method = "travelInFluid",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z")
    )
    private boolean setInWater(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }
}
