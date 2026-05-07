package net.nanaky.ultimate_villager_armour;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.nanaky.ultimate_villager_armour.interaction.VillagerArmorInteraction;

public class UltimateVillagerArmour implements ModInitializer {

    public static final String MOD_ID = "ultimate_villager_armour";

    @Override
    public void onInitialize() {
        UseEntityCallback.EVENT.register(VillagerArmorInteraction::onUseEntity);
    }
}