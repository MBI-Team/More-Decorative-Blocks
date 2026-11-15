package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.EntityRegistry;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.MODID;

/**
 * 客户端实体渲染器注册。
 * <p>
 * 原项目注册了 {@code rainbow_slime_ball} 这个投射物实体，但从未注册渲染器，
 * 结果就是扔出去的球在客户端完全隐形（只有粒子）。这里补上原版的
 * {@link ThrownItemRenderer}，让投射物按物品图标渲染。
 */
@EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class EntityRenderers {

    private EntityRenderers() {
        throw new IllegalStateException("Utility class");
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(EntityRegistry.RAINBOW_SLIME_BALL_ENTITY.get(),
                context -> new ThrownItemRenderer<>(context, 1.0F, true));
    }
}
