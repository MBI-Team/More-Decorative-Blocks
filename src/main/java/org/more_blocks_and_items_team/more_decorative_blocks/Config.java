package org.more_blocks_and_items_team.more_decorative_blocks;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;
import static org.more_blocks_and_items_team.more_decorative_blocks.utils.LOGGER.LOGGER;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_VERSION_CHECKER;
    public static final ModConfigSpec.IntValue HEIGHT_OF_SEAT_STONE;

    // 公共静态变量，用于在代码中访问配置值
    public static boolean enableVersionChecker;
    public static int heightOfSeatStone;

    public static final ModConfigSpec SPEC; // 声明但不在这里初始化

    static {
        ENABLE_VERSION_CHECKER = BUILDER
                .comment("config.more_decorative_blocks.enable_version_checker.comment")
                .define("enableVersionChecker", true);

        HEIGHT_OF_SEAT_STONE = BUILDER
                .comment("config.more_decorative_blocks.height_of_seat_stone.comment")
                .defineInRange("heightOfSeatStone", 24, 16, 32);

        // 确保在所有配置项定义后再构建SPEC
        SPEC = BUILDER.build();
    }


    // 当配置加载调用此方法
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getModId().equals(MODID)) {
            enableVersionChecker = ENABLE_VERSION_CHECKER.get();
            heightOfSeatStone = HEIGHT_OF_SEAT_STONE.get();
            LOGGER.info("[Init]Config load!: enableVersionChecker={}, heightOfSeatStone={}", enableVersionChecker, heightOfSeatStone);
        }
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getModId().equals(MODID)) {
            enableVersionChecker = ENABLE_VERSION_CHECKER.get();
            heightOfSeatStone = HEIGHT_OF_SEAT_STONE.get();
            LOGGER.info("[Init]Config reload!: enableVersionChecker={}, heightOfSeatStone={}", enableVersionChecker, heightOfSeatStone);
        }
    }
}