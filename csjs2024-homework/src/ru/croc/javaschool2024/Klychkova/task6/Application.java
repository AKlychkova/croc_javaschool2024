package ru.croc.javaschool2024.Klychkova.task6;

/**
 * Приложение, проверяющее возможность прохождения последовательности клеток на шахматной доске ходом коня.
 */
public final class Application {

    /**
     * Основной метод приложения.
     *
     * @param args аргументы
     */
    public static void main(final String[] args) {
        KnightsMoveChecker checker = KnightsMoveCheckerFactory.get();
        try {
            checker.check(args);
            System.out.print("OK");
        } catch (IllegalMoveException e) {
            System.out.print("конь так не ходит: " + e.getMessage());
        }
    }
}
