package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import org.more_blocks_and_items_team.more_decorative_blocks.Config;

import java.util.EnumMap;
import java.util.Map;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.ItemRegistry.GLASS_CUP;

/**
 * 橱柜方块：可以往里面放玻璃杯（最多 4 个），
 * 潜行 + 空手右键可以把玻璃杯取回来。
 */
public class Cupboard extends Block {
    /**
     * 橱柜最多能放多少个玻璃杯。
     */
    public static final int MAX_GLASS_CUPS = 4;

    /**
     * 橱柜内玻璃杯数量（0-4），对应不同模型。
     */
    public static final IntegerProperty GLASS_CUP_NUM = IntegerProperty.create("gcn", 0, MAX_GLASS_CUPS);
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    private static final Map<Direction, VoxelShape> SHAPES = new EnumMap<>(Direction.class);
    private static final VoxelShape FALLBACK = Block.box(0, 0, 0, 16, 28, 16);

    static {
        // 橱柜模型比一格更宽（向一侧延伸 16 像素），碰撞箱与模型保持一致
        SHAPES.put(Direction.NORTH, Block.box(-16, 0, 0, 16, 28, 16));
        SHAPES.put(Direction.SOUTH, Block.box(0, 0, 0, 32, 28, 16));
        SHAPES.put(Direction.WEST, Block.box(0, 0, 0, 16, 28, 32));
        SHAPES.put(Direction.EAST, Block.box(0, 0, -16, 16, 28, 16));
    }

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
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState state, @NotNull Level level,
                                                       @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand,
                                                       @NotNull BlockHitResult hit) {
        int current = state.getValue(GLASS_CUP_NUM);

        // 放入玻璃杯
        if (itemStack.getItem() == GLASS_CUP.get()) {
            if (current < MAX_GLASS_CUPS) {
                if (!level.isClientSide) {
                    level.setBlock(pos, state.setValue(GLASS_CUP_NUM, current + 1), Block.UPDATE_ALL);
                    if (!player.isCreative()) {
                        itemStack.shrink(1);
                    }
                }
                return ItemInteractionResult.SUCCESS;
            }
            // 已经装满，交给默认逻辑（避免吞掉物品）
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // 潜行 + 空手取出玻璃杯（原实现只能放不能取，杯子会被永久锁死在方块里）
        if (itemStack.isEmpty() && player.isShiftKeyDown() && current > 0) {
            if (!level.isClientSide) {
                level.setBlock(pos, state.setValue(GLASS_CUP_NUM, current - 1), Block.UPDATE_ALL);
                ItemStack cup = new ItemStack(GLASS_CUP.get());
                if (!player.getInventory().add(cup)) {
                    player.drop(cup, false);
                }
            }
            return ItemInteractionResult.SUCCESS;
        }

        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(FACING, mirror.mirror(state.getValue(FACING)));
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull RenderShape getRenderShape(@NotNull BlockState pState) {
        return RenderShape.MODEL;
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

    @Override
    @SuppressWarnings("deprecation")
    public float getDestroyProgress(@NotNull BlockState state, @NotNull Player player, @NotNull BlockGetter blockGetter, @NotNull BlockPos pos) {
        float baseProgress = super.getDestroyProgress(state, player, blockGetter, pos);

        // 获取玩家手中的物品
        ItemStack itemStack = player.getMainHandItem();

        // 如果手中没有物品，返回基础进度（徒手挖掘）
        if (itemStack.isEmpty()) {
            return baseProgress;
        }

        // 检查物品是否是 DiggerItem（工具类物品），并获取其等级
        Tier tier = null;
        if (itemStack.getItem() instanceof DiggerItem diggerItem) {
            tier = diggerItem.getTier();
        }

        // 如果不是工具类物品，返回基础进度
        if (tier == null) {
            return baseProgress;
        }

        // 根据工具等级应用不同的系数
        double coefficient = getCoefficientForTier(tier);

        // 系数越大，挖掘越慢（进度越小）
        return (float) (baseProgress / coefficient);
    }

    /**
     * 根据工具等级获取对应的系数
     *
     * @param tier 工具等级
     * @return 挖掘系数
     */
    private double getCoefficientForTier(Tier tier) {
        return switch (tier) {
            case Tiers.WOOD -> Config.mdbBlockWoodMiningCoefficient;
            case Tiers.STONE -> Config.mdbBlockStoneMiningCoefficient;
            case Tiers.GOLD -> Config.mdbBlockGoldMiningCoefficient;
            case Tiers.IRON -> Config.mdbBlockIronMiningCoefficient;
            case Tiers.DIAMOND -> Config.mdbBlockDiamondMiningCoefficient;
            case Tiers.NETHERITE -> Config.mdbBlockNetheriteMiningCoefficient;
            case null, default ->
                // 其他工具（如金镐、下界合金等）使用默认系数 1.0
                    1.0;
        };
    }
}
