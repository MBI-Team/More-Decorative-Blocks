package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.*;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.basic.NormalBlock;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.basic.RightClinkAndRedstoneBlock;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
public class BlockRegistry {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> MDB_BLOCK = BLOCKS.register("mdb_block", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 8)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)
    ));

    public static final DeferredBlock<Block> WATER_BOOK = BLOCKS.register("water_book", () -> new WaterBook(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.SLIME_BLOCK)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> FIRE_BOOK = BLOCKS.register("fire_book", () -> new FireBook(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.SLIME_BLOCK)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> SEAT_STONE = BLOCKS.register("seat_stone", () -> new SeatStone(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 1)
            .strength(1.5f, 3f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> TABLET = BLOCKS.register("tablet", () -> new Tablet(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.METAL)
            .lightLevel(state -> 10)
            .strength(0.1f, 0.1f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> AC = BLOCKS.register("air_conditioner", () -> new AC(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.METAL)
            .lightLevel(state -> 7)
            .strength(1f, 1f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> IRON_CUPBOARD = BLOCKS.register("iron_cupboard", () -> new Cupboard(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.METAL)
            .lightLevel(state -> 1)
            .strength(2f, 2f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> OAK_WOOD_CUPBOARD = BLOCKS.register("oak_wood_cupboard", () -> new Cupboard(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 1)
            .strength(1f, 1f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> ACACIA_WOOD_CUPBOARD = BLOCKS.register("acacia_wood_cupboard", () -> new Cupboard(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 1)
            .strength(1f, 1f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> CLOSESTOOL = BLOCKS.register("closestool", () -> new RightClinkAndRedstoneBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 1)
            .strength(1.5f, 1f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> ASPHALT_ROAD = BLOCKS.register("asphalt_road", () -> new Block(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(3f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> LIMESTONE = BLOCKS.register("limestone", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> SHALE = BLOCKS.register("shale", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> GRAVEL = BLOCKS.register("gravel", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.GRAVEL)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> COBBLESTONE = BLOCKS.register("cobblestone", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(2.0f, 6.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> LIGHT_BLUE_TILE = BLOCKS.register("light_blue_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> CRACKED_LIGHT_BLUE_TILE = BLOCKS.register("cracked_light_blue_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> CHIPPED_LIGHT_BLUE_TILE = BLOCKS.register("chipped_light_blue_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.COLOR_LIGHT_BLUE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> STEEL_PLATE_RIVETED = BLOCKS.register("steel_plate_riveted", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .lightLevel(state -> 0)
            .strength(5.0f, 6.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> STEEL_BLOCK = BLOCKS.register("steel_block", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.METAL)
            .sound(SoundType.METAL)
            .lightLevel(state -> 0)
            .strength(5.0f, 6.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> WHITE_TILE = BLOCKS.register("white_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> CRACKED_WHITE_TILE = BLOCKS.register("cracked_white_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> CHIPPED_WHITE_TILE = BLOCKS.register("chipped_white_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 0)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
    ));

    // 浅色木地板和地砖
    public static final DeferredBlock<Block> LIGHT_WOOD_PLANK = BLOCKS.register("light_wood_plank", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 0)
            .strength(2.0f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> LIGHT_WOOD_TILE = BLOCKS.register("light_wood_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 0)
            .strength(2.0f, 3.0f)  // 硬度参数（可选）
    ));

    // 深色木地板和地砖
    public static final DeferredBlock<Block> DARK_WOOD_PLANK = BLOCKS.register("dark_wood_plank", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 0)
            .strength(2.0f, 3.0f)  // 硬度参数（可选）
    ));

    public static final DeferredBlock<Block> DARK_WOOD_TILE = BLOCKS.register("dark_wood_tile", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.WOOD)
            .sound(SoundType.WOOD)
            .lightLevel(state -> 0)
            .strength(2.0f, 3.0f)  // 硬度参数（可选）
    ));
}
