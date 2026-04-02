package be.florens.expandability.test.fabric.gametest;

import be.florens.expandability.api.EventResult;
import be.florens.expandability.api.fabric.PlayerSwimCallback;
import be.florens.expandability.test.fabric.mixin.ServerGamePacketListenerImplAccessor;
import carpet.patches.EntityPlayerMPFake;
import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

// TODO fall damage on fake players is broken (again)
@SuppressWarnings("unused")
public class SwimmingTest {
    private static final BlockPos STREAM_MIDDLE = new BlockPos(2, 2, 1);
    private static final BlockPos FALLING_TOP_POS = new BlockPos(0, 6, 0);
    private static final BlockPos FALLING_BOTTOM_POS = new BlockPos(0, 2, 0);

    @GameTest(structure = "expandability:staircase", setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsDefault_moved(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS); // same as not registering at all
        createFakePlayer(helper, STREAM_MIDDLE, playerName);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.PLAYER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsDisabled_doesNotMove(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.FAIL : EventResult.PASS);
        ServerPlayer player = createFakePlayer(helper, STREAM_MIDDLE, playerName);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityInstancePresent(player, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", setupTicks = 20)
    public void standInWaterStream_withFluidPhysicsEnabled_moved(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.SUCCESS : EventResult.PASS);
        createFakePlayer(helper, STREAM_MIDDLE, playerName);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.PLAYER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:staircase", setupTicks = 20)
    public void mobWaterStream_withFluidPhysicsDefault_moved(GameTestHelper helper) {
        helper.spawnWithNoFreeWill(EntityType.VILLAGER, STREAM_MIDDLE);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityNotPresent(EntityType.VILLAGER, STREAM_MIDDLE))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform")
    public void fallInAir_withFluidPhysicsDefault_playerKilled(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS);  // same as not registering at all
        ServerPlayer player = createFakePlayer(helper, FALLING_TOP_POS, playerName);
        helper.withLowHealth(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertPlayerInstanceDead(player))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform")
    public void fallInAir_withFluidPhysicsEnabled_playerAliveAndStillDescending(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.SUCCESS : EventResult.PASS);
        ServerPlayer player = createFakePlayer(helper, FALLING_TOP_POS, playerName);
        helper.withLowHealth(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> {
                    assertPlayerInstanceAlive(player);
                    helper.assertEntityNotPresent(EntityType.PLAYER, FALLING_BOTTOM_POS);
                })
                .thenSucceed();
    }

    @GameTest(structure = "expandability:platform")
    public void fallInAir_withFluidPhysicsDisabled_playerKilled(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.FAIL : EventResult.PASS);
        ServerPlayer player = createFakePlayer(helper, FALLING_TOP_POS, playerName);
        helper.withLowHealth(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertPlayerInstanceDead(player))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool")
    public void fallInWater_withFluidPhysicsDisabled_playerKilled(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.FAIL : EventResult.PASS);
        ServerPlayer player = createFakePlayer(helper, FALLING_TOP_POS, playerName);
        helper.withLowHealth(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20, () -> assertPlayerInstanceDead(player))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool")
    public void fallInWater_withFluidPhysicsDefault_playerLandsInWater(GameTestHelper helper) {
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> EventResult.PASS); // same as not registering at all
        ServerPlayer player = createFakePlayer(helper, FALLING_TOP_POS, playerName);
        helper.withLowHealth(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20, () ->  helper.assertEntityInstancePresent(player, FALLING_BOTTOM_POS))
                .thenSucceed();
    }

    @GameTest(structure = "expandability:pool")
    public void standInDeepWater_withFluidPhysicsDisabled_playerDrowns(GameTestHelper helper) {
        helper.setBlock(FALLING_BOTTOM_POS.above(), Blocks.WATER); // make pool two deep
        String playerName = createPlayerName();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? EventResult.FAIL : EventResult.PASS);
        ServerPlayer player = createFakePlayer(helper, FALLING_BOTTOM_POS, playerName);
        helper.makeAboutToDrown(player);
        player.getFoodData().setFoodLevel(15); // prevent regen

        helper.startSequence()
                .thenExecuteAfter(20 * 2, () ->  assertPlayerInstanceDead(player))
                .thenSucceed();
    }

    private static void assertPlayerInstanceDead(Player player) {
        if (player.isAlive()) {
            throw new GameTestAssertException(Component.literal("Expected player to be dead"), 0);
        }
    }

    private static void assertPlayerInstanceAlive(Player player) {
        if (player.isDeadOrDying()) {
            throw new GameTestAssertException(Component.literal("Expected player to be alive"), 0);
        }
    }

    private static String createPlayerName() {
        return Long.toString((long) (Math.random() * Math.pow(10, 16)));
    }

    private static ServerPlayer createFakePlayer(GameTestHelper helper, BlockPos pos, String name) {
        ServerLevel level = helper.getLevel();
        Vec3 spawnPos = Vec3.atBottomCenterOf(helper.absolutePos(pos));

        EntityPlayerMPFake.createFake(name, level.getServer(), spawnPos, 0, 0,
                level.dimension(), GameType.SURVIVAL, false);
        ServerPlayer player = level.getServer().getPlayerList().getPlayerByName(name);
        //noinspection DataFlowIssue
        ((ServerGamePacketListenerImplAccessor) player.connection).invokeMarkClientLoaded();
        return player;
    }
}
