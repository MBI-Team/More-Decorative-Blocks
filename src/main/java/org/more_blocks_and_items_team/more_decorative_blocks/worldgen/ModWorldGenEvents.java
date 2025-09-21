package org.more_blocks_and_items_team.more_decorative_blocks.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.*;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.List;

@EventBusSubscriber(modid = "more_decorative_blocks", bus = EventBusSubscriber.Bus.MOD)
public class ModWorldGenEvents {
    // 泥浆流体特征的配置键
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUD_FLUID_FEATURE_KEY =
            ResourceKey.create(Registries.CONFIGURED_FEATURE, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("more_decorative_blocks", "mud_fluid_feature"));

    // 泥浆流体放置特征的配置键
    public static final ResourceKey<PlacedFeature> MUD_FLUID_PLACED_FEATURE_KEY =
            ResourceKey.create(Registries.PLACED_FEATURE, net.minecraft.resources.ResourceLocation.fromNamespaceAndPath("more_decorative_blocks", "mud_fluid_placed_feature"));

    @SubscribeEvent
    public static void registerWorldGenFeatures(RegisterEvent event) {
        event.register(Registries.CONFIGURED_FEATURE, helper -> {
            // 注册配置的特征
        });

        event.register(Registries.PLACED_FEATURE, helper -> {
            // 注册放置的特征
        });
    }

    public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        register(context, ModFeatures.MUD_FLUID_FEATURE.get());
    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);
        Holder<ConfiguredFeature<?, ?>> mudFluidFeatureHolder = configuredFeatures.getOrThrow(MUD_FLUID_FEATURE_KEY);

        // 注册放置特征，使其在世界中生成
        context.register(MUD_FLUID_PLACED_FEATURE_KEY,
                new PlacedFeature(mudFluidFeatureHolder,
                        List.of(
                                RarityFilter.onAverageOnceEvery(4), // 平均每4个区块生成一次
                                InSquarePlacement.spread(), // 在区块内随机分布
                                HeightmapPlacement.onHeightmap(net.minecraft.world.level.levelgen.Heightmap.Types.WORLD_SURFACE), // 在地表生成
                                BiomeFilter.biome() // 只在合适的生物群系生成
                        )
                )
        );
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            F feature
    ) {
        context.register(ModWorldGenEvents.MUD_FLUID_FEATURE_KEY, new ConfiguredFeature<>(feature, (FC) NoneFeatureConfiguration.INSTANCE));
    }
}