package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import net.minecraft.client.renderer.RenderType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry;

@EventBusSubscriber(modid = "more_decorative_blocks", bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class RenderTypeEvents {
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // 为所有橱柜方块设置cutout渲染类型
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(BlockRegistry.OAK_WOOD_CUPBOARD.get(), ChunkRenderTypeSet.of(RenderType.cutout()));
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(BlockRegistry.IRON_CUPBOARD.get(), ChunkRenderTypeSet.of(RenderType.cutout()));
            net.minecraft.client.renderer.ItemBlockRenderTypes.setRenderLayer(BlockRegistry.ACACIA_WOOD_CUPBOARD.get(), ChunkRenderTypeSet.of(RenderType.cutout()));
        });
    }
}
