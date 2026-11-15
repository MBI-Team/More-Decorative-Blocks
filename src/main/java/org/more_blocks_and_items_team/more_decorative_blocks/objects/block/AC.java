package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.basic.RedstoneBlock;

import java.util.EnumMap;
import java.util.Map;

/**
 * 空调方块：模型向 FACING 方向再延伸一格（32 像素），因此碰撞箱同样延伸，
 * 保证“视觉与碰撞箱一致”。
 * <p>
 * 原实现中 {@code getShape} 的兜底分支会再次调用自身，一旦遇到未覆盖的朝向就会
 * 无限递归并抛出 {@link StackOverflowError}；这里改为查表，永不递归。
 */
public class AC extends RedstoneBlock {

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final VoxelShape FALLBACK = Block.box(0, 0, 0, 16, 16, 16);

    static {
        // 模型本体在 FACING 的反方向，因此碰撞箱朝 FACING 之外的一侧延伸
        SHAPES.put(Direction.NORTH, Block.box(-16, 0, 0, 16, 16, 16));
        SHAPES.put(Direction.SOUTH, Block.box(0, 0, 0, 32, 16, 16));
        SHAPES.put(Direction.WEST, Block.box(0, 0, 0, 16, 16, 32));
        SHAPES.put(Direction.EAST, Block.box(0, 0, -16, 16, 16, 16));
    }

    public AC(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                        @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPES.getOrDefault(state.getValue(FACING), FALLBACK);
    }

    @Override
    public @NotNull VoxelShape getCollisionShape(@NotNull BlockState state, @NotNull BlockGetter level,
                                                 @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return getShape(state, level, pos, context);
    }
}
