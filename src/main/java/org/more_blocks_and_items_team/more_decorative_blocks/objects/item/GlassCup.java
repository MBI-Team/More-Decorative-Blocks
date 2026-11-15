package org.more_blocks_and_items_team.more_decorative_blocks.objects.item;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import org.jetbrains.annotations.NotNull;

/**
 * 玻璃杯。
 * <p>
 * 放入橱柜的逻辑由 {@code Cupboard#useItemOn} 负责（方块交互先于物品的 {@code useOn} 被调用），
 * 因此这里不需要、也不应该拦截交互。
 */
public class GlassCup extends Item {

    public GlassCup(Properties prop) {
        super(prop);
    }

    @Override
    public @NotNull InteractionResult useOn(@NotNull UseOnContext context) {
        // 玻璃杯不是 BlockItem，无法放置成方块。
        // 这里必须返回 PASS：原实现在非橱柜方块上返回 SUCCESS，
        // 会“吞掉”这次右键（例如无法继续用其他逻辑处理该方块），
        // 而且只列举了铁橱柜与橡木橱柜，漏掉了金合欢橱柜。
        return InteractionResult.PASS;
    }
}

