package org.more_blocks_and_items_team.more_decorative_blocks.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.more_blocks_and_items_team.more_decorative_blocks.init.getModInformation.GITHUB_API_URL;

public class VersionChecker {
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
        String version = extractValue(jsonResponse);

        return version != null ? version : "unknown";
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
