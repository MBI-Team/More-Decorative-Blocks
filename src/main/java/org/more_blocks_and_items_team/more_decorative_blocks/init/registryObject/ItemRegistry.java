package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.item.GlassCup;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry.*;
import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class ItemRegistry {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

    public static final DeferredItem<BlockItem> MDB_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("mdb_block", MDB_BLOCK);

    public static final DeferredItem<BlockItem> WATER_BOOK_ITEM = ITEMS.registerSimpleBlockItem("water_book", WATER_BOOK);

    public static final DeferredItem<BlockItem> FIRE_BOOK_ITEM = ITEMS.registerSimpleBlockItem("fire_book", FIRE_BOOK);

    public static final DeferredItem<BlockItem> SEAT_STONE_ITEM = ITEMS.registerSimpleBlockItem("seat_stone", SEAT_STONE);

    public static final DeferredItem<BlockItem> TABLET_ITEM = ITEMS.registerSimpleBlockItem("tablet", TABLET);

    public static final DeferredItem<BlockItem> AC_ITEM = ITEMS.registerSimpleBlockItem("air_conditioner", AC);

    public static final DeferredItem<BlockItem> IRON_CUPBOARD_ITEM = ITEMS.registerSimpleBlockItem("iron_cupboard", IRON_CUPBOARD);

    public static final DeferredItem<BlockItem> OAK_WOOD_CUPBOARD_ITEM = ITEMS.registerSimpleBlockItem("oak_wood_cupboard", OAK_WOOD_CUPBOARD);

    public static final DeferredItem<BlockItem> ACACIA_WOOD_CUPBOARD_ITEM = ITEMS.registerSimpleBlockItem("acacia_wood_cupboard", ACACIA_WOOD_CUPBOARD);

    public static final DeferredItem<Item> GLASS_CUP = ITEMS.registerItem("glass_cup", GlassCup::new, new GlassCup.Properties()
            .setNoRepair()
            .stacksTo(16)
    );

    public static final DeferredItem<Item> STOOL = ITEMS.registerItem("stool", Item::new, new Item.Properties()
            .setNoRepair()
            .food(new FoodProperties.Builder()
                    .nutrition(1)
                    .effect(() -> new MobEffectInstance(MobEffects.HUNGER, 300, 5, true, true), 1f)
                    .alwaysEdible()
                    .saturationModifier(1f)
                    .build())
    );

    public static final DeferredItem<BlockItem> CLOSESTOOL_ITEM = ITEMS.registerSimpleBlockItem("closestool", CLOSESTOOL);

    public static final DeferredItem<Item> IRON_STICK = ITEMS.registerSimpleItem("iron_stick", new Item.Properties());

    public static final DeferredItem<BlockItem> ASPHALT_ROAD_ITEM = ITEMS.registerSimpleBlockItem("asphalt_road", ASPHALT_ROAD);

    public static final DeferredItem<BlockItem> LIMESTONE_ITEM = ITEMS.registerSimpleBlockItem("limestone", LIMESTONE);

    public static final DeferredItem<BlockItem> SHALE_ITEM = ITEMS.registerSimpleBlockItem("shale", SHALE);

    public static final DeferredItem<BlockItem> GRAVEL_ITEM = ITEMS.registerSimpleBlockItem("gravel", GRAVEL);

    public static final DeferredItem<BlockItem> COBBLESTONE_ITEM = ITEMS.registerSimpleBlockItem("cobblestone", COBBLESTONE);

    public static final DeferredItem<BlockItem> LIGHT_BLUE_TILE_ITEM = ITEMS.registerSimpleBlockItem("light_blue_tile", LIGHT_BLUE_TILE);

    public static final DeferredItem<BlockItem> CRACKED_LIGHT_BLUE_TILE_ITEM = ITEMS.registerSimpleBlockItem("cracked_light_blue_tile", CRACKED_LIGHT_BLUE_TILE);

    public static final DeferredItem<BlockItem> CHIPPED_LIGHT_BLUE_TILE_ITEM = ITEMS.registerSimpleBlockItem("chipped_light_blue_tile", CHIPPED_LIGHT_BLUE_TILE);

    public static final DeferredItem<BlockItem> STEEL_PLATE_RIVETED_ITEM = ITEMS.registerSimpleBlockItem("steel_plate_riveted", STEEL_PLATE_RIVETED);

    public static final DeferredItem<BlockItem> STEEL_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("steel_block", STEEL_BLOCK);

    public static final DeferredItem<BlockItem> WHITE_TILE_ITEM = ITEMS.registerSimpleBlockItem("white_tile", WHITE_TILE);

    public static final DeferredItem<BlockItem> CRACKED_WHITE_TILE_ITEM = ITEMS.registerSimpleBlockItem("cracked_white_tile", CRACKED_WHITE_TILE);

    public static final DeferredItem<BlockItem> CHIPPED_WHITE_TILE_ITEM = ITEMS.registerSimpleBlockItem("chipped_white_tile", CHIPPED_WHITE_TILE);

    // 浅色木地板和地砖物品
    public static final DeferredItem<BlockItem> LIGHT_WOOD_PLANK_ITEM = ITEMS.registerSimpleBlockItem("light_wood_plank", LIGHT_WOOD_PLANK);
    public static final DeferredItem<BlockItem> LIGHT_WOOD_TILE_ITEM = ITEMS.registerSimpleBlockItem("light_wood_tile", LIGHT_WOOD_TILE);

    // 深色木地板和地砖物品
    public static final DeferredItem<BlockItem> DARK_WOOD_PLANK_ITEM = ITEMS.registerSimpleBlockItem("dark_wood_plank", DARK_WOOD_PLANK);
    public static final DeferredItem<BlockItem> DARK_WOOD_TILE_ITEM = ITEMS.registerSimpleBlockItem("dark_wood_tile", DARK_WOOD_TILE);

    public static final DeferredItem<Item> ASPHALT = ITEMS.registerItem("asphalt", Item::new, new Item.Properties()
            .setNoRepair()
            .stacksTo(64)
    );
}
