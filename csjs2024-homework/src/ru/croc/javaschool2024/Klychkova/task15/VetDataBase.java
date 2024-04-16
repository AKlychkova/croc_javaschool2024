package ru.croc.javaschool2024.Klychkova.task15;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.*;
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
        this("vet_clinic", user, password);
    }

    public VetDataBase() {
        this("vet_clinic", "sa", "");
    }

    private void createTables() {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            final Statement statement = conn.createStatement();
            statement.execute(
                    """
                            CREATE TABLE IF NOT EXISTS clients(
                            id INT PRIMARY KEY,
                            name VARCHAR(255),
                            surname VARCHAR(255),
                            phone VARCHAR(12))
                            """
            );
            statement.execute(
                    """
                            CREATE TABLE IF NOT EXISTS pets(
                            card INT PRIMARY KEY,
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

    public void addClient(Client client) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "MERGE INTO clients(id, name, surname, phone) KEY(id) VALUES (?,?,?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, client.id());
                statement.setString(2, client.name());
                statement.setString(3, client.surname());
                statement.setString(4, client.phone());
                statement.execute();
            }
        }
    }

    public void addPet(Pet pet) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "MERGE INTO pets(card, name, age) KEY(card) VALUES (?,?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1,pet.cardNumber());
                statement.setString(2, pet.name());
                statement.setInt(3, pet.age());
                statement.execute();
            }
        }
    }

    public void addRelationship(int client_id, int pet_id) throws SQLException {
        try (Connection conn = DriverManager.getConnection(connectionUrl, user, password)) {
            String sql = "MERGE INTO has(client_id, pet_id) KEY(client_id, pet_id) VALUES (?,?)";
            try (PreparedStatement statement = conn.prepareStatement(sql)) {
                statement.setInt(1, client_id);
                statement.setInt(2, pet_id);
                statement.execute();
            }
        }
    }

    public void addFromCSV(final Path path) throws FileNotFoundException, SQLException {
        try (Scanner scanner = new Scanner(path.toFile())) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                Client client = Client.getFromLine(line);
                Pet pet = Pet.getFromLine(line);
                addClient(client);
                addPet(pet);
                addRelationship(client.id(), pet.cardNumber());
            }
        }
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
                        System.out.printf("%d\t%s\t%s\t%s\n",id,name,surname,phone);
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
                        System.out.printf("%d\t%s\t%d\n",id,name,age);
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
                        System.out.printf("%d\t%d\n",c_id,p_id);
                    }
                }
            }
        }
    }
}
