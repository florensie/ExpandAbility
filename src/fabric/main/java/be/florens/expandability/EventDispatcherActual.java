package be.florens.expandability;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.msrandom.multiplatform.annotations.Actual;

public class EventDispatcherActual {

    @Actual
    public static EventResult onPlayerSwim(Player player) {
        return PlayerSwimCallback.EVENT.invoker().swim(player);
    }

    @Actual
    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        return LivingFluidCollisionCallback.EVENT.invoker().collide(entity, fluidState);
    }
}
