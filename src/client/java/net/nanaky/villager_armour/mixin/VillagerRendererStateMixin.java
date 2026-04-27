package net.nanaky.villager_armour.mixin;

import net.nanaky.villager_armour.client.state.IHumanoidRenderState;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(VillagerRenderState.class)
public class VillagerRendererStateMixin implements IHumanoidRenderState {
    @Unique private ItemStack val$headEquipment  = ItemStack.EMPTY;
    @Unique private ItemStack val$chestEquipment = ItemStack.EMPTY;
    @Unique private ItemStack val$legEquipment   = ItemStack.EMPTY;
    @Unique private ItemStack val$feetEquipment  = ItemStack.EMPTY;
    @Unique private boolean val$isCrouching;
    @Unique private float val$elytraRotX = (float)(Math.PI / 12);
    @Unique private float val$elytraRotY = 0.0F;
    @Unique private float val$elytraRotZ = (float)(-Math.PI / 12);

    @Override public ItemStack val$headEquipment()  { return val$headEquipment; }
    @Override public ItemStack val$chestEquipment() { return val$chestEquipment; }
    @Override public ItemStack val$legEquipment()   { return val$legEquipment; }
    @Override public ItemStack val$feetEquipment()  { return val$feetEquipment; }
    @Override public boolean val$isCrouching()      { return val$isCrouching; }
    @Override public float val$elytraRotX()         { return val$elytraRotX; }
    @Override public float val$elytraRotY()         { return val$elytraRotY; }
    @Override public float val$elytraRotZ()         { return val$elytraRotZ; }

    @Override public void val$setHeadEquipment(ItemStack i)  { val$headEquipment  = i; }
    @Override public void val$setChestEquipment(ItemStack i) { val$chestEquipment = i; }
    @Override public void val$setLegEquipment(ItemStack i)   { val$legEquipment   = i; }
    @Override public void val$setFeetEquipment(ItemStack i)  { val$feetEquipment  = i; }
    @Override public void val$setIsCrouching(boolean v)      { val$isCrouching    = v; }
    @Override public void val$setElytraRotX(float v)         { val$elytraRotX     = v; }
    @Override public void val$setElytraRotY(float v)         { val$elytraRotY     = v; }
    @Override public void val$setElytraRotZ(float v)         { val$elytraRotZ     = v; }
}