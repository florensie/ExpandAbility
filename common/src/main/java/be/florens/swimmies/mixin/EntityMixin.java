package be.florens.swimmies.mixin;

import be.florens.swimmies.Swimmies;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Unique private boolean lastWaterState;

	@Shadow public abstract boolean isInWater();

	@Inject(method = "tick", at = @At("TAIL"))
	private void blep(CallbackInfo info) {
		boolean inWater = this.isInWater();
		if (inWater != this.lastWaterState) {
			Swimmies.LOGGER.info("isInWater() = " + inWater);
		}
		this.lastWaterState = inWater;
	}

	@Redirect(method = {"updateSwimming", "isVisuallyCrawling", "canSpawnSprintParticle", "move"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
	private boolean setInWater(Entity entity) {
		return true;
	}

	@Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSwimSound(F)V"))
	private void cancelPlaySwimSound(Entity entity, float f) {
	}

	@Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z"))
	private boolean setUnderWater(Entity entity) {
		return true;
	}
}
