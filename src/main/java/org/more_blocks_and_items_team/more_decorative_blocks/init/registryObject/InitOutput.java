package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.CreativeModeTabRegistry.*;
import static org.more_blocks_and_items_team.more_decorative_blocks.tools.LOGGER.LOGGER;

public class InitOutput {
    public static void init() {
        LOGGER.info("[Mod Init]CreativeModeTabRegistry:");
        LOGGER.info(String.valueOf(MDB_BUILDING_TAB));
        LOGGER.info(String.valueOf(MDB_DECORATIVE_TAB));
        LOGGER.info(String.valueOf(MDB_MATERIAL_TAB));
        LOGGER.info(String.valueOf(MDB_REDSTONE_TAB));
        LOGGER.info(String.valueOf(MDB_DECORATIVE_TAB));
    }
}
