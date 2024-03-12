package ru.croc.javaschool2024.surname.task1;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Scanner;

public class Task1 {
    private static Triangle readTriangle() {
        Point[] vertexes = new Point[3];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 3; ++i) {
            System.out.printf("Введите координату х вершины №%d:\n", i+1);
            var x = in.nextDouble();
            System.out.printf("Введите координату y вершины №%d:\n", i+1);
            var y = in.nextDouble();
            vertexes[i] = new Point(x, y);
        }
        in.close();
        return new Triangle(vertexes[0],vertexes[1],vertexes[2]);
    }

    public static void main(String[] args) {
        Triangle triangle = readTriangle();
        System.out.printf(Locale.US,"Площадь треугольника: %.1f", triangle.getSquare());
    }
}