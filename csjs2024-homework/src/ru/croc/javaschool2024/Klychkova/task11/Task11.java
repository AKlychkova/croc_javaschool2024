package ru.croc.javaschool2024.Klychkova.task11;

import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Task11 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Integer> userHistory = HistoryParser.parseLine(scanner.nextLine());
        var rsystem = new RecommendationSystem (
                new FilmsList(Paths.get("src\\ru\\croc\\javaschool2024\\Klychkova\\task11\\resources\\movies.txt")),
                new ViewingHistory(Paths.get("src\\ru\\croc\\javaschool2024\\Klychkova\\task11\\resources\\history.txt"))
        );
        System.out.println(rsystem.getRecommendation(userHistory));
    }
}
