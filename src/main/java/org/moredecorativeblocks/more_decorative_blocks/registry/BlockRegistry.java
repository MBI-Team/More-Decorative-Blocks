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
@Mod(org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.MODID)
public class BlockRegistry {
    public static final String MODID = "more_decorative_blocks";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    // Creates a new Block with the id "moredecorativeblocks:example_block", combining the namespace and path
    public static final DeferredBlock<Block> MDB_BLOCK = BLOCKS.registerSimpleBlock("mdb_block", BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(2.5f,10.0f)  // 硬度参数（可选）
            .destroyTime(1.5f)
            .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );

    public static final DeferredBlock<Block> WATER_BOOK = BLOCKS.registerSimpleBlock("water_book", BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .destroyTime(1.0f)
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );

    public static final DeferredBlock<Block> FIRE_BOOK = BLOCKS.registerSimpleBlock("fire_book", BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .destroyTime(1.0f)
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)  // 设置为不透明方块
    );

}
