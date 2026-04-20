package be.florens.expandability.test.fabric.gametest;

import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class TestUtil {

    public static void assertEntityDied(LivingEntity entity, ResourceKey<DamageType> expectedDamageType) {
        DamageSource damageSource = entity.getLastDamageSource();
        String entityName = entity.typeHolder().unwrapKey().orElseThrow().identifier().getPath();
        if (entity.isAlive()) {
            throw new GameTestAssertException(Component.literal("Expected %s to be dead".formatted(entityName)), 0);
        } else if (!Objects.requireNonNull(damageSource).is(DamageTypes.FALL)) {
            String expected = expectedDamageType.identifier().toString();
            String actual = damageSource.typeHolder().getRegisteredName();
            throw new GameTestAssertException(Component.literal(
                    "Expected %s to die from %s, died from %s instead".formatted(entityName, expected, actual)),
                    0
            );
        }
    }
}
