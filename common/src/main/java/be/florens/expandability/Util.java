package be.florens.expandability;

import net.minecraft.world.InteractionResult;

import java.util.function.Supplier;

public class Util {

    /**
     * Resolves an {@link InteractionResult} from an event to a boolean with a default value
     * @param result the {@link InteractionResult} received from the invoked event
     * @param defaultValue a boolean that defines the default/vanilla behaviour
     * @return true for success, false for fail, or the default value if the event passed
     */
    public static boolean processEventResult(EventResult result, boolean defaultValue) {
        return processEventResult(result, true, false, defaultValue);
    }

    /**
     * Resolves an {@link InteractionResult} from an event to a boolean with a default supplier
     * @param result the {@link InteractionResult} received from the invoked event
     * @param defaultSupplier a boolean {@link Supplier<Boolean>} that defines the default/vanilla behaviour
     * @return true for success, false for fail, or the boolean defined by default supplier if the event passed
     */
    public static boolean processEventResult(EventResult result, Supplier<Boolean> defaultSupplier) {
        return processEventResult(result, true, false, defaultSupplier);
    }

    public static <T> T processEventResult(EventResult result, T success, T fail, Supplier<T> defaultSupplier) {
        return processEventResult(result, success, fail, defaultSupplier.get());
    }

    public static <T> T processEventResult(EventResult result, T success, T fail, T defaultValue) {
        return switch (result) {
            case SUCCESS -> success;
            case FAIL -> fail;
            case PASS -> defaultValue;
        };
    }
}
