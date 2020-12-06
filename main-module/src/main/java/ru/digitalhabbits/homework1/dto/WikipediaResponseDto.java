package ru.digitalhabbits.homework1.dto;

import java.util.Map;
import java.util.Set;

public class WikipediaResponseDto {

    private String batchcomplete;

    private Query query;

    private static class Query {
        private Normalized[] normalized;
        private Map<String, WikiPage> pages;
    }

    private static class Normalized {
        private String from;
        private String to;
    }

    private static class WikiPage {
        private Long pageid;
        private Long ns;
        private String title;
        private String extract;
    }

    public String getExtractText() {

        Set<String> keySet = query.pages.keySet();

        if (keySet.isEmpty()) {
            return "";
        }

        String key = "";

        for(String _key: keySet) {
            key = _key;
            break;
        }

        String extractText = query.pages.get(key).extract;

        if (extractText == null) {
            return "";
        }

        return extractText.replaceAll("\\\\n", "\n");
    }
}
