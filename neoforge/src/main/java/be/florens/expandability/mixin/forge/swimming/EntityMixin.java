package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.EventResult;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyExpressionValue(method = {"updateSwimming", "isVisuallyCrawling"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInFluidType(Ljava/util/function/BiPredicate;)Z"))
    private boolean setInFluidType(boolean original) {
        if ((Object) this instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
        }

        return original;
    }

    @ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canStartSwimming()Z"))
    private boolean setCanStartSwimming(boolean original) {
        if ((Object) this instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
        }

        return original;
    }

    // FIXME: still no fall damage
    @WrapWithCondition(method = "updateInWaterStateAndDoFluidPushing", require = 1, at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/world/entity/Entity;fallDistance:F"))
    private boolean cancelFallDistanceUpdate(Entity entity, float fallDistance) {
        return !(entity instanceof Player player) || EventDispatcher.onPlayerSwim(player) != EventResult.FAIL;
    }

    @ModifyExpressionValue(method = "canSpawnSprintParticle", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInFluidType()Z"))
    private boolean setInFluidTypeNoParams(boolean original) {
        if ((Object) this instanceof Player player) {
            return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
        }

        return original;
    }

    /**
     * - $26: Forge 46.x.x
     * - $29: Forge 47.x.x
     */
    @WrapWithCondition(method = {"lambda$updateFluidHeightAndDoFluidPushing$26", "lambda$updateFluidHeightAndDoFluidPushing$29"}, require = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
    private boolean cancelFluidPushing(Entity entity, Vec3 vec3) {
        //noinspection ConstantValue
        return entity instanceof Player player && EventDispatcher.onPlayerSwim(player) != EventResult.FAIL;
    }
}
