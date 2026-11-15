package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.entity.RainbowSlimeBallEntity;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;

/**
 * 实体注册器
 */
public class EntityRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.ENTITY_TYPE, MODID);

    /**
     * 彩虹史莱姆球投射物。
     * <p>
     * 注意：注册名原先误拼为 {@code ranbow_slime_ball}（少了一个 i），
     * 与物品 {@code rainbow_slime_ball} 不一致，这里已更正。
     */
    public static final DeferredHolder<EntityType<?>, EntityType<RainbowSlimeBallEntity>> RAINBOW_SLIME_BALL_ENTITY =
            ENTITY_TYPES.register("rainbow_slime_ball", () -> EntityType.Builder.<RainbowSlimeBallEntity>of(
                            RainbowSlimeBallEntity::new,
                            MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(MODID + ":rainbow_slime_ball"));
}
