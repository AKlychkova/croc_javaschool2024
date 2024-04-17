package ru.croc.javaschool2024.Klychkova.task16;

import java.sql.SQLException;
import java.util.List;

public class PetDAO {
    private final VetDataBase database;

    public PetDAO(VetDataBase database) {
        this.database = database;
    }

    Pet createPet(String name, Integer age, List<Client> clients) {
        try {
            int id = database.addPet(name, age);
            for (Client client : clients) {
                database.addRelationship(client.id(), id);
            }
            return new Pet(id, name, age);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    Pet findPet(Integer medicalCardNumber) {
        try{
            return database.findPetByCard(medicalCardNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    Pet updatePet(Pet pet) {
        try{
            database.updatePet(pet);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
        return pet;
    }

    void deletePet(Integer medicalCardNumber) {
        try{
            database.deletePet(medicalCardNumber);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    List<String> findClientPhoneNumbersBy(Pet pet) {
        try {
            return database.findClientPhoneNumbersBy(pet.cardNumber());
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

}
