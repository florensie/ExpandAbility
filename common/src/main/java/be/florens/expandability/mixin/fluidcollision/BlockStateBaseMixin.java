package be.florens.expandability.mixin.fluidcollision;

import be.florens.expandability.EventDispatcher;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.EntityCollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateBaseMixin {

    @Shadow public abstract FluidState getFluidState();

    @ModifyReturnValue(method = "getCollisionShape(Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/phys/shapes/CollisionContext;)Lnet/minecraft/world/phys/shapes/VoxelShape;",
            at = @At("RETURN"))
    private VoxelShape addFluidCollision(VoxelShape originalShape, BlockGetter blockGetter, BlockPos blockPos, CollisionContext collisionContext) {
        FluidState fluidState = this.getFluidState();

        if (!(collisionContext instanceof EntityCollisionContext entityCollisionContext)
                || !(entityCollisionContext.getEntity() instanceof LivingEntity entity)
                || fluidState.isEmpty()
                || !blockGetter.getFluidState(blockPos.above()).isEmpty()) { // only collide on surface fluid blocks
            return originalShape;
        }

        double stepHeight = entity.getAttributeValue(Attributes.STEP_HEIGHT);
        float fluidHeight = fluidState.getHeight(blockGetter, blockPos);
        /* TODO (26.1+): check if this is still needed
         *
         * Add a small offset to the fluid height (while staying within the block's bounding box)
         *
         * Entity.checkInsideBlocks() erroneously collides with a block's fluid state when standing on it at/below y=63.
         * Could be related to getFluidHeight returning level/9, which can't be expressed exactly as a float.
         * Entity.fluidHeight (which isInWater/Lava use) are calculated elsewhere and seem to be unaffected.
         *
         * This wasn't an issue in 1.21.1 (although there used to be a comment here about fluidState.getShape
         * being bugged, could have been two bugs cancelling each other out)
         */
        fluidHeight = Math.min(1, fluidHeight + 0.00005F);
        // same as fluidState.getShape(), except for the small offset
        VoxelShape fluidShape = Shapes.box(0.0, 0.0, 0.0, 1.0, fluidHeight, 1.0);

        if (collisionContext.isAbove(fluidShape.move(0, -stepHeight, 0), blockPos, false)) {
            if (EventDispatcher.onLivingFluidCollision(entity, fluidState)) {
                return Shapes.or(fluidShape, originalShape);
            }
        }

        return originalShape;
    }
}
