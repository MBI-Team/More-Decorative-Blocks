package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;

/**
 * 彩虹史莱姆方块。
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
public class RainbowSlimeBlock extends MultifaceBlock {

    /**
     * 蔓延器配置。MultifaceSpreader 在 1.21.1 下通过 {@link MultifaceSpreader.SpreadConfig} 配置：
     * <ul>
     *   <li>{@code getStateForPlacement} 直接返回当前 {@code currentState}，由
     *       {@link MultifaceBlock} 在放置/蔓延时自行按面打开 boolean 属性。</li>
     *   <li>{@code canSpreadInto} 仅当目标位置是空气或可替换方块时返回 {@code true}，
     *       其余贴附可行性（支撑面、isFaceSupported 等）由 spreader 在放置阶段判定。
     *       这与 {@link MultifaceBlock} 默认 {@code SpreadConfig} 行为一致。</li>
     * </ul>
     * 父类在每次 randomTick 时通过 {@link #getSpreader()} 拿到该实例向相邻空气蔓延，
     * 行为与幽匿脉络（Sculk Vein）一致。
     */
    private final MultifaceSpreader spreader = new MultifaceSpreader(new MultifaceSpreader.SpreadConfig() {
        @Override
        public @NotNull BlockState getStateForPlacement(@NotNull BlockState currentState, @NotNull BlockGetter level,
                                                        @NotNull BlockPos pos, @NotNull Direction lookingDirection) {
            return currentState;
        }

        @Override
        public boolean canSpreadInto(@NotNull BlockGetter level, @NotNull BlockPos pos,
                                     MultifaceSpreader.@NotNull SpreadPos spreadPos) {
            BlockState targetState = level.getBlockState(pos);
            return targetState.isAir() || targetState.canBeReplaced();
        }
    });

    public RainbowSlimeBlock(Properties properties) {
        super(properties);
    }

    /**
     * 静态辅助方法：将 {@code state} 中对应 {@code direction} 的 boolean 面属性置为 {@code true}。
     * <p>
     * 注意：{@link MultifaceBlock#getFaceProperty(Direction)} 是实例方法，
     * 必须通过具体的 {@link Block} 实例调用。
     *
     * @param state     源方块状态
     * @param self      实际的 Block 实例（用于获取面属性映射）
     * @param direction 需要打开（贴附）的方向
     * @return 修改后的方块状态
     */
    public static BlockState attachFace(BlockState state, Block self, Direction direction) {
        BooleanProperty property = getFaceProperty(direction);
        return state.setValue(property, Boolean.TRUE);
    }

    /**
     * 占位方法以保留与 {@link BlockGetter}/{@link BlockPos} 的 javadoc 引用。
     */
    @SuppressWarnings("unused")
    private static void unused(BlockGetter level, BlockPos pos) {
    }

    @Override
    protected @NotNull MapCodec<? extends RainbowSlimeBlock> codec() {
        return simpleCodec(RainbowSlimeBlock::new);
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
}