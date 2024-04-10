package be.florens.expandability.mixin.fluidcollision;

import be.florens.expandability.CursedVoxelShapeWrapper;
import be.florens.expandability.EventDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("UnreachableCode")
@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow public abstract FluidState getFluidState();

    @Inject(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("RETURN"), cancellable = true)
    private void addFluidCollision(BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext, CallbackInfoReturnable<VoxelShape> callbackInfo) {
        FluidState fluidState = this.getFluidState();

        if (!(collisionContext instanceof EntityCollisionContext entityCollisionContext)
                || !(entityCollisionContext.getEntity() instanceof LivingEntity entity)
                || fluidState.isEmpty()) {
            return;
        }

        VoxelShape fluidShape = fluidState.getShape(blockGetter, blockPos);
        float stepHeight = entity.maxUpStep();

        //noinspection ConstantValue
        if (collisionContext.isAbove(fluidShape.move(0, -stepHeight, 0), blockPos, false)
                && EventDispatcher.onLivingFluidCollision(entity, fluidState)) {
            VoxelShape originalShape = callbackInfo.getReturnValue();
            VoxelShape mergedShape = Shapes.or(fluidShape, originalShape);

            //callbackInfo.setReturnValue(new CursedVoxelShapeWrapper(originalShape, mergedShape));
            callbackInfo.setReturnValue(mergedShape);
            callbackInfo.cancel();
        }
    }
}
