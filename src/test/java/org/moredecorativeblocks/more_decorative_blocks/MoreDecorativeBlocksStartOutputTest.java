package org.moredecorativeblocks.more_decorative_blocks;

import com.mojang.logging.LogUtils;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

/**
 * More_decorative_blocks.startOutput() 方法的单元测试
 */
public class MoreDecorativeBlocksStartOutputTest {

    private MockedStatic<LogUtils> mockedLogUtils;
    private Logger mockLogger;

    @BeforeEach
    public void setUp() {
        // Mock LogUtils.getLogger()
        mockedLogUtils = Mockito.mockStatic(LogUtils.class);
        mockLogger = mock(Logger.class);
        mockedLogUtils.when(LogUtils::getLogger).thenReturn(mockLogger);
    }

    @AfterEach
    public void tearDown() {
        // 关闭静态mock以避免影响其他测试
        mockedLogUtils.close();
    }

    /**
     * 设置 mod_version 字段值的工具方法
     */
    private void setModVersion(String version) throws Exception {
        Field field = More_decorative_blocks.class.getDeclaredField("mod_version");
        field.setAccessible(true);
        field.set(null, version);
    }

    /**
     * 测试预发布版本的输出
     */
    @Test
    public void testStartOutputWithPreReleaseVersion() throws Exception {
        // 设置版本为预发布版本
        setModVersion("1.0.0-pre");

        // 调用测试方法
        More_decorative_blocks.startOutput();

        // 验证日志输出
        verify(mockLogger, times(1)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        verify(mockLogger, times(1)).info("                Copyright More Blocks and Items Team                ");
        verify(mockLogger, times(1)).info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        verify(mockLogger, times(1)).info("                █    █    █ █       █    █");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        verify(mockLogger, times(1)).info("modid:more_decorative_blocks");
        verify(mockLogger, times(1)).warn("Be careful,you are use test version,it's not stable.");
        verify(mockLogger, times(1)).info("             All right ©More Blocks and Items Team 2025             ");
    }

    /**
     * 测试稳定版本的输出
     */
    @Test
    public void testStartOutputWithStableVersion() throws Exception {
        // 设置版本为稳定版本
        setModVersion("1.0.0-stable");

        // 调用测试方法
        More_decorative_blocks.startOutput();

        // 验证日志输出
        verify(mockLogger, times(1)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        verify(mockLogger, times(1)).info("                Copyright More Blocks and Items Team                ");
        verify(mockLogger, times(1)).info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        verify(mockLogger, times(1)).info("                █    █    █ █       █    █");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        verify(mockLogger, times(1)).info("modid:more_decorative_blocks");
        verify(mockLogger, times(1)).warn("The stable release,don't worried for game crash.");
        verify(mockLogger, times(1)).info("             All right ©More Blocks and Items Team 2025             ");
    }

    /**
     * 测试开发版本的输出
     */
    @Test
    public void testStartOutputWithDevVersion() throws Exception {
        // 设置版本为开发版本
        setModVersion("1.0.0-dev");

        // 调用测试方法
        More_decorative_blocks.startOutput();

        // 验证日志输出
        verify(mockLogger, times(1)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        verify(mockLogger, times(1)).info("                Copyright More Blocks and Items Team                ");
        verify(mockLogger, times(1)).info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        verify(mockLogger, times(1)).info("                █    █    █ █       █    █");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        verify(mockLogger, times(1)).info("modid:more_decorative_blocks");
        verify(mockLogger, times(1)).warn("The internal testing version.");
        verify(mockLogger, times(1)).info("             All right ©More Blocks and Items Team 2025             ");
    }

    /**
     * 测试发布候选版本的输出
     */
    @Test
    public void testStartOutputWithRCVersion() throws Exception {
        // 设置版本为发布候选版本
        setModVersion("1.0.0-rc1");

        // 调用测试方法
        More_decorative_blocks.startOutput();

        // 验证日志输出
        verify(mockLogger, times(1)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        verify(mockLogger, times(1)).info("                Copyright More Blocks and Items Team                ");
        verify(mockLogger, times(1)).info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        verify(mockLogger, times(1)).info("                █    █    █ █       █    █");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        verify(mockLogger, times(1)).info("modid:more_decorative_blocks");
        verify(mockLogger, times(1)).warn("The release candidate version.");
        verify(mockLogger, times(1)).info("             All right ©More Blocks and Items Team 2025             ");
    }

    /**
     * 测试自定义版本的输出（不匹配任何特定类型）
     */
    @Test
    public void testStartOutputWithCustomVersion() throws Exception {
        // 设置版本为自定义版本
        setModVersion("1.0.0-custom");

        // 调用测试方法
        More_decorative_blocks.startOutput();

        // 验证日志输出
        verify(mockLogger, times(1)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
        verify(mockLogger, times(1)).info("                Copyright More Blocks and Items Team                ");
        verify(mockLogger, times(1)).info("                █▀▀▀▀█▀▀▀▀█ █▀▀▀▀▀▀▀▄ ▀▀▀█▀▀▀");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀    █");
        verify(mockLogger, times(1)).info("                █    █    █ █       █    █");
        verify(mockLogger, times(1)).info("                █    █    █ █▄▄▄▄▄▄▄▀ ▄▄▄█▄▄▄");
        verify(mockLogger, times(1)).info("modid:more_decorative_blocks");
        // 验证没有警告输出
        verify(mockLogger, never()).warn(anyString());
        verify(mockLogger, times(1)).info("             All right ©More Blocks and Items Team 2025             ");
    }
}
