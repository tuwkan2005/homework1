package ru.digitalhabbits.homework1.service;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static java.util.Arrays.stream;
import static org.slf4j.LoggerFactory.getLogger;

public class FileEngine {

    private static final String RESULT_FILE_PATTERN = "results-%s.txt";
    private static final String RESULT_DIR = "results";
    private static final String RESULT_EXT = "txt";
    private static final Logger logger = getLogger(FileEngine.class);

    public boolean writeToFile(@Nonnull String text, @Nonnull String pluginName) {

        final String currentDir = System.getProperty("user.dir");
        final File resultFile = new File(String.format(currentDir + "/" + RESULT_DIR + "/" + RESULT_FILE_PATTERN, pluginName));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(resultFile))) {

            writer.write(text);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public void cleanResultDir() {
        final String currentDir = System.getProperty("user.dir");
        final File resultDir = new File(currentDir + "/" + RESULT_DIR);
        resultDir.mkdir();
        stream(resultDir.list((dir, name) -> name.endsWith(RESULT_EXT)))
                .forEach(fileName -> new File(resultDir + "/" + fileName).delete());
    }
}
