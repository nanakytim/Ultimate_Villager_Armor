package net.nanaky.ultimate_villager_armour.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import java.io.*;
import java.nio.file.Path;

public class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("ultimate_villager_armour_client.json");
    public static Config INSTANCE = new Config();
    public static void load() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return;
        if (!PATH.toFile().exists()) { save(); return; }
        try (Reader r = new FileReader(PATH.toFile())) {
            Config loaded = GSON.fromJson(r, Config.class);
            if (loaded != null) copyInto(loaded, INSTANCE);
        } catch (Exception e) {
            System.err.println("[VillagerArmour] Failed to load client config: " + e);
        }
    }

    public static void save() {
        if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return;
        try (Writer w = new FileWriter(PATH.toFile())) {
            GSON.toJson(INSTANCE, w);
        } catch (Exception e) {
            System.err.println("[VillagerArmour] Failed to save client config: " + e);
        }
    }

    private static void copyInto(Config src, Config dst) {
        dst.renderHelmet     = src.renderHelmet;
        dst.renderHeads      = src.renderHeads;
        dst.renderChestplate = src.renderChestplate;
        dst.renderElytra     = src.renderElytra;
        dst.renderLeggings   = src.renderLeggings;
        dst.renderBoots      = src.renderBoots;
        dst.helmetOffsetX = src.helmetOffsetX; dst.helmetOffsetY = src.helmetOffsetY;
        dst.helmetOffsetZ = src.helmetOffsetZ; dst.helmetScale   = src.helmetScale;
        dst.headsOffsetX  = src.headsOffsetX;  dst.headsOffsetY  = src.headsOffsetY;
        dst.headsOffsetZ  = src.headsOffsetZ;  dst.headsScale    = src.headsScale;
        dst.chestOffsetX  = src.chestOffsetX;  dst.chestOffsetY  = src.chestOffsetY;
        dst.chestOffsetZ  = src.chestOffsetZ;  dst.chestScale    = src.chestScale;
        dst.elytraOffsetX = src.elytraOffsetX; dst.elytraOffsetY = src.elytraOffsetY;
        dst.elytraOffsetZ = src.elytraOffsetZ; dst.elytraScale   = src.elytraScale;
        dst.legsOffsetX   = src.legsOffsetX;   dst.legsOffsetY   = src.legsOffsetY;
        dst.legsOffsetZ   = src.legsOffsetZ;   dst.legsScale     = src.legsScale;
        dst.feetOffsetX   = src.feetOffsetX;   dst.feetOffsetY   = src.feetOffsetY;
        dst.feetOffsetZ   = src.feetOffsetZ;   dst.feetScale     = src.feetScale;
        dst.babyHelmetOffsetX = src.babyHelmetOffsetX; dst.babyHelmetOffsetY = src.babyHelmetOffsetY;
        dst.babyHelmetOffsetZ = src.babyHelmetOffsetZ; dst.babyHelmetScale   = src.babyHelmetScale;
        dst.babyHeadsOffsetX  = src.babyHeadsOffsetX;  dst.babyHeadsOffsetY  = src.babyHeadsOffsetY;
        dst.babyHeadsOffsetZ  = src.babyHeadsOffsetZ;  dst.babyHeadsScale    = src.babyHeadsScale;
        dst.babyChestOffsetX  = src.babyChestOffsetX;  dst.babyChestOffsetY  = src.babyChestOffsetY;
        dst.babyChestOffsetZ  = src.babyChestOffsetZ;  dst.babyChestScale    = src.babyChestScale;
        dst.babyElytraOffsetX = src.babyElytraOffsetX; dst.babyElytraOffsetY = src.babyElytraOffsetY;
        dst.babyElytraOffsetZ = src.babyElytraOffsetZ; dst.babyElytraScale   = src.babyElytraScale;
        dst.babyLegsOffsetX   = src.babyLegsOffsetX;   dst.babyLegsOffsetY   = src.babyLegsOffsetY;
        dst.babyLegsOffsetZ   = src.babyLegsOffsetZ;   dst.babyLegsScale     = src.babyLegsScale;
        dst.babyFeetOffsetX   = src.babyFeetOffsetX;   dst.babyFeetOffsetY   = src.babyFeetOffsetY;
        dst.babyFeetOffsetZ   = src.babyFeetOffsetZ;   dst.babyFeetScale     = src.babyFeetScale;
    }
}