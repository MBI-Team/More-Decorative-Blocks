package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.fluid.MudFluidType;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class FluidTypeRegistry {
    public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, MODID);

    public static final net.neoforged.neoforge.registries.DeferredHolder<FluidType, FluidType> MUD_FLUID_TYPE = FLUID_TYPES.register("mud", () ->
            new MudFluidType(FluidType.Properties.create()
                    .density(2000)
                    .viscosity(3000) // 高粘度，类似蜘蛛网效果
                    .temperature(300)
                    .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
                    .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
                    .sound(SoundActions.FLUID_VAPORIZE, SoundEvents.FIRE_EXTINGUISH),
                    ResourceLocation.fromNamespaceAndPath(MODID, "block/mud_still"),
                    ResourceLocation.fromNamespaceAndPath(MODID, "block/mud_flow")
            )
    );
}