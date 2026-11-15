package org.more_blocks_and_items_team.more_decorative_blocks;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.more_blocks_and_items_team.more_decorative_blocks.events.client.ClientCommands;
import org.more_blocks_and_items_team.more_decorative_blocks.events.client.ConfigScreen;
import org.more_blocks_and_items_team.more_decorative_blocks.events.common.commandExecutes;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.*;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionComparator;
import org.slf4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;
import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(MODID)
public class More_decorative_blocks {
    public static final Logger LOGGER = LogUtils.getLogger();

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public More_decorative_blocks(IEventBus modEventBus, ModContainer modContainer) {
        LOGGER.info("[Mod Init]Starting init {}...", MODID);
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register our mod's ModConfigSpec
        LOGGER.info("[Mod Init]Load configs...");
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // 配置界面是纯客户端功能，在专用服务器上不能触碰 IConfigScreenFactory / ConfigScreen，
        // 否则会抛出 NoClassDefFoundError 导致服务端无法启动。
        if (FMLEnvironment.dist == Dist.CLIENT) {
            LOGGER.info("[Mod Init]Load config screen");
            modContainer.registerExtensionPoint(IConfigScreenFactory.class, (container, lastScreen) ->
                    new ConfigScreen(lastScreen, Config.SPEC));
        }

        LOGGER.info("[Mod Init]Registering blocks...");
        BlockRegistry.BLOCKS.register(modEventBus);
        LOGGER.info("[Mod Init]Registering items...");
        ItemRegistry.ITEMS.register(modEventBus);
        LOGGER.info("[Mod Init]Registering entities...");
        EntityRegistry.ENTITY_TYPES.register(modEventBus);
        LOGGER.info("[Mod Init]Registering creative mode tabs...");
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        LOGGER.info("[Mod Init]Registering tooltips...");
        NeoForge.EVENT_BUS.register(TooltipRegistry.class);

        // ClientVersionCheckHandler 已经通过 @EventBusSubscriber(modid = MODID, value = Dist.CLIENT) 自动注册，
        // 这里重复注册会让登录事件被处理两次（重复弹窗），因此不再手动注册。

        // Register command event
        NeoForge.EVENT_BUS.register(this);
    }

    public static void startOutput() {
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("                Copyright More Blocks and Items Team                ");
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀                       ");
        LOGGER.info("                █    █    █ █▄▄▄▄▄▄▄▀    █                          ");
        LOGGER.info("                █    █    █ █       █    █                          ");
        LOGGER.info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄                       ");
        LOGGER.info("                 modid:" + MODID);
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("             All right ©More Blocks and Items Team 2025             ");
        if (mod_version.contains("pre") || mod_version.contains("alpha") || mod_version.contains("beta") || mod_version.contains("preview") || mod_version.contains("nightly")) {
            LOGGER.warn("Be careful,you are using the  test version,it's not stable.");
        } else if (mod_version.contains("stable") || mod_version.contains("release") || mod_version.contains("final")) {
            LOGGER.warn("You are using the stable release,don't worried for game crash.");
        } else if (mod_version.contains("dev") || mod_version.contains("snapshot")) {
            LOGGER.warn("You are using the internal testing version.");
        } else if (mod_version.contains("rc")) {
            LOGGER.warn("You are using the release candidate version.");
        }
    }

    /**
     * 在后台线程检查 GitHub 上的最新版本，仅打印日志（界面提示由 {@code ClientVersionCheckHandler} 负责）。
     * <p>
     * 该方法不会再向调用线程抛出异常：原实现里 {@link URISyntaxException} 被包装成 RuntimeException
     * 直接抛在无名线程中，会打印一大段未捕获异常堆栈。
     */
    public static void checkVersion() {
        if (!Config.enableVersionChecker) {
            LOGGER.info("[VersionCheck]Version checker is disabled in config, skipping.");
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                String latestVersion = VersionCheckUtils.getLatestVersion();
                LOGGER.info("Current version: {}, The latest version in Github: {}", mod_version, latestVersion);

                if (VersionComparator.isNewer(mod_version, latestVersion)) {
                    LOGGER.warn("There is a new version available: {}!", latestVersion);
                    LOGGER.warn("Please update from GitHub: https://github.com/MBI-Team/More-Decorative-Blocks/releases");
                } else {
                    LOGGER.info("You are using the latest version.");
                }
            } catch (IOException | URISyntaxException e) {
                LOGGER.warn("Failed to check for updates: {}", e.getMessage());
            } catch (RuntimeException e) {
                LOGGER.warn("Unexpected error while checking for updates: {}", e.getMessage());
            }
        }, "MDB-VersionCheck");
        thread.setDaemon(true);
        thread.start();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("[Mod Init]Common setup completed");
        startOutput();
        // 版本检查由客户端登录时触发（ClientVersionCheckHandler），这里不再重复请求 GitHub。
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        startOutput();
    }

    // Register commands
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();

        // 版本信息命令
        dispatcher.register(Commands.literal("mdbversion")
                .requires(source -> source.hasPermission(2))
                .executes(commandExecutes::MDBVersionCMD)
        );

        // 调试用的界面命令只能在客户端注册：它们会直接操作 Minecraft 客户端实例，
        // 在专用服务器上引用这些类会直接崩溃。
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientCommands.register(dispatcher);
        }
    }
}
