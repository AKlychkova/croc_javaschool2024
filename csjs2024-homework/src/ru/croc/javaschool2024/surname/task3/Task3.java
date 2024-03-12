package ru.croc.javaschool2024.surname.task3;

public class Task3 {
    /**
     * Calculate the sum of the arithmetic progression
     * @param start start element
     * @param diff difference of progression
     * @param num amount of elements
     * @return sum of the arithmetic progression
     */
    public static long arithmeticProgressionSum(int start, int diff, int num) {
        long sum = start;
        for (int i = 1; i < num; ++i) {
            sum += start + (long) i * diff;
        }
        return sum;
    }

    public static void main(String[] args) {
        var start = Integer.parseInt(args[0]);
        var diff = Integer.parseInt(args[1]);
        var num = Integer.parseInt(args[2]);
        System.out.println(arithmeticProgressionSum(start, diff, num));
    }
}
