package ru.croc.javaschool2024.Klychkova.task6;

import static java.lang.Math.abs;

/**
 * Класс, реализующий фабричный метод, возвращающий обработчики, проверяющие, что последовательность клеток на шахматной
 * доске может быть пройдена ходом коня.
 *
 * @author Dmitry Malenok
 */
public final class KnightsMoveCheckerFactory {

    /**
     * Конструктор.
     */
    private KnightsMoveCheckerFactory() {
        // Конструктор задан только для того, чтобы экземпляр класса случайно не создали.
    }

    /**
     * Возвращает обработчик, проверяющий, что последовательность клеток на шахматной доске может быть пройдена ходом
     * коня.
     *
     * @return обработчик, проверяющий, что последовательность клеток на шахматной доске может быть пройдена ходом коня
     */
    public static KnightsMoveChecker get() {
        return new KnightsMoveChecker() {
            private boolean checkMove(ChessPosition from, ChessPosition to) {
                return (abs(to.x() - from.x()) == 2 && abs(to.y() - from.y()) == 1) ||
                        (abs(to.x() - from.x()) == 1 && abs(to.y() - from.y()) == 2);
            }

            @Override
            public void check(String[] positions) throws IllegalMoveException {
                for (int i = 0; i < positions.length - 1; ++i) {
                    ChessPosition from = ChessPositionParser.parse(positions[i]);
                    ChessPosition to = ChessPositionParser.parse(positions[i + 1]);
                    if (!checkMove(from, to)) {
                        throw new IllegalMoveException(from,to);
                    }
                }
            }
        };
    }
}
