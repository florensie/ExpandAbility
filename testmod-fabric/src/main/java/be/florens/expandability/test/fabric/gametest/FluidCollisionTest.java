package be.florens.expandability.test.fabric.gametest;

import be.florens.expandability.api.fabric.LivingFluidCollisionCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.Villager;

@SuppressWarnings("unused")
public class FluidCollisionTest {

    private static final BlockPos ABOVE_POOL = new BlockPos(1, 5, 1);
    private static final BlockPos STAIRCASE_BOTTOM = new BlockPos(10, 2, 1);
    private static final BlockPos STAIRCASE_TOP = new BlockPos(1, 3, 1);
    private static final int STAIRCASE_TIMEOUT = 2 * 20;

    @GameTest(template = "expandability:pool")
    public void dropAboveWater_noCollisionEvent_dropsIntoWater(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, ABOVE_POOL);
        helper.startSequence()
                .thenExecuteAfter(20, () -> helper.assertEntityInstancePresent(villager, 1, 2, 1))
                .thenSucceed();
    }

    @GameTest(template = "expandability:pool")
    public void dropAboveWater_withCollisionEvent_collidesWithWaterSurface(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, ABOVE_POOL);
        LivingFluidCollisionCallback.EVENT.register((entity, fluidState) -> entity.equals(villager));
        helper.startSequence()
                .thenExecuteAfter(20, () -> helper.assertEntityInstancePresent(villager, 1, 4, 1))
                .thenSucceed();
    }

    @GameTest(template = "expandability:staircase")
    public void walkDownWaterStaircase_noCollisionEvent_doesNotReachBottom(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, STAIRCASE_TOP);
        helper.walkTo(villager, STAIRCASE_BOTTOM, 1.0f)
                .thenExecuteAfter(STAIRCASE_TIMEOUT, () -> helper.assertEntityNotPresent(EntityType.VILLAGER, STAIRCASE_BOTTOM))
                .thenSucceed();
    }

    @GameTest(template = "expandability:staircase")
    public void walkDownWaterStaircase_withCollisionEvent_reachesBottom(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, STAIRCASE_TOP);
        LivingFluidCollisionCallback.EVENT.register((entity, fluidState) -> entity.equals(villager));
        helper.walkTo(villager, STAIRCASE_BOTTOM, 1.0f)
                .thenExecuteAfter(STAIRCASE_TIMEOUT, () -> helper.assertEntityPresent(EntityType.VILLAGER, STAIRCASE_BOTTOM))
                .thenSucceed();
    }

    @GameTest(template = "expandability:staircase")
    public void walkUpWaterStaircase_noCollisionEvent_doesNotReachBottom(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, STAIRCASE_BOTTOM);
        helper.walkTo(villager, STAIRCASE_TOP, 1.0f)
                .thenExecuteAfter(STAIRCASE_TIMEOUT, () -> helper.assertEntityNotPresent(EntityType.VILLAGER, STAIRCASE_TOP))
                .thenSucceed();
    }

    @GameTest(template = "expandability:staircase")
    public void walkUpWaterStaircase_withCollisionEvent_reachesTop(GameTestHelper helper) {
        Villager villager = helper.spawnWithNoFreeWill(EntityType.VILLAGER, STAIRCASE_BOTTOM);
        LivingFluidCollisionCallback.EVENT.register((entity, fluidState) -> entity.equals(villager));
        helper.walkTo(villager, STAIRCASE_TOP, 1.0f)
                .thenExecuteAfter(STAIRCASE_TIMEOUT, () -> helper.assertEntityPresent(EntityType.VILLAGER, STAIRCASE_TOP))
                .thenSucceed();
    }
}
