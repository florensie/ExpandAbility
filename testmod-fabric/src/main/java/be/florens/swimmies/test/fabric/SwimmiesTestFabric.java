package be.florens.swimmies.test.fabric;

import be.florens.swimmies.api.fabric.PlayerSwimCallback;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

public class SwimmiesTestFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PlayerSwimCallback.EVENT.register(player -> {
            Item heldItem = player.getMainHandItem().getItem();
            return heldItem == Items.DEBUG_STICK ? InteractionResult.SUCCESS
                    : heldItem == Items.BARRIER ? InteractionResult.FAIL
                    : InteractionResult.PASS;
        });
    }
}
