package be.florens.expandability;

import net.minecraft.world.InteractionResult;

import java.util.function.Supplier;

public class Util {

    /**
     * Resolves an {@link InteractionResult} from an event to a boolean with a default supplier
     * @param result the {@link InteractionResult} received from the invoked event
     * @param defaultSupplier a boolean {@link Supplier<Boolean>} that defines the default/vanilla behaviour
     * @return {@link InteractionResult} success or fail, or boolean defined by default supplier if passed
     */
    public static boolean processEventResult(EventResult result, Supplier<Boolean> defaultSupplier) {
        return processEventResult(result, true, false, defaultSupplier);
    }

    public static <T> T processEventResult(EventResult result, T success, T fail, Supplier<T> defaultSupplier) {
        return switch (result) {
            case SUCCESS -> success;
            case FAIL -> fail;
            case PASS -> defaultSupplier.get();
        };
    }
}
