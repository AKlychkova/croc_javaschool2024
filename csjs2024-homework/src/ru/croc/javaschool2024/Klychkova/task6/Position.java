package ru.croc.javaschool2024.Klychkova.task6;

public class Position implements ChessPosition {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalPositionException(
                    String.format("Position (%d, %d) cannot be created, both coordinates must be in [0,7]", x, y)
            );
        }
        this.x = x;
        this.y = y;
    }

    @Override
    public int x() {
        return x;
    }

    @Override
    public int y() {
        return y;
    }

    @Override
    public String toString() {
        return String.format("%c%d", 'a' + x, y + 1);
    }
}
