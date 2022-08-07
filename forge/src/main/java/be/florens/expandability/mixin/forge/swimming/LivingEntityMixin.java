package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Redirect(method = "travel", require = 2, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInFluidType(Lnet/minecraft/world/level/material/FluidState;)Z"))
    private boolean setInWater(LivingEntity entity, FluidState fluidState) {
        if (entity instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), () -> player.isInFluidType(fluidState));
        }

        return entity.isInFluidType(fluidState); // Vanilla behaviour
    }
}
