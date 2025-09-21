package org.more_blocks_and_items_team.more_decorative_blocks.worldgen;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject.FluidRegistry;

public class ModMudFluidFeature extends Feature<NoneFeatureConfiguration> {
    public ModMudFluidFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos origin = context.origin();
        RandomSource random = context.random();

        // 随机生成泥浆的位置（在地表附近）
        BlockPos pos = origin.offset(
                random.nextInt(8) - random.nextInt(8),
                random.nextInt(4) - random.nextInt(4),
                random.nextInt(8) - random.nextInt(8)
        );

        // 检查是否是合适的生成位置（在地表上）
        if (!level.isEmptyBlock(pos)) {
            return false;
        }

        // 检查下方是否是固体方块
        BlockPos belowPos = pos.below();
        if (!level.getBlockState(belowPos).isSolid()) {
            return false;
        }

        // 放置泥浆
        BlockState mudState = FluidRegistry.MUD_BLOCK.get().defaultBlockState();
        level.setBlock(pos, mudState, 2);

        return true;
    }
}