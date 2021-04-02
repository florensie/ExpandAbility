package be.florens.swimmies.compat;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import me.shedaniel.architectury.event.events.CommandRegistrationEvent;
import net.minecraft.world.InteractionResult;

import static net.minecraft.commands.Commands.literal;


/**
 * For features that are only active when architectury is installed
 */
public class ArchitecturyCompat {

    public static InteractionResult swimmie = InteractionResult.PASS;

    public static void init() {
        platformInit();
        CommandRegistrationEvent.EVENT.register((dispatcher, selection) -> {
            dispatcher.register(literal("swimmie")
                    .then(literal("default")
                            .executes(context -> {
                                swimmie = InteractionResult.PASS;
                                return 1;
                            })
                    )
                    .then(literal("enable")
                            .executes(context -> {
                                swimmie = InteractionResult.SUCCESS;
                                return 1;
                            })
                    )
                    .then(literal("disable")
                            .executes(context -> {
                                swimmie = InteractionResult.FAIL;
                                return 1;
                            })
                    )
            );
        });
    }

    @ExpectPlatform
    private static void platformInit() {
        throw new IllegalStateException();
    }
}
