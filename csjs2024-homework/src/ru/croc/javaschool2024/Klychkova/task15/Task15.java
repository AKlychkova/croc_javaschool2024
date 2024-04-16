package ru.croc.javaschool2024.Klychkova.task15;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Task15 {
    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Неправильный формат аргументов командной строки");
        }
        Path path = Paths.get(args[0]);

        VetDataBase data = new VetDataBase();
        try {
            data.addFromCSV(path);
            data.printClients();
            data.printPets();
            data.printRelationship();
        } catch (SQLException e) {
            System.out.println("Не получилось обратиться к базе данных: " + e.getMessage());
        } catch (FileNotFoundException e) {
            System.out.printf("Файл %s не найден", path);
        }
    }
}
