package be.florens.expandability.forge;

import be.florens.expandability.EntityAttributes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraftforge.common.ForgeMod;
import org.jetbrains.annotations.ApiStatus;

public class EntityAttributesImpl {

    @ApiStatus.Internal
    public static float getStepHeight(LivingEntity entity) {
        // ServerPlayer has step height of 1.0, should be fixed in 1.20.5
        if (entity instanceof ServerPlayer) {
            float extraStepHeight = 0.0f;

            AttributeInstance attribute = entity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
            if (attribute != null) {
                extraStepHeight += (float) attribute.getValue();
            }

            return EntityAttributes.BASE_STEP_HEIGHT + extraStepHeight;
        }

        return entity.getStepHeight();
    }
}
