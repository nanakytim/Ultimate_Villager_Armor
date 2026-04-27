package net.nanaky.villager_armour.interaction;

import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlot.Type;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class VillagerArmorInteraction {

    public static InteractionResult onUseEntity(
            Player player,
            Level world,
            InteractionHand hand,
            Entity entity,
            @Nullable EntityHitResult hitResult
    ) {
        if (world.isClientSide()) return InteractionResult.PASS;

        if (!(entity instanceof Villager villager)) return InteractionResult.PASS;

        if (!player.isShiftKeyDown()) return InteractionResult.PASS;

        ItemStack heldStack = player.getItemInHand(hand);
        EquipmentSlot heldSlot = villager.getEquipmentSlotForItem(heldStack);

        if (heldStack.isEmpty()) {
            if (hitResult == null) return InteractionResult.PASS;

            EquipmentSlot clickedSlot = getClickedSlot(hitResult.getLocation(), villager);
            ItemStack wornItem = villager.getItemBySlot(clickedSlot);

            if (!wornItem.isEmpty()) {
                player.setItemInHand(hand, wornItem.copy());
                villager.setItemSlot(clickedSlot, ItemStack.EMPTY);
                world.playSound(null, villager, getEquipSound(wornItem), SoundSource.PLAYERS, 1.0F, 1.5F);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.PASS;

        } else if (heldSlot.getType() == Type.HUMANOID_ARMOR) {
            ItemStack wornItem = villager.getItemBySlot(heldSlot);

            if (!wornItem.isEmpty()) {
                return InteractionResult.FAIL;
            }

            villager.setItemSlot(heldSlot, heldStack.copyWithCount(1));
            if (!player.getAbilities().instabuild) {
                heldStack.shrink(1);
            }
            world.playSound(null, villager, getEquipSound(heldStack), SoundSource.PLAYERS, 1.0F, 1.0F);
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.FAIL;
    }

    private static SoundEvent getEquipSound(ItemStack stack) {
        Equippable equippable = stack.get(DataComponents.EQUIPPABLE);
        if (equippable != null) {
            return equippable.equipSound().value();
        }
        return SoundEvents.ARMOR_EQUIP_GENERIC.value();
    }

    private static EquipmentSlot getClickedSlot(Vec3 hitPos, Villager villager) {
        double clickY = (hitPos.y - villager.getY()) / villager.getScale();

        if (clickY >= 1.6 && villager.hasItemInSlot(EquipmentSlot.HEAD)) {
            return EquipmentSlot.HEAD;
        } else if (clickY >= 0.9 && !villager.getItemBySlot(EquipmentSlot.CHEST).isEmpty()) {
            return EquipmentSlot.CHEST;
        } else if (clickY >= 0.4 && !villager.getItemBySlot(EquipmentSlot.LEGS).isEmpty()) {
            return EquipmentSlot.LEGS;
        } else if (clickY >= 0.1 && !villager.getItemBySlot(EquipmentSlot.FEET).isEmpty()) {
            return EquipmentSlot.FEET;
        }

        for (EquipmentSlot slot : new EquipmentSlot[]{
                EquipmentSlot.HEAD,
                EquipmentSlot.CHEST,
                EquipmentSlot.LEGS,
                EquipmentSlot.FEET
        }) {
            if (!villager.getItemBySlot(slot).isEmpty()) return slot;
        }

        return EquipmentSlot.HEAD;
    }
}