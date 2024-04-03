package ru.croc.javaschool2024.Klychkova.task11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public final class HistoryParser {
    private HistoryParser() {
        // Конструктор задан только для того, чтобы экземпляр класса случайно не создали.
    }

    public static List<Integer> parseLine(final String line){
        List<Integer> history = new ArrayList<>();
        String[] substrings = line.split(",");
        for (String substr: substrings) {
            history.add(Integer.parseInt(substr));
        }
        return history;
    }

    public static List<List<Integer>> parseFile(final Path path) throws IOException {
        List<List<Integer>> histories = new ArrayList<>();
        try (BufferedReader r = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                histories.add(parseLine(line));
            }
        }
        return histories;
    }
}
