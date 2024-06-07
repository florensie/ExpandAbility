package be.florens.expandability.mixin.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.api.EventResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BubbleColumnBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BubbleColumnBlock.class)
public abstract class BubbleColumnBlockMixin {

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void cancelBubbleColumnDrag(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo info) {
        // TODO: do we still want the particles? Inject Entity onAboveBubbleCol/onInsideBubbleColumn instead?
        if (entity instanceof Player player && EventDispatcher.onPlayerSwim(player) == EventResult.FAIL) {
            info.cancel();
        }
    }
}
