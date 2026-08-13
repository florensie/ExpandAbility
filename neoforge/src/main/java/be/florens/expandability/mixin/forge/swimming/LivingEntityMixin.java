package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import be.florens.expandability.api.EventResult;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityFluidInteraction;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.fluids.InFluidPredicate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    /**
     * See also: common module mixin targeting the getFluidHeight calls in the same method
     */
    @ModifyExpressionValue(
            method = "aiStep",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidTypeHeight(Lnet/neoforged/neoforge/fluids/FluidType;)D")
    )
    private double setFluidHeight(double original) {
        if ((Object) this instanceof Avatar player) {
            EventResult shouldSwim = EventDispatcher.onPlayerSwim(player);
            return Util.processEventResult(shouldSwim, 1D, 0D, original);
        }

        return original;
    }

    /**
     * See also: common module mixin targeting the isInWater check in the same method
     */
    @WrapOperation(
            method = "travelInFluid",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;isInFluidMatching(Lnet/minecraft/world/entity/Entity;Lnet/neoforged/neoforge/fluids/InFluidPredicate;)Z")
    )
    private boolean modifyIsInFluidMatching(EntityFluidInteraction instance, Entity entity, InFluidPredicate<?> inFluidPredicate, Operation<Boolean> original) {
        if (Util.shouldPlayerSwim(this, true)) {
            return original.call(instance, entity, inFluidPredicate);
        }
        return false;
    }
}
