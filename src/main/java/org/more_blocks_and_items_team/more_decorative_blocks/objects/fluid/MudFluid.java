package org.more_blocks_and_items_team.more_decorative_blocks.objects.fluid;

import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import org.jetbrains.annotations.NotNull;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.FluidRegistry;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.FluidTypeRegistry;

public abstract class MudFluid extends BaseFlowingFluid {
    protected MudFluid() {
        super(new Properties(FluidTypeRegistry.MUD_FLUID_TYPE,
                FluidRegistry.MUD_FLUID,
                FluidRegistry.MUD_FLUID_FLOWING)
                .slopeFindDistance(3)
                .levelDecreasePerBlock(2)
                .block(FluidRegistry.MUD_BLOCK)
                .bucket(FluidRegistry.MUD_BUCKET));
    }

    public static class Flowing extends MudFluid {
        @Override
        protected void createFluidStateDefinition(StateDefinition.Builder<Fluid, FluidState> builder) {
            super.createFluidStateDefinition(builder);
            builder.add(LEVEL);
        }

        @Override
        public int getAmount(FluidState state) {
            return state.getValue(LEVEL);
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return false;
        }
    }

    public static class Source extends MudFluid {
        @Override
        public int getAmount(@NotNull FluidState state) {
            return 8;
        }

        @Override
        public boolean isSource(@NotNull FluidState state) {
            return true;
        }
    }
}