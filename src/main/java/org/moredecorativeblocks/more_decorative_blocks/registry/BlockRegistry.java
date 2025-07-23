package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.moredecorativeblocks.more_decorative_blocks.block.AC;
import org.moredecorativeblocks.more_decorative_blocks.block.Cupboard;
import org.moredecorativeblocks.more_decorative_blocks.block.RoadLine;
import org.moredecorativeblocks.more_decorative_blocks.block.Tablet;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.NormalBlock;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RightClinkAndRedstoneBlock;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RightClinkBlock;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
public class BlockRegistry {
    public static final String MODID = "more_decorative_blocks";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> MDB_BLOCK = BLOCKS.register("mdb_block", () -> new NormalBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 8)
            .strength(1.5f, 3.0f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)
    ));

    public static final DeferredBlock<Block> WATER_BOOK = BLOCKS.register("water_book", () -> new RightClinkBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.SLIME_BLOCK)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> FIRE_BOOK = BLOCKS.register("fire_book", () -> new RightClinkBlock(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.SLIME_BLOCK)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .dynamicShape()
    ));

    public static final DeferredBlock<Block> SEAT_STONE = BLOCKS.register("seat_stone", () -> new NormalBlock(BlockBehaviour.Properties.of()
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

    public static final DeferredBlock<Block> WHITE_SOLID_LINE = BLOCKS.register("white_solid_line", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_SOLID_LINE_MIDDLE = BLOCKS.register("white_solid_line_middle", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_DASHED_LINE = BLOCKS.register("white_dashed_line", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_DASHED_LINE_MIDDLE = BLOCKS.register("white_dashed_line_middle", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_LINE_TURNLEFT = BLOCKS.register("white_line_turnleft", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_LINE_TURNRIGHT = BLOCKS.register("white_line_turnright", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_LINE_MIDDLE_TURNLEFT = BLOCKS.register("white_line_middle_turnleft", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> WHITE_LINE_MIDDLE_TURNRIGHT = BLOCKS.register("white_line_middle_turnright", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_SOLID_LINE = BLOCKS.register("yellow_solid_line", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_SOLID_LINE_MIDDLE = BLOCKS.register("yellow_solid_line_middle", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_DASHED_LINE = BLOCKS.register("yellow_dashed_line", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_DASHED_LINE_MIDDLE = BLOCKS.register("yellow_dashed_line_middle", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_LINE_TURNLEFT = BLOCKS.register("yellow_line_turnleft", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_LINE_TURNRIGHT = BLOCKS.register("yellow_line_turnright", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_LINE_MIDDLE_TURNLEFT = BLOCKS.register("yellow_line_middle_turnleft", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> YELLOW_LINE_MIDDLE_TURNRIGHT = BLOCKS.register("yellow_line_middle_turnright", () -> new RoadLine(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.STONE)
            .lightLevel(state -> 3)
            .strength(0.5f, 10f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));
}
