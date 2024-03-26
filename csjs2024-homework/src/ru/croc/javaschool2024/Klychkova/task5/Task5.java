package ru.croc.javaschool2024.Klychkova.task5;

import static java.lang.Thread.sleep;

public class Task5 {
    public static void main(String[] args) throws InterruptedException {
        Order order = new Order(
                "Клычкова Анастасия Дмитриевна",
                "+79990002545",
                new Product("9785699448944","Хроники Нарнии", 785.0,
                        "Древние мифы, старинные предания и волшебные сказки, детские впечатления и взрослые размышления"),
                new Product("9785041604820", "Благие знамения", 739.0,
                        "Мир закончится в субботу. В следующую субботу на свмом деле. Где-то перед ужином."));
        System.out.println(order.getCreatedDate().toString());
        sleep(5000);
        order.collect();
        System.out.println(order.getCollectedDate().toString());
        sleep(5000);
        order.close();
        System.out.println(order.getClosedDate().toString());

        System.out.println(order.getNotificationText());
    }
}
