package org.more_blocks_and_items_team.more_decorative_blocks;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.more_blocks_and_items_team.more_decorative_blocks.events.client.ConfigScreen;
import org.more_blocks_and_items_team.more_decorative_blocks.events.common.commandExecutes;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.CreativeModeTabRegistry;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.ItemRegistry;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.TooltipRegistry;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionChecker;

import java.io.IOException;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;
import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;
import static org.more_blocks_and_items_team.more_decorative_blocks.utils.LOGGER.LOGGER;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MODID)
public class More_decorative_blocks {


    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public More_decorative_blocks(IEventBus modEventBus, ModContainer modContainer) throws InterruptedException {
        LOGGER.info("[Mod Init]Starting init {}...", MODID);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // Register our mod's ModConfigSpec
        LOGGER.info("[Mod Init]Load configs...");
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // Register config screen
        LOGGER.info("[Mod Init]Load config screen");
        modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, lastScreen) ->
                new ConfigScreen(lastScreen, Config.SPEC));

        LOGGER.info("[Mod Init]Registering blocks...");
        BlockRegistry.BLOCKS.register(modEventBus);
        Thread.sleep(5000);
        LOGGER.info("[Mod Init]Registering items...");
        ItemRegistry.ITEMS.register(modEventBus);
        Thread.sleep(5000);
        LOGGER.info("[Mod Init]Registering creative mode tabs...");
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        LOGGER.info("[Mod Init]Registering tooltips...");
        NeoForge.EVENT_BUS.register(TooltipRegistry.class);

        // Register command event
        NeoForge.EVENT_BUS.register(this);
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
    }

    public static void checkVersion() {
        new Thread(() -> {
            try {
                String latestVersion = VersionChecker.getLatestVersion();
                LOGGER.info("Current version: {}, The latest version in Github: {}", mod_version, latestVersion);

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
                        LOGGER.info("You are using the test version.");
                    }
                } else {
                    LOGGER.info("You are using the latest version.");
                }
            } catch (IOException e) {
                LOGGER.warn("Failed to check for updates: {}", e.getMessage());
            }
        }).start();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("[Mod Init]Common setup completed");
        startOutput();
        checkVersion();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {

    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        startOutput();
        checkVersion();
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        LOGGER.info("[Event Test]Player right clicked block at {}", event.getPos().toShortString());
    }

    // Register commands
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("mdbversion")
                .requires(source -> source.hasPermission(2))
                .executes(commandExecutes::MDBVersionCMD)
        );
    }
}