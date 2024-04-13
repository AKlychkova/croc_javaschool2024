package ru.croc.javaschool2024.Klychkova.task13;

import ru.croc.javaschool2024.Klychkova.task13.dish.Dish;
import ru.croc.javaschool2024.Klychkova.task13.kitchener.Kitchener;

import java.util.*;
import java.util.function.Predicate;

public class Kitchen {
    private final Map<Kitchener, Set<Dish>> kitchenerDishesMap;

    public Kitchen(Map<Kitchener, Set<Dish>> kitchenerDishesMap) {
        this.kitchenerDishesMap = kitchenerDishesMap;
    }

    /**
     * Метод добавления нового повара
     *
     * @param kitchener новый повор
     * @param dishes    блюда, которые он умеет готовить
     */
    public void addKitchener(Kitchener kitchener, Set<Dish> dishes) {
        kitchenerDishesMap.put(kitchener, dishes);
    }

    /**
     * Метод удаления повара
     *
     * @param id id повара, которого необходимо удалить
     */
    public void removeKitchener(long id) {
        // Т.к. equals() поворов переопределен, можем удалить повара создав повара с таким же id
        kitchenerDishesMap.remove(new Kitchener(id, ""));
    }

    /**
     * Метод генерации меню.
     *
     * @param workingKitcheners      Список сотрудников, работающих в нужный день
     * @param unavailableIngredients Недоступные (по причине отсутствия их на кухне) ингредиенты
     * @param maxLength              Максимально допустимая длина генерируемого меню
     * @return Перечень блюд, которые возможно приготовить, отсортированный по оценкам, не длиннее maxLength
     */
    public Collection<Dish> generateMenu(
            Collection<Kitchener> workingKitcheners,
            Collection<String> unavailableIngredients,
            int maxLength
    ) {
        return generateMenu(workingKitcheners, unavailableIngredients, dish -> true, maxLength);
    }

    /**
     * Метод генерации меню с учётом дополнительный требований.
     *
     * @param workingKitcheners      Список сотрудников, работающих в нужный день
     * @param unavailableIngredients Недоступные (по причине отсутствия их на кухне) ингредиенты
     * @param additionalConditions   Дополнительные требования
     * @param maxLength              Максимально допустимая длина генерируемого меню
     * @return Перечень блюд, которые возможно приготовить, отсортированный по оценкам, не длиннее maxLength
     */
    public Collection<Dish> generateMenu(
            Collection<Kitchener> workingKitcheners,
            Collection<String> unavailableIngredients,
            Predicate<Dish> additionalConditions,
            int maxLength
    ) {
        return kitchenerDishesMap.entrySet().stream()
                // отбираем блюда, которые куня способна приготовить c данным составом поворов
                .filter(entry -> workingKitcheners.contains(entry.getKey()))
                .flatMap(entry -> entry.getValue().stream())
                // отбираем блюда, которые куня способна приготовить c данным набором продуктов
                .filter(dish -> dish.getIngredients().stream().noneMatch(unavailableIngredients::contains))
                // оставляем только уникальные
                .distinct()
                // проверяем условия
                .filter(additionalConditions)
                // сортируем по оценкам
                .sorted(Comparator
                        .comparing(Dish::getKingAssessment)
                        .thenComparing(Dish::getCourtiersAssessment)
                        .reversed())
                // ограничиваем размер
                .limit(maxLength)
                .toList();
    }

    /**
     * Метод добавления новых блюд поварам, прошедшим обучение
     *
     * @param newDishes  новые блюда
     * @param kitcheners повара, прошедшие обучение
     */
    public void addDishes(Collection<Dish> newDishes, Collection<Kitchener> kitcheners) {
        kitcheners.forEach(kitchener -> kitchenerDishesMap.get(kitchener).addAll(newDishes));
    }
}
