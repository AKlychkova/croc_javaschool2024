package ru.croc.javaschool2024.Klychkova.task6;

/**
 * Исключение, выбрасываемое в случае, если при перемещении шахматного коня из текущей клетки в следующую происходит с
 * нарушением правил.
 * 
 * @author Dmitry Malenok
 */
public class IllegalMoveException extends Exception {
    private final ChessPosition from;
    private final ChessPosition to;

    public IllegalMoveException(ChessPosition from, ChessPosition to) {
        super(String.format("%s -> %s", from.toString(),to.toString()));
        this.from = from;
        this.to = to;
    }

    public ChessPosition getFrom() {
        return from;
    }

    public ChessPosition getTo() {
        return to;
    }
}
