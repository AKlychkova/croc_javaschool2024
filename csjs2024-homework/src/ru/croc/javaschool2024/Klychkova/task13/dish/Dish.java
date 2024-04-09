package ru.croc.javaschool2024.Klychkova.task13.dish;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class Dish {
    private final String name;
    private final String recipe;
    private final Collection<String> ingredients;
    private final DishCategory category;
    byte kingAssessment;
    byte courtiersAssertion;

    public Dish(
            String name,
            String recipe,
            Collection<String> ingredients,
            DishCategory category,
            byte kingAssessment,
            byte courtiersAssessment
    ) {
        this.name = name;
        this.recipe = recipe;
        this.ingredients = ingredients;
//        if (category != DishCategory.BEVERAGE) {
//            this.category = category;
//        } else {
//            throw new IllegalArgumentException("Блюдо может быть только закуской, горячим или десертом");
//        }
        this.category = category;
        setKingAssessment(kingAssessment);
        setCourtiersAssertion(courtiersAssessment);
    }

    public DishCategory getCategory() {
        return category;
    }

    public Collection<String> getIngredients() {
        return new ArrayList<>(ingredients);
    }

    public String getName() {
        return name;
    }

    public byte getKingAssessment() {
        return kingAssessment;
    }

    public byte getCourtiersAssertion() {
        return courtiersAssertion;
    }


    // будем считать, что мнение короля и придворных о блюде может измениться, для этого определим сеттеры для оценок
    public void setKingAssessment(byte kingAssessment) {
        if (kingAssessment >= 0 && kingAssessment <= 100) {
            this.kingAssessment = kingAssessment;
        } else {
            throw new IllegalArgumentException("Оценка короля должна быть от 0 до 100.");
        }
    }

    public void setCourtiersAssertion(byte courtiersAssertion) {
        if (courtiersAssertion >= 0 && courtiersAssertion <= 100) {
            this.courtiersAssertion = courtiersAssertion;
        } else {
            throw new IllegalArgumentException("Оценка придворных должна быть от 0 до 100.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Objects.equals(name, dish.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
