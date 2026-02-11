package org.more_blocks_and_items_team.more_decorative_blocks.events.common;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

public class commandExecutes {
    public static int MDBVersionCMD(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("---More Decorative Blocks---"), true);
        context.getSource().sendSuccess(() -> Component.literal("Your current version: " + mod_version), true);
        context.getSource().sendSuccess(() -> Component.literal("All Right Reserved ©More Blocks And Items Team"), true);
        return 1;
    }
}
