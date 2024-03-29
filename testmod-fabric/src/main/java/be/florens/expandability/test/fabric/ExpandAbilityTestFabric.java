package be.florens.expandability.test.fabric;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import be.florens.expandability.test.ExpandAbilityTest;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

public class ExpandAbilityTestFabric implements ModInitializer {

    public static final Block SPEEDY_BLOCK = new Block(FabricBlockSettings.create().speedFactor(2));

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK, new ResourceLocation(ExpandAbilityTest.MOD_ID, "speedy_block"), SPEEDY_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, new ResourceLocation(ExpandAbilityTest.MOD_ID, "speedy_block"), new BlockItem(SPEEDY_BLOCK, new Item.Properties()));

        PlayerSwimCallback.EVENT.register(player -> {
            Item heldItem = player.getMainHandItem().getItem();
            return heldItem == Items.DEBUG_STICK ? TriState.TRUE
                    : heldItem == Items.BARRIER ? TriState.FALSE
                    : TriState.DEFAULT;
        });

        LivingFluidCollisionCallback.EVENT.register((entity, fluidState)
                -> entity.isHolding(Items.WATER_BUCKET) && fluidState.is(FluidTags.WATER));
    }
}
