package net.nanaky.villager_armour.mixin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.VillagerRenderer;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.npc.villager.Villager;
import net.nanaky.villager_armour.client.VAModelLayers;
import net.nanaky.villager_armour.client.layers.VillagerArmourLayers;
import net.nanaky.villager_armour.client.layers.VillagerWingsLayer;
import net.nanaky.villager_armour.client.models.VillagerArmourModel;
import net.nanaky.villager_armour.client.models.VillagerElytraModel;
import net.nanaky.villager_armour.client.state.IHumanoidRenderState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerRenderer.class)
public abstract class VillagerRendererMixin {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
        private void villager_armour$addArmorLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        EquipmentLayerRenderer equipmentRenderer = context.getEquipmentRenderer();

        ((LivingEntityRendererInvoker) (Object) this).invokeAddLayer(
        new VillagerArmourLayers(
                (RenderLayerParent) (Object) this,
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.VILLAGER_ARMOR_HEAD)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.VILLAGER_ARMOR_CHEST)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.VILLAGER_ARMOR_LEGS)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.VILLAGER_ARMOR_FEET)),
                equipmentRenderer));

        ((LivingEntityRendererInvoker) (Object) this).invokeAddLayer(
        new VillagerWingsLayer(
                (RenderLayerParent) (Object) this,
                new VillagerElytraModel<>(context.bakeLayer(VAModelLayers.VILLAGER_ELYTRA)),
                equipmentRenderer));
        }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/npc/villager/Villager;Lnet/minecraft/client/renderer/entity/state/VillagerRenderState;F)V",
            at = @At("TAIL"))
    private void villager_armour$extractEquipment(Villager entity, VillagerRenderState state, float partialTick, CallbackInfo ci) {
        IHumanoidRenderState hs = (IHumanoidRenderState) state;
        hs.val$setHeadEquipment(entity.getItemBySlot(EquipmentSlot.HEAD));
        hs.val$setChestEquipment(entity.getItemBySlot(EquipmentSlot.CHEST));
        hs.val$setLegEquipment(entity.getItemBySlot(EquipmentSlot.LEGS));
        hs.val$setFeetEquipment(entity.getItemBySlot(EquipmentSlot.FEET));
    }
}