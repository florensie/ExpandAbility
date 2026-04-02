package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	// Fabric-only: removed by Forge patch
    @ModifyExpressionValue(
			method = "updateSwimming",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z")
	)
	private boolean setInFluidState(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	@ModifyExpressionValue(
			method = "updateSwimming",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z")
	)
	private boolean setUnderWater(boolean original) {
		return Util.shouldPlayerSwim(this, original);
	}

	// Fabric-only: removed by Forge patch
	@WrapWithCondition(
			method = "updateFluidHeightAndDoFluidPushing",
			require = 1,
			at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V")
	)
	private boolean cancelFluidPushing(Entity entity, Vec3 vec3) {
		return Util.shouldPlayerSwim(this, true);
	}
}
