package be.florens.swimmies;

import net.minecraft.world.InteractionResult;

import java.util.function.Supplier;

public class Util {

    /**
     * Resolves an {@link InteractionResult} from an event to a boolean with a default supplier
     * @param result the {@link InteractionResult} received from the invoked event
     * @param defaultSupplier a boolean {@link Supplier<Boolean>} that defines the default/vanilla behaviour
     * @return {@link InteractionResult} success or fail, or boolean defined by default supplier if passed
     */
    public static boolean processEventResult(InteractionResult result, Supplier<Boolean> defaultSupplier) {
        return result.consumesAction() // Success
                || (result != InteractionResult.FAIL // Fail
                && defaultSupplier.get()); // Pass (default/vanilla behaviour)
    }
}
