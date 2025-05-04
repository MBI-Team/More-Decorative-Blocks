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

import org.slf4j.Logger;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(org.moredecorativeblocks.more_decorative_blocks.registry.TooltipRegistry.MODID)
public class TooltipRegistry {
    public static final String MODID = "more_decorative_blocks";

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem()  == org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.FIRE_BOOK_ITEM.get())  {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.fire_book.tooltip"));
        } else if (event.getItemStack().getItem()  == org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.WATER_BOOK_ITEM.get())  {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.water_book.tooltip"));
        } else if (event.getItemStack().getItem()  == org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.MDB_BLOCK_ITEM.get())  {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.mdb_block.tooltip"));
        }
    }
}
