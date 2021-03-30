package be.florens.swimmies.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

	@ModifyVariable(method = "aiStep", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/world/entity/LivingEntity;getFluidHeight(Lnet/minecraft/tags/Tag;)D"))
	private double setFluidHeight(double fluidHeight) {
		return 1D;
	}

	@Redirect(method = {"aiStep", "travel", "checkFallDamage"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;isInWater()Z"))
	private boolean setInWater(LivingEntity entity) {
		return true;
	}
}
