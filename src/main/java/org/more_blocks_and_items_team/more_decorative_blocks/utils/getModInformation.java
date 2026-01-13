package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import org.more_blocks_and_items_team.more_decorative_blocks.More_decorative_blocks;

import java.util.Properties;

import static org.more_blocks_and_items_team.more_decorative_blocks.utils.LOGGER.LOGGER;

public class getModInformation {
    public static final String MODID = "more_decorative_blocks";

    public static String loadVersion() {
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

    public static String mod_version = loadVersion();
    public static String licence = getLicence();

    public static String getLicence() {
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
}
