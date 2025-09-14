package org.more_blocks_and_items_team.more_decorative_blocks.init.worldgen;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.BlockRegistry;

import java.util.List;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class ModOreConfigurations {
    // 配置特征的资源键
    public static final ResourceKey<ConfiguredFeature<?, ?>> ALUMINUM_ORE = ResourceKey.create(
            net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "aluminum_ore")
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEPSLATE_ALUMINUM_ORE = ResourceKey.create(
            net.minecraft.core.registries.Registries.CONFIGURED_FEATURE,
            ResourceLocation.fromNamespaceAndPath(MODID, "deepslate_aluminum_ore")
    );

    /**
     * 注册配置特征
     */
    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        // 铝矿石配置
        context.register(ALUMINUM_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(net.minecraft.tags.BlockTags.STONE_ORE_REPLACEABLES),
                        BlockRegistry.ALUMINUM_ORE.get().defaultBlockState())), 9)));

        // 深层铝矿石配置
        context.register(DEEPSLATE_ALUMINUM_ORE, new ConfiguredFeature<>(Feature.ORE, new OreConfiguration(
                List.of(OreConfiguration.target(new TagMatchTest(net.minecraft.tags.BlockTags.DEEPSLATE_ORE_REPLACEABLES),
                        BlockRegistry.DEEPSLATE_ALUMINUM_ORE.get().defaultBlockState())), 12)));
    }

}