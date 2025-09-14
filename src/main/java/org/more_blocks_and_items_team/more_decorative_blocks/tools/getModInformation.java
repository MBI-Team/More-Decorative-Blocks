package org.more_blocks_and_items_team.more_decorative_blocks.tools;

import org.more_blocks_and_items_team.more_decorative_blocks.More_decorative_blocks;

import java.util.Properties;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.LOGGER.LOGGER;

public class getModInformation {
    public static final String MODID = "more_decorative_blocks";

    public static String loadID() {
        LOGGER.info("[Mod Init]Loading ID from GradleInit.properties");
        try {
            Properties props = new Properties();
            props.load(More_decorative_blocks.class.getResourceAsStream("/GradleInit.properties"));
            return props.getProperty("mod.id", "unknown");
        } catch (Exception e) {
            LOGGER.error("[Mod Init]Failed to load ID from GradleInit.properties", e);
            return "unknown";
        }
    }

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
}
