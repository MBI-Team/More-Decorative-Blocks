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

public class WaterBook extends RightClinkBlock {
    public WaterBook(Properties prop) {
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
        if (state.getValue(OPEN)) {
            // Open book - rotate collision box based on facing
            switch (facing) {
                case NORTH:
                case SOUTH:
                    return Block.box(0, 6.83765f, 1, 17, 8.83765f, 15);
                case EAST:
                case WEST:
                    return Block.box(1, 6.83765f, 0, 15, 8.83765f, 17);
                default:
                    return Block.box(0, 6.83765f, 1, 17, 8.83765f, 15);
            }
        } else {
            // Closed book - rotate collision box based on facing
            switch (facing) {
                case NORTH:
                case SOUTH:
                    return Block.box(7, 5.74605f, 1, 9, 14.74605f, 15);
                case EAST:
                case WEST:
                    return Block.box(1, 5.74605f, 7, 15, 14.74605f, 9);
                default:
                    return Block.box(7, 5.74605f, 1, 9, 14.74605f, 15);
            }
        }
    }
}