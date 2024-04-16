package ru.croc.javaschool2024.Klychkova.task15;

public record Pet(int cardNumber, String name, int age) {
    public static Pet getFromLine(String line) {
        String[] substrings = line.split(",");
        if (substrings.length != 7) {
            throw new IllegalArgumentException(
                    "Неправильный формат строки"
            );
        }
        return new Pet(Integer.parseInt(substrings[4]), substrings[5], Integer.parseInt(substrings[6]));
    }
}
