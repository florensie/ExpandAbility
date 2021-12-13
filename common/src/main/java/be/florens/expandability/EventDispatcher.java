package be.florens.expandability;

import be.florens.expandability.api.Attributes;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;

public class EventDispatcher {

    public static EventResult onPlayerSwim(Player player) {
        // Event takes priority over attribute
        EventResult result = dispatchPlayerSwim(player);

        if (result == EventResult.PASS) {
            AttributeInstance swimAttribute = player.getAttribute(Attributes.SWIMMING);
            AttributeInstance sinkAttribute = player.getAttribute(Attributes.SINKING);

            // TODO: add sprinting check etc for swimming
            // TODO: sync C2S
            if (swimAttribute != null && swimAttribute.getValue() > 0) {
                result = EventResult.SUCCESS;
            } else if (sinkAttribute != null && sinkAttribute.getValue() > 0) {
                result = EventResult.FAIL;
            }
        }

        return result;
    }

    public static boolean onLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        AttributeInstance attribute = entity.getAttribute(Attributes.FLUID_WALKING);
        return dispatchLivingFluidCollision(entity, fluidState) || (attribute != null && attribute.getValue() > 0);
    }

    @ExpectPlatform
    public static EventResult dispatchPlayerSwim(Player player) {
        return EventResult.PASS;
    }

    @ExpectPlatform
    public static boolean dispatchLivingFluidCollision(LivingEntity entity, FluidState fluidState) {
        return false;
    }
}
