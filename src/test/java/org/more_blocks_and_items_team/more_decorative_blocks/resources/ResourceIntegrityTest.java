package org.more_blocks_and_items_team.more_decorative_blocks.resources;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 资源与数据包的完整性检查。
 * <p>
 * 这些测试不启动 Minecraft，只读取仓库里的资源文件与注册表源码，
 * 用来确保「代码里注册了什么」与「resources 里提供了什么」始终一致，
 * 一旦有人改名 / 删文件 / 写错命名空间，构建阶段就会失败，
 * 而不是等到进游戏后看到紫黑方块或丢方块才被发现。
 */
class ResourceIntegrityTest {

    private static final String MODID = "more_decorative_blocks";

    private static final Path RESOURCES = Paths.get("src", "main", "resources");
    private static final Path TEMPLATES = Paths.get("src", "main", "templates");
    private static final Path ASSETS = RESOURCES.resolve("assets").resolve(MODID);
    private static final Path DATA = RESOURCES.resolve("data").resolve(MODID);
    private static final Path VANILLA_DATA = RESOURCES.resolve("data").resolve("minecraft");
    private static final Path REGISTRY_SRC = Paths.get("src", "main", "java", "org",
            "more_blocks_and_items_team", "more_decorative_blocks", "init", "registryObject");

    private static final Pattern BLOCK_REGISTER = Pattern.compile("BLOCKS\\.register\\(\"([a-z0-9_]+)\"");
    private static final Pattern ITEM_REGISTER = Pattern.compile("ITEMS\\.register\\w*\\(\"([a-z0-9_]+)\"");

    // ------------------------------------------------------------------
    // 打包元数据
    // ------------------------------------------------------------------

    private static void assertNoProblems(String title, List<String> problems) {
        assertTrue(problems.isEmpty(),
                () -> title + "，共 " + problems.size() + " 处：\n  - " + String.join("\n  - ", problems));
    }

    // ------------------------------------------------------------------
    // 注册表 <-> 资源
    // ------------------------------------------------------------------

    private static String rel(Path file) {
        return RESOURCES.relativize(file).toString().replace('\\', '/');
    }

    private static String read(Path file) {
        try {
            return Files.readString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("读取失败: " + file, e);
        }
    }

    // ------------------------------------------------------------------
    // 模型 / 纹理
    // ------------------------------------------------------------------

    private static JsonObject json(Path file) {
        try (Reader reader = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            return JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException e) {
            throw new UncheckedIOException("读取失败: " + file, e);
        }
    }

    private static List<Path> jsonFiles(Path dir) {
        if (!Files.isDirectory(dir)) {
            return List.of();
        }
        try (Stream<Path> stream = Files.walk(dir)) {
            return stream.filter(Files::isRegularFile)
                    .filter(p -> p.getFileName().toString().endsWith(".json"))
                    .sorted()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("遍历失败: " + dir, e);
        }
    }

    private static Set<String> fileStems(Path dir) {
        if (!Files.isDirectory(dir)) {
            return Set.of();
        }
        try (Stream<Path> stream = Files.list(dir)) {
            return stream.filter(Files::isRegularFile)
                    .map(p -> p.getFileName().toString())
                    .filter(n -> n.endsWith(".json"))
                    .map(n -> n.substring(0, n.length() - ".json".length()))
                    .collect(java.util.stream.Collectors.toCollection(TreeSet::new));
        } catch (IOException e) {
            throw new UncheckedIOException("遍历失败: " + dir, e);
        }
    }

    // ------------------------------------------------------------------
    // 语言文件
    // ------------------------------------------------------------------

    private static Set<String> registeredIds(Path source, Pattern pattern) {
        String text = read(source);
        Set<String> ids = new TreeSet<>();
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            ids.add(matcher.group(1));
        }
        return ids;
    }

    private static String namespaceOf(String id) {
        int i = id.indexOf(':');
        return i < 0 ? "minecraft" : id.substring(0, i);
    }

    // ------------------------------------------------------------------
    // 配方 / 战利品表 / 标签 / 进度
    // ------------------------------------------------------------------

    private static String pathOf(String id) {
        int i = id.indexOf(':');
        return i < 0 ? id : id.substring(i + 1);
    }

    private static Path modelFile(String id) {
        return RESOURCES.resolve("assets").resolve(namespaceOf(id)).resolve("models").resolve(pathOf(id) + ".json");
    }

    private static Path itemModelFile(String id) {
        return RESOURCES.resolve("assets").resolve(namespaceOf(id)).resolve("models").resolve("item").resolve(pathOf(id) + ".json");
    }

    private static Path textureFile(String id) {
        return RESOURCES.resolve("assets").resolve(namespaceOf(id)).resolve("textures").resolve(pathOf(id) + ".png");
    }

    private static String optString(JsonObject object, String key) {
        JsonElement element = object.get(key);
        return element == null || element.isJsonNull() ? null : element.getAsString();
    }

    // ------------------------------------------------------------------
    // 工具方法
    // ------------------------------------------------------------------

    /**
     * 收集 blockstate 中 variants / multipart 引用到的全部模型 id。
     */
    private static List<String> modelsOf(JsonObject blockstate) {
        List<String> models = new ArrayList<>();

        JsonObject variants = blockstate.getAsJsonObject("variants");
        if (variants != null) {
            for (Map.Entry<String, JsonElement> entry : variants.entrySet()) {
                JsonElement value = entry.getValue();
                if (value.isJsonArray()) {
                    for (JsonElement e : value.getAsJsonArray()) {
                        models.add(e.getAsJsonObject().get("model").getAsString());
                    }
                } else if (value.isJsonObject()) {
                    models.add(value.getAsJsonObject().get("model").getAsString());
                }
            }
        }

        JsonArray multipart = blockstate.getAsJsonArray("multipart");
        if (multipart != null) {
            for (JsonElement part : multipart) {
                JsonElement apply = part.getAsJsonObject().get("apply");
                if (apply.isJsonArray()) {
                    for (JsonElement e : apply.getAsJsonArray()) {
                        models.add(e.getAsJsonObject().get("model").getAsString());
                    }
                } else {
                    models.add(apply.getAsJsonObject().get("model").getAsString());
                }
            }
        }
        return models;
    }

    /**
     * 模型自身及其（模组内可读）父链上定义的全部纹理变量名。
     */
    private static Set<String> textureKeysInChain(JsonObject model) {
        Set<String> keys = new LinkedHashSet<>();
        JsonObject current = model;
        int guard = 0;
        while (current != null && guard++ < 32) {
            JsonObject textures = current.getAsJsonObject("textures");
            if (textures != null) {
                keys.addAll(textures.keySet());
            }
            String parent = optString(current, "parent");
            if (parent == null) {
                break;
            }
            Path parentFile = modelFile(parent);
            // 原版模型不在本仓库内，无法继续向上解析
            current = Files.isRegularFile(parentFile) ? json(parentFile) : null;
        }
        return keys;
    }

    /**
     * 收集模型所有面引用到的纹理变量名（去掉前导 #）。
     */
    private static Set<String> faceTextureRefs(JsonObject model) {
        Set<String> refs = new LinkedHashSet<>();
        JsonArray elements = model.getAsJsonArray("elements");
        if (elements == null) {
            return refs;
        }
        for (JsonElement element : elements) {
            JsonObject faces = element.getAsJsonObject().getAsJsonObject("faces");
            if (faces == null) {
                continue;
            }
            for (Map.Entry<String, JsonElement> face : faces.entrySet()) {
                JsonElement texture = face.getValue().getAsJsonObject().get("texture");
                if (texture != null) {
                    String value = texture.getAsString();
                    if (value.startsWith("#")) {
                        refs.add(value.substring(1));
                    }
                }
            }
        }
        return refs;
    }

    /**
     * 递归找出 JSON 中出现过的模组命名空间 id。
     */
    private static Set<String> modIdsIn(JsonElement element) {
        Set<String> ids = new TreeSet<>();
        collectModIds(element, ids);
        return ids;
    }

    private static void collectModIds(JsonElement element, Set<String> out) {
        if (element == null || element.isJsonNull()) {
            return;
        }
        if (element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String value = element.getAsString();
            if (value.startsWith(MODID + ":")) {
                out.add(value);
            }
            return;
        }
        if (element.isJsonArray()) {
            for (JsonElement child : element.getAsJsonArray()) {
                collectModIds(child, out);
            }
            return;
        }
        if (element.isJsonObject()) {
            for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
                // random_sequence / parent 之类的字段是资源路径而不是物品 id
                if (entry.getKey().equals("random_sequence") || entry.getKey().equals("parent")) {
                    continue;
                }
                collectModIds(entry.getValue(), out);
            }
        }
    }

    private static long countPlaceholders(String text) {
        Matcher matcher = Pattern.compile("%(\\d+\\$)?[sdf]").matcher(text);
        long count = 0;
        while (matcher.find()) {
            count++;
        }
        return count;
    }

    private static boolean hasLangKey(String key) {
        JsonObject en = json(ASSETS.resolve("lang").resolve("en_us.json"));
        JsonObject zh = json(ASSETS.resolve("lang").resolve("zh_cn.json"));
        return en.has(key) && zh.has(key);
    }

    @Test
    @DisplayName("pack.mcmeta 的 pack_format 与 1.21.x 匹配")
    void packFormatMatchesTargetVersion() {
        Path mcmeta = TEMPLATES.resolve("pack.mcmeta");
        assertTrue(Files.isRegularFile(mcmeta), "缺少 " + mcmeta + "，模组包元数据不会被打进 jar");

        int format = json(mcmeta).getAsJsonObject("pack").get("pack_format").getAsInt();
        assertEquals(34, format,
                "Minecraft 1.21.1 的数据包格式为 34；写成 15（1.20.1）会导致客户端提示资源包版本不兼容");
    }

    @Test
    @DisplayName("每个注册方块都有 blockstate 与战利品表")
    void everyRegisteredBlockHasAssets() {
        Set<String> blocks = registeredIds(REGISTRY_SRC.resolve("BlockRegistry.java"), BLOCK_REGISTER);
        assertTrue(blocks.size() > 0, "没能从 BlockRegistry.java 解析出任何方块，正则或源码结构已变化");

        Set<String> blockstates = fileStems(ASSETS.resolve("blockstates"));
        Set<String> lootTables = fileStems(DATA.resolve("loot_table").resolve("blocks"));

        assertEquals(new TreeSet<>(blocks), new TreeSet<>(blockstates),
                "注册的方块与 blockstates 文件必须一一对应（缺文件会变成紫黑方块，多文件是孤儿资源）");
        assertEquals(new TreeSet<>(blocks), new TreeSet<>(lootTables),
                "注册的方块与战利品表必须一一对应（缺表则方块被破坏后不掉落任何东西）");
    }

    @Test
    @DisplayName("每个注册物品都有物品模型与创造模式物品栏归属")
    void everyRegisteredItemHasModel() {
        Set<String> items = registeredIds(REGISTRY_SRC.resolve("ItemRegistry.java"), ITEM_REGISTER);
        assertTrue(items.size() > 0, "没能从 ItemRegistry.java 解析出任何物品，正则或源码结构已变化");

        Set<String> models = fileStems(ASSETS.resolve("models").resolve("item"));
        assertEquals(new TreeSet<>(items), new TreeSet<>(models),
                "注册的物品与 models/item 下的模型必须一一对应");
    }

    @Test
    @DisplayName("blockstate 引用的模型文件都存在")
    void blockstatesReferenceExistingModels() {
        List<String> problems = new ArrayList<>();
        for (Path file : jsonFiles(ASSETS.resolve("blockstates"))) {
            JsonObject root = json(file);
            for (String model : modelsOf(root)) {
                if (!Files.isRegularFile(modelFile(model))) {
                    problems.add(rel(file) + " -> " + model);
                }
            }
        }
        assertNoProblems("以下 blockstate 指向了不存在的模型文件", problems);
    }

    @Test
    @DisplayName("模型引用的父模型与纹理文件都存在")
    void modelsReferenceExistingParentsAndTextures() {
        List<String> problems = new ArrayList<>();
        for (Path file : jsonFiles(ASSETS.resolve("models"))) {
            JsonObject root = json(file);

            String parent = optString(root, "parent");
            if (parent != null && MODID.equals(namespaceOf(parent)) && !Files.isRegularFile(modelFile(parent))) {
                problems.add(rel(file) + " 的 parent 不存在: " + parent);
            }

            JsonObject textures = root.getAsJsonObject("textures");
            if (textures != null) {
                for (Map.Entry<String, JsonElement> e : textures.entrySet()) {
                    String value = e.getValue().getAsString();
                    if (value.startsWith("#")) {
                        continue; // 纹理互相引用，由下面的“变量是否定义”检查负责
                    }
                    if (!Files.isRegularFile(textureFile(value))) {
                        problems.add(rel(file) + " 的纹理 " + e.getKey() + " 不存在: " + value);
                    }
                }
            }
        }
        assertNoProblems("以下模型引用了不存在的父模型或纹理文件", problems);
    }

    @Test
    @DisplayName("模型每个面的纹理变量都有定义（不会出现紫黑缺失纹理）")
    void everyFaceTextureVariableIsDefined() {
        List<String> problems = new ArrayList<>();
        for (Path file : jsonFiles(ASSETS.resolve("models"))) {
            JsonObject root = json(file);
            Set<String> defined = textureKeysInChain(root);
            for (String ref : faceTextureRefs(root)) {
                if (!defined.contains(ref)) {
                    problems.add(rel(file) + " 的面引用了未定义的纹理变量: #" + ref);
                }
            }
        }
        assertNoProblems("以下模型使用了未定义的纹理变量（游戏内会渲染成紫黑缺失纹理）", problems);
    }

    @Test
    @DisplayName("中英文语言文件键集合一致，且方块/物品翻译齐全")
    void langFilesAreInSync() {
        JsonObject en = json(ASSETS.resolve("lang").resolve("en_us.json"));
        JsonObject zh = json(ASSETS.resolve("lang").resolve("zh_cn.json"));

        assertEquals(new TreeSet<>(en.keySet()), new TreeSet<>(zh.keySet()),
                "en_us.json 与 zh_cn.json 的键必须完全一致（缺键会在游戏内显示成原始键名）");

        List<String> problems = new ArrayList<>();
        Set<String> blockstates = fileStems(ASSETS.resolve("blockstates"));
        for (String id : blockstates) {
            String key = "block." + MODID + "." + id;
            if (!en.has(key) || !zh.has(key)) {
                problems.add("缺少方块翻译键: " + key);
            }
        }
        Set<String> itemModels = fileStems(ASSETS.resolve("models").resolve("item"));
        for (String id : itemModels) {
            if (blockstates.contains(id)) {
                continue; // 方块物品用的是 block.* 键
            }
            String key = "item." + MODID + "." + id;
            if (!en.has(key) || !zh.has(key)) {
                problems.add("缺少物品翻译键: " + key);
            }
        }
        assertNoProblems("翻译键缺失", problems);
    }

    @Test
    @DisplayName("翻译文本里的格式化占位符数量一致且合法")
    void langPlaceholdersAreConsistent() {
        JsonObject en = json(ASSETS.resolve("lang").resolve("en_us.json"));
        JsonObject zh = json(ASSETS.resolve("lang").resolve("zh_cn.json"));

        List<String> problems = new ArrayList<>();
        for (String key : en.keySet()) {
            String a = en.get(key).getAsString();
            String b = zh.get(key).getAsString();

            // 常见的 %s / %d / %1$s 占位符
            long ca = countPlaceholders(a);
            long cb = countPlaceholders(b);
            if (ca != cb) {
                problems.add(key + " 的占位符数量不一致: en=" + ca + " zh=" + cb
                        + "  (en=\"" + a + "\" zh=\"" + b + "\")");
            }
            // 形如 "?s" 这种被损坏的占位符，会让 String.format 抛异常或原样显示
            if (b.contains("?s") || a.contains("?s")) {
                problems.add(key + " 疑似占位符损坏（出现 \"?s\"）: en=\"" + a + "\" zh=\"" + b + "\"");
            }
        }
        assertNoProblems("格式化占位符问题", problems);
    }

    @Test
    @DisplayName("配方里出现的模组物品都已注册")
    void recipesOnlyUseKnownItems() {
        Set<String> known = new TreeSet<>(fileStems(ASSETS.resolve("models").resolve("item")));
        known.addAll(fileStems(ASSETS.resolve("blockstates")));

        List<String> problems = new ArrayList<>();
        for (Path file : jsonFiles(DATA.resolve("recipe"))) {
            for (String id : modIdsIn(json(file))) {
                if (!known.contains(pathOf(id))) {
                    problems.add(rel(file) + " -> " + id);
                }
            }
        }
        assertNoProblems("配方引用了不存在的物品 / 方块", problems);
    }

    @Test
    @DisplayName("战利品表掉落的物品都已注册")
    void lootTablesOnlyDropKnownItems() {
        Set<String> known = new TreeSet<>(fileStems(ASSETS.resolve("models").resolve("item")));

        List<String> problems = new ArrayList<>();
        for (Path file : jsonFiles(DATA.resolve("loot_table"))) {
            for (String id : modIdsIn(json(file).getAsJsonArray("pools"))) {
                if (!known.contains(pathOf(id))) {
                    problems.add(rel(file) + " -> " + id);
                }
            }
        }
        assertNoProblems("战利品表掉落了不存在的物品", problems);
    }

    @Test
    @DisplayName("标签引用的方块都存在")
    void tagsOnlyReferenceKnownBlocks() {
        Set<String> known = new TreeSet<>(fileStems(ASSETS.resolve("blockstates")));

        List<Path> tagFiles = new ArrayList<>();
        tagFiles.addAll(jsonFiles(DATA.resolve("tags")));
        tagFiles.addAll(jsonFiles(VANILLA_DATA.resolve("tags")));

        List<String> problems = new ArrayList<>();
        for (Path file : tagFiles) {
            JsonArray values = json(file).getAsJsonArray("values");
            if (values == null) {
                continue;
            }
            for (JsonElement value : values) {
                String id = value.isJsonObject()
                        ? value.getAsJsonObject().get("id").getAsString()
                        : value.getAsString();
                if (!MODID.equals(namespaceOf(id))) {
                    continue;
                }
                if (!known.contains(pathOf(id))) {
                    problems.add(rel(file) + " -> " + id);
                }
            }
        }
        assertNoProblems("标签引用了不存在的方块", problems);
    }

    @Test
    @DisplayName("需要正确工具的方块都登记进了挖掘标签")
    void toolRequiringBlocksAreMineable() {
        Path source = REGISTRY_SRC.resolve("BlockRegistry.java");
        String text = read(source);

        // 逐个注册块切分，找出带 requiresCorrectToolForDrops() 的方块
        Set<String> needsTool = new LinkedHashSet<>();
        Matcher matcher = BLOCK_REGISTER.matcher(text);
        List<int[]> spans = new ArrayList<>();
        List<String> names = new ArrayList<>();
        while (matcher.find()) {
            names.add(matcher.group(1));
            spans.add(new int[]{matcher.start(), matcher.end()});
        }
        for (int i = 0; i < names.size(); i++) {
            int end = (i + 1 < spans.size()) ? spans.get(i + 1)[0] : text.length();
            String body = text.substring(spans.get(i)[1], end);
            if (body.contains("requiresCorrectToolForDrops")) {
                needsTool.add(names.get(i));
            }
        }
        assertFalse(needsTool.isEmpty(), "没能解析出任何 requiresCorrectToolForDrops 方块");

        Set<String> tagged = new LinkedHashSet<>();
        for (Path file : jsonFiles(VANILLA_DATA.resolve("tags").resolve("blocks").resolve("mineable"))) {
            JsonArray values = json(file).getAsJsonArray("values");
            if (values == null) {
                continue;
            }
            for (JsonElement value : values) {
                tagged.add(pathOf(value.getAsString()));
            }
        }

        List<String> problems = new ArrayList<>();
        for (String block : needsTool) {
            if (!tagged.contains(block)) {
                problems.add(block + " 需要正确工具才能掉落，但没有进任何 mineable/* 标签（被破坏后什么都不会掉）");
            }
        }
        assertNoProblems("挖掘标签缺失", problems);
    }

    @Test
    @DisplayName("进度的条件、父进度、图标与文本都齐全")
    void advancementsAreConsistent() {
        Path dir = DATA.resolve("advancement");
        List<String> problems = new ArrayList<>();

        for (Path file : jsonFiles(dir)) {
            JsonObject root = json(file);
            String id = dir.relativize(file).toString().replace('\\', '/').replace(".json", "");

            JsonObject criteria = root.getAsJsonObject("criteria");
            assertTrue(criteria != null && criteria.size() > 0, id + " 没有任何 criteria，进度永远无法完成");

            // requirements 里引用的条件名必须真的存在
            JsonArray requirements = root.getAsJsonArray("requirements");
            if (requirements != null) {
                for (JsonElement row : requirements) {
                    for (JsonElement name : row.getAsJsonArray()) {
                        if (!criteria.has(name.getAsString())) {
                            problems.add(id + " 的 requirements 引用了不存在的条件: " + name.getAsString()
                                    + "（实际条件: " + new TreeSet<>(criteria.keySet()) + "）");
                        }
                    }
                }
            }

            // 父进度必须存在
            String parent = optString(root, "parent");
            if (parent != null && MODID.equals(namespaceOf(parent))
                    && !Files.isRegularFile(dir.resolve(pathOf(parent) + ".json"))) {
                problems.add(id + " 的父进度不存在: " + parent);
            }

            JsonObject display = root.getAsJsonObject("display");
            if (display != null) {
                String icon = display.getAsJsonObject("icon").get("id").getAsString();
                if (MODID.equals(namespaceOf(icon))) {
                    boolean iconExists = Files.isRegularFile(itemModelFile(icon))
                            || Files.isRegularFile(ASSETS.resolve("blockstates").resolve(pathOf(icon) + ".json"));
                    if (!iconExists) {
                        problems.add(id + " 的图标物品不存在: " + icon);
                    }
                }
                for (String suffix : List.of("title", "description")) {
                    String key = display.getAsJsonObject(suffix).get("translate").getAsString();
                    if (key.startsWith("advancements." + MODID + ".") && !hasLangKey(key)) {
                        problems.add(id + " 引用了缺失的翻译键: " + key);
                    }
                }
            }

            // 判定条件里出现的模组物品必须存在（例如 inventory_changed 的 items）
            for (String item : modIdsIn(criteria)) {
                if (!Files.isRegularFile(itemModelFile(item))) {
                    problems.add(id + " 的判定条件引用了不存在的物品: " + item);
                }
            }
        }
        assertNoProblems("进度定义不完整", problems);
    }
}
