package ru.croc.javaschool2024.Klychkova.task15;

public record Client(int id, String name, String surname, String phone) {
    public static Client getFromLine(String line) {
        String[] substrings = line.split(",");
        if (substrings.length != 7) {
            throw new IllegalArgumentException(
                    "Неправильный формат строки"
            );
        }
        return new Client(Integer.parseInt(substrings[0]), substrings[2],substrings[1], substrings[3]);
    }

}
