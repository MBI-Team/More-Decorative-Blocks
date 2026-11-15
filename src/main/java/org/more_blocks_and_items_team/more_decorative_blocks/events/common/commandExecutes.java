package org.more_blocks_and_items_team.more_decorative_blocks.events.common;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

/**
 * 通用（客户端 / 专用服务器都能执行）的命令实现。
 * <p>
 * 注意：本类运行在 common 代码路径上，<b>不能</b>引用任何客户端专属类（如 {@code Minecraft}、GUI 界面），
 * 需要界面的调试命令请放在 {@code events.client.ClientCommands}。
 */
public class commandExecutes {

    /**
     * 显示模组版本信息
     */
    public static int MDBVersionCMD(CommandContext<CommandSourceStack> context) {
        context.getSource().sendSuccess(() -> Component.literal("---More Decorative Blocks---"), true);
        context.getSource().sendSuccess(() -> Component.literal("Your current version: " + mod_version), true);
        context.getSource().sendSuccess(() -> Component.literal("All Right Reserved ©More Blocks And Items Team"), true);
        return 1;
    }
}
