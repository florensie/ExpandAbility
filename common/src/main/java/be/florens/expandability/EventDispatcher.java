package be.florens.expandability;

import be.florens.expandability.api.EventResult;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.ApiStatus;

public class EventDispatcher {

    @ExpectPlatform
    @ApiStatus.Internal
    public static EventResult onPlayerSwim(Player player) {
        throw new IllegalStateException();
    }

    @ExpectPlatform
    @ApiStatus.Internal
    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        throw new IllegalStateException();
    }
}
