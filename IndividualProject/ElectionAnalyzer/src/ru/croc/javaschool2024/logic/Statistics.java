package ru.croc.javaschool2024.logic;

import java.util.Arrays;

import static java.lang.Math.sqrt;

public class Statistics {
    private Statistics() {
    }

    /**
     * Считает среднее выборочное.
     * @param values Значения, для которых необходимо посчитать среднее.
     * @return Среднее выборочное.
     */
    public static double mean(double[] values) {
        return Arrays.stream(values).sum() / values.length;
    }

    /**
     * Считает выборочную дисперсию.
     * @param values Значения, для которых необходимо посчитать дисперсию.
     * @return Выборочная дисперсия.
     */
    public static double variance(double[] values) {
        return cov(values, values);
    }

    /**
     * Считает выборочное стандартное отклонение.
     * @param values Значения для которых необходимо найти стандартное отклонение.
     * @return Выборочное стандартное отклонение.
     */
    public static double std(double[] values) {
        return sqrt(variance(values));
    }

    /**
     * Считает выборочную ковариацию.
     * @param a Выборка 1.
     * @param b Выборка 2.
     * @return Выборочная ковариация.
     * @throws IllegalArgumentException Если размеры выборок не совпадают.
     */
    public static double cov(double[] a, double[] b) {
        if (a.length != b.length) {
            throw new IllegalArgumentException("Несогласование размеров выборок");
        }
        double a_mean = mean(a);
        double b_mean = mean(b);
        double cov = 0.0;
        for (int i = 0; i < a.length; ++i) {
            cov += (a[i] - a_mean) * (b[i] - b_mean);
        }
        return cov / a.length;
    }

    /**
     * Считает выборочную корреляцию.
     * @param a Выборка 1.
     * @param b Выборка 2.
     * @return Выборочная корреляция.
     */
    public static double corr(double[] a, double[] b) {
        return cov(a,b) / (std(a) * std(b));
    }
}
