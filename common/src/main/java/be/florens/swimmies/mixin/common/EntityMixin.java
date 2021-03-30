package be.florens.swimmies.mixin.common;

import it.unimi.dsi.fastutil.objects.Object2DoubleMap;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

	@Unique private boolean fakeFluidStateApplied;

	@Redirect(method = {"updateSwimming", "isVisuallyCrawling", "canSpawnSprintParticle"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isInWater()Z"))
	private boolean setInWater(Entity entity) {
		return true;
	}

	@Redirect(method = "updateSwimming", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;isUnderWater()Z"))
	private boolean setUnderWater(Entity entity) {
		return true;
	}

	@Redirect(method = "updateFluidHeightAndDoFluidPushing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;getFluidState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/material/FluidState;"))
	private FluidState setFluidState(Level world, BlockPos blockPos, Tag<Fluid> tag) {
		if (tag != FluidTags.WATER) {
			this.fakeFluidStateApplied = false;
			return world.getFluidState(blockPos);
		}

		this.fakeFluidStateApplied = true;
		return Fluids.WATER.defaultFluidState();
	}

	@Redirect(method = "updateFluidHeightAndDoFluidPushing", allow = 1, at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/Object2DoubleMap;put(Ljava/lang/Object;D)D"))
	private double cancelAddFluidHeight(Object2DoubleMap<Tag<Fluid>> fluidHeight, Object tag, double height) {
		if (!this.fakeFluidStateApplied) {
			//noinspection unchecked
			return fluidHeight.put((Tag<Fluid>) tag, height);
		}

		return 0;
	}

	@Inject(method = "updateFluidHeightAndDoFluidPushing", at = @At("RETURN"), cancellable = true)
	private void returnFalse(CallbackInfoReturnable<Boolean> info) {
		if (this.fakeFluidStateApplied) {
			info.setReturnValue(false);
		}
	}
}
