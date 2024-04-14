package be.florens.expandability.test.fabric.mixin;

import carpet.patches.EntityPlayerMPFake;
import com.mojang.authlib.GameProfile;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.players.GameProfileCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Mixin(EntityPlayerMPFake.class)
public class EntityPlayerMPFakeMixin {

    @Redirect(method = "createFake", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/MinecraftServer;getProfileCache()Lnet/minecraft/server/players/GameProfileCache;"))
    private static GameProfileCache getFakeProfileCache(MinecraftServer server) throws IOException {
        return new GameProfileCache(null, File.createTempFile("fake", ".txt")) {
            @Override
            public Optional<GameProfile> get(String string) {
                return Optional.empty();
            }
        };
    }
}
