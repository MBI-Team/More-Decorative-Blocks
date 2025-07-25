package org.moredecorativeblocks.more_decorative_blocks;

import com.mojang.logging.LogUtils;
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
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry;
import org.moredecorativeblocks.more_decorative_blocks.registry.CreativeModeTabRegistry;
import org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry;
import org.moredecorativeblocks.more_decorative_blocks.registry.TooltipRegistry;
import org.slf4j.Logger;

import java.io.IOException;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(org.moredecorativeblocks.more_decorative_blocks.More_decorative_blocks.MODID)
public class More_decorative_blocks {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "more_decorative_blocks";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static String mod_version = "${mod_version}";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public More_decorative_blocks(IEventBus modEventBus, ModContainer modContainer) throws InterruptedException {
        LOGGER.info("[Init]Starting init {}...", MODID);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (Moredecorativeblocks) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        LOGGER.info("[Init]Registering blocks...");
        BlockRegistry.BLOCKS.register(modEventBus);
        Thread.sleep(5000);
        LOGGER.info("[Init]Registering items...");
        ItemRegistry.ITEMS.register(modEventBus);
        Thread.sleep(5000);
        LOGGER.info("[Init]Registering creative mode tabs...");
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        LOGGER.info("[Init]Registering tooltips...");
        NeoForge.EVENT_BUS.register(TooltipRegistry.class);
    }

    public static void startOutput() {
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("                Copyright More Blocks and Items Team                ");
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        LOGGER.info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        LOGGER.info("                █    █    █ █       █    █");
        LOGGER.info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        LOGGER.info("modid:" + MODID);
        if (mod_version.contains("pre") || mod_version.contains("alpha") || mod_version.contains("beta") || mod_version.contains("preview") || mod_version.contains("nightly")) {
            LOGGER.warn("Be careful,you are use test version,it's not stable.");
        } else if (mod_version.contains("stable") || mod_version.contains("release") || mod_version.contains("final")) {
            LOGGER.warn("The stable release,don't worried for game crash.");
        } else if (mod_version.contains("dev") || mod_version.contains("snapshot")) {
            LOGGER.warn("The internal testing version.");
        } else if (mod_version.contains("rc")) {
            LOGGER.warn("The release candidate version.");
        }
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("             All right ©More Blocks and Items Team 2025             ");
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }

    public static void checkVersion() {
        new Thread(() -> {
            try {
                String latestVersion = VersionChecker.getLatestVersion();
                LOGGER.info("Current version: {}, The latest version in Github: {}", mod_version, latestVersion);
                LOGGER.info("Your major version: {}, The latest major version in Github: {}", mod_version.charAt(0), latestVersion.charAt(0));
                LOGGER.info("Your minor version: {}, The latest minor version in Github: {}", mod_version.charAt(2), latestVersion.charAt(2));
                LOGGER.info("Your patch version: {}, The latest patch version in Github: {}", mod_version.charAt(4), latestVersion.charAt(4));

                if (!mod_version.contains(latestVersion)) {
                    if (mod_version.charAt(0) < latestVersion.charAt(0)) {
                        LOGGER.warn("There is a new version available: {}!", latestVersion);
                        LOGGER.warn("Please update from GitHub: https://github.com/MBI-Team/More-Decorative-Blocks/releases");
                    } else if (mod_version.charAt(0) == latestVersion.charAt(0)) {
                        if (mod_version.charAt(2) < latestVersion.charAt(2)) {
                            LOGGER.warn("There is a new version available: {}!", latestVersion);
                            LOGGER.warn("Please update from GitHub: https://github.com/MBI-Team/More-Decorative-Blocks/releases");
                        } else if (mod_version.charAt(2) == latestVersion.charAt(2)) {
                            if (mod_version.charAt(4) < latestVersion.charAt(4)) {
                                LOGGER.warn("There is a new version available: {}!", latestVersion);
                                LOGGER.warn("Please update from GitHub: https://github.com/MBI-Team/More-Decorative-Blocks/releases");
                            } else {
                                LOGGER.info("You are using the latest version.");
                            }
                        } else {
                            LOGGER.info("You are using the latest version.");
                        }
                    } else {
                        LOGGER.info("You are using the latest version.");
                    }
                } else {
                    LOGGER.info("You are using the latest version.");
                }
            } catch (IOException e) {
                LOGGER.warn("Failed to check for updates: {}", e.getMessage());
            }
        }).start();
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        startOutput();
        checkVersion();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            startOutput();
            checkVersion();
        }
    }

}
