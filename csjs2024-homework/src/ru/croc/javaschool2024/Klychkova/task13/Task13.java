package ru.croc.javaschool2024.Klychkova.task13;

import ru.croc.javaschool2024.Klychkova.task13.dish.Dish;
import ru.croc.javaschool2024.Klychkova.task13.kitchener.Kitchener;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Task13 {
    public static void main(String[] args) {
        Kitchener kitchener = new Kitchener(12, "Петров");
        var map = new HashMap<Kitchener, Set<Dish>>();
        map.put(kitchener, new HashSet<Dish>());
        Kitchen kitchen = new Kitchen(map);
        kitchen.removeKitchener(kitchener.getId());
    }
}
