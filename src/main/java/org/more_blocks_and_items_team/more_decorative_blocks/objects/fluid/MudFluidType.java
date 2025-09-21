package org.more_blocks_and_items_team.more_decorative_blocks.objects.fluid;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.pathfinder.PathType;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidType;

import java.util.function.Consumer;

public class MudFluidType extends FluidType {
    private final ResourceLocation stillTexture;
    private final ResourceLocation flowingTexture;

    public MudFluidType(Properties properties, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        super(properties);
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
    }

    @Override
    public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
        consumer.accept(new IClientFluidTypeExtensions() {
            @Override
            public ResourceLocation getStillTexture() {
                return stillTexture;
            }

            @Override
            public ResourceLocation getFlowingTexture() {
                return flowingTexture;
            }
        });
    }

    public boolean canSwim() {
        return false;
    }

    public boolean canDrown() {
        return true;
    }

    public PathType getPathType() {
        return PathType.WATER;
    }

    public PathType getAdjacentPathType() {
        return PathType.WATER;
    }
}