package ru.croc.javaschool2024.Klychkova.task16;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VetDataBase {
    private final String connectionUrl;
    private final String user;
    private final String password;

    public VetDataBase(String name, String user, String password) {
        connectionUrl = String.format("jdbc:h2:/%s", name);
        this.user = user;
        this.password = password;
        createTables();
    }

    public VetDataBase(String name) {
        this(name, "sa", "");
    }

    public VetDataBase(String user, String password) {
        this("vet_clinic_2", user, password);
    }

    public VetDataBase() {
        this("vet_clinic_2", "sa", "");
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            final Statement statement = conn.createStatement();
            statement.execute(
                    """
                            CREATE TABLE IF NOT EXISTS clients(
                            id IDENTITY NOT NULL PRIMARY KEY,
                            name VARCHAR(255),
                            surname VARCHAR(255),
                            phone VARCHAR(12))
                            """
            );
            statement.execute(
                    """
                            CREATE TABLE IF NOT EXISTS pets(
                            card IDENTITY NOT NULL PRIMARY KEY,
                            name VARCHAR(255),
                            age INT)
                            """
            );
            statement.execute(
                    """
                            CREATE TABLE IF NOT EXISTS has(
                            client_id INT,
                            pet_id INT,
                            FOREIGN KEY (client_id) REFERENCES clients(id),
                            FOREIGN KEY (pet_id) REFERENCES pets(card))
                            """
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int addClient(String name, String surname, String phone) throws SQLException {
        int id = -1;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "INSERT INTO clients(name, surname, phone) VALUES (?,?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setString(2, surname);
                statement.setString(3, phone);
                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    while(rs.next()) {
                        id =  rs.getInt(1);
                    }
                }
            }
        }
        return id;
    }

    public Client findClientById(int id) throws SQLException {
        Client client = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "SELECT * FROM clients WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        client = new Client(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("surname"),
                                result.getString("phone")
                        );
                    }
                }
            }
        }
        return client;
    }

    public Client findClientByPhone(String phone) throws SQLException {
        Client client = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "SELECT * FROM clients WHERE phone = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, phone);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        client = new Client(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("surname"),
                                result.getString("phone")
                        );
                    }
                }
            }
        }
        return client;
    }

    public void updateClient(Client client) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = """
                    UPDATE clients SET
                    name = ?,
                    surname = ?,
                    phone = ?
                    WHERE id = ?
                    """;
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, client.name());
                statement.setString(2, client.surname());
                statement.setString(3, client.phone());
                statement.setInt(4, client.id());
                statement.execute();
            }
        }
    }

    public void deleteClient(int id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "DELETE FROM clients WHERE id = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.execute();
            }
        }
    }

    public int addPet(String name, int age) throws SQLException {
        int id = -1;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "INSERT INTO pets(name, age) VALUES (?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, name);
                statement.setInt(2, age);
                statement.executeUpdate();
                try (ResultSet rs = statement.getGeneratedKeys()) {
                    while(rs.next()) {
                        id =  rs.getInt(1);
                    }
                }
            }
        }
        return id;
    }

    public Pet findPetByCard(int card) throws SQLException {
        Pet pet = null;
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "SELECT * FROM pets WHERE card = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, card);
                try (ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                       pet = new Pet(
                                result.getInt("card"),
                                result.getString("name"),
                                result.getInt("age")
                        );
                    }
                }
            }
        }
        return pet;
    }

    public void updatePet(Pet pet) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = """
                    UPDATE pets SET
                    name = ?,
                    age = ?
                    WHERE card = ?
                    """;
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setString(1, pet.name());
                statement.setInt(2, pet.age());
                statement.setInt(3, pet.cardNumber());
                statement.execute();
            }
        }
    }

    public void deletePet(int cardNumber) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "DELETE FROM pets WHERE card = ?";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, cardNumber);
                statement.execute();
            }
        }
    }

    public void addRelationship(int client_id, int pet_id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "INSERT INTO has(client_id, pet_id) VALUES (?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, client_id);
                statement.setInt(2, pet_id);
                statement.execute();
            }
        }
    }

    List<String> findClientPhoneNumbersBy(int card) throws SQLException {
        List<String> phones = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = """
                    SELECT c.phone
                    FROM clients as c
                    JOIN has as h
                    ON c.id = h.client_id
                    WHERE h.pet_id = ?
                    """;
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, card);
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            phones.add(result.getString("phone"));
                        }
                    }
                }
        }
        return phones;
    }

    List<Pet> getAllPetsOf(Client client) throws SQLException {
            List<Pet> pets = new ArrayList<>();
            try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
                String sql = """
                    SELECT p.card, p.name, p.age
                    FROM pets as p
                    JOIN has as h
                    ON p.card = h.pet_id
                    WHERE h.client_id = ?
                    """;
                try (PreparedStatement statement = conn.prepareStatement(sql)) {
                    statement.setInt(1, client.id());
                    try (ResultSet result = statement.executeQuery()) {
                        while (result.next()) {
                            pets.add(new Pet(
                                    result.getInt("card"),
                                    result.getString("name"),
                                    result.getInt("age")
                            ));
                        }
                    }
                }
            }
            return pets;
    }

    public void printClients() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet result = statement.executeQuery("SELECT * FROM clients")) {
                    while (result.next()) {
                        int id = result.getInt("id");
                        String name = result.getString("name");
                        String surname = result.getString("surname");
                        String phone = result.getString("phone");
                        System.out.printf("%d\t%s\t%s\t%s\n", id, name, surname, phone);
                    }
                }
            }
        }
    }

    public void printPets() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet result = statement.executeQuery("SELECT * FROM pets")) {
                    while (result.next()) {
                        int id = result.getInt("card");
                        String name = result.getString("name");
                        int age = result.getInt("age");
                        System.out.printf("%d\t%s\t%d\n", id, name, age);
                    }
                }
            }
        }
    }

    public void printRelationship() throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            try (Statement statement = conn.createStatement()) {
                try (ResultSet result = statement.executeQuery("SELECT * FROM has")) {
                    while (result.next()) {
                        int c_id = result.getInt("client_id");
                        int p_id = result.getInt("pet_id");
                        System.out.printf("%d\t%d\n", c_id, p_id);
                    }
                }
            }
        }
    }
}
