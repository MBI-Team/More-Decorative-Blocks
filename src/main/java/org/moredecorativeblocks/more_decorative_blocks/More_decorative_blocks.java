package org.moredecorativeblocks.more_decorative_blocks;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(More_decorative_blocks.MODID)
public class More_decorative_blocks {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "more_decorative_blocks";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "moredecorativeblocks" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "moredecorativeblocks" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "moredecorativeblocks" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "moredecorativeblocks:example_block", combining the namespace and path
    public static final DeferredBlock<Block> MDB_BLOCK = BLOCKS.registerSimpleBlock("mdb_block", BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(2.5f)  // 硬度参数（可选）
                    .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
                    .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
                    .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );
    // Creates a new BlockItem with the id "moredecorativeblocks:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> MDB_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mdb_block", MDB_BLOCK);

    public static final DeferredBlock<Block> WATER_BOOK = BLOCKS.registerSimpleBlock("water_book", BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(1.0f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );
    // Creates a new BlockItem with the id "moredecorativeblocks:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> WATER_BOOK_ITEM = ITEMS.registerSimpleBlockItem("water_book", WATER_BOOK);

    public static final DeferredBlock<Block> FIRE_BOOK = BLOCKS.registerSimpleBlock("fire_book", BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(1.0f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );
    // Creates a new BlockItem with the id "moredecorativeblocks:example_block", combining the namespace and path
    public static final DeferredItem<BlockItem> FIRE_BOOK_ITEM = ITEMS.registerSimpleBlockItem("fire_book", FIRE_BOOK);

    // Creates a creative tab with the id "moredecorativeblocks:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> MDB_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MDB_BLOCK_ITEM.get());
        output.accept(WATER_BOOK_ITEM.get());
        output.accept(FIRE_BOOK_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
    }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public More_decorative_blocks(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Moredecorativeblocks) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));

        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) event.accept(MDB_BLOCK_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
}
