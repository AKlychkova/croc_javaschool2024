package ru.croc.javaschool2024.Klychkova.task16;

import java.sql.SQLException;
import java.util.List;

public class ClientDAO {
    private final VetDataBase database;

    public ClientDAO(VetDataBase database) {
        this.database = database;
    }

    Client createClient(Client client) {
        try {
            if(database.findClientByPhone(client.phone()) == null) {
                int id = database.addClient(client.name(), client.surname(), client.phone());
                return new Client(id, client.name(), client.surname(), client.phone());
            } else {
                throw new IllegalArgumentException("Клиент с таким телефоном уже существует.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    Client findClient(Integer id){
        try{
            return database.findClientById(id);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    Client updateClient(Client client) {
        try{
            database.updateClient(client);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
        return client;
    }

    void deleteClient(Integer id) {
        try{
            database.deleteClient(id);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }

    List<Pet> getAllPetsOf(Client client) {
        try {
            return database.getAllPetsOf(client);
        } catch (SQLException e) {
            throw new RuntimeException("Проблема при доступе к БД: " + e.getMessage());
        }
    }
}
