package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;
import ru.digitalhabbits.homework1.plugin.PluginInterface;

import javax.annotation.Nonnull;

import static org.slf4j.LoggerFactory.getLogger;

public class PluginEngine {

    private static final Logger logger = getLogger(PluginEngine.class);

    @Nonnull
    public <T extends PluginInterface> String applyPlugin(@Nonnull Class<T> cls, @Nonnull String text) {

        String result = "";

        try {

            T instance = cls.getConstructor().newInstance();
            result = (String) cls.getDeclaredMethod("apply", String.class).invoke(instance, text);

            if (result == null) {
                result = "";
            }

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return result;
    }
}
