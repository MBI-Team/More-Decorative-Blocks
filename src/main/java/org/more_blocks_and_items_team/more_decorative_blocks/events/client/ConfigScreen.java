package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Checkbox;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarrationElementOutput;
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

        // 添加间距
        rowHelper.addChild(new SpacingComponent(20), 2);

        // 添加文本
        rowHelper.addChild(new LabelWidget(Component.translatable("config.more_decorative_blocks.height_of_seat_stone"), this.font), 2);

        // 添加文本框
        heightOfSeatStoneBox = new EditBox(this.font, 0, 0, 100, 20,
                Component.translatable("config.more_decorative_blocks.height_of_seat_stone"));
        heightOfSeatStoneBox.setValue(String.valueOf(Config.heightOfSeatStone));
        rowHelper.addChild(heightOfSeatStoneBox, 2);

        grid.arrangeElements();
        layout.addToContents(grid);

        // 添加按钮
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, (button) -> this.onDone()).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void onDone() {
        // 保存配置到配置规范值
        Config.ENABLE_VERSION_CHECKER.set(this.enableVersionCheckerBox.selected());
        try {
            int height = Integer.parseInt(this.heightOfSeatStoneBox.getValue());
            // 确保值在有效范围内
            if (height < 16) {
                height = 16;
            } else if (height > 32) {
                height = 32;
            }
            Config.HEIGHT_OF_SEAT_STONE.set(height);
        } catch (NumberFormatException e) {
            Config.HEIGHT_OF_SEAT_STONE.set(24); // 默认值
        }

        // 保存到配置文件
        this.configSpec.save();

        // 更新静态变量
        Config.enableVersionChecker = Config.ENABLE_VERSION_CHECKER.get();
        Config.heightOfSeatStone = Config.HEIGHT_OF_SEAT_STONE.get();

        // 返回上一个屏幕
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }

    /**
     * 自定义组件类，用于创建垂直间距
     */
    private static class SpacingComponent extends net.minecraft.client.gui.components.AbstractWidget {
        public SpacingComponent(int height) {
            super(0, 0, 0, height, Component.empty());
        }

        @Override
        protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // 这是一个不可见的组件，仅用于提供间距
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            // 空实现，因为这是一个不可见的组件
        }
    }

    /**
     * 自定义组件类，用于显示文本标签
     */
    private static class LabelWidget extends net.minecraft.client.gui.components.AbstractWidget {
        private final Component text;
        private final net.minecraft.client.gui.Font font;

        public LabelWidget(Component text, net.minecraft.client.gui.Font font) {
            super(0, 0, font.width(text), font.lineHeight, text);
            this.text = text;
            this.font = font;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // 渲染文本，使用白色颜色
            guiGraphics.drawString(font, text, this.getX(), this.getY(), 0xFFFFFF);
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            // 空实现
        }
    }
}