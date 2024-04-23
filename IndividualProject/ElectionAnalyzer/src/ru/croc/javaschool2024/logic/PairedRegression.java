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
            double x_mean = Statistics.mean(x);
            double y_mean = Statistics.mean(y);
            beta = 0.0;
            for (int i = 0; i < n; ++i) {
                beta += (x[i] - x_mean) * (y[i] - y_mean);
            }
            beta /= Arrays.stream(x).map(x_i -> pow(x_i - x_mean, 2)).sum();
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
