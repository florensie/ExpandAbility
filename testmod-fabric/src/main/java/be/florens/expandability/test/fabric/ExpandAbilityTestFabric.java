package be.florens.expandability.test.fabric;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import be.florens.expandability.test.ExpandAbilityTest;
import net.fabricmc.api.ModInitializer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ExpandAbilityTestFabric implements ModInitializer {

    public static final Block SPEEDY_BLOCK = new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block"))).speedFactor(2));

    @Override
    public void onInitialize() {
        Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block"), SPEEDY_BLOCK);
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block"), new BlockItem(SPEEDY_BLOCK, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(ExpandAbilityTest.MOD_ID, "speedy_block")))));

        PlayerSwimCallback.EVENT.register(player -> {
            if (player.isHolding(Items.BARRIER)) {
                return EventResult.FAIL;
            } else if (player.isHolding(Items.DEBUG_STICK)) {
                return EventResult.SUCCESS;
            }
            return EventResult.PASS;
        });

        LivingFluidCollisionCallback.EVENT.register((entity, fluidState)
                -> entity.isHolding(Items.WATER_BUCKET) && fluidState.is(FluidTags.WATER)
                || entity.isHolding(Items.LAVA_BUCKET) && fluidState.is(FluidTags.LAVA)
        );
    }
}
