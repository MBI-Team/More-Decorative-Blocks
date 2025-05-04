package org.moredecorativeblocks.more_decorative_blocks.registry;

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
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.bus.api.EventPriority;
import org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry;

import org.slf4j.Logger;

import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.*;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(org.moredecorativeblocks.more_decorative_blocks.registry.CreativeModeTabRegistry.MODID)
public class CreativeModeTabRegistry {
    public static final String MODID = "more_decorative_blocks";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Creates a creative tab with the id "moredecorativeblocks:example_tab" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> MDB_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MDB_BLOCK_ITEM.get());
        output.accept(WATER_BOOK_ITEM.get());
        output.accept(FIRE_BOOK_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
    }).build());

    // The constructor for the mod class is the first code that is run when your mod is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and pass them in automatically.
}
