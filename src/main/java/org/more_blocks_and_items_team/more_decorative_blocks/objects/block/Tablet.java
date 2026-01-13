package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.basic.RightClinkBlock;

public class Tablet extends RightClinkBlock {
    public Tablet(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return getCollisionShape(state, level, pos, context);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                               @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Direction facing = state.getValue(FACING);
        // Tablet collision box depends on open/closed state and facing direction
        if (state.getValue(OPEN)) {
            // Open tablet - larger collision box for the screen
            switch (facing) {
                case NORTH:
                case SOUTH:
                    return Block.box(0, 0, 0, 14, 1, 16);
                case EAST:
                case WEST:
                    return Block.box(0, 0, 0, 16, 1, 14);
                default:
                    return Block.box(0, 0, 0, 14, 1, 16);
            }
        } else {
            // Closed tablet - smaller collision box
            switch (facing) {
                case NORTH:
                case SOUTH:
                    return Block.box(1, 0, 1, 13, 1, 15);
                case EAST:
                case WEST:
                    return Block.box(1, 0, 1, 15, 1, 13);
                default:
                    return Block.box(1, 0, 1, 13, 1, 15);
            }
        }
    }
}
