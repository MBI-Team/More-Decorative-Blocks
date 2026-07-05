package org.more_blocks_and_items_team.more_decorative_blocks.init;

import com.mojang.logging.LogUtils;
import org.more_blocks_and_items_team.more_decorative_blocks.More_decorative_blocks;
import org.slf4j.Logger;

import java.util.Properties;

public class getModInformation {
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final String MODID = "more_decorative_blocks";

    public static String GITHUB_API_URL = getGAU();

    public static String mod_version = loadVersion();

    @SuppressWarnings("unused")
    public static String licence = getLicence();

    private static String loadVersion() {
        LOGGER.info("[VersionChecker]Loading version from GradleInit.properties");
        try {
            Properties props = new Properties();
            props.load(More_decorative_blocks.class.getResourceAsStream("/GradleInit.properties"));
            return props.getProperty("mod.version", "unknown");
        } catch (Exception e) {
            LOGGER.error("[VersionChecker]Failed to load version from GradleInit.properties", e);
            return "unknown";
        }
    }

    private static String getLicence() {
        LOGGER.info("[Mod Init]Getting license......");
        try {
            Properties props = new Properties();
            props.load(More_decorative_blocks.class.getResourceAsStream("/GradleInit.properties"));
            return props.getProperty("license", "unknown");
        } catch (Exception e) {
            LOGGER.error("[Init]Failed to get license", e);
            return "unknown";
        }
    }

    private static String getGAU() {
        try {
            Properties props = new Properties();
            props.load(More_decorative_blocks.class.getResourceAsStream("/GradleInit.properties"));
            return props.getProperty("GITHUB_API_URL", "unknown");
        } catch (Exception e) {
            LOGGER.error("[Init]Failed to get GITHUB_API_URL", e);
            return "unknown";
        }
    }
}
