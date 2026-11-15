package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

/**
 * 几个界面共用的轻量组件。
 * <p>
 * 原先 {@code ConfigScreen}、{@code VersionCheckScreen}、{@code VersionCheckErrorScreen}
 * 各自内嵌了一份几乎相同的 {@code SpacingComponent} / {@code LabelWidget} 实现，
 * 这里统一收拢，避免一处修改漏掉另外两处。
 */
public final class ScreenWidgets {

    private ScreenWidgets() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 不可见的间距占位组件。
     */
    public static class Spacing extends AbstractWidget {
        public Spacing(int height) {
            super(0, 0, 0, height, Component.empty());
        }

        @Override
        protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            // 不可见，仅用于占位
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            // 不可见组件无需旁白
        }
    }

    /**
     * 纯文本标签组件。
     */
    public static class Label extends AbstractWidget {
        private final Component text;
        private final Font font;
        private final int color;
        private final boolean shadow;

        public Label(Component text, Font font, int color) {
            this(text, font, color, true);
        }

        public Label(Component text, Font font, int color, boolean shadow) {
            super(0, 0, font.width(text), font.lineHeight, text);
            this.text = text;
            this.font = font;
            this.color = color;
            this.shadow = shadow;
        }

        @Override
        protected void renderWidget(@NotNull GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            guiGraphics.drawString(font, text, this.getX(), this.getY(), color, shadow);
        }

        @Override
        protected void updateWidgetNarration(@NotNull NarrationElementOutput narrationElementOutput) {
            this.defaultButtonNarrationText(narrationElementOutput);
        }
    }
}
