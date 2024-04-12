package be.florens.expandability.test.fabric.mixin;

import carpet.patches.EntityPlayerMPFake;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.gametest.framework.StructureUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(StructureUtils.class)
public class StructureUtilsMixin {

    @ModifyExpressionValue(method = "clearSpaceForStructure", at = @At(value = "NEW", target = "(DDDDDD)Lnet/minecraft/world/phys/AABB;"))
    private static AABB clearFakePlayers(AABB aabb, BoundingBox boundingBox, int i, ServerLevel serverLevel) {
        serverLevel.getEntitiesOfClass(EntityPlayerMPFake.class, aabb, p -> true)
                .forEach(EntityPlayerMPFake::kill);

        return aabb;
    }
}
