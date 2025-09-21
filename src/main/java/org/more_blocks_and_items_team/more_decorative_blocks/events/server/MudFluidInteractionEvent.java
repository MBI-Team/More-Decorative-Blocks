package org.more_blocks_and_items_team.more_decorative_blocks.events.server;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.level.BlockEvent;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.FluidRegistry;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

@EventBusSubscriber(modid = MODID)
public class MudFluidInteractionEvent {

    @SubscribeEvent
    public static void onFluidPlaceBlock(BlockEvent.FluidPlaceBlockEvent event) {
        LevelAccessor level = event.getLevel();
        BlockPos pos = event.getPos();
        FluidState fluidState = level.getFluidState(pos);

        // 检查是否是泥浆流体
        if (fluidState.getType() == FluidRegistry.MUD_FLUID.get() ||
                fluidState.getType() == FluidRegistry.MUD_FLUID_FLOWING.get()) {

            // 检查相邻方块是否是水
            if (isWaterAround(level, pos)) {
                // 泥浆遇到水变成水
                event.setNewState(Blocks.WATER.defaultBlockState());
                return;
            }

            // 检查相邻方块是否是岩浆
            if (isLavaAround(level, pos)) {
                // 泥浆遇到岩浆变成泥土
                event.setNewState(Blocks.DIRT.defaultBlockState());
                return;
            }
        }

        // 检查水遇到泥浆的情况
        if (level.getFluidState(pos).getType() == net.minecraft.world.level.material.Fluids.WATER) {
            if (isMudAround(level, pos)) {
                // 水遇到泥浆变成泥浆
                event.setNewState(FluidRegistry.MUD_BLOCK.get().defaultBlockState());
            }
        }
    }

    private static boolean isWaterAround(LevelAccessor level, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    BlockPos neighborPos = pos.offset(x, y, z);
                    FluidState fluidState = level.getFluidState(neighborPos);
                    if (fluidState.getType() == net.minecraft.world.level.material.Fluids.WATER) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isLavaAround(LevelAccessor level, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    BlockPos neighborPos = pos.offset(x, y, z);
                    FluidState fluidState = level.getFluidState(neighborPos);
                    if (fluidState.getType() == net.minecraft.world.level.material.Fluids.LAVA) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private static boolean isMudAround(LevelAccessor level, BlockPos pos) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (x == 0 && y == 0 && z == 0) continue;
                    BlockPos neighborPos = pos.offset(x, y, z);
                    FluidState fluidState = level.getFluidState(neighborPos);
                    if (fluidState.getType() == FluidRegistry.MUD_FLUID.get() ||
                            fluidState.getType() == FluidRegistry.MUD_FLUID_FLOWING.get()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}