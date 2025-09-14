package org.more_blocks_and_items_team.more_decorative_blocks.init.worldgen;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class ModOrePlacements {
    // 放置特征的资源键
    public static final ResourceKey<PlacedFeature> ALUMINUM_ORE_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "aluminum_ore_placed")
    );

    public static final ResourceKey<PlacedFeature> DEEPSLATE_ALUMINUM_ORE_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "deepslate_aluminum_ore_placed")
    );

    /**
     * 注册放置特征
     */
    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        // 获取配置的特征
        var aluminumOre = configuredFeatures.getOrThrow(ModOreConfigurations.ALUMINUM_ORE);
        var deepslateAluminumOre = configuredFeatures.getOrThrow(ModOreConfigurations.DEEPSLATE_ALUMINUM_ORE);

        // 注册放置的特征 - 铝矿石
        context.register(ALUMINUM_ORE_PLACED, new PlacedFeature(
                aluminumOre,
                List.of(
                        CountPlacement.of(8), // 每区块8次尝试
                        InSquarePlacement.spread(), // 在区块内随机分布
                        HeightRangePlacement.uniform( // 在Y=-24到Y=40之间均匀分布
                                VerticalAnchor.absolute(-24),
                                VerticalAnchor.absolute(40)
                        ),
                        BiomeFilter.biome() // 只在合适的生物群系中生成
                )
        ));

        // 注册放置的特征 - 深层铝矿石
        context.register(DEEPSLATE_ALUMINUM_ORE_PLACED, new PlacedFeature(
                deepslateAluminumOre,
                List.of(
                        CountPlacement.of(12), // 每区块12次尝试
                        InSquarePlacement.spread(), // 在区块内随机分布
                        HeightRangePlacement.uniform( // 在Y=-64到Y=-24之间均匀分布
                                VerticalAnchor.absolute(-64),
                                VerticalAnchor.absolute(-24)
                        ),
                        BiomeFilter.biome() // 只在合适的生物群系中生成
                )
        ));
    }

}