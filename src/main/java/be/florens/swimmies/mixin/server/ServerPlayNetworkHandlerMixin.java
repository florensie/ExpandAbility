package be.florens.swimmies.mixin.server;

import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerPlayNetworkHandlerMixin {

	@Shadow private boolean clientIsFloating;

	@Inject(method = "handleMovePlayer", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;clientIsFloating:Z", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD))
	private void allowSwimFlying(CallbackInfo info) {
		this.clientIsFloating = false;
	}
}
