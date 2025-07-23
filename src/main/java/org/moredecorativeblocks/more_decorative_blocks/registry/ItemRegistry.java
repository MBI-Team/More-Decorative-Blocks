package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.moredecorativeblocks.more_decorative_blocks.item.GlassCup;

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

    public static final DeferredItem<BlockItem> WSL_ITEM = ITEMS.registerSimpleBlockItem("white_solid_line", WHITE_SOLID_LINE);

    public static final DeferredItem<BlockItem> WSLM_ITEM = ITEMS.registerSimpleBlockItem("white_solid_line_middle", WHITE_SOLID_LINE_MIDDLE);

    public static final DeferredItem<BlockItem> WDL_ITEM = ITEMS.registerSimpleBlockItem("white_dashed_line", WHITE_DASHED_LINE);

    public static final DeferredItem<BlockItem> WDLM_ITEM = ITEMS.registerSimpleBlockItem("white_dashed_line_middle", WHITE_DASHED_LINE_MIDDLE);

    public static final DeferredItem<BlockItem> WLTL_ITEM = ITEMS.registerSimpleBlockItem("white_line_turnleft", WHITE_LINE_TURNLEFT);

    public static final DeferredItem<BlockItem> WLTR_ITEM = ITEMS.registerSimpleBlockItem("white_line_turnright", WHITE_LINE_TURNRIGHT);

    public static final DeferredItem<BlockItem> WLMTL_ITEM = ITEMS.registerSimpleBlockItem("white_line_middle_turnleft", WHITE_LINE_MIDDLE_TURNLEFT);

    public static final DeferredItem<BlockItem> WLMTR_ITEM = ITEMS.registerSimpleBlockItem("white_line_middle_turnright", WHITE_LINE_MIDDLE_TURNRIGHT);

    public static final DeferredItem<BlockItem> YSL_ITEM = ITEMS.registerSimpleBlockItem("yellow_solid_line", YELLOW_SOLID_LINE);

    public static final DeferredItem<BlockItem> YSLM_ITEM = ITEMS.registerSimpleBlockItem("yellow_solid_line_middle", YELLOW_SOLID_LINE_MIDDLE);

    public static final DeferredItem<BlockItem> YDL_ITEM = ITEMS.registerSimpleBlockItem("yellow_dashed_line", YELLOW_DASHED_LINE);

    public static final DeferredItem<BlockItem> YDLM_ITEM = ITEMS.registerSimpleBlockItem("yellow_dashed_line_middle", YELLOW_DASHED_LINE_MIDDLE);

    public static final DeferredItem<BlockItem> YLTL_ITEM = ITEMS.registerSimpleBlockItem("yellow_line_turnleft", YELLOW_LINE_TURNLEFT);

    public static final DeferredItem<BlockItem> YLTR_ITEM = ITEMS.registerSimpleBlockItem("yellow_line_turnright", YELLOW_LINE_TURNRIGHT);

    public static final DeferredItem<BlockItem> YLMTL_ITEM = ITEMS.registerSimpleBlockItem("yellow_line_middle_turnleft", YELLOW_LINE_MIDDLE_TURNLEFT);

    public static final DeferredItem<BlockItem> YLMTR_ITEM = ITEMS.registerSimpleBlockItem("yellow_line_middle_turnright", YELLOW_LINE_MIDDLE_TURNRIGHT);
}
