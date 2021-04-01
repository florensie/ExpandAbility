package be.florens.swimmies.test.forge;

import be.florens.swimmies.api.forge.PlayerSwimEvent;
import be.florens.swimmies.test.SwimmiesTest;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod(SwimmiesTest.MOD_ID)
public class SwimmiesTestForge {

	public SwimmiesTestForge() {
		SwimmiesTest.init();
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void onPlayerSwim(PlayerSwimEvent event) {
		Item heldItem = event.getPlayer().getMainHandItem().getItem();
		Event.Result result = heldItem == Items.DEBUG_STICK ? Event.Result.ALLOW
				: heldItem == Items.BARRIER ? Event.Result.DENY
				: Event.Result.DEFAULT;
		event.setResult(result);
	}
}
