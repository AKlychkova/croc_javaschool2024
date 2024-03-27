package ru.croc.javaschool2024.Klychkova.task6;

public record Position(int x, int y) implements ChessPosition {
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        if (x < 0 || x > 7 || y < 0 || y > 7) {
            throw new IllegalPositionException(
                    String.format("Position %s cannot be created", this),
                    this.toString()
            );
        }
    }

    @Override
    public String toString() {
        return String.format("%c%d", 'a' + x, y + 1);
    }
}
