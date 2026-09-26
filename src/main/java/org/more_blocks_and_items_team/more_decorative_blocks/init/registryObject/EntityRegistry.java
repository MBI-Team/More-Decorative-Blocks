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

    public static final DeferredHolder<EntityType<?>, EntityType<RainbowSlimeBallEntity>> RANBOW_SLIME_BALL_ENTITY =
            ENTITY_TYPES.register("ranbow_slime_ball", () -> EntityType.Builder.<RainbowSlimeBallEntity>of(
                            RainbowSlimeBallEntity::new,
                            MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10)
                    .build(MODID + ":ranbow_slime_ball"));
}
