package net.nanaky.villager_armour;

import net.fabricmc.api.ClientModInitializer;
import net.nanaky.villager_armour.client.VAModelLayers;

public class VillagerArmourClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        VAModelLayers.register();
    }
}