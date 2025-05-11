package org.moredecorativeblocks.more_decorative_blocks.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.moredecorativeblocks.more_decorative_blocks.block.BlockFacing;


// The value here should match an entry in the META-INF/neoforge.mods.toml file
public class BlockRegistry {
    public static final String MODID = "more_decorative_blocks";
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final DeferredBlock<Block> MDB_BLOCK = BLOCKS.register("mdb_block", () -> new BlockFacing(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .sound(SoundType.NETHERITE_BLOCK) // 新型金属声效
            .lightLevel(state -> 8)
            .strength(2.5f,10.0f)  // 硬度参数（可选）
            .destroyTime(1.5f)
            .requiresCorrectToolForDrops()  // 需要正确工具采集（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
            .isRedstoneConductor((state, level, pos) -> true)
    ));

    public static final DeferredBlock<Block> WATER_BOOK = BLOCKS.register("water_book", () -> new BlockFacing(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

    public static final DeferredBlock<Block> FIRE_BOOK = BLOCKS.register("fire_book", () -> new BlockFacing(BlockBehaviour.Properties.of()
            .mapColor(MapColor.STONE)
            .lightLevel(state -> 8)
            .strength(1.0f,0.5f)  // 硬度参数（可选）
            .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
    ));

}
