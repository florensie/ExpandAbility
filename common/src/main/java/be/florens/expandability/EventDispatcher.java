package be.florens.expandability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class EventDispatcher {

    @ExpectPlatform
    public static InteractionResult onPlayerSwim(Player player) {
        return InteractionResult.PASS;
    }

    @ExpectPlatform
    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        return false;
    }
}
