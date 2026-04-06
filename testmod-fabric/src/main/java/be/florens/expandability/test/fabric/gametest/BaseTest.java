package be.florens.expandability.test.fabric.gametest;

import net.fabricmc.fabric.api.gametest.v1.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import org.spongepowered.asm.mixin.MixinEnvironment;

@SuppressWarnings("unused")
public class BaseTest {

    @GameTest()
    public void audit_mixins_succeeds(GameTestHelper helper) {
        MixinEnvironment.getCurrentEnvironment().audit();

        helper.succeed();
    }
}
