package org.more_blocks_and_items_team.more_decorative_blocks.events.server;

import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.LOGGER.LOGGER;
import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModWorldGenEvents {

    @SubscribeEvent
    public static void onRegister(RegisterEvent event) {
        if (event.getRegistryKey().equals(Registries.CONFIGURED_FEATURE)) {
            event.register(Registries.CONFIGURED_FEATURE, helper -> {
                // 实际的注册逻辑由数据生成系统处理
                LOGGER.info("CONFIGURED_FEATURE registry event received");
            });
        }

        if (event.getRegistryKey().equals(Registries.PLACED_FEATURE)) {
            event.register(Registries.PLACED_FEATURE, helper -> {
                // 实际的注册逻辑由数据生成系统处理
                LOGGER.info("PLACED_FEATURE registry event received");
            });
        }
    }
}