package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public class EntityMixin {

    /**
     * Removed by NeoForge patch, replaced with a call to IEntityExtension::canStartSwimming
     */
    @ModifyExpressionValue(
            method = "updateSwimming",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z")
    )
    private boolean setUnderWater(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    /**
     * Removed by NeoForge patch, replaced by the same call to IEntityExtension::canStartSwimming
     */
    @ModifyExpressionValue(
            method = "updateSwimming",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
    )
    private boolean setInFluidState(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }
}
