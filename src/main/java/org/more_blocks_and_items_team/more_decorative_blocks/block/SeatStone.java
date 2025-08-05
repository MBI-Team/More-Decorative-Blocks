package org.more_blocks_and_items_team.more_decorative_blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.block.basic.NormalBlock;

import static org.more_blocks_and_items_team.more_decorative_blocks.Config.heightOfSeatStone;

public class SeatStone extends NormalBlock {
    // 默认高度值，以防配置未正确加载
    private static final int DEFAULT_HEIGHT = 24;

    public SeatStone(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        // 确保高度值在有效范围内（1-16），并提供默认值保护
        int actualHeight = (heightOfSeatStone >= 1 && heightOfSeatStone <= 32) ?
                heightOfSeatStone : DEFAULT_HEIGHT;
        return Block.box(0, 0, 0, 16, actualHeight, 16);
    }
}