package be.florens.expandability.api.forge;

import be.florens.expandability.forge.ExpandAbilityForge;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public class AttributesImpl {

    public static Attribute register(String id, Supplier<Attribute> attributeSupplier) {
        return ExpandAbilityForge.ATTRIBUTES.register(id, attributeSupplier).get();
    }
}
