package org.more_blocks_and_items_team.more_decorative_blocks.tools;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public class VersionChecker {
    private static final String GITHUB_API_URL = "https://api.github.com/repos/MBI-Team/More-Decorative-Blocks/releases/latest";

    public static String getLatestVersion() throws IOException {
        try {
            URI uri = URI.create(GITHUB_API_URL);
            URL url = uri.toURL();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            final String jsonResponse = getResponse(connection);
            String version = extractValue(jsonResponse);

            return version != null ? version : "unknown";
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid URI: " + e.getMessage(), e);
        }
    }

    private static @NotNull String getResponse(HttpURLConnection connection) throws IOException {
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
        return response.toString();
    }

    private static String extractValue(String json) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("\"tag_name\"\\s*:\\s*\"([^\"]+)\"");
        java.util.regex.Matcher matcher = pattern.matcher(json);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}