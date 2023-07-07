package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.damagesource.FallLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FallLocation.class)
public abstract class FallLocationMixin {

    // TODO: set mixin.debug.countInjections to true in runs using build.gradle (expect = 1)
    // Require 0 because this is extremely unimportant, mixin is allowed to fail
    @ModifyExpressionValue(method = "getCurrentFallLocation", require = 0, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
    private static boolean setInWater(boolean original, LivingEntity entity) {
        if (entity instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
        }

        return original; // Vanilla behaviour
    }
}
