package be.florens.expandability.fabric;

import dev.emi.stepheightentityattribute.StepHeightEntityAttributeMain;
import net.minecraft.world.entity.ai.attributes.Attribute;

class StepHeightEntityAttributeCompat {

    static Attribute getAttribute() {
        return StepHeightEntityAttributeMain.STEP_HEIGHT;
    }
}
