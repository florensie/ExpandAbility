package be.florens.expandability.api.fabric;

import be.florens.expandability.ExpandAbility;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.attributes.Attribute;

import java.util.function.Supplier;

public class AttributesImpl {

    public static Attribute register(String id, Supplier<Attribute> attributeSupplier) {
        return Registry.register(Registry.ATTRIBUTE, ExpandAbility.id(id), attributeSupplier.get());
    }
}
