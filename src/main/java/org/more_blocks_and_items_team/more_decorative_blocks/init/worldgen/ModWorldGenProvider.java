package org.more_blocks_and_items_team.more_decorative_blocks.init.worldgen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, context -> {
                System.out.println("Bootstrapping configured features");
                ModOreConfigurations.bootstrap(context);
                System.out.println("Finished bootstrapping configured features");
            })
            .add(Registries.PLACED_FEATURE, context -> {
                System.out.println("Bootstrapping placed features");
                ModOrePlacements.bootstrap(context);
                System.out.println("Finished bootstrapping placed features");
            });

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of("more_decorative_blocks"));
        System.out.println("ModWorldGenProvider initialized with namespace: more_decorative_blocks");
    }

    @Override
    public @NotNull String getName() {
        return "More Decorative Blocks World Gen";
    }
}