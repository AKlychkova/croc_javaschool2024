package ru.croc.javaschool2024.Klychkova.task11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ViewingHistory {
    private Path filePath;
    private List<List<Integer>> history;

    public ViewingHistory(Path path) {
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

    public List<List<Integer>> getHistory() {
        return new ArrayList<>(history);
    }

    public void update() {
        try {
            history = HistoryParser.parseFile(filePath);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла:" + e.getMessage());
        }
    }

    public Map<Integer,Integer> countViews() {
        Map<Integer,Integer> views = new HashMap<>();
        for (List<Integer> userHistory : history){
            for(int filmId : userHistory) {
                int count = views.getOrDefault(filmId, 0) + 1;
                views.put(filmId, count);
            }
        }
        return views;
    }
}
