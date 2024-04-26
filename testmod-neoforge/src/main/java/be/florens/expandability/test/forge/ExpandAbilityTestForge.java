package be.florens.expandability.test.forge;

import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import be.florens.expandability.api.forge.PlayerSwimEvent;
import be.florens.expandability.test.ExpandAbilityTest;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;


@Mod(ExpandAbilityTest.MOD_ID)
public class ExpandAbilityTestForge {

	public ExpandAbilityTestForge() {
		ExpandAbilityTest.init();
		NeoForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerSwim(PlayerSwimEvent event) {
		Item heldItem = event.getEntity().getMainHandItem().getItem();
		Event.Result result = heldItem == Items.DEBUG_STICK ? Event.Result.ALLOW
				: heldItem == Items.BARRIER ? Event.Result.DENY
				: Event.Result.DEFAULT;
		event.setResult(result);
	}

	@SubscribeEvent
	public void onLivingFluidCollision(LivingFluidCollisionEvent event) {
		if (event.getEntity().isHolding(Items.WATER_BUCKET) && event.getFluidState().is(FluidTags.WATER)) {
			event.setResult(Event.Result.ALLOW);
		}
	}
}
