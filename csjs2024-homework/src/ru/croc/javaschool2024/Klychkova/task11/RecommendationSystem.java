package ru.croc.javaschool2024.Klychkova.task11;

import java.util.*;

public class RecommendationSystem {
    private final FilmsList filmsList;
    private final ViewingHistory history;

    public RecommendationSystem(FilmsList filmsList, ViewingHistory history) {
        this.filmsList = filmsList;
        this.history = history;
    }

    private List<Integer> selectFilms(List<Integer> targetHistory) {
        Set<Integer> recommendedFilms = new HashSet<>();
        for (List<Integer> userHistory : history.getHistory()) {
            int size = userHistory.size();
            userHistory.removeAll(targetHistory);
            if (size - userHistory.size() >= (targetHistory.size() + 1) / 2) {
                recommendedFilms.addAll(userHistory);
            }
        }
        return new ArrayList<>(recommendedFilms);
    }

    public String getRecommendation(List<Integer> targetHistory) {
        // обновляем истории просмотра и список фильмов
        filmsList.update();
        history.update();

        // считаем количество просмотров по каждому из фильмов
        Map<Integer, Integer> views = history.countViews();

        // отбираем фильмы
        List<Integer> recommendations = selectFilms(targetHistory);

        // сортируем по количеству просмотров по убыванию
        recommendations.sort((id1, id2) -> views.get(id2).compareTo(views.get(id1)));

        if (!recommendations.isEmpty()) {
            return filmsList.getName(recommendations.get(0));
        }
        else {
            return "-";
        }
    }
}
