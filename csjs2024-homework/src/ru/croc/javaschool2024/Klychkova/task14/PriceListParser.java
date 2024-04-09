package ru.croc.javaschool2024.Klychkova.task14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class PriceListParser {
    private PriceListParser() {
        // Конструктор задан только для того, чтобы экземпляр класса случайно не создали.
    }

    record Material(String name, BigDecimal price) {}

    private static PriceListParser.Material parseLine(String priceString) {
        String[] substrings = priceString.split(",");
        if (substrings.length != 2) {
            throw new IllegalArgumentException(
                    "Строка должна содержать название и цену, разделенные запятой."
            );
        }
        double price;
        try {
            price = Double.parseDouble(substrings[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Цена должна быть вещественным числом");
        }
        return new PriceListParser.Material(substrings[0], BigDecimal.valueOf(price));
    }

    public static Map<String, BigDecimal> parseFile(final Path path) throws IOException {
        Map<String, BigDecimal> priceList = new HashMap<>();
        try (BufferedReader r = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            while ((line = r.readLine()) != null) {
                PriceListParser.Material material = parseLine(line);
                priceList.put(material.name, material.price);
            }
        }
        return priceList;
    }
}
