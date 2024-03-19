package ru.croc.javaschool2024.Klychkova.task6;

public class IllegalPositionException extends RuntimeException {
    private final String message;

    public IllegalPositionException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
