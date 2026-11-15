package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * {@link VersionComparator} 的单元测试。
 * <p>
 * 这些用例覆盖了原实现会出错的实际场景：
 * {@code alpha} 之类的预发布标识无法被解析成数字、版本段长度不一致、
 * 远端 tag 与本地版本号格式不同等。
 */
class VersionComparatorTest {

    @Test
    @DisplayName("相同版本不算有新版本")
    void sameVersionIsNotNewer() {
        assertFalse(VersionComparator.isNewer("1.5.0-alpha-a96478c+20261004", "1.5.0-alpha-a96478c"));
        assertFalse(VersionComparator.isNewer("1.5.0", "1.5.0"));
    }

    @Test
    @DisplayName("补丁 / 次要 / 主要版本升级都能被识别")
    void numericUpgradesAreDetected() {
        assertTrue(VersionComparator.isNewer("1.5.0-alpha-abc", "1.5.1-alpha-abc"));
        assertTrue(VersionComparator.isNewer("1.5.0-alpha-abc", "1.6.0-alpha-abc"));
        assertTrue(VersionComparator.isNewer("1.5.0-alpha-abc", "2.0.0-alpha-abc"));
        assertTrue(VersionComparator.isNewer("1.5.9", "1.10.0"), "字符串比较会把 1.10.0 判成更旧");
    }

    @Test
    @DisplayName("本地版本更新时不应提示更新")
    void newerLocalVersionIsNotReported() {
        assertFalse(VersionComparator.isNewer("1.6.0-alpha-abc", "1.5.0-alpha-abc"));
        assertFalse(VersionComparator.isNewer("2.0.0", "1.9.9"));
    }

    @Test
    @DisplayName("预发布标识按 正式版 > rc > beta > alpha > dev 排序")
    void qualifierPriority() {
        assertTrue(VersionComparator.isNewer("1.5.0-alpha", "1.5.0-beta"));
        assertTrue(VersionComparator.isNewer("1.5.0-beta", "1.5.0-rc"));
        assertTrue(VersionComparator.isNewer("1.5.0-rc", "1.5.0"));
        assertTrue(VersionComparator.isNewer("1.5.0-dev-abc", "1.5.0-release"));
        assertFalse(VersionComparator.isNewer("1.5.0", "1.5.0-rc"), "正式版不应被 rc 覆盖");
    }

    @Test
    @DisplayName("忽略构建元数据与 v 前缀")
    void ignoresBuildMetadataAndPrefix() {
        assertEquals(0, VersionComparator.compare("1.5.0+20260101", "1.5.0+20261231"));
        assertEquals(0, VersionComparator.compare("v1.5.0", "1.5.0"));
        assertTrue(VersionComparator.isNewer("v1.5.0", "1.6.0"));
    }

    @Test
    @DisplayName("无法解析的版本号不应崩溃，也不应误报更新")
    void unknownVersionsAreSafe() {
        assertFalse(VersionComparator.isNewer("1.5.0-alpha-abc", "unknown"));
        assertFalse(VersionComparator.isNewer("unknown", "1.5.0-alpha-abc"));
        assertFalse(VersionComparator.isNewer(null, "1.5.0"));
        assertFalse(VersionComparator.isNewer("1.5.0", null));
        assertEquals(0, VersionComparator.compare("", ""));
    }
}
