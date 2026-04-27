package net.nanaky.villager_armour.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.nanaky.villager_armour.client.models.VillagerElytraModel;
import net.nanaky.villager_armour.client.state.IHumanoidRenderState;

public class VillagerWingsLayer<S extends LivingEntityRenderState, M extends EntityModel<S>>
        extends RenderLayer<S, M> {

    private final VillagerElytraModel<S> elytraModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public VillagerWingsLayer(RenderLayerParent<S, M> parent, VillagerElytraModel<S> elytraModel,
                          EquipmentLayerRenderer equipmentRenderer) {
        super(parent);
        this.elytraModel = elytraModel;
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int uv2,
                       S state, float netHeadYaw, float headPitch) {
        IHumanoidRenderState hs = (IHumanoidRenderState) state;
        ItemStack itemStack = hs.val$chestEquipment();
        if (itemStack.isEmpty()) return;
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable == null || equippable.assetId().isEmpty()) return;

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.0F, 0.125F);
        elytraModel.setupAnim(state);
        this.equipmentRenderer.renderLayers(
                EquipmentClientInfo.LayerType.WINGS,
                equippable.assetId().orElseThrow(),
                (net.minecraft.client.model.Model<S>) elytraModel,
                state,
                itemStack,
                poseStack,
                collector,
                uv2,
                state.outlineColor);
        poseStack.popPose();
    }
}