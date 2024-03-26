package ru.croc.javaschool2024.Klychkova.task6;

public class Position implements ChessPosition {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalPositionException(
                    String.format("Position %s cannot be created", this)
            );
        }
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
