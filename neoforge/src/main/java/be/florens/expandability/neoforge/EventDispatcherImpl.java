package be.florens.expandability.neoforge;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.common.NeoForge;

public class EventDispatcherImpl {

    public static EventResult onPlayerSwim(Player player) {
        PlayerSwimEvent event = new PlayerSwimEvent(player);
        NeoForge.EVENT_BUS.post(event);
        return event.getResult();
    }

    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        LivingFluidCollisionEvent event = new LivingFluidCollisionEvent(entity, fluidState);
        NeoForge.EVENT_BUS.post(event);
        return event.shouldCollide();
    }
}
