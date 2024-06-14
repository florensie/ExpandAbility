package be.florens.expandability.test.fabric;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import be.florens.expandability.test.ExpandAbilityTest;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ExpandAbilityTestFabric implements ModInitializer {

    public static final Block SPEEDY_BLOCK = new Block(BlockBehaviour.Properties.of().speedFactor(2));

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block"), SPEEDY_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block"), new BlockItem(SPEEDY_BLOCK, new Item.Properties()));

        PlayerSwimCallback.EVENT.register(player -> {
            Item heldItem = player.getMainHandItem().getItem();
            return heldItem == Items.DEBUG_STICK ? EventResult.SUCCESS
                    : heldItem == Items.BARRIER ? EventResult.FAIL
                    : EventResult.PASS;
        });

        LivingFluidCollisionCallback.EVENT.register((entity, fluidState)
                -> entity.isHolding(Items.WATER_BUCKET) && fluidState.is(FluidTags.WATER));
    }
}
