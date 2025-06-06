package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.*;

public class TooltipRegistry {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == FIRE_BOOK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.fire_book.tooltip"));
        } else if (event.getItemStack().getItem() == WATER_BOOK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.water_book.tooltip"));
        } else if (event.getItemStack().getItem() == MDB_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.mdb_block.tooltip"));
        } else if (event.getItemStack().getItem() == SEAT_STONE_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.seat_stone.tooltip"));
        } else if (event.getItemStack().getItem() == TABLET_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.tablet.tooltip"));
        } else if (event.getItemStack().getItem() == AC_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.air_conditioner.tooltip"));
        } else if (event.getItemStack().getItem() == IRON_CUPBOARD_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.iron_cupboard.tooltip"));
        } else if (event.getItemStack().getItem() == OAK_WOOD_CUPBOARD_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.oak_wood_cupboard.tooltip"));
        } else if (event.getItemStack().getItem() == GLASS_CUP.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.glass_cup.tooltip"));
        } else if (event.getItemStack().getItem() == CLOSESTOOL_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.closestool.tooltip"));
        }
    }
}
