package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

public class TooltipRegistry {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onItemTooltip(ItemTooltipEvent event) {
        if (event.getItemStack().getItem() == ItemRegistry.FIRE_BOOK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.fire_book.tooltip"));
        } else if (event.getItemStack().getItem() == ItemRegistry.WATER_BOOK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.water_book.tooltip"));
        } else if (event.getItemStack().getItem() == ItemRegistry.MDB_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.mdb_block.tooltip"));
        } else if (event.getItemStack().getItem() == ItemRegistry.MDB_BLOCK_ITEM.get()) {
            event.getToolTip().add(Component.translatable("tooltip.more_decorative_blocks.seat_stone.tooltip"));
        }
    }
}
