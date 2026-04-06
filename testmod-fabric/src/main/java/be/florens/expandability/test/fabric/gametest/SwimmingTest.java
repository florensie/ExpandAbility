package be.florens.expandability.test.fabric.gametest;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Mannequin;
import net.minecraft.world.level.block.Blocks;

@SuppressWarnings("unused")
public class SwimmingTest {
    private static final BlockPos STREAM_MIDDLE = new BlockPos(2, 1, 1);
    private static final BlockPos FALLING_TOP_POS = new BlockPos(0, 5, 0);
    private static final BlockPos FALLING_BOTTOM_POS = new BlockPos(0, 1, 0);

    @GameTest(structure = "expandability:staircase", maxTicks = 100, setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsDefault_moved(GameTestHelper helper) {
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS); // same as not registering at all
        helper.spawn(EntityType.MANNEQUIN, STREAM_MIDDLE);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.PLAYER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", maxTicks = 100, setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsDisabled_doesNotMove(GameTestHelper helper) {
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, STREAM_MIDDLE);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.FAIL : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityInstancePresent(mannequin, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", maxTicks = 100, setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsEnabled_moved(GameTestHelper helper) {
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, STREAM_MIDDLE);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.SUCCESS : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.PLAYER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", maxTicks = 100, setupTicks = 20)
    public void mobWaterStream_withFluidPhysicsDefault_moved(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(EntityType.VILLAGER, STREAM_MIDDLE);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.VILLAGER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform", maxTicks = 100)
    public void fallInAir_withFluidPhysicsDefault_playerKilled(GameTestHelper helper) {
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS);  // same as not registering at all
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.withLowHealth(mannequin);

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertMannequinInstanceDead(mannequin))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform", maxTicks = 100)
    public void fallInAir_withFluidPhysicsEnabled_playerAliveAndStillDescending(GameTestHelper helper) {
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.withLowHealth(mannequin);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.SUCCESS : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> {
                    assertMannequinInstanceAlive(mannequin);
                    helper.assertEntityNotPresent(EntityType.PLAYER, FALLING_BOTTOM_POS);
                })
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform", maxTicks = 100)
    public void fallInAir_withFluidPhysicsDisabled_playerKilled(GameTestHelper helper) {
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.withLowHealth(mannequin);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.FAIL : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertMannequinInstanceDead(mannequin))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool", maxTicks = 100)
    public void fallInWater_withFluidPhysicsDisabled_playerKilled(GameTestHelper helper) {
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.withLowHealth(mannequin);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.FAIL : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertMannequinInstanceDead(mannequin))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool", maxTicks = 100)
    public void fallInWater_withFluidPhysicsDefault_playerLandsInWater(GameTestHelper helper) {
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS); // same as not registering at all
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.withLowHealth(mannequin);

        helper.startSequence()
                .thenExecuteAfter(20, () ->  helper.assertEntityInstancePresent(mannequin, FALLING_BOTTOM_POS))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool", maxTicks = 100)
    public void standInDeepWater_withFluidPhysicsDisabled_playerDrowns(GameTestHelper helper) {
        helper.setBlock(FALLING_BOTTOM_POS.above(), Blocks.WATER); // make pool two deep
        Mannequin mannequin = helper.spawn(EntityType.MANNEQUIN, FALLING_TOP_POS);
        helper.makeAboutToDrown(mannequin);
        PlayerSwimCallback.EVENT.register(p -> p.equals(mannequin) ? EventResult.FAIL : EventResult.PASS);

        helper.startSequence()
                .thenExecuteAfter(20 * 2, () ->  assertMannequinInstanceDead(mannequin))
                .thenSucceed();
    }

    private static void assertMannequinInstanceDead(Mannequin mannequin) {
        if (mannequin.isAlive()) {
            throw new GameTestAssertException(Component.literal("Expected mannequin to be dead"), 0);
        }
    }

    private static void assertMannequinInstanceAlive(Mannequin mannequin) {
        if (mannequin.isDeadOrDying()) {
            throw new GameTestAssertException(Component.literal("Expected mannequin to be alive"), 0);
        }
    }
}
