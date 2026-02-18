package lab.server;

import lab.model.Reservation;
import lab.model.Transaction;
import lab.model.Workshop;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class DataManager {

    // Baza de date in memorie. Folosim HashMap pentru acces rapid dupa ID.
    // Aici tinem starea curenta a aplicatiei.
    private final Map<String, Reservation> reservations = new HashMap<>();
    private final List<Transaction> transactions = new ArrayList<>();

    // Configuratia centrelor si atelierelor.
    private final Map<Integer, List<Workshop>> centers = new HashMap<>();

    // ReentrantReadWriteLock permite mai multor thread-uri sa citeasca in acelasi timp (daca e cazul),
    // dar obliga thread-ul care vrea sa scrie sa aiba acces exclusiv.
    // Astfel, cand Auditorul face verificarea, blocheaza tot (WriteLock), ca sa nu se schimbe
    // soldurile in timp ce el le aduna.
    public final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final String resFile = "rezervari.txt";
    private final String transFile = "tranzactii.txt";

    public DataManager() {
        // Initializam datele
        for (int i = 1; i <= 3; i++) {
            List<Workshop> ws = new ArrayList<>();
            ws.add(new Workshop(1, "Ceramica", 100, 60, 5));
            ws.add(new Workshop(2, "Pictura", 120, 90, 3));
            ws.add(new Workshop(3, "Foto", 150, 60, 4));
            ws.add(new Workshop(4, "Sculptura", 200, 120, 2));
            centers.put(i, ws);
        }
        // Golim fisierele text la pornirea serverului pentru a incepe testul de la 0.
        initFiles();
    }

    private void initFiles() {
        try {
            // Deschidere fara append, stergere continut
            new FileWriter(resFile).close();
            new FileWriter(transFile).close();
        } catch (IOException e) { e.printStackTrace(); }
    }


    // Folosim synchronized pentru a proteja scrierea in fisier.
    // Chiar daca avem rwLock pentru datele din memorie, operatiile I/O (scrierea pe disc)
    // trebuie si ele protejate ca sa nu se amestece liniile de la doua thread-uri diferite.
    public synchronized void logReservation(Reservation r) {
        try (PrintWriter out = new PrintWriter(new FileWriter(resFile, true))) {
            out.printf("%s, %s, %s, C%d, W%d, %d:00, %s, %d%n",
                    r.id, r.clientName, r.cnp, r.centerId, r.workshopId, r.hour, r.status, r.timestamp);
        } catch (IOException e) { e.printStackTrace(); }
    }

    public synchronized void logTransaction(Transaction t) {
        try (PrintWriter out = new PrintWriter(new FileWriter(transFile, true))) {
            out.printf("%s, %s, %s, %.2f, %d%n",
                    t.id, t.resId, t.type, t.amount, t.timestamp);
        } catch (IOException e) { e.printStackTrace(); }
    }

    // Getteri simpli pentru a accesa colectiile
    public Map<Integer, List<Workshop>> getCenters() { return centers; }
    public Map<String, Reservation> getReservations() { return reservations; }
    public List<Transaction> getTransactions() { return transactions; }
}