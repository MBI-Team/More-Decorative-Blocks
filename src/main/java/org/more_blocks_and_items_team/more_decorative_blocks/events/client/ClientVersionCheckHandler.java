package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
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

    /**
     * 同一个游戏会话内只弹一次界面。
     * <p>
     * 原实现只判断“是否检查过 + 是否有新版本”，而 {@link ScreenEvent.Opening} 会在
     * 每次打开界面（包括版本界面的自己）时触发，结果是界面反复弹出、无法关闭。
     */
    private static boolean updateScreenShown = false;
    private static boolean errorScreenShown = false;

    @SubscribeEvent
    public static void onClientLoggedIn(ClientPlayerNetworkEvent.LoggingIn event) {
        // 当玩家登录到世界时检查版本（只检查一次）
        if (!VersionCheckUtils.hasCheckedVersion() && Config.enableVersionChecker) {
            VersionCheckUtils.setCheckedStatus(true);
            checkVersionAsync();
        }
    }

    @SubscribeEvent
    public static void onScreenOpened(ScreenEvent.Opening event) {
        if (updateScreenShown) {
            return;
        }

        VersionCheckUtils.VersionCheckResult result = VersionCheckUtils.getLastCheckResult();
        if (VersionCheckUtils.hasCheckedVersion() && result != null && result.isNewVersionAvailable()) {
            updateScreenShown = true;
            LOGGER.debug("[VersionCheck] Showing update screen on title screen");
            // 事件对象会被复用，先把当前界面取出来再切到主线程
            Screen currentScreen = event.getScreen();
            Minecraft.getInstance().execute(() -> showVersionCheckScreen(currentScreen));
        }
    }

    /**
     * 异步检查版本
     */
    private static void checkVersionAsync() {
        LOGGER.info("[VersionCheck] Starting version check...");

        VersionCheckUtils.checkVersionAsync().thenAccept(result -> {
            LOGGER.info("[VersionCheck] Version check completed. Update available: {}", result.isNewVersionAvailable());
            VersionCheckUtils.setLastCheckResult(result);
            if (result.isNewVersionAvailable()) {
                // 在主线程中显示界面
                Minecraft.getInstance().execute(() -> showVersionCheckScreen(Minecraft.getInstance().screen));
            } else {
                LOGGER.info("[VersionCheck] No update needed.");
            }
        }).exceptionally(throwable -> {
            LOGGER.error("[VersionCheck] Version check failed: ", throwable);
            // 在主线程中显示错误界面（每个会话只弹一次）
            Minecraft.getInstance().execute(() -> {
                if (errorScreenShown) {
                    return;
                }
                errorScreenShown = true;
                showErrorScreen();
            });
            return null;
        });
    }

    /**
     * 显示版本检查界面
     */
    private static void showVersionCheckScreen(Screen lastScreen) {
        VersionCheckUtils.VersionCheckResult result = VersionCheckUtils.getLastCheckResult();
        if (result != null && result.isNewVersionAvailable()) {
            Minecraft minecraft = Minecraft.getInstance();
            minecraft.setScreen(new VersionCheckScreen(result, lastScreen));
        }
    }

    /**
     * 重置检查状态（用于调试或重新检查）
     */
    public static void resetCheckStatus() {
        VersionCheckUtils.resetCheckStatus();
        updateScreenShown = false;
        errorScreenShown = false;
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
