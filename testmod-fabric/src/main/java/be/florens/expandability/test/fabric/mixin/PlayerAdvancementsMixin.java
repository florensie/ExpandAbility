package be.florens.expandability.test.fabric.mixin;

import carpet.patches.EntityPlayerMPFake;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.network.chat.Component;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PlayerAdvancements.class)
public class PlayerAdvancementsMixin {

    @Shadow private ServerPlayer player;

    @WrapWithCondition(method = "method_53637", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/players/PlayerList;broadcastSystemMessage(Lnet/minecraft/network/chat/Component;Z)V"))
    private boolean award(PlayerList instance, Component component, boolean bl) {
        return !(this.player instanceof EntityPlayerMPFake);
    }
}
