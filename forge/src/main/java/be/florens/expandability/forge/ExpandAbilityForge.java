package be.florens.expandability.forge;

import be.florens.expandability.ExpandAbility;
import net.minecraft.world.entity.ai.attributes.Attribute;
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
	}
}
