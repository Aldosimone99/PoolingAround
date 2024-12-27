package com.poolingaround.controllers;

import java.util.Scanner;

import com.poolingaround.services.UtilService;

public class Controller {

    UtilService service = new UtilService();

    public void startMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Menu PoolingAround ---");
            System.out.println("1. Visualizzare tutti i viaggi dell'interno del sistema");
            System.out.println("2. Prenotare un viaggio esistente");
            System.out.println("3. Disdire la prenotazione di un viaggio");
            System.out.println("4. Aggiungere nuovo utente");
            System.out.println("5. Esporta un file con i viaggi disponibili");
            System.out.println("0. Uscire dal programma");
            System.out.print("Seleziona un'opzione: ");

            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    service.displayAllActivities();
                    break;
                case "2":
                    service.bookActivity();
                    break;
                case "3":
                    service.cancelBooking();
                    break;
                case "4":
                    service.addUser();
                    break;
                case "5":
                    service.exportAvailableTrips();
                    break;
                case "0":
                    exit = true;
                    System.out.println("\n\nUscita dal programma.\nGrazie per aver utilizzato PoolingAround!\n\n");
                    break;
                default:
                    System.out.println("\n\nOpzione non valida. Riprova.");
            }
        }
        scanner.close();
    }


}