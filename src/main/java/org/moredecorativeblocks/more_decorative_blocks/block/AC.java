package org.moredecorativeblocks.more_decorative_blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RedstoneBlock;

import static net.minecraft.core.Direction.*;

/**
 * Extends from {@link RedstoneBlock} and extends {@link Block}
 */
public class AC extends RedstoneBlock {
    public AC(Properties prop) {
        super(prop);
    }

    /* Add Shape*/
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(FACING) == SOUTH) {
            return Block.box(0, 0, 0, 32, 16, 16);
        } else if (state.getValue(FACING) == WEST) {
            return Block.box(0, 0, 0, 16, 16, 32);
        } else if (state.getValue(FACING) == NORTH) {
            return Block.box(-16, 0, 0, 16, 16, 16);
        } else if (state.getValue(FACING) == EAST) {
            return Block.box(0, 0, -16, 16, 16, 16);
        }
        return getShape(state, level, pos, context);
    }
}
