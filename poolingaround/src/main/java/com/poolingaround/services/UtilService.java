package com.poolingaround.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.poolingaround.interfaces.Prenotazioni;
import com.poolingaround.interfaces.Utenti;
import com.poolingaround.interfaces.Viaggi;

public class UtilService {

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    public void displayAllActivities() {
        System.out.println("\n========\n\nVisualizzazione di tutte le attività...\n\n=======\n\n");
        // Implementa la logica per visualizzare tutte le attività
    }

    public void bookActivity() {
        System.out.println("\n========\n\nPrenotazione di un'attività...\n\n=======\n\n");
        // Implementa la logica per prenotare un'attività
    }

    public void cancelBooking() {
        System.out.println("\n========\n\nCancellazione di una prenotazione...\n\n=======\n\n");
        // Implementa la logica per cancellare una prenotazione
    }

    public void addUser() {
        System.out.println("\n========\n\nAggiunta di un nuovo utente...\n\n=======\n\n");
        // Implementa la logica per aggiungere un nuovo utente
    }

    public void exportAvailableTrips() {
        System.out.println("\n========\n\nEsportazione di viaggi disponibili...\n\n=======\n\n");
        // Implementa la logica per esportare viaggi disponibili
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
