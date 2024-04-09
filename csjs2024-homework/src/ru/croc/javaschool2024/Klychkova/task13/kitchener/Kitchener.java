package ru.croc.javaschool2024.Klychkova.task13.kitchener;

import java.util.Objects;

/**
 * Повар.
 */
public class Kitchener {
    private final long id;
    private String name;

    public Kitchener(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    // Считаем, что имя повара может измениться
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kitchener other = (Kitchener) o;
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
