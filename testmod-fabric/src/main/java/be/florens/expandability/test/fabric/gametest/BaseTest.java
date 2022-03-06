package be.florens.expandability.test.fabric.gametest;

import net.fabricmc.fabric.api.gametest.v1.FabricGameTest;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import org.spongepowered.asm.mixin.MixinEnvironment;

@SuppressWarnings("unused")
public class BaseTest {

    @GameTest(template = FabricGameTest.EMPTY_STRUCTURE)
    public void audit_mixins(GameTestHelper helper) {
        MixinEnvironment.getCurrentEnvironment().audit();

        helper.succeed();
    }
}
