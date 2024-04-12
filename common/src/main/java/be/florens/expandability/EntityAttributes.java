package be.florens.expandability;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.ApiStatus;

public class EntityAttributes {
    public static final float BASE_STEP_HEIGHT = 0.6f;

    @ExpectPlatform
    @ApiStatus.Internal
    public static float getStepHeight(LivingEntity entity) {
        throw new IllegalStateException();
    }
}
