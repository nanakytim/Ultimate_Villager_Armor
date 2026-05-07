package net.nanaky.ultimate_villager_armour.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.nanaky.ultimate_villager_armour.client.state.IHumanoidRenderState;
import net.nanaky.ultimate_villager_armour.config.Config;
import net.nanaky.ultimate_villager_armour.config.ConfigManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public abstract class CustomHeadLayerMixin<S extends LivingEntityRenderState, M extends EntityModel<S> & HeadedModel> {

    @Inject(method = "submit", at = @At("HEAD"), cancellable = true)
    private void villager_armour$headBefore(PoseStack poseStack, SubmitNodeCollector collector,
                                             int lightCoords, S state,
                                             float yRot, float xRot, CallbackInfo ci) {
        if (!(state instanceof IHumanoidRenderState hs)) return;

        Config cfg = ConfigManager.INSTANCE;

        if (!cfg.renderHeads) {
            ci.cancel();
            return;
        }

        boolean baby = hs.val$isBaby();
        float ox = baby ? cfg.babyHeadsOffsetX        : cfg.headsOffsetX;
        float oy = baby ? cfg.babyHeadsOffsetY + 3.0f : cfg.headsOffsetY + 0.8f;
        float oz = baby ? cfg.babyHeadsOffsetZ        : cfg.headsOffsetZ;
        float sc = baby ? cfg.babyHeadsScale * 0.9f   : cfg.headsScale * 1.02f;

        poseStack.pushPose();
        poseStack.translate(ox / 16f, oy / 16f, oz / 16f);
        poseStack.scale(sc, sc, sc);
    }

    @Inject(method = "submit", at = @At("RETURN"))
    private void villager_armour$headAfter(PoseStack poseStack, SubmitNodeCollector collector,
                                            int lightCoords, S state,
                                            float yRot, float xRot, CallbackInfo ci) {
        if (!(state instanceof IHumanoidRenderState)) return;
        poseStack.popPose();
    }
}