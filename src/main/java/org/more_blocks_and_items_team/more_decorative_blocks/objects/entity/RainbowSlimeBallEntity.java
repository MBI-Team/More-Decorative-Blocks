package org.more_blocks_and_items_team.more_decorative_blocks.objects.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.AttachFace;
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
 * 铺设一个 {@link RainbowSlimeBlock}（普通方块，不再像幽匿脉络）。
 */
public class RainbowSlimeBallEntity extends Snowball {

    private static final Random RNG = new Random();

    public RainbowSlimeBallEntity(EntityType<? extends Snowball> entityType, Level level) {
        super(entityType, level);
    }

    public RainbowSlimeBallEntity(Level level, LivingEntity shooter) {
        super(level, shooter);
    }

    /**
     * 覆盖击中实体的行为：不做任何伤害与击退处理，
     * 也不触发雪球默认的小伤害逻辑。
     * 同时显示击中的彩色粒子。
     */
    @Override
    protected void onHitEntity(@NotNull EntityHitResult result) {
        if (!this.level().isClientSide()) {
            // 在被命中的实体位置生成击中粒子
            if (this.level() instanceof ServerLevel serverLevel) {
                Vec3 loc = result.getEntity().position().add(0.0D, result.getEntity().getBbHeight() / 2.0D, 0.0D);
                spawnColoredHitParticles(serverLevel, loc);
            }
            this.discard();
        }
    }

    /**
     * 落地处理：不会生成物品掉落，而是在被击中方块的被击中那一面
     * 上铺设一个 {@link RainbowSlimeBlock}。
     */
    @Override
    protected void onHitBlock(@NotNull BlockHitResult result) {
        Level level = this.level();
        if (level.isClientSide()) {
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

        // 根据击中面推断 AttachFace 与水平朝向
        AttachFace face;
        Direction facing;
        switch (hitSide) {
            case UP -> {
                face = AttachFace.CEILING;
                facing = Direction.NORTH;
            }
            case DOWN -> {
                face = AttachFace.FLOOR;
                facing = Direction.NORTH;
            }
            default -> {
                face = AttachFace.WALL;
                // 对于墙面，把面向玩家的方向作为水平朝向
                facing = hitSide;
            }
        }

        BlockState state = coverBlock.defaultBlockState()
                .setValue(RainbowSlimeBlock.FACE, face)
                .setValue(RainbowSlimeBlock.FACING, facing);

        if (!state.canSurvive(level, placePos)) {
            this.discard();
            return;
        }

        level.setBlock(placePos, state, 3);

        // 播放黏液击中音效
        level.playSound(null, placePos.getX() + 0.5D, placePos.getY() + 0.5D, placePos.getZ() + 0.5D,
                SoundEvents.SLIME_BLOCK_PLACE, SoundSource.BLOCKS, 0.8F, 1.0F);

        // 服务端生成彩色粒子
        if (level instanceof ServerLevel serverLevel) {
            spawnColoredHitParticles(serverLevel,
                    Vec3.atCenterOf(placePos));
        }

        this.discard();
    }

    /**
     * 每 tick 调用一次，用于生成飞行过程中的粒子效果。
     */
    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            return;
        }
        Level level = this.level();
        // 在客户端生成飞行粒子（彩色粘液粒子）
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
     */
    private void spawnColoredHitParticles(ServerLevel level, Vec3 center) {
        for (int i = 0; i < 24; i++) {
            double dx = (RNG.nextDouble() - 0.5D) * 0.6D;
            double dy = (RNG.nextDouble() - 0.5D) * 0.6D;
            double dz = (RNG.nextDouble() - 0.5D) * 0.6D;
            level.sendParticles(pickRandomColor(), center.x, center.y, center.z,
                    0, dx, dy, dz, 0.1D);
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
     * 不再生成物品掉落。
     */
    @Override
    protected @NotNull Item getDefaultItem() {
        return null;
    }
}