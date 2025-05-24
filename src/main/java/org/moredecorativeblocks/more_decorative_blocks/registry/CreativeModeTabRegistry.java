package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.*;


public class CreativeModeTabRegistry {
    public static final String MODID = "more_decorative_blocks";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_BUILDING_TAB = CREATIVE_MODE_TABS.register("mdb_building_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_building")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> MDB_BLOCK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(MDB_BLOCK_ITEM.get());
        output.accept(SEAT_STONE_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_DECORATIVE_TAB = CREATIVE_MODE_TABS.register("mdb_decorative_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_decorative")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> WATER_BOOK_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(WATER_BOOK_ITEM.get());
        output.accept(FIRE_BOOK_ITEM.get());
    }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MDB_REDSTONE_TAB = CREATIVE_MODE_TABS.register("mdb_redstone_tab", () -> CreativeModeTab.builder().title(Component.translatable("itemGroup.more_decorative_blocks_redstone")).withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> TABLET_ITEM.get().getDefaultInstance()).displayItems((parameters, output) -> {
        output.accept(TABLET_ITEM.get());
        output.accept(AC_ITEM.get());
    }).build());
}
