package ru.croc.javaschool2024.surname.task1;

import java.security.InvalidParameterException;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Triangle {
    private final Point vertex1;
    private final Point vertex2;
    private final Point vertex3;

    public Triangle(Point vertex1, Point vertex2, Point vertex3) {
        if (vertex1 == null || vertex2 == null || vertex3 == null) {
            throw new NullPointerException();
        }
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
        this.vertex3 = vertex3;
    }

    public Point getVertex1() {
        return vertex1;
    }

    public Point getVertex2() {
        return vertex2;
    }

    public Point getVertex3() {
        return vertex3;
    }

    /**
     * @return the length of side between vertex 1 and vertex 2
     */
    public double getSide12() {
        return sqrt(pow(vertex1.x() - vertex2.x(), 2) + pow(vertex1.y() - vertex2.y(), 2));
    }

    /**
     * @return the length of side between vertex 2 and vertex 3
     */
    public double getSide23() {
        return sqrt(pow(vertex2.x() - vertex3.x(), 2) + pow(vertex2.y() - vertex3.y(), 2));
    }

    /**
     * @return the length of side between vertex 3 and vertex 1
     */
    public double getSide31() {
        return sqrt(pow(vertex1.x() - vertex3.x(), 2) + pow(vertex1.y() - vertex3.y(), 2));
    }

    /**
     * Calculate square of the triangle
     * @return Square
     */
    public double getSquare() {
        double a = getSide12();
        double b = getSide23();
        double c = getSide31();
        double p = (a + b + c) / 2;

        return sqrt(p*(p-a)*(p-b)*(p-c));
    }
}
