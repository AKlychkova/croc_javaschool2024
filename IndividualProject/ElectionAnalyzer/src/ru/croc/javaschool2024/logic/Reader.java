package ru.croc.javaschool2024.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

/**
 * Класс для чтения .csv файлов с данными об избирательных участках
 */
public class Reader {
    private Reader() {
    }

    /**
     * Читает .csv файл с данными в следующем формате:
     * Первая строка:
     * {},{},{кандидат_1},{кандидат_2},...
     * Остальные строки:
     * {Общее количество избирателей},
     * {Количество проголосовавших избирателей(действительных бюллетений)},
     * {Количество голосов отданных за кандидата 1},
     * {Количество голосов отданных за кандидата 2},...
     * @param path Путь к файлу с данными.
     * @return Данные об избирательных участках.
     */
    public static PollingStationsData readPollingStationsInfo(Path path) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            // Читаем имена кандидатов
            String[] names = parseNames(reader.readLine());
            // Читаем данные об участках
            // (Подразумевается, что анализ проводится над близкими по составу электората участками,
            // то есть участками, находящимися в одном или в нескольких соседних регионах.
            // В среднем по РФ на один регион приходится около тысячи и.у., ожидаемый объем данных до 5000 строк.)
            List<PollingStation> list = reader.lines().map(Reader::parsePollingStation).toList();
            return new PollingStationsData(names, list);
        }
    }

    /**
     * Преобразует первую строку файла в список имен кандидатов.
     * @param line Строка для преобразования.
     * @return Список имен кандидатов.
     */
    private static String[] parseNames(String line) {
        String[] substrs = line.split(",");
        return Arrays.copyOfRange(substrs, 2, substrs.length);
    }

    /**
     * Преобразует строку файла в данные об избирательном участке.
     * @param line Строка для преобразования.
     * @return Данные избирательного участка.
     */
    private static PollingStation parsePollingStation(String line) {
        int[] info = Arrays.stream(line.split(","))
                .mapToInt(Integer::parseInt)
                .toArray();

        return new PollingStation(
                info[0],
                info[1],
                Arrays.copyOfRange(info, 2, info.length)
        );
    }
}
