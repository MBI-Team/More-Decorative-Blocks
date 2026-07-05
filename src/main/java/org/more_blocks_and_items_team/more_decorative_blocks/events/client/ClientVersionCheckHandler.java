package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.ScreenEvent;
import org.more_blocks_and_items_team.more_decorative_blocks.Config;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;
import org.slf4j.Logger;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;

@EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
public class ClientVersionCheckHandler {
    public static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        // 当玩家登录到世界时检查版本（只检查一次）
        if (!VersionCheckUtils.hasCheckedVersion() && Config.enableVersionChecker) {
            checkVersionAsync();
            VersionCheckUtils.setCheckedStatus(true);
        }
    }

    @SubscribeEvent
    public static void onScreenOpened(ScreenEvent.Opening event) {
        // 当主菜单打开时显示版本检查界面
        LOGGER.info("[VersionCheck] Screen opening: {}", event.getScreen().getClass().getSimpleName());
        if (event.getScreen() instanceof TitleScreen) {
            LOGGER.info("[VersionCheck] Title screen opened, checked: {}, has result: {}",
                    VersionCheckUtils.hasCheckedVersion(),
                    VersionCheckUtils.getLastCheckResult() != null);

            if (VersionCheckUtils.hasCheckedVersion() &&
                    VersionCheckUtils.getLastCheckResult() != null &&
                    VersionCheckUtils.getLastCheckResult().isNewVersionAvailable()) {

                LOGGER.info("[VersionCheck] Showing update screen on title screen");
                Minecraft.getInstance().execute(ClientVersionCheckHandler::showVersionCheckScreen);
            }
        }
    }

    /**
     * 异步检查版本
     */
    private static void checkVersionAsync() {
        LOGGER.info("[VersionCheck] Starting version check...");
        LOGGER.info("[VersionCheck] Config enableVersionChecker: {}", Config.enableVersionChecker);
        LOGGER.info("[VersionCheck] Has checked version: {}", VersionCheckUtils.hasCheckedVersion());

        VersionCheckUtils.checkVersionAsync().thenAccept(result -> {
            LOGGER.info("[VersionCheck] Version check completed. Result available: {}", result.isNewVersionAvailable());
            VersionCheckUtils.setLastCheckResult(result);
            if (result.isNewVersionAvailable()) {
                LOGGER.info("[VersionCheck] Showing update screen...");
                // 在主线程中显示界面
                Minecraft.getInstance().execute(ClientVersionCheckHandler::showVersionCheckScreen);
            } else {
                LOGGER.info("[VersionCheck] No update needed.");
            }
        }).exceptionally(throwable -> {
            LOGGER.error("[VersionCheck] Version check failed: ", throwable);
            // 在主线程中显示错误界面
            Minecraft.getInstance().execute(ClientVersionCheckHandler::showErrorScreen);
            return null;
        });
    }

    /**
     * 显示版本检查界面
     */
    private static void showVersionCheckScreen() {
        VersionCheckUtils.VersionCheckResult result = VersionCheckUtils.getLastCheckResult();
        if (result != null && result.isNewVersionAvailable()) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new VersionCheckScreen(result, minecraft.screen));
        }
    }

    /**
     * 重置检查状态（用于调试或重新检查）
     */
    public static void resetCheckStatus() {
        VersionCheckUtils.resetCheckStatus();
    }

    /**
     * 显示错误界面
     */
    private static void showErrorScreen() {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.setScreen(new VersionCheckErrorScreen(minecraft.screen));
    }

    /**
     * 获取上次检查结果
     */
    @SuppressWarnings("unused")
    public static VersionCheckUtils.VersionCheckResult getLastCheckResult() {
        return VersionCheckUtils.getLastCheckResult();
    }
}