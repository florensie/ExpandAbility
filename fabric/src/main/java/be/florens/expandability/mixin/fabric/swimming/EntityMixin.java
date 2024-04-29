package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.EventResult;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

	// Fabric-only: removed by Forge patch
    @ModifyExpressionValue(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean setInFluidState(boolean original) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), original);
		}

		return original;
	}

	@WrapWithCondition(method = "updateFluidHeightAndDoFluidPushing", require = 1, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;setDeltaMovement(Lnet/minecraft/world/phys/Vec3;)V"))
	private boolean cancelFluidPushing(Entity entity, Vec3 vec3) {
        //noinspection ConstantValue
        return !(entity instanceof Player player) || EventDispatcher.onPlayerSwim(player) != EventResult.FAIL;
	}
}
