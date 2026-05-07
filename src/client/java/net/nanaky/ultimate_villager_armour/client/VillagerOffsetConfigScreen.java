package net.nanaky.ultimate_villager_armour.client;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.StringWidget;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.nanaky.ultimate_villager_armour.config.Config;
import net.nanaky.ultimate_villager_armour.config.ConfigManager;

import java.util.Set;
import java.util.function.Consumer;

public class VillagerOffsetConfigScreen extends Screen {

    private final Screen lastScreen;
    private final boolean baby;
    private final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    private static final int BOX_W  = 40;
    private static final int BOX_H  = 20;
    private static final int BTN_W  = 40;
    private static final int GAP_W  = 8;
    private static final int LBL_W  = 60;

    private static final String[][] ADULT_PIECES = {
            {"helmet", "config.ultimate_villager_armour.category.helmet_offset"},
            {"heads",  "config.ultimate_villager_armour.category.heads_offset"},
            {"chest",  "config.ultimate_villager_armour.category.chest_offset"},
            {"elytra", "config.ultimate_villager_armour.category.elytra_offset"},
            {"legs",   "config.ultimate_villager_armour.category.legs_offset"},
            {"feet",   "config.ultimate_villager_armour.category.feet_offset"},
    };
    private static final String[][] BABY_PIECES = {
            {"baby_helmet", "config.ultimate_villager_armour.category.baby_helmet_offset"},
            {"baby_heads",  "config.ultimate_villager_armour.category.baby_heads_offset"},
            {"baby_chest",  "config.ultimate_villager_armour.category.baby_chest_offset"},
            {"baby_elytra", "config.ultimate_villager_armour.category.baby_elytra_offset"},
            {"baby_legs",   "config.ultimate_villager_armour.category.baby_legs_offset"},
            {"baby_feet",   "config.ultimate_villager_armour.category.baby_feet_offset"},
    };

    private static final Set<String> SPACER_AFTER = Set.of(
            "heads", "elytra", "legs",
            "baby_heads", "baby_elytra", "baby_legs"
    );

    public VillagerOffsetConfigScreen(Screen lastScreen, boolean baby) {
        super(Component.translatable(baby
                ? "config.ultimate_villager_armour.title.baby"
                : "config.ultimate_villager_armour.title.villager"));
        this.lastScreen = lastScreen;
        this.baby = baby;
    }

    private StringWidget centeredHeader(Component text) {
        return new StringWidget(BOX_W, 9, text, font) {
            @Override
            public void visitLines(net.minecraft.client.gui.ActiveTextCollector output) {
                int textWidth = getFont().width(getMessage().getVisualOrderText());
                int x = getX() + (getWidth() - textWidth) / 2;
                int y = getY() + (getHeight() - 9) / 2;
                output.accept(x, y, getMessage().getVisualOrderText());
            }
        };
    }

    @Override
    protected void init() {
        layout.addToHeader(new StringWidget(title, font));

        Config cfg = ConfigManager.INSTANCE;
        String[][] pieces = baby ? BABY_PIECES : ADULT_PIECES;

        GridLayout grid = new GridLayout().columnSpacing(4).rowSpacing(4);
        GridLayout.RowHelper row = grid.createRowHelper(7);

        row.addChild(new StringWidget(LBL_W, 3, Component.empty(), font));
        row.addChild(centeredHeader(Component.translatable("config.ultimate_villager_armour.offset_x")));
        row.addChild(centeredHeader(Component.translatable("config.ultimate_villager_armour.offset_y")));
        row.addChild(centeredHeader(Component.translatable("config.ultimate_villager_armour.offset_z")));
        row.addChild(new StringWidget(GAP_W, 3, Component.empty(), font));
        row.addChild(centeredHeader(Component.translatable("config.ultimate_villager_armour.scale")));
        row.addChild(new StringWidget(BTN_W, 3, Component.empty(), font));

        for (String[] piece : pieces) {
            String pieceKey = piece[0];
            String labelKey = piece[1];

            LinearLayout labelCell = LinearLayout.vertical();
            labelCell.defaultCellSetting().alignVerticallyMiddle();
            labelCell.addChild(new StringWidget(LBL_W, BOX_H, Component.translatable(labelKey), font));

            EditBox xBox = makeFloatBox(getX(cfg, pieceKey),     v -> setX(cfg, pieceKey, v));
            EditBox yBox = makeFloatBox(getY(cfg, pieceKey),     v -> setY(cfg, pieceKey, v));
            EditBox zBox = makeFloatBox(getZ(cfg, pieceKey),     v -> setZ(cfg, pieceKey, v));
            EditBox sBox = makeFloatBox(getScale(cfg, pieceKey), v -> setScale(cfg, pieceKey, v));

            row.addChild(labelCell);
            row.addChild(xBox);
            row.addChild(yBox);
            row.addChild(zBox);
            row.addChild(new StringWidget(GAP_W, BOX_H, Component.empty(), font));
            row.addChild(sBox);
            row.addChild(Button.builder(Component.literal("Reset"), btn -> {
                        setX(cfg, pieceKey, 0.0f);     xBox.setValue("0.0");
                        setY(cfg, pieceKey, 0.0f);     yBox.setValue("0.0");
                        setZ(cfg, pieceKey, 0.0f);     zBox.setValue("0.0");
                        setScale(cfg, pieceKey, 1.0f); sBox.setValue("1.0");
                    })
                    .size(BTN_W, BOX_H)
                    .build());

            if (SPACER_AFTER.contains(pieceKey)) {
                for (int i = 0; i < 7; i++) {
                    row.addChild(new StringWidget(Component.empty(), font));
                }
            }
        }

        layout.addToContents(grid);
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, btn -> onClose()).build());
        layout.arrangeElements();
        layout.visitWidgets(this::addRenderableWidget);
    }

    private EditBox makeFloatBox(float initialValue, Consumer<Float> setter) {
        EditBox box = new EditBox(font, BOX_W, BOX_H, Component.empty());
        box.setValue(String.valueOf(initialValue));
        box.setResponder(s -> {
            try { setter.accept(Float.parseFloat(s)); }
            catch (NumberFormatException ignored) {}
        });
        return box;
    }

    private float getX(Config c, String p) { return switch (p) {
        case "helmet"      -> c.helmetOffsetX;
        case "heads"       -> c.headsOffsetX;
        case "chest"       -> c.chestOffsetX;
        case "elytra"      -> c.elytraOffsetX;
        case "legs"        -> c.legsOffsetX;
        case "feet"        -> c.feetOffsetX;
        case "baby_helmet" -> c.babyHelmetOffsetX;
        case "baby_heads"  -> c.babyHeadsOffsetX;
        case "baby_chest"  -> c.babyChestOffsetX;
        case "baby_elytra" -> c.babyElytraOffsetX;
        case "baby_legs"   -> c.babyLegsOffsetX;
        case "baby_feet"   -> c.babyFeetOffsetX;
        default -> 0f;
    };}
    private float getY(Config c, String p) { return switch (p) {
        case "helmet"      -> c.helmetOffsetY;
        case "heads"       -> c.headsOffsetY;
        case "chest"       -> c.chestOffsetY;
        case "elytra"      -> c.elytraOffsetY;
        case "legs"        -> c.legsOffsetY;
        case "feet"        -> c.feetOffsetY;
        case "baby_helmet" -> c.babyHelmetOffsetY;
        case "baby_heads"  -> c.babyHeadsOffsetY;
        case "baby_chest"  -> c.babyChestOffsetY;
        case "baby_elytra" -> c.babyElytraOffsetY;
        case "baby_legs"   -> c.babyLegsOffsetY;
        case "baby_feet"   -> c.babyFeetOffsetY;
        default -> 0f;
    };}
    private float getZ(Config c, String p) { return switch (p) {
        case "helmet"      -> c.helmetOffsetZ;
        case "heads"       -> c.headsOffsetZ;
        case "chest"       -> c.chestOffsetZ;
        case "elytra"      -> c.elytraOffsetZ;
        case "legs"        -> c.legsOffsetZ;
        case "feet"        -> c.feetOffsetZ;
        case "baby_helmet" -> c.babyHelmetOffsetZ;
        case "baby_heads"  -> c.babyHeadsOffsetZ;
        case "baby_chest"  -> c.babyChestOffsetZ;
        case "baby_elytra" -> c.babyElytraOffsetZ;
        case "baby_legs"   -> c.babyLegsOffsetZ;
        case "baby_feet"   -> c.babyFeetOffsetZ;
        default -> 0f;
    };}
    private float getScale(Config c, String p) { return switch (p) {
        case "helmet"      -> c.helmetScale;
        case "heads"       -> c.headsScale;
        case "chest"       -> c.chestScale;
        case "elytra"      -> c.elytraScale;
        case "legs"        -> c.legsScale;
        case "feet"        -> c.feetScale;
        case "baby_helmet" -> c.babyHelmetScale;
        case "baby_heads"  -> c.babyHeadsScale;
        case "baby_chest"  -> c.babyChestScale;
        case "baby_elytra" -> c.babyElytraScale;
        case "baby_legs"   -> c.babyLegsScale;
        case "baby_feet"   -> c.babyFeetScale;
        default -> 1f;
    };}

    private void setX(Config c, String p, float v) { switch (p) {
        case "helmet"      -> c.helmetOffsetX     = v;
        case "heads"       -> c.headsOffsetX      = v;
        case "chest"       -> c.chestOffsetX      = v;
        case "elytra"      -> c.elytraOffsetX     = v;
        case "legs"        -> c.legsOffsetX       = v;
        case "feet"        -> c.feetOffsetX       = v;
        case "baby_helmet" -> c.babyHelmetOffsetX = v;
        case "baby_heads"  -> c.babyHeadsOffsetX  = v;
        case "baby_chest"  -> c.babyChestOffsetX  = v;
        case "baby_elytra" -> c.babyElytraOffsetX = v;
        case "baby_legs"   -> c.babyLegsOffsetX   = v;
        case "baby_feet"   -> c.babyFeetOffsetX   = v;
    }}
    private void setY(Config c, String p, float v) { switch (p) {
        case "helmet"      -> c.helmetOffsetY     = v;
        case "heads"       -> c.headsOffsetY      = v;
        case "chest"       -> c.chestOffsetY      = v;
        case "elytra"      -> c.elytraOffsetY     = v;
        case "legs"        -> c.legsOffsetY       = v;
        case "feet"        -> c.feetOffsetY       = v;
        case "baby_helmet" -> c.babyHelmetOffsetY = v;
        case "baby_heads"  -> c.babyHeadsOffsetY  = v;
        case "baby_chest"  -> c.babyChestOffsetY  = v;
        case "baby_elytra" -> c.babyElytraOffsetY = v;
        case "baby_legs"   -> c.babyLegsOffsetY   = v;
        case "baby_feet"   -> c.babyFeetOffsetY   = v;
    }}
    private void setZ(Config c, String p, float v) { switch (p) {
        case "helmet"      -> c.helmetOffsetZ     = v;
        case "heads"       -> c.headsOffsetZ      = v;
        case "chest"       -> c.chestOffsetZ      = v;
        case "elytra"      -> c.elytraOffsetZ     = v;
        case "legs"        -> c.legsOffsetZ       = v;
        case "feet"        -> c.feetOffsetZ       = v;
        case "baby_helmet" -> c.babyHelmetOffsetZ = v;
        case "baby_heads"  -> c.babyHeadsOffsetZ  = v;
        case "baby_chest"  -> c.babyChestOffsetZ  = v;
        case "baby_elytra" -> c.babyElytraOffsetZ = v;
        case "baby_legs"   -> c.babyLegsOffsetZ   = v;
        case "baby_feet"   -> c.babyFeetOffsetZ   = v;
    }}
    private void setScale(Config c, String p, float v) { switch (p) {
        case "helmet"      -> c.helmetScale      = v;
        case "heads"       -> c.headsScale       = v;
        case "chest"       -> c.chestScale       = v;
        case "elytra"      -> c.elytraScale      = v;
        case "legs"        -> c.legsScale        = v;
        case "feet"        -> c.feetScale        = v;
        case "baby_helmet" -> c.babyHelmetScale  = v;
        case "baby_heads"  -> c.babyHeadsScale   = v;
        case "baby_chest"  -> c.babyChestScale   = v;
        case "baby_elytra" -> c.babyElytraScale  = v;
        case "baby_legs"   -> c.babyLegsScale    = v;
        case "baby_feet"   -> c.babyFeetScale    = v;
    }}

    @Override
    public void removed() { ConfigManager.save(); }

    @Override
    public void onClose() { minecraft.setScreen(lastScreen); }
}