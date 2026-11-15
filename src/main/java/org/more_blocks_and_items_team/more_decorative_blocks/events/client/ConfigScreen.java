package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleConsumer;

/**
 * 模组配置界面。
 * <p>
 * 原实现只暴露了 {@code enableVersionChecker} 与 {@code heightOfSeatStone} 两项，
 * 后加的六个挖掘系数配置在游戏内无法修改；这里补全所有配置项。
 */
public class ConfigScreen extends Screen {
    private static final double MIN_COEFFICIENT = 0.1D;
    private static final double MAX_COEFFICIENT = 10.0D;
    private static final int MIN_SEAT_HEIGHT = 16;
    private static final int MAX_SEAT_HEIGHT = 32;

    private final Screen lastScreen;
    private final ModConfigSpec configSpec;

    private Checkbox enableVersionCheckerBox;
    private EditBox heightOfSeatStoneBox;

    /**
     * 需要读写的浮点配置项：配置值 + 输入框 + 写回方法。
     */
    private final List<DoubleField> doubleFields = new ArrayList<>();

    public ConfigScreen(Screen lastScreen, ModConfigSpec configSpec) {
        super(Component.translatable("config.more_decorative_blocks.title"));
        this.lastScreen = lastScreen;
        this.configSpec = configSpec;
    }

    private static String formatDouble(double value) {
        if (value == Math.floor(value)) {
            return String.valueOf((long) value);
        }
        return String.valueOf(value);
    }

    private static int parseInt(String value, int fallback, int min, int max) {
        try {
            return Math.max(min, Math.min(max, Integer.parseInt(value.trim())));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    private static double parseDouble(String value, double fallback, double min, double max) {
        try {
            return Math.max(min, Math.min(max, Double.parseDouble(value.trim())));
        } catch (NumberFormatException e) {
            return fallback;
        }
    }

    @Override
    protected void init() {
        super.init();

        HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
        layout.setHeaderHeight(30);

        GridLayout grid = new GridLayout();
        grid.defaultCellSetting().alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = grid.createRowHelper(2);

        // 版本检查开关
        enableVersionCheckerBox = Checkbox.builder(Component.translatable("config.more_decorative_blocks.enable_version_checker"), this.font)
                .selected(Config.enableVersionChecker)
                .build();
        rowHelper.addChild(enableVersionCheckerBox, 2);
        rowHelper.addChild(new ScreenWidgets.Spacing(12), 2);

        // 石墩子高度
        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("config.more_decorative_blocks.height_of_seat_stone"), this.font, 0xFFFFFF), 1);
        heightOfSeatStoneBox = createNumberBox(String.valueOf(Config.heightOfSeatStone));
        rowHelper.addChild(heightOfSeatStoneBox, 1);

        // 挖掘系数（系数越大，挖掘越慢）
        doubleFields.clear();
        addCoefficientRow(rowHelper, "mdb_block_wood_mining_coefficient",
                Config.mdbBlockWoodMiningCoefficient, value -> Config.MDB_BLOCK_WOOD_MINING_COEFFICIENT.set(value));
        addCoefficientRow(rowHelper, "mdb_block_stone_mining_coefficient",
                Config.mdbBlockStoneMiningCoefficient, value -> Config.MDB_BLOCK_STONE_MINING_COEFFICIENT.set(value));
        addCoefficientRow(rowHelper, "mdb_block_gold_mining_coefficient",
                Config.mdbBlockGoldMiningCoefficient, value -> Config.MDB_BLOCK_GOLD_MINING_COEFFICIENT.set(value));
        addCoefficientRow(rowHelper, "mdb_block_iron_mining_coefficient",
                Config.mdbBlockIronMiningCoefficient, value -> Config.MDB_BLOCK_IRON_MINING_COEFFICIENT.set(value));
        addCoefficientRow(rowHelper, "mdb_block_diamond_mining_coefficient",
                Config.mdbBlockDiamondMiningCoefficient, value -> Config.MDB_BLOCK_DIAMOND_MINING_COEFFICIENT.set(value));
        addCoefficientRow(rowHelper, "mdb_block_netherite_mining_coefficient",
                Config.mdbBlockNetheriteMiningCoefficient, value -> Config.MDB_BLOCK_NETHERITE_MINING_COEFFICIENT.set(value));

        grid.arrangeElements();
        layout.addToContents(grid);

        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> this.onDone()).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void addCoefficientRow(GridLayout.RowHelper rowHelper, String langKey, double currentValue, DoubleConsumer setter) {
        Component label = Component.translatable("config.more_decorative_blocks." + langKey);
        EditBox box = createNumberBox(formatDouble(currentValue));
        doubleFields.add(new DoubleField(box, setter));
        rowHelper.addChild(new ScreenWidgets.Label(label, this.font, 0xFFFFFF), 1);
        rowHelper.addChild(box, 1);
    }

    private EditBox createNumberBox(String value) {
        EditBox box = new EditBox(this.font, 0, 0, 80, 20, Component.empty());
        box.setMaxLength(16);
        box.setFilter(text -> text.isEmpty() || text.matches("\\d{0,4}(\\.\\d{0,4})?"));
        box.setValue(value);
        return box;
    }

    private void onDone() {
        // 版本检查开关
        Config.ENABLE_VERSION_CHECKER.set(this.enableVersionCheckerBox.selected());

        // 石墩子高度（越界时钳制到合法范围，非法输入回退默认值）
        Config.HEIGHT_OF_SEAT_STONE.set(parseInt(this.heightOfSeatStoneBox.getValue(), 24, MIN_SEAT_HEIGHT, MAX_SEAT_HEIGHT));

        // 挖掘系数
        for (DoubleField field : doubleFields) {
            field.setter.accept(parseDouble(field.box.getValue(), 1.0D, MIN_COEFFICIENT, MAX_COEFFICIENT));
        }

        // 保存到配置文件并同步静态字段
        this.configSpec.save();
        Config.syncValues();

        // 返回上一个屏幕
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }

    /**
     * 浮点配置项的输入框与写回方法。
     */
    private record DoubleField(EditBox box, DoubleConsumer setter) {
    }
}
