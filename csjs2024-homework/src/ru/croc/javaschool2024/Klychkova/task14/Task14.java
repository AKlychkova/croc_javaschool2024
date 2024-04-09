package ru.croc.javaschool2024.Klychkova.task14;

import java.io.IOException;
import java.nio.file.Paths;

public class Task14 {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Должно быть два аргумента командной строки " +
                    "(путь к прайслисту и путь у списку покупок)");
        }
        String priceListPath = args[0];
        String purchaseListPath = args[1];

        try {
            CostCalculator calculator = new CostCalculator(
                    PriceListParser.parseFile(Paths.get(priceListPath)),
                    PurchaseListParser.parseFile(Paths.get(purchaseListPath))
            );
            System.out.println(calculator.calculateCost().stripTrailingZeros().toPlainString());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
