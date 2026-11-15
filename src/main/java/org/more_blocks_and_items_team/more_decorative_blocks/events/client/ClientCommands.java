package org.more_blocks_and_items_team.more_decorative_blocks.events.client;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

/**
 * 只在客户端注册的调试命令。
 * <p>
 * 这些命令会直接创建 GUI 界面（依赖 {@link Minecraft}），因此必须与通用命令分开放置：
 * 如果在 {@code events/common} 里直接引用客户端类，专用服务器加载命令类时会抛出
 * {@code NoClassDefFoundError}。
 */
public final class ClientCommands {

    private ClientCommands() {
        throw new IllegalStateException("Utility class");
    }

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        // 重置版本检查状态
        dispatcher.register(Commands.literal("testversioncheck")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    ClientVersionCheckHandler.resetCheckStatus();
                    context.getSource().sendSuccess(
                            () -> Component.literal("版本检查状态已重置，将在下次登录时检查版本"), false);
                    return 1;
                })
        );

        // 强制显示更新界面
        dispatcher.register(Commands.literal("testversionui")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    VersionCheckUtils.VersionCheckResult testResult = new VersionCheckUtils.VersionCheckResult(
                            true,
                            mod_version,
                            "999.999.999",
                            "https://github.com/MBI-Team/More-Decorative-Blocks/releases"
                    );

                    Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(
                            new VersionCheckScreen(testResult, Minecraft.getInstance().screen)
                    ));

                    context.getSource().sendSuccess(() -> Component.literal("强制显示更新界面"), false);
                    return 1;
                })
        );

        // 强制显示版本检查失败界面
        dispatcher.register(Commands.literal("testerrorui")
                .requires(source -> source.hasPermission(2))
                .executes(context -> {
                    Minecraft.getInstance().execute(() -> Minecraft.getInstance().setScreen(
                            new VersionCheckErrorScreen(Minecraft.getInstance().screen)
                    ));

                    context.getSource().sendSuccess(() -> Component.literal("显示版本检查错误界面"), false);
                    return 1;
                })
        );
    }
}
