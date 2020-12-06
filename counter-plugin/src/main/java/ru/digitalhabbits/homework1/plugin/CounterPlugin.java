package ru.digitalhabbits.homework1.plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CounterPlugin
        implements PluginInterface {

    private final String wordRegExp = "\\b[a-zA-Z][a-zA-Z.0-9]*\\b";

    @Nullable
    @Override
    public String apply(@Nonnull String text) {

        long lines = text.lines().count();

        Matcher wordMatcher = Pattern.compile(wordRegExp).matcher(text);
        long words = wordMatcher.results().count();

        long letters = text.chars().count();

        return String.format("%d;%d;%d", lines, words, letters);
    }
}
