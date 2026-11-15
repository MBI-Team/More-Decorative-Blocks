package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * 版本检查失败时显示的界面。
 * <p>
 * 原实现在 {@code init()} 里创建了文字组件，又在 {@code render()} 里手动
 * {@code drawString} 画了一遍同样的文字 —— 两段文字会重叠在一起；
 * 同时 {@code render()} 里还重复调用了 {@code renderBackground()}（父类已经调用过一次）。
 * 这里只保留组件化渲染。
 */
public class VersionCheckErrorScreen extends Screen {
    private static final int COLOR_TITLE = 0xFF5555;
    private static final int COLOR_MESSAGE = 0xFFFFFF;
    private static final int COLOR_HINT = 0xAAAAAA;

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

        rowHelper.addChild(new ScreenWidgets.Spacing(20), 1);
        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("screen.more_decorative_blocks.version_check.error_title"), this.font, COLOR_TITLE), 1);
        rowHelper.addChild(new ScreenWidgets.Spacing(15), 1);
        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("screen.more_decorative_blocks.version_check.error_message"), this.font, COLOR_MESSAGE), 1);
        rowHelper.addChild(new ScreenWidgets.Spacing(10), 1);
        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("screen.more_decorative_blocks.version_check.error_suggestion"), this.font, COLOR_HINT), 1);
        rowHelper.addChild(new ScreenWidgets.Spacing(30), 1);

        grid.arrangeElements();
        layout.addToContents(grid);
        layout.addToFooter(Button.builder(CommonComponents.GUI_DONE, button -> onClose()).build());

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
}
