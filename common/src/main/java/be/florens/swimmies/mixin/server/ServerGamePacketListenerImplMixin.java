package be.florens.swimmies.mixin.server;

import be.florens.swimmies.EventDispatcher;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerGamePacketListenerImplMixin {

	@Shadow private boolean clientIsFloating;

	@Shadow public ServerPlayer player;

	/**
	 * Avoids getting kicked for flying while swimming is enabled
	 */
	@Inject(method = "handleMovePlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;clientIsFloating:Z", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD))
	private void allowSwimFlying(CallbackInfo info) {
		if (EventDispatcher.onPlayerSwim(this.player).consumesAction()) {
			this.clientIsFloating = false;
		}
	}
}
