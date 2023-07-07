package be.florens.expandability.test.fabric.gametest;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;

@SuppressWarnings("unused")
public class FluidCollisionTest {

    @GameTest(template = "expandability:pool")
    public void no_collide(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, 1, 5, 1);
        helper.startSequence()
                .thenExecuteAfter(20, () -> helper.assertEntityInstancePresent(villager, 1, 2, 1))
                .thenSucceed();
    }

    @GameTest(template = "expandability:pool")
    public void collide(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, 1, 5, 1);
        LivingFluidCollisionCallback.EVENT.register((entity, fluidState) -> entity.equals(villager));
        helper.startSequence()
                .thenExecuteAfter(20, () -> helper.assertEntityInstancePresent(villager, 1, 4, 1))
                .thenSucceed();
    }
}
