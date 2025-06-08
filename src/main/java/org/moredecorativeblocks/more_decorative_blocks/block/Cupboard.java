package org.moredecorativeblocks.more_decorative_blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.NormalBlock;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RightClinkBlock;

import static net.minecraft.core.Direction.*;
import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.GLASS_CUP;

/**
 * Extends from {@link RightClinkBlock} and it extends {@link Block}
 */
public class Cupboard extends NormalBlock {
    static int gcn = 0;
    public static final IntegerProperty GLASS_CUP_NUM = IntegerProperty.create("gcn", gcn, 12);
    public Cupboard(Properties prop) {
        super(prop);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(GLASS_CUP_NUM, 0)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLASS_CUP_NUM);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        ItemStack mainHandItem = player.getMainHandItem();
        if (!level.isClientSide) {
            if (mainHandItem.is(GLASS_CUP)) {
                if (gcn < 12) {
                    gcn += 1;
                    BlockState newState = state.setValue(GLASS_CUP_NUM, gcn);
                    level.setBlock(pos, newState, 1, 2);
                } else {
                    gcn = 0;
                    BlockState newState = state.setValue(GLASS_CUP_NUM, gcn);
                    level.setBlock(pos, newState, 1, 2);
                }
            }
        }
        return ItemInteractionResult.SUCCESS;
    }

    /* Add Shape*/
    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        if (state.getValue(FACING) == SOUTH) {
            return Block.box(0, 0, 0, 32, 28, 16);
        } else if (state.getValue(FACING) == WEST) {
            return Block.box(0, 0, 0, 16, 28, 32);
        } else if (state.getValue(FACING) == NORTH) {
            return Block.box(-16, 0, 0, 16, 28, 16);
        } else if (state.getValue(FACING) == EAST) {
            return Block.box(0, 0, -16, 16, 28, 16);
        }
        return getShape(state, level, pos, context);
    }
}
