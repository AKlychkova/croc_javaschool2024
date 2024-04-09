package ru.croc.javaschool2024.Klychkova.task12;

import java.time.LocalTime;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;

public class Task12 {
    public static <T, R> Function<T, R> ternaryOperator(Predicate<T> condition,
                                                        Function<T, R> ifTrue,
                                                        Function<T, R> ifFalse) {
        return t -> {
            if (condition.test(t)) {
                return ifTrue.apply(t);
            } else {
                return ifFalse.apply(t);
            }
        };
    }

    public static void main(String[] args) {
        String greeting = Task12.<LocalTime, String>ternaryOperator(
                        time -> time.isBefore(LocalTime.of(15, 0)),
                        time -> String.format("Добрый день!\uD83C\uDF1E Текущее время %d часов %d минут", time.getHour(), time.getMinute()),
                        time -> String.format("Добрый вечер!\uD83C\uDF1B Текущее время %d часов %d минут", time.getHour(), time.getMinute()))
                .apply(LocalTime.now());
        System.out.println(greeting);

        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int absValue = Task12.<Integer, Integer>ternaryOperator(i -> i >= 0, i -> i, i -> -i).apply(a);
        System.out.println("|a| = " + absValue);
    }
}
