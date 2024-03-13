package ru.croc.javaschool2024.Klychkova.task2;


import java.security.InvalidParameterException;
import java.util.Locale;

import static java.lang.Math.log;
import static java.lang.Math.pow;

public class Task2 {

    /**
     * Format and display the specified size in bytes
     * @param bytes size in bytes
     */
    public static void printBytes(long bytes) {
        String[] unites = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
        if (bytes < 0) {
            throw new InvalidParameterException("Size cannot be less than zero");
        } else if (bytes == 0){
            System.out.printf(Locale.US,"%.1f %s", 0.0, unites[0]);
            return;
        }
        int exponent = (int)(log(bytes) / log(1024));
        double result = bytes * pow(1024, -exponent);
        System.out.printf(Locale.US,"%.1f %s", result, unites[exponent]);
    }

    public static void main(String[] args) {
        printBytes(Long.parseLong(args[0]));
    }
}