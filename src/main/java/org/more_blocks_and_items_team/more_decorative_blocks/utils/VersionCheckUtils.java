package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.GITHUB_API_URL;
import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

/**
 * 版本检查工具类，整合了版本检查、结果处理和网络请求功能
 */
public class VersionCheckUtils {
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final Pattern VERSION_PATTERN = Pattern.compile("\"tag_name\"\\s*:\\s*\"v?([^\"]+)\"");

    // 检查状态
    private static boolean hasCheckedVersion = false;
    private static VersionCheckResult lastCheckResult = null;

    /**
     * 从GitHub获取最新版本号
     */
    public static String getLatestVersion() throws IOException, URISyntaxException {
        LOGGER.info("[VersionCheck] Fetching latest version from GitHub...");
        URL url = new URI(GITHUB_API_URL).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // 如果是 HTTPS 连接，配置 SSL
        if (connection instanceof HttpsURLConnection) {
            configureSSL((HttpsURLConnection) connection);
        }
        
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
        } catch (javax.net.ssl.SSLException e) {
            LOGGER.warn("[VersionCheck] SSL certificate error: {}. Skipping version check.", e.getMessage());
            throw new IOException("SSL certificate verification failed. This is likely a network environment issue.", e);
        } finally {
            connection.disconnect();
        }
    }

    /**
     * 配置 SSL 连接以处理证书问题
     */
    private static void configureSSL(HttpsURLConnection connection) {
        try {
            // 创建一个信任所有证书的 TrustManager（仅用于版本检查）
            TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return new X509Certificate[0];
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            connection.setSSLSocketFactory(sc.getSocketFactory());

            // 禁用主机名验证（仅用于版本检查）
            connection.setHostnameVerifier((hostname, session) -> true);

            LOGGER.debug("[VersionCheck] SSL configuration applied for version check");
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            LOGGER.warn("[VersionCheck] Failed to configure SSL: {}", e.getMessage());
        }
    }

    /**
     * 比较版本号，判断远端版本是否比当前版本新。
     * <p>
     * 具体规则见 {@link VersionComparator}：支持
     * {@code 1.5.0-alpha-a96478c+20261004} 这类带预发布标识 / 构建元数据的版本号，
     * 不会再因为 {@code alpha} 无法解析成数字而静默返回“没有新版本”。
     */
    public static boolean compareVersions(String currentVersion, String latestVersion) {
        return VersionComparator.isNewer(currentVersion, latestVersion);
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
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
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
    public record VersionCheckResult(boolean isNewVersionAvailable, String currentVersion, String latestVersion,
                                     String updateUrl) {
    }
}