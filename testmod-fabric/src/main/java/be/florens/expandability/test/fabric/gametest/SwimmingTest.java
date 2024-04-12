package be.florens.expandability.test.fabric.gametest;

import be.florens.expandability.api.fabric.PlayerSwimCallback;
import carpet.patches.EntityPlayerMPFake;
import net.fabricmc.fabric.api.util.TriState;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.GameType;
import net.minecraft.world.phys.Vec3;

import java.util.UUID;

public class SwimmingTest {
    private static final BlockPos STREAM_MIDDLE = new BlockPos(2, 2, 1);

    // TODO: test drowning, sinking in air, falling in fluid

    @GameTest(template = "expandability:staircase", setupTicks = 20L)
    public void standInWaterStream_withFluidPhysicsDisabled_doesNotMove(GameTestHelper helper) {
        String playerName = UUID.randomUUID().toString();
        PlayerSwimCallback.EVENT.register(p -> p.getName().getString().equals(playerName) ? TriState.FALSE : TriState.DEFAULT);
        ServerPlayer player = createFakePlayer(helper, STREAM_MIDDLE, playerName);

        helper.startSequence()
                .thenExecuteAfter(20 * 3, () -> helper.assertEntityInstancePresent(player, STREAM_MIDDLE))
                .thenSucceed();
    }

    private static ServerPlayer createFakePlayer(GameTestHelper helper, BlockPos pos, String name) {
        ServerLevel level = helper.getLevel();
        Vec3 spawnPos = Vec3.atBottomCenterOf(helper.absolutePos(pos));

        return EntityPlayerMPFake.createFake(name, level.getServer(), spawnPos, 0, 0,
                level.dimension(), GameType.SURVIVAL, false);
    }
}
