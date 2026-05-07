package net.nanaky.ultimate_villager_armour.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.nanaky.ultimate_villager_armour.config.Config;
import net.nanaky.ultimate_villager_armour.config.ConfigManager;

public class VillagerArmourConfigScreen extends Screen {

    private final Screen lastScreen;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public VillagerArmourConfigScreen(Screen lastScreen) {
        super(Component.translatable("config.ultimate_villager_armour.title"));
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        layout.addToHeader(new StringWidget(title, font));

        Config cfg = ConfigManager.INSTANCE;

        GridLayout grid = new GridLayout().columnSpacing(8).rowSpacing(4);
        GridLayout.RowHelper row = grid.createRowHelper(2);

        row.addChild(makeToggle("config.ultimate_villager_armour.render_helmet",
                cfg.renderHelmet, v -> cfg.renderHelmet = v));
        row.addChild(makeToggle("config.ultimate_villager_armour.render_heads",
                cfg.renderHeads, v -> cfg.renderHeads = v));
        row.addChild(makeToggle("config.ultimate_villager_armour.render_chestplate",
                cfg.renderChestplate, v -> cfg.renderChestplate = v));
        row.addChild(makeToggle("config.ultimate_villager_armour.render_elytra",
                cfg.renderElytra, v -> cfg.renderElytra = v));
        row.addChild(makeToggle("config.ultimate_villager_armour.render_leggings",
                cfg.renderLeggings, v -> cfg.renderLeggings = v));
        row.addChild(makeToggle("config.ultimate_villager_armour.render_boots",
                cfg.renderBoots, v -> cfg.renderBoots = v));

        row.addChild(new StringWidget(Component.empty(), font));

        row.addChild(new StringWidget(Component.empty(), font));

        row.addChild(Button.builder(
                Component.translatable("config.ultimate_villager_armour.open_villager_rendering"),
                btn -> {
                    ConfigManager.save();
                    minecraft.setScreen(new VillagerOffsetConfigScreen(this, false));
                }).build());

        row.addChild(Button.builder(
                Component.translatable("config.ultimate_villager_armour.open_baby_rendering"),
                btn -> {
                    ConfigManager.save();
                    minecraft.setScreen(new VillagerOffsetConfigScreen(this, true));
                }).build());

        layout.addToContents(grid);
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, btn -> onClose()).build());

        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }

    private Button makeToggle(String key, boolean initial, java.util.function.Consumer<Boolean> setter) {
        return Button.builder(
                Component.translatable(key).append(": ").append(
                        initial
                        ? Component.translatable("options.on")
                        : Component.translatable("options.off")),
                btn -> {
                    setter.accept(!initial);
                    ConfigManager.save();
                    minecraft.setScreen(new VillagerArmourConfigScreen(lastScreen));
                }).build();
    }

    @Override
    public void removed() {
        ConfigManager.save();
    }

    @Override
    public void onClose() {
        minecraft.setScreen(lastScreen);
    }
}