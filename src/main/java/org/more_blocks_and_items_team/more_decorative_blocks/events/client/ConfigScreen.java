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

public class ConfigScreen extends Screen {
    private final Screen lastScreen;
    private final ModConfigSpec configSpec;

    private Checkbox enableVersionCheckerBox;
    private EditBox heightOfSeatStoneBox;

    public ConfigScreen(Screen lastScreen, ModConfigSpec configSpec) {
        super(Component.translatable("config.more_decorative_blocks.title"));
        this.lastScreen = lastScreen;
        this.configSpec = configSpec;
    }

    @Override
    protected void init() {
        super.init();

        HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
        layout.setHeaderHeight(30);

        GridLayout grid = new GridLayout();
        grid.defaultCellSetting().alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = grid.createRowHelper(2);

        // 添加复选框
        enableVersionCheckerBox = Checkbox.builder(Component.translatable("config.more_decorative_blocks.enable_version_checker"), this.font)
                .selected(Config.enableVersionChecker)
                .build();
        rowHelper.addChild(enableVersionCheckerBox, 2);

        // 添加文本框
        heightOfSeatStoneBox = new EditBox(this.font, 0, 0, 100, 20,
                Component.translatable("config.more_decorative_blocks.height_of_seat_stone"));
        heightOfSeatStoneBox.setValue(String.valueOf(Config.heightOfSeatStone));
        rowHelper.addChild(heightOfSeatStoneBox, 2);

        // 添加按钮
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> {
            this.onDone();
        }).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void onDone() {
        // 保存配置
        Config.enableVersionChecker = this.enableVersionCheckerBox.selected();
        try {
            Config.heightOfSeatStone = Integer.parseInt(this.heightOfSeatStoneBox.getValue());
            // 确保值在有效范围内
            if (Config.heightOfSeatStone < 16) {
                Config.heightOfSeatStone = 16;
            } else if (Config.heightOfSeatStone > 32) {
                Config.heightOfSeatStone = 32;
            }
        } catch (NumberFormatException e) {
            Config.heightOfSeatStone = 24; // 默认值
        }

        // 保存到配置文件
        this.configSpec.save();

        // 返回上一个屏幕
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }
}