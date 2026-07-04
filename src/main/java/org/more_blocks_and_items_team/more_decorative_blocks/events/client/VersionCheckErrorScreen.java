package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import static net.minecraft.client.gui.screens.advancements.AdvancementsScreen.WINDOW_HEIGHT;

public class VersionCheckErrorScreen extends Screen {
    private final Screen lastScreen;

    public VersionCheckErrorScreen(Screen lastScreen) {
        super(Component.translatable("screen.more_decorative_blocks.version_check.error_title"));
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

        // 添加垂直间距
        rowHelper.addChild(new SpacingComponent(20));

        // 错误标题
        Component title = Component.translatable("screen.more_decorative_blocks.version_check.error_title");
        rowHelper.addChild(new LabelWidget(title, this.font, 0xFFFF5555));

        // 添加垂直间距
        rowHelper.addChild(new SpacingComponent(15));

        // 错误信息
        Component errorMessage = Component.translatable("screen.more_decorative_blocks.version_check.error_message");
        rowHelper.addChild(new LabelWidget(errorMessage, this.font, 0xFFFFFFFF));

        // 添加垂直间距
        rowHelper.addChild(new SpacingComponent(10));

        // 建议信息
        Component suggestion = Component.translatable("screen.more_decorative_blocks.version_check.error_suggestion");
        rowHelper.addChild(new LabelWidget(suggestion, this.font, 0xFFAAAAAA));

        // 添加垂直间距
        rowHelper.addChild(new SpacingComponent(30));

        grid.arrangeElements();
        layout.addToContents(grid);

        // 底部按钮
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).build());

        layout.visitWidgets(this::addRenderableWidget);
        layout.arrangeElements();

        this.repositionElements();
    }

    @Override
    public void render(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        // 渲染半透明背景
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        // 错误标题
        Component title = Component.translatable("screen.more_decorative_blocks.version_check.error_title");
        guiGraphics.drawString(this.font, title,
                centerX - this.font.width(title) / 2,
                centerY - WINDOW_HEIGHT / 2 + 20,
                0xFFFF5555, false); // 红色，不带阴影

        // 错误信息
        Component errorMessage = Component.translatable("screen.more_decorative_blocks.version_check.error_message");
        guiGraphics.drawString(this.font, errorMessage,
                centerX - this.font.width(errorMessage) / 2,
                centerY - 10,
                0xFFFFFFFF, false); // 白色，不带阴影

        // 建议信息
        Component suggestion = Component.translatable("screen.more_decorative_blocks.version_check.error_suggestion");
        guiGraphics.drawString(this.font, suggestion,
                centerX - this.font.width(suggestion) / 2,
                centerY + 10,
                0xFFAAAAAA, false); // 浅灰色，不带阴影

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