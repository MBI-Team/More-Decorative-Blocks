package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.GITHUB_API_URL;
import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

/**
 * 版本检查工具类，整合了版本检查、结果处理和网络请求功能
 */
public class VersionCheckUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(VersionCheckUtils.class);
    private static final Pattern VERSION_PATTERN = Pattern.compile("\"tag_name\"\\s*:\\s*\"v?([^\"]+)\"");

    // 检查状态
    private static boolean hasCheckedVersion = false;
    private static VersionCheckResult lastCheckResult = null;

    /**
     * 从GitHub获取最新版本号
     */
    public static String getLatestVersion() throws IOException {
        LOGGER.info("[VersionCheck] Fetching latest version from GitHub...");
        URL url = new URL(GITHUB_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "More-Decorative-Blocks-Version-Checker");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            Matcher matcher = VERSION_PATTERN.matcher(response.toString());
            if (matcher.find()) {
                String version = matcher.group(1);
                LOGGER.info("[VersionCheck] Found latest version: {}", version);
                return version;
            } else {
                throw new IOException("Could not parse version from GitHub response");
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 比较版本号
     */
    public static boolean compareVersions(String currentVersion, String latestVersion) {
        if (currentVersion.equals(latestVersion)) {
            return false;
        }

        try {
            String[] currentParts = currentVersion.split("[.-]");
            String[] latestParts = latestVersion.split("[.-]");

            int maxLength = Math.max(currentParts.length, latestParts.length);

            for (int i = 0; i < maxLength; i++) {
                int currentNum = i < currentParts.length ? Integer.parseInt(currentParts[i]) : 0;
                int latestNum = i < latestParts.length ? Integer.parseInt(latestParts[i]) : 0;

                if (latestNum > currentNum) {
                    return true;
                } else if (latestNum < currentNum) {
                    return false;
                }
            }

            // 如果数字部分相同，检查预发布标识
            if (currentParts.length > 3 && latestParts.length > 3) {
                String currentPre = currentParts[3].toLowerCase();
                String latestPre = latestParts[3].toLowerCase();

                int currentPriority = getPreReleasePriority(currentPre);
                int latestPriority = getPreReleasePriority(latestPre);

                return latestPriority > currentPriority;
            }

            return false;
        } catch (Exception e) {
            LOGGER.warn("[VersionCheck] Error comparing versions: {}", e.getMessage());
            return false;
        }
    }

    private static int getPreReleasePriority(String preRelease) {
        switch (preRelease) {
            case "release":
            case "stable":
            case "final":
                return 5;
            case "rc":
                return 4;
            case "beta":
                return 3;
            case "alpha":
                return 2;
            default:
                return 1;
        }
    }

    /**
     * 异步检查版本
     */
    public static CompletableFuture<VersionCheckResult> checkVersionAsync() {
        LOGGER.info("[VersionCheck] Starting version check...");
        LOGGER.info("[VersionCheck] Current version: {}", mod_version);
        LOGGER.info("[VersionCheck] Has checked version: {}", hasCheckedVersion);

        return CompletableFuture.supplyAsync(() -> {
            try {
                String latestVersion = getLatestVersion();
                LOGGER.info("[VersionCheck] Latest version: {}", latestVersion);

                boolean isNewVersion = compareVersions(mod_version, latestVersion);
                LOGGER.info("[VersionCheck] Is new version available: {}", isNewVersion);

                if (isNewVersion) {
                    LOGGER.info("[VersionCheck] New version available: {} -> {}", mod_version, latestVersion);
                    return new VersionCheckResult(true, mod_version, latestVersion,
                            "https://github.com/MBI-Team/More-Decorative-Blocks/releases");
                } else {
                    LOGGER.info("[VersionCheck] You are using the latest version: {}", mod_version);
                    return new VersionCheckResult(false, mod_version, latestVersion, "");
                }
            } catch (IOException e) {
                LOGGER.warn("[VersionCheck] Failed to check for updates: {}", e.getMessage());
                return new VersionCheckResult(false, mod_version, "unknown", "");
            }
        });
    }

    /**
     * 重置检查状态（用于调试或重新检查）
     */
    public static void resetCheckStatus() {
        hasCheckedVersion = false;
        lastCheckResult = null;
        LOGGER.info("[VersionCheck] Version check status reset");
    }

    /**
     * 获取上次检查结果
     */
    public static VersionCheckResult getLastCheckResult() {
        return lastCheckResult;
    }

    /**
     * 设置最后检查结果（内部使用）
     */
    public static void setLastCheckResult(VersionCheckResult result) {
        lastCheckResult = result;
    }

    /**
     * 设置检查状态（内部使用）
     */
    public static void setCheckedStatus(boolean checked) {
        hasCheckedVersion = checked;
    }

    /**
     * 获取是否已检查过版本
     */
    public static boolean hasCheckedVersion() {
        return hasCheckedVersion;
    }

    /**
     * 版本检查结果类
     */
    public static class VersionCheckResult {
        private final boolean isNewVersionAvailable;
        private final String currentVersion;
        private final String latestVersion;
        private final String updateUrl;

        public VersionCheckResult(boolean isNewVersionAvailable, String currentVersion, String latestVersion, String updateUrl) {
            this.isNewVersionAvailable = isNewVersionAvailable;
            this.currentVersion = currentVersion;
            this.latestVersion = latestVersion;
            this.updateUrl = updateUrl;
        }

        public boolean isNewVersionAvailable() {
            return isNewVersionAvailable;
        }

        public String getCurrentVersion() {
            return currentVersion;
        }

        public String getLatestVersion() {
            return latestVersion;
        }

        public String getUpdateUrl() {
            return updateUrl;
        }
    }
}