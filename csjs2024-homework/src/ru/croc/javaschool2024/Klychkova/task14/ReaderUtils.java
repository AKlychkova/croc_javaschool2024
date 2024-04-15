package ru.croc.javaschool2024.Klychkova.task14;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;

public class ReaderUtils {
    public static final String COMMA_DELIMITER = ",";

    private ReaderUtils() {
    }

    public static <K, V> Map<K, V> readMapFromCsv(
            final Path path,
            Function<String, K> keyConverter,
            Function<String, V> valueConverter
    ) throws IOException {
        Map<K, V> map = new HashMap<>();
        try (Scanner scanner = new Scanner(path.toFile())) {
            while (scanner.hasNextLine()) {
                Map.Entry<K, V> entry = getEntryFromLine(scanner.nextLine(),keyConverter,valueConverter);
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

    private static <K, V> Map.Entry<K, V> getEntryFromLine(
            String line,
            Function<String, K> keyConverter,
            Function<String, V> valueConverter) {
        String[] substrings = line.split(COMMA_DELIMITER);
        if (substrings.length != 2) {
            throw new IllegalArgumentException(
                    "Строка должна содержать ключ и значение, разделенные запятой"
            );
        }
        return Map.entry(keyConverter.apply(substrings[0]), valueConverter.apply(substrings[1]));
    }
}
