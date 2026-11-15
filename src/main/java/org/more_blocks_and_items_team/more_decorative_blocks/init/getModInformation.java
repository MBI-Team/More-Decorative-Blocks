package org.more_blocks_and_items_team.more_decorative_blocks.init;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.io.InputStream;
import java.util.Properties;

/**
 * 读取由 Gradle 在 {@code processResources} 阶段写入的 {@code GradleInit.properties}。
 * <p>
 * 该文件只读取一次并缓存，避免每次启动都重复解析资源、也避免资源缺失时反复打印堆栈。
 */
public class getModInformation {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "more_decorative_blocks";

    private static final String UNKNOWN = "unknown";

    private static Properties properties;

    public static String GITHUB_API_URL = getProperty("GITHUB_API_URL", "https://api.github.com/repos/MBI-Team/More-Decorative-Blocks/releases/latest");

    public static String mod_version = getProperty("mod.version", UNKNOWN);

    @SuppressWarnings("unused")
    public static String licence = getProperty("license", UNKNOWN);

    /**
     * 读取并缓存 {@code GradleInit.properties}。
     * <p>
     * 使用 try-with-resources 保证流被关闭；资源不存在（例如在 IDE 中直接运行单元测试）时返回空属性，
     * 由 {@link #getProperty(String, String)} 回退到默认值。
     */
    private static synchronized Properties properties() {
        if (properties != null) {
            return properties;
        }

        Properties loaded = new Properties();
        try (InputStream stream = getModInformation.class.getResourceAsStream("/GradleInit.properties")) {
            if (stream == null) {
                LOGGER.warn("[Mod Init]GradleInit.properties not found in resources, using default values");
            } else {
                loaded.load(stream);
            }
        } catch (Exception e) {
            LOGGER.error("[Mod Init]Failed to load GradleInit.properties", e);
        }

        properties = loaded;
        return properties;
    }

    private static String getProperty(String key, String defaultValue) {
        String value = properties().getProperty(key);
        return value == null || value.isBlank() ? defaultValue : value;
    }
}
