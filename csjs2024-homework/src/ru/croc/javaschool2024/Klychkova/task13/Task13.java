package ru.croc.javaschool2024.Klychkova.task13;

import ru.croc.javaschool2024.Klychkova.task13.dish.Dish;
import ru.croc.javaschool2024.Klychkova.task13.dish.DishCategory;
import ru.croc.javaschool2024.Klychkova.task13.kitchener.Kitchener;

import java.util.*;

public class Task13 {
    static List<Kitchener> kitcheners = List.of(
            new Kitchener(1, "Георгий Седов"),
            new Kitchener(2, "Георгий Брусилов"),
            new Kitchener(3, "Владимир Русанов")
    );

    static Dish kozuli = new Dish(
            "Козули",
            "-",
            List.of("Ржаная мука", "Вода", "Сахар", "Пряности", "Яйца"),
            DishCategory.DESSERT,
            (byte) 98,
            (byte) 100
    );

    static Dish mezenCoffee = new Dish(
            "Кофе по-мезенски",
            "-",
            List.of("Кофе", "Вода", "Соль", "Пряности"),
            DishCategory.BEVERAGE,
            (byte) 50,
            (byte) 70
    );

    static Dish fishSoup = new Dish(
            "Поморская уха",
            "-",
            List.of("Треска", "Вода", "Лук", "Морковь", "Картофель", "Специи"),
            DishCategory.SOUP,
            (byte) 73,
            (byte) 88
    );

    static Dish shangi = new Dish(
            "Шаньги",
            "-",
            List.of("Мука", "Молоко", "Яйца", "Сметана", "Соль", "Сахар"),
            DishCategory.DESSERT,
            (byte) 80,
            (byte) 90
    );

    static Map<Kitchener, Set<Dish>> map = new HashMap<Kitchener, Set<Dish>>();

    static private void fillMap() {
        map.put(kitcheners.get(0), Set.of(kozuli, shangi, mezenCoffee));
        map.put(kitcheners.get(1), Set.of(fishSoup, shangi));
        map.put(kitcheners.get(2), Set.of(mezenCoffee, fishSoup));
    }

    public static void main(String[] args) {
        fillMap();
        Kitchen kitchen = new Kitchen(map);
        System.out.println("По умолчанию");
        kitchen.generateMenu(
                kitcheners,
                List.of(),
                3
        ).forEach(System.out::println);

        System.out.println("\n\nБез десертов");
        kitchen.generateMenu(
                kitcheners,
                List.of(),
                dish -> dish.getCategory() != DishCategory.DESSERT,
                3
        ).forEach(System.out::println);

        System.out.println("\n\nОдин повар");
        kitchen.generateMenu(
                kitcheners.subList(1, 2),
                List.of(),
                3
        ).forEach(System.out::println);

        System.out.println("\n\nБез яиц");
        kitchen.generateMenu(
                kitcheners,
                List.of("Яйца"),
                3
        ).forEach(System.out::println);

        System.out.println("\n\nТолько с пряностями");
        kitchen.generateMenu(
                kitcheners,
                List.of(),
                dish -> dish.getIngredients().contains("Пряности"),
                3
        ).forEach(System.out::println);

        System.out.println("\n\nТолько одно блюдо");
        kitchen.generateMenu(
                kitcheners,
                List.of(),
                1
        ).forEach(System.out::println);

        System.out.println("\n\nОценка прислуги >= 90");
        kitchen.generateMenu(
                kitcheners,
                List.of(),
                dish -> dish.getCourtiersAssessment() >= 90,
                3
        ).forEach(System.out::println);
    }
}
