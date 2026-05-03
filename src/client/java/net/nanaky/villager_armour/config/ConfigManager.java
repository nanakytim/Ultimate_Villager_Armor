package net.nanaky.villager_armour.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.nio.file.Path;

public class ConfigManager {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path PATH =
            FabricLoader.getInstance().getConfigDir().resolve("villager_armour_client.json");

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

    /** Copies all fields from src into dst so INSTANCE reference stays stable. */
    private static void copyInto(Config src, Config dst) {
        dst.renderHelmet     = src.renderHelmet;
        dst.renderChestplate = src.renderChestplate;
        dst.renderLeggings   = src.renderLeggings;
        dst.renderBoots      = src.renderBoots;
        dst.renderElytra     = src.renderElytra;

        dst.helmetOffsetX = src.helmetOffsetX; dst.helmetOffsetY = src.helmetOffsetY; dst.helmetOffsetZ = src.helmetOffsetZ;
        dst.chestOffsetX  = src.chestOffsetX;  dst.chestOffsetY  = src.chestOffsetY;  dst.chestOffsetZ  = src.chestOffsetZ;
        dst.legsOffsetX   = src.legsOffsetX;   dst.legsOffsetY   = src.legsOffsetY;   dst.legsOffsetZ   = src.legsOffsetZ;
        dst.feetOffsetX   = src.feetOffsetX;   dst.feetOffsetY   = src.feetOffsetY;   dst.feetOffsetZ   = src.feetOffsetZ;
        dst.elytraOffsetX = src.elytraOffsetX; dst.elytraOffsetY = src.elytraOffsetY; dst.elytraOffsetZ = src.elytraOffsetZ;
        dst.helmetScale  = src.helmetScale;
        dst.chestScale   = src.chestScale;
        dst.legsScale    = src.legsScale;
        dst.feetScale    = src.feetScale;
        dst.elytraScale  = src.elytraScale;
    }
}