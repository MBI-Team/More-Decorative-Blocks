package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry;

/**
 * 彩虹史莱姆脉络。
 * <p>
 * 继承自 {@link MultifaceBlock}，行为类似幽匿脉络（Sculk Vein）：
 * <ul>
 *   <li>可以贴在任何"实体"方块的六个面上，由 {@code RainbowSlimeBallEntity}
 *       击中目标方块时放置到其相邻面的位置（自动打开被击中的面）。</li>
 *   <li>放置后通过 {@link MultifaceSpreader} 周期性自动向相邻空气蔓延。</li>
 * </ul>
 * <p>
 * 渲染/形状由父类 {@link MultifaceBlock} 根据各面 boolean 状态自动组合；
 * 蔓延行为由子类实现的 {@link #getSpreader()} 决定。
 */
public class RainbowSlimeVein extends MultifaceBlock {
    //TODO：完成蔓延逻辑

    /**
     * 蔓延器配置。{@link MultifaceSpreader} 在 1.21.1 下通过 {@link MultifaceSpreader.SpreadConfig} 配置。
     * <p>
     * 我们这里的关键改动：
     * <ul>
     *   <li>{@code getStateForPlacement} 直接返回当前 {@code currentState}，
     *       这样 spreader 不会主动修改 state（即不会主动取消某些面）。
     *       这就避免了父类默认行为在 randomTick 时把"上下"或"没有 FULL 形状支撑"的面取消，
     *       从而避免"一个 slime 方块最多只有两个面"的问题。</li>
     *   <li>{@code canSpreadInto} 仅当目标位置是空气或可替换方块时返回 {@code true}，
     *       其余贴附可行性由 spreader 在放置阶段判定。</li>
     * </ul>
     * 父类在每次 randomTick 时通过 {@link #getSpreader()} 拿到该实例向相邻空气蔓延。
     */
    private final MultifaceSpreader spreader = new MultifaceSpreader(new MultifaceSpreader.SpreadConfig() {
        @Override
        public @NotNull BlockState getStateForPlacement(@NotNull BlockState currentState, @NotNull BlockGetter level,
                                                        @NotNull BlockPos pos, @NotNull Direction lookingDirection) {
            // 关键修复：返回 currentState 而不是 spreader 自动生成的状态。
            // 这样 spreader 在 randomTick 时不会"取消"某些没有 FULL 形状支撑的面，
            // 我们的 slime 可以保留全部 6 个面（被玩家多次投掷打开的面）。
            return currentState;
        }

        @Override
        public boolean canSpreadInto(@NotNull BlockGetter level, @NotNull BlockPos pos,
                                     MultifaceSpreader.@NotNull SpreadPos spreadPos) {
            BlockState targetState = level.getBlockState(pos);
            return targetState.isAir() || targetState.canBeReplaced();
        }
    });

    public RainbowSlimeVein(Properties properties) {
        super(properties);
    }

    /**
     * 静态辅助方法：将 {@code state} 中对应 {@code direction} 的 boolean 面属性置为 {@code true}。
     * <p>
     * 注意：{@link MultifaceBlock#getFaceProperty(Direction)} 是实例方法，
     * 必须通过具体的 {@link Block} 实例调用。
     *
     * @param state     源方块状态
     * @param direction 需要打开（贴附）的方向
     * @return 修改后的方块状态
     */
    public static BlockState attachFace(BlockState state, Direction direction) {
        BooleanProperty property = getFaceProperty(direction);
        return state.setValue(property, Boolean.TRUE);
    }

    /**
     * 静态辅助：判断 {@code placePos} 处能否在 {@code direction} 方向贴附一个 slime 面。
     * <p>
     * 仅当 {@code direction} 方向相邻方块是"实体"（非空气且不可替换）时返回 true。
     * 这样从 {@code RainbowSlimeBallEntity} 投放 slime 时可以确保落地一定能稳定贴附。
     *
     * @param level     世界
     * @param placePos  slime 自身所在的位置
     * @param direction slime 朝向哪个方向贴附（slime 在该方向的邻居就是支撑方块）
     */
    public static boolean isSupportedFace(@NotNull Level level, @NotNull BlockPos placePos, @NotNull Direction direction) {
        BlockPos supportPos = placePos.relative(direction);
        BlockState supportState = level.getBlockState(supportPos);
        return !supportState.isAir() && !supportState.canBeReplaced() && !supportState.is(BlockRegistry.RAINBOW_SLIME_VEIN.get());
    }

    /**
     * 检查指定方向上是否有实体支撑方块（非空气、不可替换）。
     * <p>
     * 与 {@link MultifaceBlock} 内部 {@code isFaceSupported} 语义一致，
     * 但这里对六个方向统一判定，不限制只能贴附"实体方块的侧面"。
     */
    private static boolean hasSupport(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull Direction face) {
        BlockPos supportPos = pos.relative(face);
        BlockState supportState = level.getBlockState(supportPos);
        return !supportState.isAir() && !supportState.canBeReplaced() && !supportState.is(BlockRegistry.RAINBOW_SLIME_VEIN.get());
    }

    /**
     * 占位方法以保留与 {@link BlockGetter}/{@link BlockPos} 的 javadoc 引用。
     */
    @SuppressWarnings("unused")
    private static void unused(BlockGetter level, BlockPos pos) {
    }

    /**
     * 必须实现的抽象方法：返回本方块使用的 {@link MultifaceSpreader}。
     * <p>
     * 父类在每次 randomTick 时通过本方法拿到 spreader，进而向邻居蔓延。
     * 这里我们使用一个新建的默认 spreader，行为与幽匿脉络一致。
     */
    @Override
    public @NotNull MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    /**
     * 任意一个面是否有支撑方块。这是 {@link MultifaceBlock#canSurvive} 默认实现所要求的。
     * <p>
     * 重写为基于本类实现的判定，使六个方向（上下左右前后）全部视为可贴附，
     * 而非依赖父类默认逻辑。这样可以解决"无法向下/向上"和"放置时朝向错误"的问题。
     */
    @Override
    public boolean canSurvive(@NotNull BlockState state, @NotNull LevelReader level, @NotNull BlockPos pos) {
        // 至少要有一个面（被打开为 true 的）方向有实体支撑方块
        for (Direction direction : Direction.values()) {
            BooleanProperty property = getFaceProperty(direction);
            if (state.hasProperty(property) && state.getValue(property)
                    && hasSupport(level, pos, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected @NotNull MapCodec<? extends RainbowSlimeVein> codec() {
        return simpleCodec(RainbowSlimeVein::new);
    }
}
