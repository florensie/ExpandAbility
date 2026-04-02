package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @ModifyExpressionValue(
            method = {"updateSwimming", "isVisuallyCrawling"},
            require = 2,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInFluidType(Ljava/util/function/BiPredicate;)Z")
    )
    private boolean setInFluidType(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    @ModifyExpressionValue(
            method = "updateSwimming",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;canStartSwimming()Z")
    )
    private boolean setCanStartSwimming(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    @WrapWithCondition(
            method = "updateInWaterStateAndDoFluidPushing",
            require = 1,
            at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/world/entity/Entity;fallDistance:D")
    )
    private boolean shouldResetFallDistance(Entity entity, double fallDistance) {
        return Util.shouldPlayerSwim(this, true);
    }

    @WrapWithCondition(
            method = "updateInWaterStateAndDoWaterCurrentPushing(Z)V",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;resetFallDistance()V")
    )
    private boolean shouldResetFallDistance(Entity instance) {
        return Util.shouldPlayerSwim(this, true);
    }

    @ModifyExpressionValue(
            method = "canSpawnSprintParticle",
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInFluidType()Z")
    )
    private boolean setInFluidTypeNoParams(boolean original) {
        return Util.shouldPlayerSwim(this, original);
    }

    /** Lol 🫠
     * <br> $26: Forge 46.x.x
     * <br> $29: Forge 47.x.x, NeoForge 20.4.x
     * <br> $28: NeoForge 20.6.x
     * <br> $22: NeoForge 21.x.x
     * <br> $27: NeoForge 21.11.x
     */
    @SuppressWarnings("UnresolvedMixinReference")
    @WrapWithCondition(
            method = {
                    "lambda$updateFluidHeightAndDoFluidPushing$22",
                    "lambda$updateFluidHeightAndDoFluidPushing$26",
                    "lambda$updateFluidHeightAndDoFluidPushing$27",
                    "lambda$updateFluidHeightAndDoFluidPushing$28",
                    "lambda$updateFluidHeightAndDoFluidPushing$29"
            },
            require = 1,
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V")
    )
    private boolean shouldDoFluidPushing(Entity entity, Vec3 vec3) {
        return Util.shouldPlayerSwim(this, true);
    }
}
