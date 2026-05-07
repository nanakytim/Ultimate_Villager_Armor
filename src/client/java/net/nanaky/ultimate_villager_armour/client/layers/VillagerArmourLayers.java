package net.nanaky.ultimate_villager_armour.client.layers;

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
import net.nanaky.ultimate_villager_armour.client.models.IHumanoidModel;
import net.nanaky.ultimate_villager_armour.client.state.IHumanoidRenderState;
import net.nanaky.ultimate_villager_armour.config.Config;
import net.nanaky.ultimate_villager_armour.config.ConfigManager;

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
    public void submit(PoseStack poseStack, SubmitNodeCollector collector,
                       int lightCoords, S state, float yRot, float xRot) {
        Config cfg = ConfigManager.INSTANCE;
        IHumanoidRenderState hs = (IHumanoidRenderState) state;
        boolean baby = hs.val$isBaby();

    if (cfg.renderHelmet)
        renderArmorPiece(poseStack, collector, state, hs.val$headEquipment(),
                EquipmentSlot.HEAD, lightCoords, headModel,
                baby ? cfg.babyHelmetOffsetX : cfg.helmetOffsetX,
                baby ? cfg.babyHelmetOffsetY + 14f : cfg.helmetOffsetY,
                baby ? cfg.babyHelmetOffsetZ : cfg.helmetOffsetZ,
                baby ? cfg.babyHelmetScale * 0.85f : cfg.helmetScale);

    if (cfg.renderChestplate)
        renderArmorPiece(poseStack, collector, state, hs.val$chestEquipment(),
                EquipmentSlot.CHEST, lightCoords, chestModel,
                baby ? cfg.babyChestOffsetX : cfg.chestOffsetX,
                baby ? cfg.babyChestOffsetY + 15f : cfg.chestOffsetY,
                baby ? cfg.babyChestOffsetZ : cfg.chestOffsetZ,
                baby ? cfg.babyChestScale * 0.55f : cfg.chestScale);

    if (cfg.renderLeggings)
        renderArmorPiece(poseStack, collector, state, hs.val$legEquipment(),
                EquipmentSlot.LEGS, lightCoords, legsModel,
                baby ? cfg.babyLegsOffsetX : cfg.legsOffsetX,
                baby ? cfg.babyLegsOffsetY + 14f : cfg.legsOffsetY,
                baby ? cfg.babyLegsOffsetZ : cfg.legsOffsetZ,
                baby ? cfg.babyLegsScale * 0.5f : cfg.legsScale);

    if (cfg.renderBoots)
        renderArmorPiece(poseStack, collector, state, hs.val$feetEquipment(),
                EquipmentSlot.FEET, lightCoords, feetModel,
                baby ? cfg.babyFeetOffsetX : cfg.feetOffsetX,
                baby ? cfg.babyFeetOffsetY + 13f : cfg.feetOffsetY,
                baby ? cfg.babyFeetOffsetZ : cfg.feetOffsetZ,
                baby ? cfg.babyFeetScale * 0.55f : cfg.feetScale);
    }

    @SuppressWarnings("unchecked")
    private void renderArmorPiece(PoseStack poseStack, SubmitNodeCollector collector, S state,
                                  ItemStack itemStack, EquipmentSlot slot, int lightCoords, A model,
                                  float offsetX, float offsetY, float offsetZ, float scale) {
        if (itemStack.isEmpty()) return;
        Equippable equippable = itemStack.get(DataComponents.EQUIPPABLE);
        if (equippable == null || equippable.assetId().isEmpty() || equippable.slot() != slot) return;

        model.setupAnim(state);
        setPartVisibility(model, slot);

        poseStack.pushPose();
        poseStack.translate(offsetX / 16f, offsetY / 16f, offsetZ / 16f);
        poseStack.scale(scale, scale, scale);

        EquipmentClientInfo.LayerType layerType = (slot == EquipmentSlot.LEGS)
                ? EquipmentClientInfo.LayerType.HUMANOID_LEGGINGS
                : EquipmentClientInfo.LayerType.HUMANOID;

        this.equipmentRenderer.renderLayers(
                layerType,
                equippable.assetId().orElseThrow(),
                (net.minecraft.client.model.Model<S>) model,
                state, itemStack, poseStack, collector,
                lightCoords, state.outlineColor);

        poseStack.popPose();
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