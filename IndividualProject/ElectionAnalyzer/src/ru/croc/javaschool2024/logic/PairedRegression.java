package ru.croc.javaschool2024.logic;

import java.util.Arrays;

import static java.lang.Math.pow;

/**
 * Класс для рассчета парной линейной регрессии (y = βx + α).
 */
public class PairedRegression {
    private Double alpha = null;
    private Double beta = null;
    private final double[] x;
    private final double[] y;
    int n;

    /**
     * @param x Значения независимой переменной.
     * @param y Значения зависимой переменной.
     * @throws IllegalArgumentException Если размер x не равен размеру y.
     */
    public PairedRegression(double[] x, double[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Несогласованность размеров значений зависимой и независимой переменных");
        }
        this.x = x;
        this.y = y;
        this.n = x.length;
    }

    /**
     * @return Свободный член α.
     */
    public double getAlpha() {
        if (alpha == null) {
            alpha = Statistics.mean(y) - getBeta() * Statistics.mean(x);
        }
        return alpha;
    }

    /**
     * @return Коэффициент регрессии β.
     */
    public double getBeta() {
        if (beta == null) {
            double xMean = Statistics.mean(x);
            double yMean = Statistics.mean(y);
            beta = 0.0;
            for (int i = 0; i < n; ++i) {
                beta += (x[i] - xMean) * (y[i] - yMean);
            }
            beta /= Arrays.stream(x).map(xI -> pow(xI - xMean, 2)).sum();
        }
        return beta;
    }

    /**
     * @param x Значение независимой переменной.
     * @return Предсказание зависимой переменной = βx + α.
     */
    public double predict(double x) {
        return getBeta() * x + getAlpha();
    }
}
