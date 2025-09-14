package org.more_blocks_and_items_team.more_decorative_blocks.events.server;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.LOGGER.LOGGER;
import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModWorldGenDatapackRegistry {

    @SubscribeEvent
    public static void onNewDatapackRegistry(DataPackRegistryEvent.NewRegistry event) {
        // 世界生成特性现在通过ModWorldGenProvider处理
        // 此事件处理器保持空实现
        LOGGER.info("World generation registry event received - features are handled by ModWorldGenProvider");
    }
}