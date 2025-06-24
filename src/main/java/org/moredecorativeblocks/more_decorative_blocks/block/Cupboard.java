package org.moredecorativeblocks.more_decorative_blocks.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.moredecorativeblocks.more_decorative_blocks.block.basic.RightClinkBlock;

import static net.minecraft.core.Direction.*;
import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.GLASS_CUP;

/**
 * Extends from {@link RightClinkBlock} and it extends {@link Block}
 */
public class Cupboard extends Block {
    public static final IntegerProperty GLASS_CUP_NUM = IntegerProperty.create("gcn", 0, 17);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public Cupboard(Properties prop) {
        super(prop);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(GLASS_CUP_NUM, 0)
                        .setValue(FACING, Direction.NORTH));
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(GLASS_CUP_NUM);
        builder.add(FACING);
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        // 检查物品是否是玻璃杯
        if (itemStack.getItem() == GLASS_CUP.get()) {
            int current = state.getValue(GLASS_CUP_NUM);
            // 检查是否达到上限
            if (current < 16) {
                if (!level.isClientSide) {
                    // 更新方块状态
                    BlockState newState = state.setValue(GLASS_CUP_NUM, current + 1);
                    level.setBlock(pos, newState, Block.UPDATE_ALL);
                    // 如果不是创造模式，减少物品数量
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
                // 返回成功
                return ItemInteractionResult.SUCCESS;
            }
        }
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    /**
     * @deprecated @Override {@link BlockState#rotate(LevelAccessor, BlockPos, Rotation)} Try to instead
     */
    @Override
    @Deprecated
    public @NotNull BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }

    @Override
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
    }

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
