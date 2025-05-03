package org.moredecorativeblocks.more_decorative_blocks;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ObjectHolder;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import static net.minecraft.world.item.Items.WOODEN_PICKAXE;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(More_decorative_blocks.MODID)
public class More_decorative_blocks {

    public static final Codec<More_decorative_blocks> CODEC = Codec.unit(More_decorative_blocks::new);

    // Define mod id in a common place for everything to reference
    public static final String MODID = "more_decorative_blocks";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    // Create a Deferred Register to hold Blocks which will all be registered under the "more_decorative_blocks" namespace
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "more_decorative_blocks" namespace
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "more_decorative_blocks" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a new Block with the id "more_decorative_blocks:MDB_block", combining the namespace and path
    public static final RegistryObject<Block> MDB_BLOCK = BLOCKS.register("mdb_block",  () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(5.0f)  // 硬度参数（可选）
                    .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
                    .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
                    .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
            )
    );
    // Creates a new BlockItem with the id "more_decorative_blocks:MDB_block", combining the namespace and path
    public static final RegistryObject<Item> MDB_BLOCK_ITEM = ITEMS.register("mdb_block", () -> new BlockItem(MDB_BLOCK.get(), new Item.Properties()));

    public static final RegistryObject<Block> WATER_BOOK = BLOCKS.register("water_book",  () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0f)  // 硬度参数（可选）
                    .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
                    .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
                    .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
            )
    );

    public static final RegistryObject<Item> WATER_BOOK_ITEM = ITEMS.register("water_book", () -> new BlockItem(WATER_BOOK.get(), new Item.Properties()));

    public static final RegistryObject<Block> FIRE_BOOK = BLOCKS.register("fire_book",  () ->
            new Block(BlockBehaviour.Properties.of()
                    .mapColor(MapColor.STONE)
                    .strength(3.0f)  // 硬度参数（可选）
                    .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
                    .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
                    .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
            )
    );

    public static final RegistryObject<Item> FIRE_BOOK_ITEM = ITEMS.register("fire_book", () -> new BlockItem(FIRE_BOOK.get(), new Item.Properties()));
    // Creates a creative tab with the id "more_decorative_blocks:mdb_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> MDB_TAB = CREATIVE_MODE_TABS.register("mdb_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.moredecorativeblocks")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> MDB_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MDB_BLOCK_ITEM.get());
        output.accept(WATER_BOOK_ITEM.get());
        output.accept(FIRE_BOOK_ITEM.get());// Add the example item to the tab. For your own tabs, this method is preferred over the event
    }).build());

        public More_decorative_blocks() {
            IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

            // Register the commonSetup method for modloading
            modEventBus.addListener(this::commonSetup);

            // Register the Deferred Register to the mod event bus so blocks get registered
            BLOCKS.register(modEventBus);
            // Register the Deferred Register to the mod event bus so items get registered
            ITEMS.register(modEventBus);
            // Register the Deferred Register to the mod event bus so tabs get registered
            CREATIVE_MODE_TABS.register(modEventBus);

            // Register ourselves for server and other game events we are interested in
            MinecraftForge.EVENT_BUS.register(this);

            // Register the item to a creative tab
            modEventBus.addListener(this::addCreative);

            // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        }

        private void commonSetup(final FMLCommonSetupEvent event) {
            // Some common setup code
            LOGGER.info("HELLO FROM COMMON SETUP");
            LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

            if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

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
        @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
        public static class ClientModEvents {

            @SubscribeEvent
            public static void onClientSetup(FMLClientSetupEvent event) {
                // Some client setup code
                if (FMLEnvironment.dist  == Dist.CLIENT) {
                    LOGGER.info("MINECRAFT  NAME >> {}", Minecraft.getInstance().getUser().getName());
                }
                LOGGER.info("HELLO FROM CLIENT SETUP");
                LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
            }
        }

    @Mod.EventBusSubscriber(modid = More_decorative_blocks.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
    public static class TooltipHandler {
        @SubscribeEvent
        public static void onItemTooltip(ItemTooltipEvent event) {
            if (event.getItemStack().getItem() == More_decorative_blocks.FIRE_BOOK_ITEM.get()) {
                event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.fire_book.tooltip"));
            } else if (event.getItemStack().getItem() == More_decorative_blocks.WATER_BOOK_ITEM.get()) {
                event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.water_book.tooltip"));
            } else if (event.getItemStack().getItem() == More_decorative_blocks.MDB_BLOCK_ITEM.get()) {
                event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.mdb_block.tooltip"));
            }
        }
    }
}
