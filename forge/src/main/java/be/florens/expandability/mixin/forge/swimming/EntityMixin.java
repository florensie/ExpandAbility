package be.florens.expandability.mixin.forge.swimming;

import be.florens.expandability.EventDispatcher;
import be.florens.expandability.EventResult;
import com.llamalad7.mixinextras.injector.WrapWithCondition;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Entity.class)
public abstract class EntityMixin {

    // FIXME: still no fall damage
    @WrapWithCondition(method = "updateInWaterStateAndDoFluidPushing", require = 1, at = @At(value = "FIELD", opcode = Opcodes.PUTFIELD, target = "Lnet/minecraft/world/entity/Entity;fallDistance:F"))
    private boolean cancelFallDistanceUpdate(Entity entity, float fallDistance) {
        return !(entity instanceof Player player) || EventDispatcher.onPlayerSwim(player) != EventResult.FAIL;
    }
}
