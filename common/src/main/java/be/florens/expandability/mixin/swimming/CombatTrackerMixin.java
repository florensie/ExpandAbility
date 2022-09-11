package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CombatTracker.class)
public abstract class CombatTrackerMixin {

    @Shadow @Final private LivingEntity mob;

    // TODO: set mixin.debug.countInjections to true in runs using build.gradle (expect = 1)
    // Require 0 because this is extremely unimportant, mixin is allowed to fail
    @ModifyExpressionValue(method = "prepareForDamage", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
    private boolean setInWater(boolean original) {
        if (this.mob instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
        }

        return original; // Vanilla behaviour
    }
}
