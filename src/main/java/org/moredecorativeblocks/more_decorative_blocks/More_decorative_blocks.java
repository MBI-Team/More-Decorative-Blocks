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

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(org.moredecorativeblocks.more_decorative_blocks.More_decorative_blocks.MODID)
public class More_decorative_blocks {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "more_decorative_blocks";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public static String mod_version = "1.0.0-neoforge-alpha-1";

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
    public More_decorative_blocks(IEventBus modEventBus, ModContainer modContainer) throws InterruptedException {
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
        BlockRegistry.BLOCKS.register(modEventBus);
        Thread.sleep(2000);
        ItemRegistry.ITEMS.register(modEventBus);
        Thread.sleep(2000);
        CreativeModeTabRegistry.CREATIVE_MODE_TABS.register(modEventBus);
        NeoForge.EVENT_BUS.register(TooltipRegistry.class);
    }

    public static void startOutput() {
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("                Copyright More Blocks and Items Team                ");
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("    ...     ..      ..            ....               ...     ..     ");
        LOGGER.info("  x*8888x.:*8888: -“888:      .xH888888Hx.        .=*8888x <”?88h.  ");
        LOGGER.info(" X   48888X `8888H  8888    .H8888888888888:     X>  '8888H> '8888  ");
        LOGGER.info("X8x.  8888X  8888X  !888>   888*“”“?“”*88888X   '88h. `8888   8888  ");
        LOGGER.info("X8888 X8888  88888   “*8%- 'f     d8x.   ^%88k  '8888 '8888    ”88> ");
        LOGGER.info("'*888!X8888> X8888  xH8>   '>    <88888X   '?8   `888 '8888.xH888x. ");
        LOGGER.info(" `?8 `8888  X888X X888>    `:..:`888888>    8>    X” :88*~  `*8888> ");
        LOGGER.info(" -^  '888“  X888  8888>           `”*88     X   ~“   !“`      ”888> ");
        LOGGER.info("   dx '88~x. !88~  8888>      .xHHhx..“      !    .H8888h.      ?88 ");
        LOGGER.info(" .8888Xf.888x:!    X888X.:   X88888888hx. ..!    :”^“88888h.    '!  ");
        LOGGER.info(":“”888“:~”888“     `888*”   !   “*888888888”     ^    ”88888hx.+“   ");
        LOGGER.info("    “~'    ”~        ”“            ^“***”`              ^“**”       ");
        LOGGER.info("version:{}", mod_version);
        LOGGER.info("modid:" + MODID);
        if (mod_version.contains("pre")) {
            LOGGER.warn("Be careful,you are use pre-release,it's not stable.             ");
        } else if (mod_version.contains("stable")) {
            LOGGER.warn("You are use stable release,don't worried for game crash.                       ");
        } else if (mod_version.contains("alpha") || mod_version.contains("beta")) {
            LOGGER.warn("Be careful,you are use test release,maybe the game will crash.               ");
        } else {
            LOGGER.warn("Your release is our recommend.");
        }
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        LOGGER.info("             All right ©More Blocks and Items Team 2025             ");
        LOGGER.info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        startOutput();
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }


    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        startOutput();
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            startOutput();
        }
    }

}
