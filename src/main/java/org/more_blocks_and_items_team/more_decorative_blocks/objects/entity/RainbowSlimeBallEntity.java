package org.more_blocks_and_items_team.more_decorative_blocks.objects.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.block.RainbowSlimeBlock;

import java.util.Random;

/**
 * 彩虹史莱姆球的投掷实体。
 * <p>
 * 行为参考雪球（投掷轨迹、击中实体的判定），
 * 击中实体不造成任何伤害或击退；
 * 落地时不会生成物品掉落，而是在被击中方块对应的一个面上
 * 铺设一个 {@link RainbowSlimeBlock}（继承自幽匿脉络 MultifaceBlock）。
 * <p>
 * 性能说明：
 * <ul>
 *   <li>飞行粒子只在客户端生成，并且每 {@link #TRAIL_PARTICLE_INTERVAL} 个 tick 才生成一次，
 *       避免高频丢球时粒子数量爆炸。</li>
 *   <li>击中粒子全部在客户端通过 {@link Level#addParticle} 渲染，
 *       不再走服务端的 {@code sendParticles} 网络广播，
 *       避免击中瞬间网络包激增带来的卡顿。</li>
 * </ul>
 */
public class RainbowSlimeBallEntity extends Snowball {

    private static final Random RNG = new Random();

    /**
     * 飞行粒子生成间隔（单位：tick）。每 2 个 tick 生成一次飞行粒子，
     * 既能保留拖尾效果，又能显著降低渲染开销。
     */
    private static final int TRAIL_PARTICLE_INTERVAL = 2;

    public RainbowSlimeBallEntity(EntityType<? extends Snowball> entityType, Level level) {
        super(entityType, level);
    }

    public RainbowSlimeBallEntity(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    /**
     * 覆盖击中实体的行为：不做任何伤害与击退处理，
     * 也不触发雪球默认的小伤害逻辑。
     * 粒子效果完全在客户端本地生成，避免服务端广播。
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        Level level = this.level();
        // 击中粒子只在客户端渲染，服务端只负责销毁实体
        if (level.isClientSide()) {
            Vec3 loc = result.getEntity().position().add(0.0D, result.getEntity().getBbHeight() / 2.0D, 0.0D);
            spawnColoredHitParticlesClient(loc);
        }
        this.discard();
    }

    /**
     * 落地处理：不会生成物品掉落，而是在被击中方块的被击中那一面
     * 上铺设一个 {@link RainbowSlimeBlock}。
     * 击中粒子同样在客户端本地生成。
     */
    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = this.level();
        if (level.isClientSide()) {
            // 客户端只负责播放击中粒子，不放置方块
            spawnColoredHitParticlesClient(result.getLocation());
            this.discard();
            return;
        }

        BlockPos hitPos = result.getBlockPos();
        Direction hitSide = result.getDirection();
        BlockPos placePos = hitPos.relative(hitSide);

        // 如果放置位置不是空气，则直接消失，不强行替换
        if (!level.getBlockState(placePos).isAir()) {
            this.discard();
            return;
        }

        var coverBlock = org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry.RAINBOW_SLIME_BLOCK.get();

        // 彩虹史莱姆方块是 MultifaceBlock，通过 RainbowSlimeBlock 的
        // 静态辅助方法生成一个"被击中面已贴附"的 BlockState。
        // 注意：getFaceProperty 是实例方法，需要传入 Block 实例。
        BlockState state = RainbowSlimeBlock.attachFace(
                coverBlock.defaultBlockState(),
                coverBlock,
                hitSide
        );

        if (!state.canSurvive(level, placePos)) {
            this.discard();
            return;
        }

        level.setBlock(placePos, state, 3);

        // 播放黏液击中音效
        level.playSound(null, placePos.getX() + 0.5D, placePos.getY() + 0.5D, placePos.getZ() + 0.5D,
                SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);

        this.discard();
    }

    /**
     * 每 tick 调用一次，用于生成飞行过程中的粒子效果。
     * 只在客户端生成，并且经过节流以减少渲染开销。
     */
    @Override
    public void tick() {
        super.tick();
        Level level = this.level();
        if (!level.isClientSide()) {
            return;
        }
        // 节流：每 TRAIL_PARTICLE_INTERVAL 个 tick 才生成一次飞行粒子
        if ((this.tickCount % TRAIL_PARTICLE_INTERVAL) != 0) {
            return;
        }
        spawnColoredTrailParticle(level);
    }

    /**
     * 生成一个随机颜色的飞行粒子，模拟"彩色"粘液球在空中飞行的效果。
     */
    private void spawnColoredTrailParticle(Level level) {
        ParticleOptions options = pickRandomColor();
        level.addParticle(
                options,
                this.getX(), this.getY() + this.getBbHeight() / 2.0D, this.getZ(),
                0.0D, 0.0D, 0.0D
        );
    }

    /**
     * 在给定坐标生成多种彩色粒子，模拟"击中"时的彩色爆裂效果。
     * <p>
     * 仅在客户端调用，通过 {@link Level#addParticle} 直接渲染，
     * 不走服务端的 {@code sendParticles} 网络广播，
     * 从而避免高频击中时的网络包风暴和主线程卡顿。
     */
    private void spawnColoredHitParticlesClient(Vec3 center) {
        // 通过传入非零速度来让 addParticle 自动产生"扩散"效果，
        // 单次调用即可完成彩色爆裂，无需服务端广播。
        for (int i = 0; i < 12; i++) {
            double dx = (RNG.nextDouble() - 0.5D) * 0.4D;
            double dy = (RNG.nextDouble() - 0.5D) * 0.4D;
            double dz = (RNG.nextDouble() - 0.5D) * 0.4D;
            ParticleOptions options = pickRandomColor();
            this.level().addParticle(
                    options,
                    center.x, center.y, center.z,
                    dx, dy, dz
            );
        }
    }

    /**
     * 从 Minecraft 内置的几个彩色/特效粒子类型里随机选择一个。
     * <p>
     * 注：这里只选取可以直接作为 {@link ParticleOptions} 使用的简单粒子，
     * 避免 {@code ENTITY_EFFECT}（需要 {@code ColorParticleOption}）
     * 和 {@code ITEM}（需要 {@code ItemParticleOption}）等需要额外参数的粒子。
     */
    private ParticleOptions pickRandomColor() {
        return switch (RNG.nextInt(6)) {
            case 0 -> ParticleTypes.END_ROD;
            case 1 -> ParticleTypes.WAX_ON;
            case 2 -> ParticleTypes.WAX_OFF;
            case 3 -> ParticleTypes.HAPPY_VILLAGER;
            case 4 -> ParticleTypes.COMPOSTER;
            default -> ParticleTypes.ITEM_SLIME;
        };
    }

    /**
     * 返回对应的物品
     * 创建默认的 {@link net.minecraft.world.item.ItemStack} 数据。
     * <p>
     * 必须返回一个非 null 的物品，否则在 1.21+ 中会抛出 NPE。
     * 注意：我们仍然不会真正掉落这个物品，因为 {@link #onHitBlock(BlockHitResult)}
     * 和 {@link #onHitEntity(EntityHitResult)} 都直接调用了 {@link #discard()}。
     */
    @Override
    protected @NotNull Item getDefaultItem() {
        return org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.ItemRegistry.RAINBOW_SLIME_BALL.get();
    }
}