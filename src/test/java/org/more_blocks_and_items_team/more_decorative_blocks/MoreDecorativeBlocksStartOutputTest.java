package org.more_blocks_and_items_team.more_decorative_blocks;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

/**
 * More_decorative_blocks.startOutput() 方法的单元测试
 */
public class MoreDecorativeBlocksStartOutputTest {

    private Logger mockLogger;

    @BeforeEach
    public void setUp() throws Exception {
        // 创建mock logger
        mockLogger = mock(Logger.class);

        // 使用反射替换LOGGER字段
        Field loggerField = More_decorative_blocks.class.getDeclaredField("LOGGER");
        loggerField.setAccessible(true);

        // 在Java 17+中，我们需要使用特殊的技巧来修改static final字段
        try {
            // 尝试使用modifiers字段的方式（在旧版本Java中有效）
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(loggerField, loggerField.getModifiers() & ~java.lang.reflect.Modifier.FINAL);
            loggerField.set(null, mockLogger);
        } catch (Exception e) {
            // 在Java 17+中，尝试使用Unsafe来修改final字段
            try {
                // 获取Unsafe实例
                Class<?> unsafeClass = Class.forName("sun.misc.Unsafe");
                Field unsafeField = unsafeClass.getDeclaredField("theUnsafe");
                unsafeField.setAccessible(true);
                Object unsafe = unsafeField.get(null);

                // 计算字段偏移量并修改字段值
                Method staticFieldOffset = unsafeClass.getMethod("staticFieldOffset", Field.class);
                Method putObject = unsafeClass.getMethod("putObject", Object.class, long.class, Object.class);

                long offset = (long) staticFieldOffset.invoke(unsafe, loggerField);
                putObject.invoke(unsafe, More_decorative_blocks.class, offset, mockLogger);
            } catch (Exception ex) {
                // 如果Unsafe也不行，尝试另一种方法
                try {
                    // 使用ReflectionFactory创建新的字段
                    Class<?> reflectionFactoryClass = Class.forName("jdk.internal.reflect.ReflectionFactory");
                    Method getReflectionFactory = reflectionFactoryClass.getDeclaredMethod("getReflectionFactory");
                    Object reflectionFactory = getReflectionFactory.invoke(null);

                    Field[] fields = More_decorative_blocks.class.getDeclaredFields();
                    for (int i = 0; i < fields.length; i++) {
                        if ("LOGGER".equals(fields[i].getName())) {
                            fields[i].setAccessible(true);
                            fields[i].set(null, mockLogger);
                            break;
                        }
                    }
                } catch (Exception exc) {
                    // 最后一种方法：直接通过字段设置（某些JVM可能允许）
                    loggerField.set(null, mockLogger);
                }
            }
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        // 由于我们无法可靠地恢复final字段，这里不进行特殊处理
        // 每次测试运行时都会创建新的mock对象
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

        // 验证日志输出 - 注意某些消息会多次出现
        verify(mockLogger, times(4)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
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

        // 验证日志输出 - 注意某些消息会多次出现
        verify(mockLogger, times(4)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
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

        // 验证日志输出 - 注意某些消息会多次出现
        verify(mockLogger, times(4)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
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

        // 验证日志输出 - 注意某些消息会多次出现
        verify(mockLogger, times(4)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
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

        // 验证日志输出 - 注意某些消息会多次出现
        verify(mockLogger, times(4)).info("▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄");
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