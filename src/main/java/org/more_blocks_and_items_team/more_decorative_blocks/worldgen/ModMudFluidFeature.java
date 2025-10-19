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

        // 生成泥浆池的大小 (3x3到7x7)
        int radius = 1 + random.nextInt(3);
        boolean placed = false;

        // 在区域内生成泥浆池
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                // 创建圆形/椭圆形池塘而不是方形
                if (x * x + z * z <= radius * radius) {
                    BlockPos pos = origin.offset(x, 0, z);

                    // 检查是否是合适的生成位置（在地表上）
                    if (level.isEmptyBlock(pos)) {
                        // 检查下方是否是固体方块
                        BlockPos belowPos = pos.below();
                        if (level.getBlockState(belowPos).isSolid()) {
                            // 放置泥浆
                            BlockState mudState = FluidRegistry.MUD_BLOCK.get().defaultBlockState();
                            level.setBlock(pos, mudState, 2);
                            placed = true;
                        }
                    }
                }
            }
        }

        return placed;
    }
}