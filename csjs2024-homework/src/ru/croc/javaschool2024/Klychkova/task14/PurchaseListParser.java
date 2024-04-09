package ru.croc.javaschool2024.Klychkova.task14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PurchaseListParser {
    private PurchaseListParser() {
        // Конструктор задан только для того, чтобы экземпляр класса случайно не создали.
    }

    record Material(String name, Integer number) {}

    private static PurchaseListParser.Material parseLine(String priceString) {
        String[] substrings = priceString.split(",");
        if (substrings.length != 2) {
            throw new IllegalArgumentException(
                    "Строка должна содержать название и количество, разделенные запятой."
            );
        }
        int number;
        try {
            number = Integer.parseInt(substrings[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Количество должно быть целым числом");
        }
        return new PurchaseListParser.Material(substrings[0], number);
    }

    public static Map<String, Integer> parseFile(final Path path) throws IOException {
        Map<String, Integer> purchaseList = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                PurchaseListParser.Material material = parseLine(line);
                purchaseList.put(material.name, material.number);
            }
        }
        return purchaseList;
    }
}
