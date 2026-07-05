package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;

public class VersionCheckScreen extends Screen {
    private final VersionCheckUtils.VersionCheckResult versionResult;
    private final Screen lastScreen;

    public VersionCheckScreen(VersionCheckUtils.VersionCheckResult versionResult, Screen lastScreen) {
        super(Component.translatable("screen.more_decorative_blocks.version_check.title"));
        this.versionResult = versionResult;
        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        super.init();

        HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
        layout.setHeaderHeight(30);

        GridLayout grid = new GridLayout();
        grid.defaultCellSetting().alignHorizontallyCenter();
        GridLayout.RowHelper rowHelper = grid.createRowHelper(1);

        // 标题
        rowHelper.addChild(new LabelWidget(Component.translatable("screen.more_decorative_blocks.version_check.title"), this.font, 0xFFFFFF), 1);

        // 添加间距
        rowHelper.addChild(new SpacingComponent(15), 1);

        // 版本信息
        Component currentVersionText = Component.translatable(
                "screen.more_decorative_blocks.version_check.current_version",
                versionResult.currentVersion());
        Component latestVersionText = Component.translatable(
                "screen.more_decorative_blocks.version_check.latest_version",
                versionResult.latestVersion());

        rowHelper.addChild(new LabelWidget(currentVersionText, this.font, 0xCCCCCC), 1);
        rowHelper.addChild(new LabelWidget(latestVersionText, this.font, 0xCCCCCC), 1);

        // 添加间距
        rowHelper.addChild(new SpacingComponent(10), 1);

        // 描述文字
        rowHelper.addChild(new LabelWidget(Component.translatable("screen.more_decorative_blocks.version_check.description"), this.font, 0xFFFFFF), 1);

        grid.arrangeElements();
        layout.addToContents(grid);

        // 添加底部按钮
        GridLayout footerGrid = new GridLayout();
        footerGrid.defaultCellSetting().alignHorizontallyCenter();
        GridLayout.RowHelper footerRow = footerGrid.createRowHelper(2);

        // 更新按钮
        footerRow.addChild(Button.builder(
                Component.translatable("screen.more_decorative_blocks.version_check.update_button"),
                button -> {
                    // 打开浏览器跳转到更新页面
                    Util.getPlatform().openUri(versionResult.updateUrl());
                    onClose();
                }).build(), 1);

        // 稍后提醒按钮
        footerRow.addChild(Button.builder(
                Component.translatable("screen.more_decorative_blocks.version_check.remind_later"),
                button -> onClose()).build(), 1);

        footerGrid.arrangeElements();
        layout.addToFooter(footerGrid);

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreen(this.lastScreen);
        }
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return super.shouldCloseOnEsc();
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
        private final int color;

        public LabelWidget(Component text, net.minecraft.client.gui.Font font, int color) {
            super(0, 0, font.width(text), font.lineHeight, text);
            this.text = text;
            this.font = font;
            this.color = color;
        }

        @Override
        protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // 渲染文本
            guiGraphics.drawString(font, text, this.getX(), this.getY(), color);
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            // 空实现
        }
    }
}