package ru.croc.javaschool2024.Klychkova.task16;

import java.util.List;

public class Task16 {
    public static void main(String[] args) {
        VetDataBase dataBase = new VetDataBase();
        ClientDAO clientDAO = new ClientDAO(dataBase);
        PetDAO petDAO = new PetDAO(dataBase);
        Client client1 = clientDAO.createClient(new Client(0, "Анастасия", "Клычкова", "+70000000034"));
        Client client2 = clientDAO.createClient(new Client(0, "Максим", "Клычков", "+79999999994"));
        Pet pet = petDAO.createPet("Барни", 7, List.of(
                client1,
                client2
        ));
        System.out.println(petDAO.findPet(pet.cardNumber()));
        petDAO.findClientPhoneNumbersBy(pet).forEach(System.out::println);
        pet = petDAO.updatePet(new Pet(pet.cardNumber(), pet.name(), 8));
        clientDAO.getAllPetsOf(client1).forEach(System.out::println);
    }
}
