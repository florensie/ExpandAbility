package be.florens.expandability.mixin.fabric.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.Util;
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

    @Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z"))
	private boolean setInFluidState(FluidState fluidState, TagKey<Fluid> tag) {
		//noinspection ConstantConditions
		if ((Object) this instanceof Player player && tag == FluidTags.WATER) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim(player), () -> fluidState.is(tag));
		}

		return fluidState.is(tag);
	}
}
