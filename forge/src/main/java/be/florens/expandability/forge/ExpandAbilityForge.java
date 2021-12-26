package be.florens.expandability.forge;

import be.florens.expandability.ExpandAbility;
import be.florens.expandability.api.Attributes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

@Mod(ExpandAbility.MOD_ID)
public class ExpandAbilityForge {

	public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Attribute.class, ExpandAbility.MOD_ID);

	public ExpandAbilityForge() {
		ExpandAbility.init();
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ATTRIBUTES.register(eventBus);
		eventBus.addListener((EntityAttributeModificationEvent event) ->
				event.getTypes().forEach(type -> event.add(type, Attributes.SWIMMING)));
	}
}
