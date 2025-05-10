package org.moredecorativeblocks.more_decorative_blocks.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

public class fire_book extends Block{
    public static final DirectionProperty FACING =
            DirectionProperty.create("facing",
                    Direction.Plane.HORIZONTAL);

    public fire_book() {
        super(BlockBehaviour.Properties.of()
                // 发光等级
                .mapColor(MapColor.STONE)
                .lightLevel(state -> 8)
                .strength(1.0f,0.5f)  // 硬度参数（可选）
                .noOcclusion()  // 关闭面剔除（谨慎使用，可能导致透视问题）
        );
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING,
                        Direction.NORTH)
        );

    }



    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, Mirror mirrorIn) {
        return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
    }
}
