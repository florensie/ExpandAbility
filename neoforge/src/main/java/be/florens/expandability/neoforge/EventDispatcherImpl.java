package be.florens.expandability.neoforge;

import be.florens.expandability.EventResult;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.common.NeoForge;

public class EventDispatcherImpl {

    public static EventResult onPlayerSwim(Player player) {
        PlayerSwimEvent event = new PlayerSwimEvent(player);
        NeoForge.EVENT_BUS.post(event);
        return getEventResult(event);
    }

    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        LivingFluidCollisionEvent event = new LivingFluidCollisionEvent(entity, fluidState);
        NeoForge.EVENT_BUS.post(event);
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
