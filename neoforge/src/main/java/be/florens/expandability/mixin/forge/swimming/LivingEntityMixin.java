package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyExpressionValue(
            method = "shouldTravelInFluid",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInFluidType(Lnet/minecraft/world/level/material/FluidState;)Z")
    )
    private boolean setInFluidState(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    @ModifyExpressionValue(
            method = "travelInFluid(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/material/FluidState;)V",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInFluidType(Lnet/neoforged/neoforge/fluids/FluidType;)Z")
    )
    private boolean setInFluidType(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    @ModifyExpressionValue(
            method = "travelInFluid(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/level/material/FluidState;)V",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z")
    )
    private boolean setInWater(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }


    @ModifyExpressionValue(
            method = "aiStep",
            require = 2,
            at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforge/fluids/FluidType;isAir()Z")
    )
    private boolean setIsAir(boolean original) {
        return !Util.shouldPlayerSwim(this, !original);
    }
}
