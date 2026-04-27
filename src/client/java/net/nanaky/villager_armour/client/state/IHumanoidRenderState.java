package net.nanaky.villager_armour.client.state;

import net.minecraft.world.item.ItemStack;

public interface IHumanoidRenderState {
    ItemStack val$headEquipment();
    ItemStack val$chestEquipment();
    ItemStack val$legEquipment();
    ItemStack val$feetEquipment();
    boolean val$isCrouching();
    float val$elytraRotX();
    float val$elytraRotY();
    float val$elytraRotZ();

    void val$setHeadEquipment(ItemStack item);
    void val$setChestEquipment(ItemStack item);
    void val$setLegEquipment(ItemStack item);
    void val$setFeetEquipment(ItemStack item);
    void val$setIsCrouching(boolean crouching);
    void val$setElytraRotX(float v);
    void val$setElytraRotY(float v);
    void val$setElytraRotZ(float v);
}