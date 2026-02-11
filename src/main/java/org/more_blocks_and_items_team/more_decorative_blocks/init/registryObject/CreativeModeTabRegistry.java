package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;
import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.ItemRegistry.*;


public class CreativeModeTabRegistry {

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_BUILDING_TAB = CREATIVE_MODE_TABS.register("mdb_building_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_building")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> MDB_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MDB_BLOCK_ITEM.get());
        output.accept(SEAT_STONE_ITEM.get());
        output.accept(ASPHALT_ROAD_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_DECORATIVE_TAB = CREATIVE_MODE_TABS.register("mdb_decorative_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_decorative")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> WATER_BOOK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(WATER_BOOK_ITEM.get());
        output.accept(FIRE_BOOK_ITEM.get());
        output.accept(GLASS_CUP.get());
        output.accept(IRON_CUPBOARD_ITEM.get());
        output.accept(OAK_WOOD_CUPBOARD_ITEM.get());
        output.accept(ACACIA_WOOD_CUPBOARD_ITEM.get());
        output.accept(TABLET_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_REDSTONE_TAB = CREATIVE_MODE_TABS.register("mdb_redstone_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_redstone")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> AC_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(AC_ITEM.get());
        output.accept(CLOSESTOOL_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_MATERIAL_TAB = CREATIVE_MODE_TABS.register("mdb_material_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_material")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> IRON_STICK.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(IRON_STICK.get());
        output.accept(ASPHALT.get());
        output.accept(STOOL.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_ROAD_TAB = CREATIVE_MODE_TABS.register("mdb_road_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_road")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> ASPHALT_ROAD_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(ASPHALT_ROAD_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_ROCK_TAB = CREATIVE_MODE_TABS.register("mdb_rock_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_rock")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> LIMESTONE_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(LIMESTONE_ITEM.get());
        output.accept(SHALE_ITEM.get());
        output.accept(GRAVEL_ITEM.get());
        output.accept(COBBLESTONE_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_BRICK_TAB = CREATIVE_MODE_TABS.register("mdb_brick_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_brick")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> LIGHT_BLUE_TILE_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(LIGHT_BLUE_TILE_ITEM.get());
        output.accept(CRACKED_LIGHT_BLUE_TILE_ITEM.get());
        output.accept(CHIPPED_LIGHT_BLUE_TILE_ITEM.get());
        output.accept(WHITE_TILE_ITEM.get());
        output.accept(CRACKED_WHITE_TILE_ITEM.get());
        output.accept(CHIPPED_WHITE_TILE_ITEM.get());
        output.accept(STEEL_PLATE_RIVETED_ITEM.get());
        output.accept(STEEL_BLOCK_ITEM.get());
    }).build());
}
