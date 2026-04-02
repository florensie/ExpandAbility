package be.florens.expandability;

import be.florens.expandability.api.EventResult;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;

public class Util {

    public static boolean shouldPlayerSwim(Object entity, boolean defaultValue) {
        if (entity instanceof Player player) {
            return processEventResult(EventDispatcher.onPlayerSwim(player), defaultValue);
        }

        return defaultValue;
    }

    /**
     * Resolves an {@link InteractionResult} from an event to a boolean with a default value
     * @param result the {@link InteractionResult} received from the invoked event
     * @param defaultValue a boolean that defines the default/vanilla behaviour
     * @return true for success, false for fail, or the default value if the event passed
     */
    public static boolean processEventResult(EventResult result, boolean defaultValue) {
        return processEventResult(result, true, false, defaultValue);
    }

    public static <T> T processEventResult(EventResult result, T success, T fail, T defaultValue) {
        return switch (result) {
            case SUCCESS -> success;
            case FAIL -> fail;
            case PASS -> defaultValue;
        };
    }
}
