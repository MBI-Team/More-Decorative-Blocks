package org.moredecorativeblocks.more_decorative_blocks.item;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import static org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.IRON_CUPBOARD;
import static org.moredecorativeblocks.more_decorative_blocks.registry.BlockRegistry.OAK_WOOD_CUPBOARD;
import static org.moredecorativeblocks.more_decorative_blocks.registry.ItemRegistry.GLASS_CUP;

public class GlassCup extends Item {
    static int gcn = 0;

    public GlassCup(Properties prop) {
        super(prop);
    }

    void shrink(int shrink, ItemStack stack, Player player, InteractionHand hand) {
        stack.shrink(shrink);  // ⭐关键代码：物品数量减1

        if (stack.isEmpty()) {
            player.setItemInHand(hand, ItemStack.EMPTY);
        }
        gcn += shrink;
    }

    @Override
    public @NotNull InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos clickedPos = context.getClickedPos();
        BlockState clickedState = level.getBlockState(clickedPos);
        Block clickedBlock = clickedState.getBlock();
        ItemStack stack = context.getItemInHand();
        InteractionHand hand = context.getHand();


        // 检测是否为自定义物品
        if (stack.getItem() == GLASS_CUP.get() && stack.getCount() >= 1) {
            if (player != null && !player.level().isClientSide) {
                if (player.getAbilities().instabuild) {
                    shrink(0, stack, player, hand);
                } else if (clickedBlock == IRON_CUPBOARD.get()) {
                    shrink(1, stack, player, hand);
                } else if (clickedBlock == OAK_WOOD_CUPBOARD.get()) {
                    shrink(1, stack, player, hand);
                }
                if (gcn == 17) {
                    gcn = 0;
                    shrink(-16, stack, player, hand);
                }
            }
        } else if (stack.isEmpty()) {
            gcn -= 1;
            shrink(-1, stack, player, hand);
        }


        return InteractionResult.FAIL;
    }
}
