package net.nanaky.villager_armour.client;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.network.chat.Component;
import net.nanaky.villager_armour.config.Config;
import net.nanaky.villager_armour.config.ConfigManager;

public class ClothConfigScreenFactory {


        private static void addOffsetCategory(
                ConfigBuilder builder, ConfigEntryBuilder eb,
                Config cfg, Config def,
                String piece,
                float curX, float curY, float curZ, float curScale,
                FloatSetter setX, FloatSetter setY, FloatSetter setZ, FloatSetter setScale) {

        String key = "config.villager_armour.category." + piece + "_offset";
        ConfigCategory cat = builder.getOrCreateCategory(Component.translatable(key));

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_x"), curX)
                .setDefaultValue(0f).setMin(-50f).setMax(50f)
                .setSaveConsumer(setX::set).build());

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_y"), curY)
                .setDefaultValue(0f).setMin(-50f).setMax(50f)
                .setSaveConsumer(setY::set).build());

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_z"), curZ)
                .setDefaultValue(0f).setMin(-50f).setMax(50f)
                .setSaveConsumer(setZ::set).build());

        // ── Scale ─────────────────────────────────────────────────────────────
        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_scale"), curScale)
                .setDefaultValue(1.0f).setMin(0.1f).setMax(20.0f)
                .setSaveConsumer(setScale::set).build());
        }

    public static ConfigScreenFactory<?> create() {
        return parent -> {
            Config cfg = ConfigManager.INSTANCE;
            Config def = new Config();

            ConfigBuilder builder = ConfigBuilder.create()
                    .setParentScreen(parent)
                    .setTitle(Component.translatable("config.villager_armour.title"))
                    .setSavingRunnable(ConfigManager::save);

            ConfigEntryBuilder eb = builder.entryBuilder();
            ConfigCategory vis = builder.getOrCreateCategory(
                    Component.translatable("config.villager_armour.category.visibility"));

            vis.addEntry(eb.startBooleanToggle(
                    Component.translatable("config.villager_armour.render_helmet"), cfg.renderHelmet)
                    .setDefaultValue(def.renderHelmet)
                    .setSaveConsumer(v -> cfg.renderHelmet = v)
                    .build());

            vis.addEntry(eb.startBooleanToggle(
                    Component.translatable("config.villager_armour.render_chestplate"), cfg.renderChestplate)
                    .setDefaultValue(def.renderChestplate)
                    .setSaveConsumer(v -> cfg.renderChestplate = v)
                    .build());

            vis.addEntry(eb.startBooleanToggle(
                    Component.translatable("config.villager_armour.render_leggings"), cfg.renderLeggings)
                    .setDefaultValue(def.renderLeggings)
                    .setSaveConsumer(v -> cfg.renderLeggings = v)
                    .build());

            vis.addEntry(eb.startBooleanToggle(
                    Component.translatable("config.villager_armour.render_boots"), cfg.renderBoots)
                    .setDefaultValue(def.renderBoots)
                    .setSaveConsumer(v -> cfg.renderBoots = v)
                    .build());

            vis.addEntry(eb.startBooleanToggle(
                    Component.translatable("config.villager_armour.render_elytra"), cfg.renderElytra)
                    .setDefaultValue(def.renderElytra)
                    .setSaveConsumer(v -> cfg.renderElytra = v)
                    .build());

        addOffsetCategory(builder, eb, cfg, def, "helmet",
                cfg.helmetOffsetX, cfg.helmetOffsetY, cfg.helmetOffsetZ, cfg.helmetScale,
                v -> cfg.helmetOffsetX = v, v -> cfg.helmetOffsetY = v, v -> cfg.helmetOffsetZ = v,
                v -> cfg.helmetScale = v);

        addOffsetCategory(builder, eb, cfg, def, "chest",
                cfg.chestOffsetX, cfg.chestOffsetY, cfg.chestOffsetZ, cfg.chestScale,
                v -> cfg.chestOffsetX = v, v -> cfg.chestOffsetY = v, v -> cfg.chestOffsetZ = v,
                v -> cfg.chestScale = v);

        addOffsetCategory(builder, eb, cfg, def, "legs",
                cfg.legsOffsetX, cfg.legsOffsetY, cfg.legsOffsetZ, cfg.legsScale,
                v -> cfg.legsOffsetX = v, v -> cfg.legsOffsetY = v, v -> cfg.legsOffsetZ = v,
                v -> cfg.legsScale = v);

        addOffsetCategory(builder, eb, cfg, def, "feet",
                cfg.feetOffsetX, cfg.feetOffsetY, cfg.feetOffsetZ, cfg.feetScale,
                v -> cfg.feetOffsetX = v, v -> cfg.feetOffsetY = v, v -> cfg.feetOffsetZ = v,
                v -> cfg.feetScale = v);

        addOffsetCategory(builder, eb, cfg, def, "elytra",
                cfg.elytraOffsetX, cfg.elytraOffsetY, cfg.elytraOffsetZ, cfg.elytraScale,
                v -> cfg.elytraOffsetX = v, v -> cfg.elytraOffsetY = v, v -> cfg.elytraOffsetZ = v,
                v -> cfg.elytraScale = v);

            return builder.build();
        };
    }

    @FunctionalInterface
    private interface FloatSetter { void set(float v); }

    private static void addOffsetCategory(
            ConfigBuilder builder, ConfigEntryBuilder eb,
            Config cfg, Config def,
            String piece,
            float curX, float curY, float curZ,
            FloatSetter setX, FloatSetter setY, FloatSetter setZ) {

        String key = "config.villager_armour.category." + piece + "_offset";
        ConfigCategory cat = builder.getOrCreateCategory(Component.translatable(key));

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_x"), curX)
                .setDefaultValue(0f)
                .setMin(-3f).setMax(3f)
                .setSaveConsumer(setX::set)
                .build());

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_y"), curY)
                .setDefaultValue(0f)
                .setMin(-3f).setMax(3f)
                .setSaveConsumer(setY::set)
                .build());

        cat.addEntry(eb.startFloatField(
                Component.translatable("config.villager_armour." + piece + "_offset_z"), curZ)
                .setDefaultValue(0f)
                .setMin(-3f).setMax(3f)
                .setSaveConsumer(setZ::set)
                .build());
    }
}