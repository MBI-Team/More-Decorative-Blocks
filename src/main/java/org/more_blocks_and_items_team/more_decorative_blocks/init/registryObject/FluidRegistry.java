package org.more_blocks_and_items_team.more_decorative_blocks.init.registryObject;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.more_blocks_and_items_team.more_decorative_blocks.objects.fluid.MudFluid;

import static org.more_blocks_and_items_team.more_decorative_blocks.tools.getModInformation.MODID;

public class FluidRegistry {
    // 创建流体注册器
    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(BuiltInRegistries.FLUID, MODID);

    // 创建泥浆流体
    public static final DeferredHolder<Fluid, MudFluid.Source> MUD_FLUID = FLUIDS.register("mud", MudFluid.Source::new);
    public static final DeferredBlock<LiquidBlock> MUD_BLOCK = BLOCKS.register("mud", () ->
            new LiquidBlock(FluidRegistry.MUD_FLUID.get(), BlockBehaviour.Properties.of()
                    .noCollission()
                    .strength(100.0F)
                    .noLootTable()
                    .mapColor(MapColor.TERRACOTTA_CYAN)
                    .replaceable()
            )
    );
    public static final DeferredItem<BucketItem> MUD_BUCKET = ITEMS.register("mud_bucket", () ->
            new BucketItem(FluidRegistry.MUD_FLUID.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1))
    );
    public static final DeferredHolder<Fluid, MudFluid.Flowing> MUD_FLUID_FLOWING = FLUIDS.register("mud_flowing", MudFluid.Flowing::new);
    // 流体方块注册
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // 桶物品注册
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);

}