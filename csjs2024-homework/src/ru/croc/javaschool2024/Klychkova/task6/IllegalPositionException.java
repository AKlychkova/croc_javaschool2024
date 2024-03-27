package ru.croc.javaschool2024.Klychkova.task6;

public class IllegalPositionException extends RuntimeException {
    private final String position;

    public String getPosition() {
        return position;
    }

    public IllegalPositionException(String message, String position) {
        super(message);
        this.position = position;
    }
}
