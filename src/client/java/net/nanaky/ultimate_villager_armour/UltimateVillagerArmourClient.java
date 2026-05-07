package net.nanaky.ultimate_villager_armour;

import net.fabricmc.api.ClientModInitializer;
import net.nanaky.ultimate_villager_armour.client.VAModelLayers;
import net.nanaky.ultimate_villager_armour.config.ConfigManager;

public class UltimateVillagerArmourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VAModelLayers.register();
        ConfigManager.load();
    }
}