package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 纯逻辑的版本号比较工具，不依赖任何 Minecraft 类，便于单元测试。
 * <p>
 * 模组的版本号由 Gradle 生成，格式为：
 * {@code <major>.<minor>.<patch>-<pre-release>-<git hash>[+<build>]}，
 * 例如 {@code 1.5.0-alpha-a96478c+20261004}；
 * 而 GitHub Release 上的 tag 可能只是 {@code 1.5.0-alpha-a96478c}。
 * <p>
 * 因此比较规则为：
 * <ol>
 *   <li>去掉可选的 {@code v} 前缀与 {@code +build} 构建元数据；</li>
 *   <li>比较点分数字主体（{@code 1.5.0}），缺位补 0；</li>
 *   <li>数字主体相同时比较预发布标识的优先级：正式版 &gt; rc &gt; beta &gt; alpha &gt; dev/snapshot。</li>
 * </ol>
 */
public final class VersionComparator {

    /**
     * 无法解析出版本数字时使用的优先级（永远视为“不比当前版本新”）。
     */
    private static final int UNKNOWN_PRIORITY = -1;

    private VersionComparator() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 比较两个版本号。
     *
     * @param current 当前版本
     * @param latest  远端最新版本
     * @return 大于 0 表示 {@code latest} 比 {@code current} 新；0 表示相同或无法比较；小于 0 表示更旧
     */
    public static int compare(String current, String latest) {
        ParsedVersion currentVersion = parse(current);
        ParsedVersion latestVersion = parse(latest);
        if (currentVersion == null || latestVersion == null) {
            return 0;
        }

        int size = Math.max(currentVersion.numbers.size(), latestVersion.numbers.size());
        for (int i = 0; i < size; i++) {
            int currentNumber = i < currentVersion.numbers.size() ? currentVersion.numbers.get(i) : 0;
            int latestNumber = i < latestVersion.numbers.size() ? latestVersion.numbers.get(i) : 0;
            if (latestNumber != currentNumber) {
                return Integer.compare(latestNumber, currentNumber);
            }
        }

        return Integer.compare(latestVersion.qualifierPriority, currentVersion.qualifierPriority);
    }

    /**
     * 判断 {@code latest} 是否比 {@code current} 新。
     */
    public static boolean isNewer(String current, String latest) {
        return compare(current, latest) > 0;
    }

    private static ParsedVersion parse(String version) {
        if (version == null || version.isBlank()) {
            return null;
        }

        String value = version.trim();
        if (value.startsWith("v") || value.startsWith("V")) {
            value = value.substring(1);
        }

        // 去掉构建元数据（+20261004）
        int buildIndex = value.indexOf('+');
        if (buildIndex >= 0) {
            value = value.substring(0, buildIndex);
        }

        // 分离数字主体与预发布标识（1.5.0-alpha-abc123 -> 1.5.0 / alpha-abc123）
        String core = value;
        String qualifier = "";
        int qualifierIndex = value.indexOf('-');
        if (qualifierIndex >= 0) {
            core = value.substring(0, qualifierIndex);
            qualifier = value.substring(qualifierIndex + 1);
        }

        List<Integer> numbers = parseNumbers(core);
        if (numbers.isEmpty()) {
            return null;
        }

        return new ParsedVersion(numbers, qualifierPriority(qualifier));
    }

    private static List<Integer> parseNumbers(String core) {
        List<Integer> numbers = new ArrayList<>();
        for (String part : core.split("\\.")) {
            if (part.isEmpty()) {
                continue;
            }
            if (!Character.isDigit(part.charAt(0))) {
                // 一旦出现非数字段，后面的内容不再参与数字比较
                break;
            }
            int end = 0;
            while (end < part.length() && Character.isDigit(part.charAt(end))) {
                end++;
            }
            try {
                numbers.add(Integer.parseInt(part.substring(0, end)));
            } catch (NumberFormatException e) {
                // 数字过大时按 0 处理，避免版本检查导致崩溃
                numbers.add(0);
            }
        }
        return numbers;
    }

    private static int qualifierPriority(String qualifier) {
        if (qualifier == null || qualifier.isBlank()) {
            // 没有预发布标识 => 正式版
            return 5;
        }

        // 只取第一段，例如 "alpha-a96478c" -> "alpha"
        String first = qualifier.split("[-.]")[0].toLowerCase(Locale.ROOT);
        return switch (first) {
            case "release", "stable", "final" -> 5;
            case "rc" -> 4;
            case "beta" -> 3;
            case "alpha" -> 2;
            case "pre", "preview", "experimental", "dev", "snapshot", "nightly" -> 1;
            default -> UNKNOWN_PRIORITY;
        };
    }

    private record ParsedVersion(List<Integer> numbers, int qualifierPriority) {
    }
}
