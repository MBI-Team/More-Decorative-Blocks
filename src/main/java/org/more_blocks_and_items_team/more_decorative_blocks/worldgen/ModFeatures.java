package org.more_blocks_and_items_team.more_decorative_blocks.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class ModFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(Registries.FEATURE, MODID);

    public static final DeferredHolder<Feature<?>, ModMudFluidFeature> MUD_FLUID_FEATURE =
            FEATURES.register("mud_fluid_feature", () -> new ModMudFluidFeature(NoneFeatureConfiguration.CODEC));
}