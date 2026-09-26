package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

/**
 * 彩虹史莱姆方块。
 * <p>
 * 这是一个普通的可放置方块（不再继承 {@code MultifaceBlock}，因此不具备
 * 像幽匿脉络一样的自动蔓延能力），由 {@code RainbowSlimeBallEntity}
 * 击中目标方块时放置到其相邻面的位置。
 */
public class RainbowSlimeBlock extends Block {

    /**
     * 朝向方向：用于正确显示方块的"贴附面"。
     */
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;

    /**
     * 附着面：地板/天花板/墙壁。
     */
    public static final net.minecraft.world.level.block.state.properties.EnumProperty<AttachFace> FACE =
            BlockStateProperties.ATTACH_FACE;

    public RainbowSlimeBlock(Properties properties) {
        super(properties);
        // 默认状态：附着在地板上，朝北
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(FACE, AttachFace.FLOOR)
                .setValue(FACING, Direction.NORTH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACE, FACING);
    }

    @Override
    public @NotNull MapCodec<? extends RainbowSlimeBlock> codec() {
        return simpleCodec(RainbowSlimeBlock::new);
    }

    /**
     * 根据 {@link #FACE} 和 {@link #FACING} 推断本方块"贴附"的方向，
     * 即支撑方块位于哪个方向上。
     */
    public Direction getAttachedDirection(BlockState state) {
        AttachFace face = state.getValue(FACE);
        Direction facing = state.getValue(FACING);
        return switch (face) {
            case FLOOR -> Direction.DOWN;
            case CEILING -> Direction.UP;
            case WALL -> facing.getOpposite();
        };
    }

    /**
     * 只有当支撑方块是"实体"方块时，本方块才能存活。
     */
    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        Direction attachedDirection = getAttachedDirection(state);
        BlockPos supportPos = pos.relative(attachedDirection);
        return level.getBlockState(supportPos).isFaceSturdy(level, supportPos, attachedDirection.getOpposite());
    }

    // 注：不再覆写 updateShape。父类 {@link Block#updateShape} 会按 1.21
    // 的签名调用自身，而我们没有"支撑方块变更时立刻掉落"之外的特殊行为。
    // 当支撑方块被破坏后，世界 tick 会调用 {@link #canSurvive} 进行检查，
    // 不满足时本方块会被游戏自身替换为空气。

    /**
     * 完整方块形状，方便玩家击中时方块显示完整外观。
     */
    @Override
    public @NotNull VoxelShape getShape(
            @NotNull BlockState state,
            @NotNull net.minecraft.world.level.BlockGetter level,
            @NotNull BlockPos pos,
            @NotNull CollisionContext context
    ) {
        return Shapes.block();
    }
}