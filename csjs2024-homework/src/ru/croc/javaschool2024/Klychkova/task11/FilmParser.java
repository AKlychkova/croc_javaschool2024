package ru.croc.javaschool2024.Klychkova.task11;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public final class FilmParser {
    private FilmParser() {
        // Конструктор задан только для того, чтобы экземпляр класса случайно не создали.
    }

    record Film(int id, String name) {}

    private static Film parseLine(final String film) {
        String[] substrings = film.split(",");
        if (substrings.length != 2) {
            throw new IllegalArgumentException(
                    "Строка должна содержать числовой идентификатор фильма и его название, разделенные запятой"
            );
        }
        int id;
        try {
            id = Integer.parseInt(substrings[0]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Идентификатор фильма должен быть числовым");
        }
        return new Film(id, substrings[1]);
    }

    public static Map<Integer, String> parseFile(final Path path) throws IOException {
        Map<Integer, String> films = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                Film film = parseLine(line);
                films.put(film.id, film.name);
            }
        }
        return films;
    }
}
