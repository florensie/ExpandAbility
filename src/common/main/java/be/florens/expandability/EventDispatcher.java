package be.florens.expandability;

import be.florens.expandability.api.EventResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.msrandom.multiplatform.annotations.Expect;

public class EventDispatcher {

    @Expect
    public static EventResult onPlayerSwim(Player player);

    @Expect
    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState);
}
