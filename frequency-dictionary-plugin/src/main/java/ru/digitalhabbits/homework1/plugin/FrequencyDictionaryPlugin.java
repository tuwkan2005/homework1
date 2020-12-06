package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.Locale;
import java.util.Map;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FrequencyDictionaryPlugin
        implements PluginInterface {

    private final Pattern pattern = Pattern.compile("(\\b[a-zA-Z][a-zA-Z.0-9]*\\b)");

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        Map<String, Integer> map = pattern.matcher(text.toLowerCase()).results()
                .collect(Collectors.groupingBy(MatchResult::group, Collectors.summingInt(p -> 1)));

        StringBuilder builder = new StringBuilder();

        map.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(p -> builder.append(p.getKey()).append(" ").append(p.getValue()).append("\n"));

        return builder.toString();
    }
}
