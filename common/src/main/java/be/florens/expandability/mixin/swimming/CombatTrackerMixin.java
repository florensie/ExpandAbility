package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {

    // TODO: set mixin.debug.countInjections to true in runs using build.gradle (expect = 1)
    @Redirect(method = "prepareForDamage", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
    private boolean setInWater(LivingEntity entity) {
        if (entity instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), player::isInWater);
        }

        return entity.isInWater(); // Vanilla behaviour
    }
}
