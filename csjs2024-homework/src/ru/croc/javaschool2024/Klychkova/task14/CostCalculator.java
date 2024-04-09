package ru.croc.javaschool2024.Klychkova.task14;

import java.math.BigDecimal;
import java.util.Map;

public class CostCalculator {
    private final Map<String, BigDecimal> priceList;
    private final Map<String, Integer> purchaseList;    // список необходимых материалов, полученный от прораба

    public CostCalculator(Map<String, BigDecimal> priceList, Map<String, Integer> purchaseList) {
        this.priceList = priceList;
        this.purchaseList = purchaseList;
    }

    public BigDecimal calculateCost() {
        BigDecimal sum = BigDecimal.valueOf(0);
        for (var entry : purchaseList.entrySet()) {
            sum = sum.add(priceList.get(entry.getKey()).multiply(BigDecimal.valueOf(entry.getValue())));
        }
        return sum;
    }
}
