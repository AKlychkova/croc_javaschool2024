package ru.croc.javaschool2024.Klychkova.task14;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;

public class Task14 {
    public static void main(String[] args) {
        if (args.length != 2) {
            throw new IllegalArgumentException("Должно быть два аргумента командной строки " +
                    "(путь к прайслисту и путь у списку покупок)");
        }
        String priceListPath = args[0];
        String purchaseListPath = args[1];
        try {
            Map<String, BigDecimal> priceList = ReaderUtils.readMapFromCsv(
                    Paths.get(priceListPath),
                    Function.identity(),
                    str -> BigDecimal.valueOf(Integer.parseInt(str))
            );
            Map<String, Integer> purchaseList = ReaderUtils.readMapFromCsv(
                    Paths.get(purchaseListPath),
                    Function.identity(),
                    Integer::valueOf
            );
            CostCalculator calculator = new CostCalculator(priceList, purchaseList);
            System.out.println(calculator.calculateCost().stripTrailingZeros().toPlainString());
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
