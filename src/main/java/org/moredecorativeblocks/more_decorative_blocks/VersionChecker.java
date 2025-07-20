package org.moredecorativeblocks.more_decorative_blocks;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class VersionChecker {
    private static final String GITHUB_API_URL = "https://api.github.com/repos/MBI-Team/More-Decorative-Blocks/releases/latest";

    public static String getLatestVersion() throws IOException {
        URL url = new URL(GITHUB_API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        if (connection.getResponseCode() != 200) {
            throw new IOException("Failed to fetch version: HTTP code " + connection.getResponseCode());
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        // 提取版本号（假设返回的是 JSON，如 {"tag_name":"v1.0.0"}）
        String jsonResponse = response.toString();
        String version = extractValue(jsonResponse, "\"tag_name\"\\s*:\\s*\"([^\"]+)\"");

        return version != null ? version : "unknown";
    }

    private static String extractValue(String json, String regex) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
