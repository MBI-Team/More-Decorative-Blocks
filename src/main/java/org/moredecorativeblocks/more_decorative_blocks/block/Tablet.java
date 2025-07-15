package org.moredecorativeblocks.more_decorative_blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RightClinkBlock;

public class Tablet extends RightClinkBlock {
    public Tablet(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(OPEN)) {
            return Block.box(0, 0, 0, 16, 14, 16);
        } else if (!state.getValue(OPEN)) {
            return Block.box(0, 0, 0, 16, 4, 16);
        }
        return getShape(state, level, pos, context);
    }
}
