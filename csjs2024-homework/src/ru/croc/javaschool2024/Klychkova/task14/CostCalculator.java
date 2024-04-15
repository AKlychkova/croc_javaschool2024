package ru.croc.javaschool2024.Klychkova.task14;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

public class CostCalculator {
    private final Map<String, BigDecimal> priceList;
    private final Map<String, Integer> purchaseList;    // список необходимых материалов, полученный от прораба

    public CostCalculator(Map<String, BigDecimal> priceList, Map<String, Integer> purchaseList) {
        this.priceList = priceList;
        this.purchaseList = purchaseList;
    }

    public BigDecimal calculateCost() {
        Optional<BigDecimal> sum = purchaseList.entrySet().stream()
                .map(entry -> priceList.get(entry.getKey()).multiply(BigDecimal.valueOf(entry.getValue())))
                .reduce(BigDecimal::add);
        return sum.orElse(BigDecimal.ZERO);
    }
}
