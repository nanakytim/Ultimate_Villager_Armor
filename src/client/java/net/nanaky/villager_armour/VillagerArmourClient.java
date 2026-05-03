package net.nanaky.villager_armour;

import net.fabricmc.api.ClientModInitializer;
import net.nanaky.villager_armour.client.VAModelLayers;
import net.nanaky.villager_armour.config.ConfigManager;

public class VillagerArmourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VAModelLayers.register();
        ConfigManager.load();
    }
}