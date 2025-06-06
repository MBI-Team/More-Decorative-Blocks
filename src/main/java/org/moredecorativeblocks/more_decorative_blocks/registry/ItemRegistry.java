package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.*;

public class ItemRegistry {
    public static final String MODID = "more_decorative_blocks";

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BlockItem> MDB_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mdb_block", MDB_BLOCK);

    public static final DeferredItem<BlockItem> WATER_BOOK_ITEM = ITEMS.registerSimpleBlockItem("water_book", WATER_BOOK);

    public static final DeferredItem<BlockItem> FIRE_BOOK_ITEM = ITEMS.registerSimpleBlockItem("fire_book", FIRE_BOOK);

    public static final DeferredItem<BlockItem> SEAT_STONE_ITEM = ITEMS.registerSimpleBlockItem("seat_stone", SEAT_STONE);

    public static final DeferredItem<BlockItem> TABLET_ITEM = ITEMS.registerSimpleBlockItem("tablet", TABLET);

    public static final DeferredItem<BlockItem> AC_ITEM = ITEMS.registerSimpleBlockItem("air_conditioner", AC);

    public static final DeferredItem<BlockItem> IRON_CUPBOARD_ITEM = ITEMS.registerSimpleBlockItem("iron_cupboard", IRON_CUPBOARD);

    public static final DeferredItem<BlockItem> OAK_WOOD_CUPBOARD_ITEM = ITEMS.registerSimpleBlockItem("wood_cupboard", OAK_WOOD_CUPBOARD);

    public static final DeferredItem<Item> GLASS_CUP = ITEMS.registerItem("glass_cup", Item::new, new Item.Properties());

    public static final DeferredItem<BlockItem> CLOSESTOOL_ITEM = ITEMS.registerSimpleBlockItem("closestool", CLOSESTOOL);

    public static final DeferredItem<Item> IRON_STICK = ITEMS.registerItem("iron_stick", Item::new, new Item.Properties());
}
