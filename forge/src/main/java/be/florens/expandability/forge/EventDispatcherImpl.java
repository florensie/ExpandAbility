package be.florens.expandability.forge;

import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;

public class EventDispatcherImpl {

    public static InteractionResult onPlayerSwim(Player player) {
        PlayerSwimEvent event = new PlayerSwimEvent(player);
        MinecraftForge.EVENT_BUS.post(event);
        return getEventResult(event);
    }

    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        LivingFluidCollisionEvent event = new LivingFluidCollisionEvent(entity, fluidState);
        MinecraftForge.EVENT_BUS.post(event);
        return event.getResult() == Event.Result.ALLOW;
    }

    private static InteractionResult getEventResult(Event event) {
        return switch (event.getResult()) {
            case ALLOW -> InteractionResult.SUCCESS;
            case DENY -> InteractionResult.FAIL;
            default -> InteractionResult.PASS;
        };
    }
}
