package org.moredecorativeblocks.more_decorative_blocks.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.IRON_CUPBOARD;
import static org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.OAK_WOOD_CUPBOARD;

public class GlassCup extends Item {

    public GlassCup(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();

        // 只处理碗柜
        if (clickedBlock == IRON_CUPBOARD.get() || clickedBlock == OAK_WOOD_CUPBOARD.get()) {
            // 返回PASS，让方块的交互方法处理
            return InteractionResult.PASS;
        }

        // 对于其他方块，我们返回SUCCESS，表示物品已经处理（什么也不做）
        return InteractionResult.SUCCESS;
    }
}

