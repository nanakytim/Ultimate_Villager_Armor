package net.nanaky.villager_armour;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.nanaky.villager_armour.interaction.VillagerArmorInteraction;

public class VillagerArmour implements ModInitializer {

    public static final String MOD_ID = "villager_armour";

    @Override
    public void onInitialize() {
        UseEntityCallback.EVENT.register(VillagerArmorInteraction::onUseEntity);
    }
}