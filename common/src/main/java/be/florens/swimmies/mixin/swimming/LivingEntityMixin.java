package be.florens.swimmies.mixin.swimming;

import be.florens.swimmies.EventDispatcher;
import be.florens.swimmies.Util;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.Fluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@Redirect(method = "aiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/Tag;)D"))
	private double setFluidHeight(LivingEntity entity, Tag<Fluid> tag) {
		if (entity instanceof Player) {
			InteractionResult shouldSwim = EventDispatcher.onPlayerSwim((Player) entity);
			return shouldSwim.consumesAction() ? 1D
					: shouldSwim == InteractionResult.FAIL ? 0D
					: entity.getFluidHeight(tag); // Vanilla behaviour
		}

		return entity.getFluidHeight(tag); // Vanilla behaviour
	}

	@Redirect(method = {"aiStep", "travel", "checkFallDamage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
	private boolean setInWater(LivingEntity entity) {
		if (entity instanceof Player) {
			return Util.processEventResult(EventDispatcher.onPlayerSwim((Player) entity), entity::isInWater);
		}

		return entity.isInWater(); // Vanilla behaviour
	}
}
