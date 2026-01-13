package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.CreativeModeTabRegistry.*;
import static org.more_blocks_and_items_team.more_decorative_blocks.utils.LOGGER.LOGGER;

public class CMTRInitOutput {
    public static void init() {
        LOGGER.info("[Mod Init]CreativeModeTabRegistry:");
        LOGGER.info(String.valueOf(MDB_BUILDING_TAB.getId()));
        LOGGER.info(String.valueOf(MDB_DECORATIVE_TAB.getId()));
        LOGGER.info(String.valueOf(MDB_MATERIAL_TAB.getId()));
        LOGGER.info(String.valueOf(MDB_REDSTONE_TAB.getId()));
        LOGGER.info(String.valueOf(MDB_ROAD_TAB.getId()));
    }
}
