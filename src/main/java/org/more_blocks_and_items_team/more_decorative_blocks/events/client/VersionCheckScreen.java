package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.Util;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.GridLayout;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;

/**
 * 发现新版本时显示的提示界面。
 */
public class VersionCheckScreen extends Screen {
    private static final int COLOR_TITLE = 0xFFFFFF;
    private static final int COLOR_DETAIL = 0xCCCCCC;

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

        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("screen.more_decorative_blocks.version_check.title"), this.font, COLOR_TITLE), 1);
        rowHelper.addChild(new ScreenWidgets.Spacing(15), 1);

        Component currentVersionText = Component.translatable(
                "screen.more_decorative_blocks.version_check.current_version",
                versionResult.currentVersion());
        Component latestVersionText = Component.translatable(
                "screen.more_decorative_blocks.version_check.latest_version",
                versionResult.latestVersion());

        rowHelper.addChild(new ScreenWidgets.Label(currentVersionText, this.font, COLOR_DETAIL), 1);
        rowHelper.addChild(new ScreenWidgets.Label(latestVersionText, this.font, COLOR_DETAIL), 1);
        rowHelper.addChild(new ScreenWidgets.Spacing(10), 1);

        rowHelper.addChild(new ScreenWidgets.Label(
                Component.translatable("screen.more_decorative_blocks.version_check.description"), this.font, COLOR_TITLE), 1);

        grid.arrangeElements();
        layout.addToContents(grid);

        GridLayout footerGrid = new GridLayout();
        footerGrid.defaultCellSetting().alignHorizontallyCenter();
        GridLayout.RowHelper footerRow = footerGrid.createRowHelper(2);

        footerRow.addChild(Button.builder(
                Component.translatable("screen.more_decorative_blocks.version_check.update_button"),
                button -> {
                    Util.getPlatform().openUri(versionResult.updateUrl());
                    onClose();
                }).build(), 1);

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
}
