package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.registries.DeferredItem;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.ItemRegistry.*;
import static org.more_blocks_and_items_team.more_decorative_blocks.tools.LOGGER.LOGGER;

public class TooltipRegistry {

    public static void RegistryBlockItemTooltip(ItemTooltipEvent event, DeferredItem<BlockItem> target, String lang) {
        if (event.getItemStack().getItem() == target.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks." + lang + ".tooltip"));
            LOGGER.info("[Mod Init][Tooltip]Added tooltip lang {} for {}", lang, target.getId());
        }
    }

    public static void RegistryItemTooltip(ItemTooltipEvent event, DeferredItem<Item> target, String lang) {
        if (event.getItemStack().getItem() == target.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks." + lang + ".tooltip"));
            LOGGER.info("[Mod Init][Tooltip]Added tooltip lang {} for {}", lang, target.getId());
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onItemTooltip(ItemTooltipEvent event) {
        RegistryBlockItemTooltip(event, FIRE_BOOK_ITEM, "fire_book");
        RegistryBlockItemTooltip(event, WATER_BOOK_ITEM, "water_book");
        RegistryBlockItemTooltip(event, MDB_BLOCK_ITEM, "mdb_block");
        RegistryBlockItemTooltip(event, SEAT_STONE_ITEM, "seat_stone");
        RegistryBlockItemTooltip(event, TABLET_ITEM, "tablet");
        RegistryBlockItemTooltip(event, AC_ITEM, "air_conditioner");
        RegistryBlockItemTooltip(event, IRON_CUPBOARD_ITEM, "iron_cupboard");
        RegistryBlockItemTooltip(event, OAK_WOOD_CUPBOARD_ITEM, "oak_wood_cupboard");
        RegistryBlockItemTooltip(event, CLOSESTOOL_ITEM, "closestool");
        RegistryItemTooltip(event, GLASS_CUP, "glass_cup");
        RegistryItemTooltip(event, STOOL, "stool");
    }
}
