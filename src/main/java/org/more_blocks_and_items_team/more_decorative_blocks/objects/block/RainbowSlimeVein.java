package org.more_blocks_and_items_team.more_decorative_blocks.objects.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry;

/**
 * 彩虹史莱姆脉络。
 * <p>
 * 继承自 {@link MultifaceBlock}，行为与幽匿脉络（Sculk Vein）一致：
 * <ul>
 *   <li>可以贴在方块的六个面上，由 {@code RainbowSlimeBallEntity} 在命中方块时放置到被击中那一面的相邻位置；</li>
 *   <li>放置后由 {@link MultifaceSpreader} 在 randomTick 时向相邻空气蔓延；</li>
 *   <li>支撑方块被破坏时，对应面会自动脱落。</li>
 * </ul>
 */
public class RainbowSlimeVein extends MultifaceBlock {

    /**
     * 蔓延器。直接使用父类的默认配置：
     * 它会调用 {@link MultifaceBlock#getStateForPlacement(BlockState, BlockGetter, BlockPos, Direction)}
     * 生成“朝支撑方块的那一面打开”的新状态。
     * <p>
     * 原实现自定义的 {@code SpreadConfig#getStateForPlacement} 直接把传入的 {@code currentState}
     * 原样返回，而该参数其实是目标位置的状态（通常是空气），
     * 于是蔓延时相当于往空气位置 setBlock(air)，<b>蔓延完全不生效</b>。
     */
    private final MultifaceSpreader spreader = new MultifaceSpreader(this);

    public RainbowSlimeVein(Properties properties) {
        super(properties);
    }

    /**
     * 将 {@code state} 中对应 {@code direction} 的 boolean 面属性置为 {@code true}。
     * <p>
     * 注意：{@link MultifaceBlock#getFaceProperty(Direction)} 是静态方法，
     * {@code direction} 表示“本方块朝向该方向邻居的那一面”。
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
     * 判断 {@code placePos} 处能否在 {@code direction} 方向贴附一个 slime 面。
     * <p>
     * 使用与父类 {@code updateShape} 完全相同的判定（{@link MultifaceBlock#canAttachTo}），
     * 保证“放下就有效”：
     * 若这里比父类宽松，贴到楼梯、栅栏等非整面方块上时，
     * 方块会在下一次邻居更新时被立刻移除。
     *
     * @param level     世界
     * @param placePos  slime 自身所在的位置
     * @param direction slime 朝向哪个方向贴附（该方向的邻居就是支撑方块）
     */
    public static boolean isSupportedFace(@NotNull BlockGetter level, @NotNull BlockPos placePos, @NotNull Direction direction) {
        BlockPos supportPos = placePos.relative(direction);
        BlockState supportState = level.getBlockState(supportPos);
        if (supportState.isAir() || supportState.canBeReplaced() || supportState.is(BlockRegistry.RAINBOW_SLIME_VEIN.get())) {
            return false;
        }
        return canAttachTo(level, direction, supportPos, supportState);
    }

    @Override
    public @NotNull MultifaceSpreader getSpreader() {
        return this.spreader;
    }

    @Override
    protected @NotNull MapCodec<? extends RainbowSlimeVein> codec() {
        return simpleCodec(RainbowSlimeVein::new);
    }
}
