package be.florens.swimmies.mixin;

import be.florens.swimmies.api.PlayerSwimEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Shadow protected abstract void playSwimSound(float f);

	@Shadow public abstract boolean isInWater();

	@Redirect(method = {"updateSwimming", "isVisuallyCrawling", "canSpawnSprintParticle", "move"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
	private boolean setInWater(Entity entity) {
		return entity instanceof Player && PlayerSwimEvent.EVENT.invoker().swim((Player) entity)
				|| entity.isInWater(); // Vanilla behaviour
	}

	@Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z"))
	private boolean setUnderWater(Entity entity) {
		return entity instanceof Player && PlayerSwimEvent.EVENT.invoker().swim((Player) entity)
				|| entity.isUnderWater(); // Vanilla behaviour
	}

	/**
	 * Prevents the swimming sound from playing when non-vanilla swimming is enabled
	 */
	@Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;playSwimSound(F)V"))
	private void cancelPlaySwimSound(Entity entity, float f) {
		// Re-check if we're in water first so we don't cancel vanilla swimming sounds
		if (!this.isInWater() && entity instanceof Player && PlayerSwimEvent.EVENT.invoker().swim((Player) entity)) {
			return;
		}

		// Vanilla behaviour
		this.playSwimSound(f);
	}
}
