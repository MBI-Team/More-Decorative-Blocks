package org.more_blocks_and_items_team.more_decorative_blocks.objects.block.basic;

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
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.Config;

public class RightClinkAndRedstoneBlock extends Block {
    public static final BooleanProperty POWERED = BooleanProperty.create("powered");
    public static final BooleanProperty OPEN = BooleanProperty.create("open");
    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);

    public RightClinkAndRedstoneBlock(BlockBehaviour.Properties prop) {
        super(prop);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(OPEN, false)
                        .setValue(POWERED, false)
        );
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(POWERED);
        builder.add(OPEN);
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
    public void neighborChanged(BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block neighborBlock, @NotNull BlockPos neighborPos, boolean isMoving) {
        boolean hasSignal = level.hasNeighborSignal(pos);
        if (hasSignal != state.getValue(POWERED)) {
            level.setBlock(pos, state.setValue(POWERED, hasSignal), Block.UPDATE_ALL);
        }
    }

    @Override
    protected @NotNull ItemInteractionResult useItemOn(@NotNull ItemStack itemStack, @NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!level.isClientSide) {
            if (state.getValue(OPEN)) {
                BlockState newState = state.setValue(OPEN, false);
                level.setBlock(pos, newState, 1, 2);
            } else {
                BlockState newState = state.setValue(OPEN, true);
                level.setBlock(pos, newState, 1, 2);
            }
        }
        return ItemInteractionResult.SUCCESS;
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
