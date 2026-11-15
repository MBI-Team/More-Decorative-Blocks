package org.more_blocks_and_items_team.more_decorative_blocks;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.slf4j.Logger;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;

@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD)
public class Config {
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue ENABLE_VERSION_CHECKER;
    public static final ModConfigSpec.IntValue HEIGHT_OF_SEAT_STONE;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_WOOD_MINING_COEFFICIENT;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_STONE_MINING_COEFFICIENT;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_GOLD_MINING_COEFFICIENT;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_IRON_MINING_COEFFICIENT;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_DIAMOND_MINING_COEFFICIENT;
    public static final ModConfigSpec.DoubleValue MDB_BLOCK_NETHERITE_MINING_COEFFICIENT;

    // 公共静态变量，用于在代码中访问配置值
    public static boolean enableVersionChecker;
    public static int heightOfSeatStone;
    public static double mdbBlockWoodMiningCoefficient;
    public static double mdbBlockStoneMiningCoefficient;
    public static double mdbBlockGoldMiningCoefficient;
    public static double mdbBlockIronMiningCoefficient;
    public static double mdbBlockDiamondMiningCoefficient;
    public static double mdbBlockNetheriteMiningCoefficient;

    public static final ModConfigSpec SPEC; // 声明但不在这里初始化

    static {
        ENABLE_VERSION_CHECKER = BUILDER
                .comment("Whether the mod should check for a newer release on GitHub (client side).")
                .define("enableVersionChecker", true);

        HEIGHT_OF_SEAT_STONE = BUILDER
                .comment("Height (in pixels, 1/16 block units) of the Seat Stone block. Range: 16 - 32.")
                .defineInRange("heightOfSeatStone", 24, 16, 32);

        // MDB Block 挖掘时间系数配置（系数越大，挖掘越慢）
        MDB_BLOCK_WOOD_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for wooden tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockWoodMiningCoefficient", 2.0, 0.1, 10.0);

        MDB_BLOCK_STONE_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for stone tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockStoneMiningCoefficient", 1.5, 0.1, 10.0);

        MDB_BLOCK_GOLD_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for golden tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockGoldMiningCoefficient", 1.0, 0.1, 10.0);


        MDB_BLOCK_IRON_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for iron tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockIronMiningCoefficient", 1.0, 0.1, 10.0);

        MDB_BLOCK_DIAMOND_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for diamond tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockDiamondMiningCoefficient", 0.8, 0.1, 10.0);

        MDB_BLOCK_NETHERITE_MINING_COEFFICIENT = BUILDER
                .comment("Mining speed coefficient for netherite tools. Higher values mean slower mining.")
                .defineInRange("mdbBlockNetheriteMiningCoefficient", 0.6, 0.1, 10.0);

        // 确保在所有配置项定义后再构建SPEC
        SPEC = BUILDER.build();
    }

    /**
     * 把配置值同步到静态字段。
     * <p>
     * 加载 / 重载 / 配置界面保存后都调用这一份逻辑，避免出现“重载后某个字段没有更新”的漏项
     * （原实现里重载分支漏掉了 {@code mdbBlockGoldMiningCoefficient}）。
     */
    public static void syncValues() {
        enableVersionChecker = ENABLE_VERSION_CHECKER.get();
        heightOfSeatStone = HEIGHT_OF_SEAT_STONE.get();
        mdbBlockWoodMiningCoefficient = MDB_BLOCK_WOOD_MINING_COEFFICIENT.get();
        mdbBlockStoneMiningCoefficient = MDB_BLOCK_STONE_MINING_COEFFICIENT.get();
        mdbBlockGoldMiningCoefficient = MDB_BLOCK_GOLD_MINING_COEFFICIENT.get();
        mdbBlockIronMiningCoefficient = MDB_BLOCK_IRON_MINING_COEFFICIENT.get();
        mdbBlockDiamondMiningCoefficient = MDB_BLOCK_DIAMOND_MINING_COEFFICIENT.get();
        mdbBlockNetheriteMiningCoefficient = MDB_BLOCK_NETHERITE_MINING_COEFFICIENT.get();
    }

    // 当配置加载调用此方法
    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        if (event.getConfig().getModId().equals(MODID)) {
            syncValues();
            LOGGER.info("[Init]Config load!: enableVersionChecker={}, heightOfSeatStone={}", enableVersionChecker, heightOfSeatStone);
            LOGGER.info("[Init]MDB Block mining coefficients: wood={}, stone={}, gold={}, iron={}, diamond={}, netherite={}",
                    mdbBlockWoodMiningCoefficient, mdbBlockStoneMiningCoefficient, mdbBlockGoldMiningCoefficient, mdbBlockIronMiningCoefficient,
                    mdbBlockDiamondMiningCoefficient, mdbBlockNetheriteMiningCoefficient);
        }
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        if (event.getConfig().getModId().equals(MODID)) {
            syncValues();
            LOGGER.info("[Init]Config reload!: enableVersionChecker={}, heightOfSeatStone={}", enableVersionChecker, heightOfSeatStone);
            LOGGER.info("[Init]MDB Block mining coefficients reloaded: wood={}, stone={}, gold={}, iron={}, diamond={}, netherite={}",
                    mdbBlockWoodMiningCoefficient, mdbBlockStoneMiningCoefficient, mdbBlockGoldMiningCoefficient, mdbBlockIronMiningCoefficient,
                    mdbBlockDiamondMiningCoefficient, mdbBlockNetheriteMiningCoefficient);
        }
    }
}