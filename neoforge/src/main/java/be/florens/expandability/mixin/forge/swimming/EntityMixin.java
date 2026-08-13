package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

    /**
     * Same as the common module mixin targeting the isInWater() call in the same method,
     * NeoForge patches an {@code && !isInAnyFluid()} onto the !isInWater() check
     */
    @ModifyExpressionValue(
            method = "canSpawnSprintParticle",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;isInAnyFluid()Z")
    )
    private boolean setInWater(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    /**
     * Same as the common module mixin targeting the isInWater() call in the same methods,
     * NeoForge patches an {@code || isInFluidMatching(...)} onto the isInWater() checks
     */
    @ModifyExpressionValue(
            method = {
                    "updateSwimming",
                    "isVisuallyCrawling"
            },
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/EntityFluidInteraction;isInFluidMatching(Lnet/minecraft/world/entity/Entity;Lnet/neoforged/neoforge/fluids/InFluidPredicate;)Z")
    )
    private boolean setInFluidMatching(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    /**
     * NeoForge patch replaces the vanilla isUnderWater & FluidState::is(TagKey) calls with an IEntityExtension::canStartSwimming call
     */
    @ModifyExpressionValue(
            method = "updateSwimming",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canStartSwimming()Z")
    )
    private boolean setCanStartSwimming(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }
}
