package be.florens.expandability.fabric;

import be.florens.expandability.EntityAttributes;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.ApiStatus;

public class EntityAttributesImpl {
    private static final boolean STEP_HEIGHT_MOD_LOADED = FabricLoader.getInstance().isModLoaded("step-height-entity-attribute");

    @ApiStatus.Internal
    public static float getStepHeight(LivingEntity entity) {
        // ServerPlayer has step height of 1.0, should be fixed in 1.20.5
        if (STEP_HEIGHT_MOD_LOADED && entity instanceof ServerPlayer) {
            float extraStepHeight = 0.0f;

            AttributeInstance attribute = entity.getAttribute(StepHeightEntityAttributeCompat.getAttribute());
            if (attribute != null) {
                extraStepHeight += (float) attribute.getValue();
            }

            return EntityAttributes.BASE_STEP_HEIGHT + extraStepHeight;
        }

        return entity.maxUpStep();
    }
}
