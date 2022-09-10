package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

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
}
