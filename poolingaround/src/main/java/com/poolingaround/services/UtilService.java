package com.poolingaround.services;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import com.poolingaround.interfaces.Prenotazioni;
import com.poolingaround.interfaces.Utenti;
import com.poolingaround.interfaces.Viaggi;

public class UtilService {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public void displayAllActivities(List<Viaggi> viaggi) {

        System.out.println("\n--- Tutti i viaggi ---");
        for (Viaggi viaggio : viaggi) {
            System.out.println("\n-----------------------------\n");
            System.out.println("Id viaggio : " + viaggio.getId());
            System.out.println("Data : " + viaggio.getData().format(dateFormatter));
            System.out.println("Durata : " + viaggio.getDurata() + " ore");
            System.out.println("Partenza : " + viaggio.getPartenza());
            System.out.println("Arrivo : " + viaggio.getArrivo());
            System.out.println("Disponibile : " + (viaggio.isDisponibile()?"SI":"NO"));
            System.out.println("\n-----------------------------\n");
        }
    }

    public boolean bookActivity(int idViaggio, int idUtente, List<Viaggi> viaggi, List<Prenotazioni> prenotazioni, List<Utenti> utenti) {
        // Cerca il viaggio specifico
        Viaggi viaggio = viaggi.stream()
                .filter(v -> v.getId() == idViaggio)
                .findFirst()
                .orElse(null);
    
        if (viaggio == null) {
            System.out.println("Errore: Il viaggio con ID " + idViaggio + " non esiste.");
            return false;
        }
    
        // Controlla se il viaggio è disponibile
        if (!viaggio.isDisponibile()) {
            System.out.println("Errore: Il viaggio con ID " + idViaggio + " non è disponibile.");
            return false;
        }
    
        // controlla se esiste l'utente
        Utenti utente = utenti.stream()
                .filter(u -> u.getId() == idUtente)
                .findFirst()
                .orElse(null);

        if (utente == null) {
            System.out.println("Errore: L'utente con ID " + idUtente + " non esiste.");
            return false;
        }

        // Controlla se l'utente ha già prenotato lo stesso viaggio
        boolean alreadyBooked = prenotazioni.stream()
                .anyMatch(p -> p.getIdViaggio() == idViaggio && p.getIdUtente() == idUtente);
    


        if (alreadyBooked) {
            System.out.println("Errore: L'utente con ID " + idUtente + " ha già prenotato questo viaggio.");
            return false;
        }

        // Genera il nuovo ID per la prenotazione
        int newId = prenotazioni.stream()
        .mapToInt(Prenotazioni::getId)
        .max()
        .orElse(0) + 1;
    
        // Aggiorna lo stato del viaggio a non disponibile
        viaggio.setDisponibile(false);
    
        // Aggiungi la nuova prenotazione
        Prenotazioni nuovaPrenotazione = new Prenotazioni(
                newId, // Genera un nuovo ID per la prenotazione
                idViaggio,
                idUtente
        );
        prenotazioni.add(nuovaPrenotazione);
    
        System.out.println("Prenotazione effettuata con successo!");
        return true;
    }

    public boolean cancelBooking(int idPrenotazione, List<Viaggi> viaggi, List<Prenotazioni> prenotazioni) {
        // Trova la prenotazione da cancellare
        Prenotazioni prenotazione = prenotazioni.stream()
                .filter(p -> p.getId() == idPrenotazione)
                .findFirst()
                .orElse(null);
    
        if (prenotazione == null) {
            System.out.println("Errore: La prenotazione con ID " + idPrenotazione + " non esiste.");
            return false;
        }
    
        // Trova il viaggio associato alla prenotazione
        Viaggi viaggio = viaggi.stream()
                .filter(v -> v.getId() == prenotazione.getIdViaggio())
                .findFirst()
                .orElse(null);
    
        if (viaggio == null) {
            System.out.println("Errore: Il viaggio associato alla prenotazione non esiste.");
            return false;
        }
    
        // Aggiorna il viaggio come disponibile
        viaggio.setDisponibile(true);
    
        // Rimuovi la prenotazione dalla lista
        prenotazioni.remove(prenotazione);
    
        System.out.println("La prenotazione con ID " + idPrenotazione + " è stata cancellata con successo.");
        System.out.println("Il viaggio con ID " + viaggio.getId() + " è ora disponibile.");
        return true;
    }

    public void addUser(List<Utenti> utenti, Scanner scanner) { // Passa lo Scanner come parametro
        System.out.println("\n--- Aggiungi un nuovo utente ---");
    
        try {
            // Inserimento dati da parte dell'utente
            System.out.print("Inserisci l'ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
    
            // Verifica unicità ID
            boolean idExists = utenti.stream().anyMatch(u -> u.getId() == id);
            if (idExists) {
                System.out.println("Errore: L'ID fornito è già associato a un utente esistente.");
                return;
            }
    
            System.out.print("Inserisci il Nome: ");
            String nome = scanner.nextLine().trim();
    
            System.out.print("Inserisci il Cognome: ");
            String cognome = scanner.nextLine().trim();
    
            System.out.print("Inserisci la Data di nascita (formato dd/MM/yyyy): ");
            LocalDate dataNascita = LocalDate.parse(scanner.nextLine().trim(), dateFormatter);
    
            System.out.print("Inserisci l'Indirizzo: ");
            String indirizzo = scanner.nextLine().trim();
    
            System.out.print("Inserisci il Documento ID: ");
            String documentoId = scanner.nextLine().trim();
    
            // Creazione nuovo utente
            Utenti nuovoUtente = new Utenti(id, nome, cognome, dataNascita, indirizzo, documentoId);
            utenti.add(nuovoUtente);
    
            System.out.println("Utente aggiunto con successo:\n" + nuovoUtente);
    
        } catch (NumberFormatException e) {
            System.out.println("Errore: L'ID deve essere un numero intero.");
        } catch (DateTimeParseException e) {
            System.out.println("Errore: Formato della data di nascita non valido. Usa il formato dd/MM/yyyy.");
        } catch (Exception e) {
            System.out.println("Errore durante l'aggiunta dell'utente: " + e.getMessage());
        }
    }

    public void exportAvailableTrips(List<Viaggi> viaggi) {
        // Filtra i viaggi disponibili
        List<Viaggi> availableTrips = viaggi.stream()
                .filter(Viaggi::isDisponibile)
                .collect(Collectors.toList());

        // Genera il nome del file con la data corrente
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd_MM_yyyy"));
        String fileName = "viaggi_" + currentDate + ".csv";

        try (FileWriter writer = new FileWriter(fileName);
            @SuppressWarnings("deprecation")
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT
                    .withHeader("ID", "Data", "Durata", "Partenza", "Arrivo"))) {

            // Scrive i dati nel file CSV
            for (Viaggi viaggio : availableTrips) {
                printer.printRecord(
                        viaggio.getId(),
                        viaggio.getData().format(dateFormatter),
                        viaggio.getDurata(),
                        viaggio.getPartenza(),
                        viaggio.getArrivo()
                );
            }
            System.out.println("\nFile esportato con successo: " + fileName);

        } catch (IOException e) {
            System.err.println("Errore durante l'esportazione del file CSV: " + e.getMessage());
        }
    }

    public List<Utenti> loadUtentiFromCSV(String filename) {
        List<Utenti> utenti = new ArrayList<>();
    
        try (BufferedReader reader = getReader(filename);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "Nome", "Cognome", "Data di nascita", "Indirizzo", "Documento ID")
                    .setSkipHeaderRecord(true)
                    .setDelimiter(';') // Usa il separatore corretto
                    .build())) {
    
    
            for (CSVRecord record : parser) {
                try {
                    utenti.add(new Utenti(
                            Integer.parseInt(record.get("ID").trim()),
                            record.get("Nome").trim(),
                            record.get("Cognome").trim(),
                            LocalDate.parse(record.get("Data di nascita").trim(), dateFormatter),
                            record.get("Indirizzo").trim(),
                            record.get("Documento ID").trim()
                    ));
                } catch (NumberFormatException | DateTimeParseException e) {
                    System.err.println("Errore durante il parsing di Utenti: " + record + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file Utenti: " + e.getMessage());
        }
        return utenti;
    }

    public List<Prenotazioni> loadPrenotazioniFromCSV(String filename) {
        List<Prenotazioni> prenotazioni = new ArrayList<>();
    
        try (BufferedReader reader = getReader(filename);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "ID Viaggio", "ID Utente") // Intestazioni esplicite
                    .setSkipHeaderRecord(true)
                    .setDelimiter(';') // Usa il separatore corretto
                    .build())) {
    
    
            for (CSVRecord record : parser) {
                try {
                    prenotazioni.add(new Prenotazioni(
                            Integer.parseInt(record.get("ID").trim()),
                            Integer.parseInt(record.get("ID Viaggio").trim()),
                            Integer.parseInt(record.get("ID Utente").trim())
                    ));
                } catch (NumberFormatException e) {
                    System.err.println("Errore durante il parsing di Prenotazioni: " + record + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file Prenotazioni: " + e.getMessage());
        }
        return prenotazioni;
    }

    public List<Viaggi> loadViaggiFromCSV(String filename) {
        List<Viaggi> viaggi = new ArrayList<>();
    
        try (BufferedReader reader = getReader(filename);
            CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.builder()
                    .setHeader("ID", "Data", "Durata (ore)", "Partenza", "Arrivo", "Disponibile")
                    .setSkipHeaderRecord(true)
                    .setDelimiter(';') // Usa il separatore corretto
                    .build())) {
    
    
            for (CSVRecord record : parser) {
                try {
                    viaggi.add(new Viaggi(
                            Integer.parseInt(record.get("ID").trim()),
                            LocalDate.parse(record.get("Data").trim(), dateFormatter),
                            Integer.parseInt(record.get("Durata (ore)").trim()),
                            record.get("Partenza").trim(),
                            record.get("Arrivo").trim(),
                            record.get("Disponibile").trim().equalsIgnoreCase("SI") // Case insensitive
                    ));
                } catch (DateTimeParseException | IllegalArgumentException e) {
                    System.err.println("Errore durante il parsing di Viaggi: " + record + " - " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore durante la lettura del file Viaggi: " + e.getMessage());
        }
        return viaggi;
    }

    private BufferedReader getReader(String filename) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(filename);
        if (is == null) {
            throw new IOException("File non trovato: " + filename);
        }
        return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
    }

}
