package be.florens.expandability.fabric;

import be.florens.expandability.EventResult;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class EventDispatcherImpl {

    public static EventResult onPlayerSwim(Player player) {
        TriState result = PlayerSwimCallback.EVENT.invoker().swim(player);
        return getEventResult(result);
    }

	public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
		return LivingFluidCollisionCallback.EVENT.invoker().collide(entity, fluidState);
	}

    private static EventResult getEventResult(TriState result) {
        return switch (result) {
            case TRUE -> EventResult.SUCCESS;
            case FALSE -> EventResult.FAIL;
            case DEFAULT -> EventResult.PASS;
        };
    }
}
