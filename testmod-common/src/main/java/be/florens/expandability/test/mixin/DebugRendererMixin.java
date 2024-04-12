package be.florens.expandability.test.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.debug.DebugRenderer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DebugRenderer.class)
public class DebugRendererMixin {

    @Shadow @Final public DebugRenderer.SimpleDebugRenderer waterDebugRenderer;
    @Shadow @Final public DebugRenderer.SimpleDebugRenderer collisionBoxRenderer;

    @Inject(method = "render", at = @At("HEAD"))
    private void moreDebugRendering(PoseStack poseStack, MultiBufferSource.BufferSource bufferSource, double d, double e, double f, CallbackInfo ci) {
        // TODO: add keybinds
//        this.waterDebugRenderer.render(poseStack, bufferSource, d, e, f);
//        this.collisionBoxRenderer.render(poseStack, bufferSource, d, e, f);
    }
}
