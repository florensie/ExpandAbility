package be.florens.swimmies.mixin;

import be.florens.swimmies.api.PlayerSwimEvent;
import net.minecraft.tags.Tag;
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
		return entity instanceof Player && PlayerSwimEvent.EVENT.invoker().swim((Player) entity) ? 1D
				: entity.getFluidHeight(tag); // Vanilla behaviour
	}

	@Redirect(method = {"aiStep", "travel", "checkFallDamage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
	private boolean setInWater(LivingEntity entity) {
		return entity instanceof Player && PlayerSwimEvent.EVENT.invoker().swim((Player) entity)
				|| entity.isInWater(); // Vanilla behaviour
	}
}
