package be.florens.expandability.forge;

import be.florens.expandability.EventResult;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class EventDispatcherImpl {

    public static EventResult dispatchPlayerSwim(Player player) {
        PlayerSwimEvent event = new PlayerSwimEvent(player);
        MinecraftForge.EVENT_BUS.post(event);
        return getEventResult(event);
    }

    public static boolean dispatchLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        LivingFluidCollisionEvent event = new LivingFluidCollisionEvent(entity, fluidState);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResult() == Event.Result.ALLOW;
    }

    private static EventResult getEventResult(Event event) {
        return switch (event.getResult()) {
            case ALLOW -> EventResult.SUCCESS;
            case DENY -> EventResult.FAIL;
            case DEFAULT -> EventResult.PASS;
        };
    }
}
