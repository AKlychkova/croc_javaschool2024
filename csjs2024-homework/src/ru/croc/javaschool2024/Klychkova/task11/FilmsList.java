package ru.croc.javaschool2024.Klychkova.task11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class FilmsList {
    private Path filePath;
    private Map<Integer, String> list;

    public FilmsList(Path path) {
        setFilePath(path);
        update();
    }

    public Path getFilePath() {
        return Paths.get(filePath.toString());
    }

    public void setFilePath(Path path) {
        if (Files.isReadable(path)) {
            this.filePath = path;
        }
        else {
            throw new IllegalArgumentException(
                    String.format("Файл %s не доступен для чтения", path.toAbsolutePath())
            );
        }
    }

    public String getName(int id) {
        return list.get(id);
    }

    public void update() {
        try {
            list = FilmParser.parseFile(filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла с фильмами: " + e.getMessage());
        }
    }
}
