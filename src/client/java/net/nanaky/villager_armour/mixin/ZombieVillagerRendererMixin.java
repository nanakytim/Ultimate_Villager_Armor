package net.nanaky.villager_armour.mixin;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ZombieVillagerRenderer;
import net.minecraft.client.renderer.entity.layers.EquipmentLayerRenderer;
import net.minecraft.client.renderer.entity.state.ZombieVillagerRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.zombie.ZombieVillager;
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

@Mixin(ZombieVillagerRenderer.class)
public abstract class ZombieVillagerRendererMixin {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Inject(method = "<init>", at = @At("TAIL"))
    private void villager_armour$addArmorLayer(EntityRendererProvider.Context context, CallbackInfo ci) {
        EquipmentLayerRenderer equipmentRenderer = context.getEquipmentRenderer();
        ((LivingEntityRendererInvoker) (Object) this).invokeAddLayer(
            new VillagerArmourLayers(
                (RenderLayerParent) (Object) this,
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.ZOMBIE_VILLAGER_ARMOR_HEAD)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.ZOMBIE_VILLAGER_ARMOR_CHEST)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.ZOMBIE_VILLAGER_ARMOR_LEGS)),
                new VillagerArmourModel<>(context.bakeLayer(VAModelLayers.ZOMBIE_VILLAGER_ARMOR_FEET)),
                equipmentRenderer));
        ((LivingEntityRendererInvoker) (Object) this).invokeAddLayer(
            new VillagerWingsLayer(
                (RenderLayerParent) (Object) this,
                new VillagerElytraModel<>(context.bakeLayer(VAModelLayers.ZOMBIE_VILLAGER_ELYTRA)),
                equipmentRenderer));
    }

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/ZombieVillager;Lnet/minecraft/client/renderer/entity/state/ZombieVillagerRenderState;F)V",
            at = @At("TAIL"))
    private void villager_armour$extractEquipment(ZombieVillager entity, ZombieVillagerRenderState state, float partialTick, CallbackInfo ci) {
        IHumanoidRenderState hs = (IHumanoidRenderState) state;
        hs.val$setHeadEquipment(entity.getItemBySlot(EquipmentSlot.HEAD));
        hs.val$setChestEquipment(entity.getItemBySlot(EquipmentSlot.CHEST));
        hs.val$setLegEquipment(entity.getItemBySlot(EquipmentSlot.LEGS));
        hs.val$setFeetEquipment(entity.getItemBySlot(EquipmentSlot.FEET));
    }
}