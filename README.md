# PoolingAround

PoolingAround è un'applicazione Java per la gestione della prenotazione di viaggi. Consente agli utenti di visualizzare viaggi disponibili, effettuare prenotazioni, cancellarle, aggiungere nuovi utenti e gestire i dati attraverso file CSV.

---

## **Funzionalità**

1. **Visualizzare viaggi disponibili**  
   Mostra un elenco di tutti i viaggi presenti nel sistema con dettagli quali: data, durata, partenza, arrivo e disponibilità.

2. **Prenotare un viaggio**  
   Gli utenti possono selezionare un viaggio esistente e prenotarlo, a condizione che sia disponibile.

3. **Cancellare una prenotazione**  
   Gli utenti possono disdire una prenotazione già effettuata, rendendo il viaggio di nuovo disponibile.

4. **Aggiungere un nuovo utente**  
   Gli amministratori possono inserire nuovi utenti fornendo dati personali come nome, cognome, data di nascita e altro.

5. **Esportare viaggi disponibili**  
   Esporta un file CSV con tutti i viaggi attualmente disponibili per prenotazioni.

---

## **Prerequisiti**

1. **Java Development Kit (JDK)** - Versione 8 o superiore.  
2. **Maven** - Per gestire le dipendenze del progetto.  
3. **Lombok** - Libreria per semplificare il codice boilerplate (assicurarsi di configurare il proprio IDE per supportare Lombok).

---

## **Tecnologie Utilizzate**

- **Java**  
- **Maven**  
- **Apache Commons CSV** - Per leggere e scrivere file CSV.  
- **Lombok** - Per ridurre il codice boilerplate.

---

## **Installazione**

Puoi clonare il repository eseguendo il seguente comando: git clone https://github.com/aldosimone99/PoolingAround.git

## **Eseguire l'Applicazione**

Una volta clonato il progetto eseguire il seguente comando: mvn clean package Eseguendo questo comando verranno compilati tutti i file nella cartella "target" e verrà creato il file .jar
Lanciare l'applicazione Per lanciare l'applicazione attraverso il file .jar appena generato all'interno della cartella "target" eseguire il seguente comando: java -jar target/LookBook.jar
Eseguire l'applicazione solo con il file .JAR

Per lanciare l'applicazione utilizzando solo il file .jar eseguire il comando java -jar PoolingAround.jar

---

## **Struttura del Progetto**
	•	com.poolingaround.controllers.Controller
Classe principale per la gestione del flusso logico dell’applicazione.
	•	com.poolingaround.services.UtilService
Classe di supporto per operazioni come lettura/scrittura CSV, gestione prenotazioni, e altro.
	•	Interfacce:
	•	Utenti - Gestisce i dati relativi agli utenti.
	•	Viaggi - Modella i dettagli di un viaggio.
	•	Prenotazioni - Tiene traccia delle prenotazioni effettuate.

 ---
 
## **Esecuzione**
L’applicazione fornisce un menu interattivo che consente di eseguire le operazioni desiderate.
Esempio del menu principale:
--- Menu PoolingAround ---
1. Visualizzare tutti i viaggi dell'interno del sistema
2. Prenotare un viaggio esistente
3. Disdire la prenotazione di un viaggio
4. Aggiungere nuovo utente
5. Esporta un file con i viaggi disponibili
0. Uscire dal programma
Seleziona un'opzione:

---
 
## **Gestione Dati**

L’applicazione utilizza file CSV per memorizzare i dati. I file richiesti sono:
	•	utenti.csv
Contiene i dettagli degli utenti (ID, nome, cognome, ecc.).
	•	viaggi.csv
Elenca i viaggi disponibili e i loro dettagli (ID, data, durata, partenza, arrivo, ecc.).
	•	prenotazioni.csv
Memorizza le prenotazioni effettuate (ID prenotazione, ID utente, ID viaggio).
