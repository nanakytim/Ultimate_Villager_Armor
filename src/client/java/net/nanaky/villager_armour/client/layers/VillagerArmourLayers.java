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
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.nanaky.villager_armour.client.models.IHumanoidModel;
import net.nanaky.villager_armour.client.state.IHumanoidRenderState;

public class VillagerArmourLayers
        <S extends LivingEntityRenderState,
        M extends EntityModel<S>,
        A extends EntityModel<S> & IHumanoidModel>
        extends RenderLayer<S, M> {

    private final A headModel;
    private final A chestModel;
    private final A legsModel;
    private final A feetModel;
    private final EquipmentLayerRenderer equipmentRenderer;

    public VillagerArmourLayers(RenderLayerParent<S, M> renderer,
                                 A headModel, A chestModel, A legsModel, A feetModel,
                                 EquipmentLayerRenderer equipmentRenderer) {
        super(renderer);
        this.headModel  = headModel;
        this.chestModel = chestModel;
        this.legsModel  = legsModel;
        this.feetModel  = feetModel;
        this.equipmentRenderer = equipmentRenderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector collector, int lightCoords, S state, float yRot, float xRot) {
        IHumanoidRenderState hs = (IHumanoidRenderState) state;

        renderArmorPiece(poseStack, collector, state, hs.val$headEquipment(),  EquipmentSlot.HEAD,  lightCoords, headModel);
        renderArmorPiece(poseStack, collector, state, hs.val$chestEquipment(), EquipmentSlot.CHEST, lightCoords, chestModel);
        renderArmorPiece(poseStack, collector, state, hs.val$legEquipment(),   EquipmentSlot.LEGS,  lightCoords, legsModel);
        renderArmorPiece(poseStack, collector, state, hs.val$feetEquipment(),  EquipmentSlot.FEET,  lightCoords, feetModel);
    }

    @SuppressWarnings("unchecked")
    private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector collector, S state,
                                   ItemStack itemStack, EquipmentSlot slot, int lightCoords, A model) {
        if (itemStack.isEmpty()) return;
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable == null || equippable.assetId().isEmpty() || equippable.slot() != slot) return;
        model.setupAnim(state);
        setPartVisibility(model, slot);

        EquipmentClientInfo.LayerType layerType = (slot == EquipmentSlot.LEGS)
                ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS
                : EquipmentClientInfo.LayerType.HUMANOID;

        this.equipmentRenderer.renderLayers(
                layerType,
                equippable.assetId().orElseThrow(),
                (net.minecraft.client.model.Model<S>) model,
                state,
                itemStack,
                poseStack,
                collector,
                lightCoords,
                state.outlineColor);
    }

    protected void setPartVisibility(A model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD  -> model.setHeadVisible(true);
            case CHEST -> { model.setBodyVisible(true); model.setArmsVisible(true); }
            case LEGS  -> { model.setBodyVisible(true); model.setLegsVisible(true); }
            case FEET  -> model.setLegsVisible(true);
            default    -> {}
        }
    }
}