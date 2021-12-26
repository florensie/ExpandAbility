package be.florens.expandability.api;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;

/**
 * Attributes for all abilities. Events take priority over these attributes.
 */
public class Attributes {

    // TODO: Sink in all fluids?
    public static final Attribute SINKING = register("generic.expandability.sinking",
            new RangedAttribute("attribute.name.generic.expandability.sinking", 0.0D, 0.0D, 1.0D).setSyncable(true));

    /**
     * Unlike the swimming event, the attribute requires the player to press the sprint key to start swimming.
     * This attribute has priority over the sinking attribute.
     */
    public static final Attribute SWIMMING = register("generic.expandability.swimming",
            new RangedAttribute("attribute.name.generic.expandability.swimming", 0.0D, 0.0D, 1.0D).setSyncable(true));

    // TODO: Walk on all fluids?
    public static final Attribute FLUID_WALKING = register("generic.expandability.fluid_walking",
            new RangedAttribute("attribute.name.generic.expandability.fluid_walking", 0.0D, 0.0D, 1.0D).setSyncable(true));

    @ExpectPlatform
    public static Attribute register(String id, Attribute attribute) {
        return null;
    }
}
