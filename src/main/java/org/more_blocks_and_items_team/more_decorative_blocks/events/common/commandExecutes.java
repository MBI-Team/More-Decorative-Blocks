package org.more_blocks_and_items_team.more_decorative_blocks.events.common;

import com.mojang.brigadier.context.CommandContext;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import org.more_blocks_and_items_team.more_decorative_blocks.events.client.VersionCheckErrorScreen;
import org.more_blocks_and_items_team.more_decorative_blocks.events.client.VersionCheckScreen;
import org.more_blocks_and_items_team.more_decorative_blocks.utils.VersionCheckUtils;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.mod_version;

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

    /**
     * 测试版本检查界面 - 强制显示更新提示
     */
    public static int testVersionUICMD(CommandContext<CommandSourceStack> context) {
        // 创建一个模拟的更新结果
        VersionCheckUtils.VersionCheckResult testResult = new VersionCheckUtils.VersionCheckResult(
                true,
                mod_version,
                "999.999.999",
                "https://github.com/MBI-Team/More-Decorative-Blocks/releases"
        );

        // 在客户端主线程中显示界面
        Minecraft.getInstance().execute(() -> {
            Minecraft.getInstance().setScreen(
                    new VersionCheckScreen(testResult, Minecraft.getInstance().screen)
            );
        });

        context.getSource().sendSuccess(() -> Component.literal("强制显示更新界面"), false);
        return 1;
    }

    /**
     * 测试错误界面 - 显示版本检查失败提示
     */
    public static int testErrorUICMD(CommandContext<CommandSourceStack> context) {
        // 直接显示错误界面
        Minecraft.getInstance().execute(() -> {
            Minecraft.getInstance().setScreen(
                    new VersionCheckErrorScreen(Minecraft.getInstance().screen)
            );
        });

        context.getSource().sendSuccess(() -> Component.literal("显示版本检查错误界面"), false);
        return 1;
    }
}
