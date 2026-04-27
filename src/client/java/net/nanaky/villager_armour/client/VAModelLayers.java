package net.nanaky.villager_armour.client;

import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.resources.Identifier;
import net.nanaky.villager_armour.client.models.VillagerArmourModel;
import net.nanaky.villager_armour.client.models.VillagerElytraModel;

public final class VAModelLayers {
        public static final ModelLayerLocation VILLAGER_ELYTRA =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "elytra"), "main");
        public static final ModelLayerLocation VILLAGER_ARMOR_HEAD =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "villager"), "armor_head");
        public static final ModelLayerLocation VILLAGER_ARMOR_CHEST =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "villager"), "armor_chest");
        public static final ModelLayerLocation VILLAGER_ARMOR_LEGS =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "villager"), "armor_legs");
        public static final ModelLayerLocation VILLAGER_ARMOR_FEET =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "villager"), "armor_feet");
        public static final ModelLayerLocation ZOMBIE_VILLAGER_ELYTRA =
        new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "zombie_villager"), "elytra");
        public static final ModelLayerLocation ZOMBIE_VILLAGER_ARMOR_HEAD =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "zombie_villager"), "armor_head");
        public static final ModelLayerLocation ZOMBIE_VILLAGER_ARMOR_CHEST =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "zombie_villager"), "armor_chest");
        public static final ModelLayerLocation ZOMBIE_VILLAGER_ARMOR_LEGS =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "zombie_villager"), "armor_legs");
        public static final ModelLayerLocation ZOMBIE_VILLAGER_ARMOR_FEET =
                new ModelLayerLocation(Identifier.fromNamespaceAndPath("villager_armour", "zombie_villager"), "armor_feet");


        public static void register() {
        CubeDeformation outer = new CubeDeformation(1.0F);
        CubeDeformation inner = new CubeDeformation(0.0F);
        
        ModelLayerRegistry.registerModelLayer(VILLAGER_ELYTRA,
                () -> VillagerElytraModel.createLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(VILLAGER_ARMOR_HEAD,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(VILLAGER_ARMOR_CHEST,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(VILLAGER_ARMOR_LEGS,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, 0.25F));
        ModelLayerRegistry.registerModelLayer(VILLAGER_ARMOR_FEET,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(ZOMBIE_VILLAGER_ELYTRA,
        () -> VillagerElytraModel.createLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(ZOMBIE_VILLAGER_ARMOR_HEAD,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(ZOMBIE_VILLAGER_ARMOR_CHEST,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));
        ModelLayerRegistry.registerModelLayer(ZOMBIE_VILLAGER_ARMOR_LEGS,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, 0.25F));
        ModelLayerRegistry.registerModelLayer(ZOMBIE_VILLAGER_ARMOR_FEET,
                () -> VillagerArmourModel.createBodyLayer(outer, 0.0F, -0.5F));

        }

    private VAModelLayers() {}
}