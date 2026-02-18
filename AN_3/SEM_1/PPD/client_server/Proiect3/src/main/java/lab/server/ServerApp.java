package lab.server;
import lab.model.Reservation;
import lab.model.Transaction;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ServerApp {
    private static final int PORT = 8888;

    // Limitam numarul de thread-uri active la 8.
    // Daca vin 100 de clienti, doar 8 sunt procesati simultan, restul stau la coada (in pool).
    // Asta previne blocarea procesorului cu prea multe thread-uri ("Context Switching" excesiv).
    private static final int THREAD_POOL_SIZE = 8;

    // Intervalul la care Auditorul face verificarea (5/10 secunde in acest cod).
    private static final int CHECK_INTERVAL = 5;
    private static final long PAYMENT_TIMEOUT_MS = 15000;

    public static void main(String[] args) {
        DataManager dataManager = new DataManager();

        // ExecutorService gestioneaza Thread Pool-ul.
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // Un scheduler separat doar pentru Auditor, ca sa ruleze periodic in fundal.
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // Programam auditorul sa ruleze la fiecare CHECK_INTERVAL secunde.
        scheduler.scheduleAtFixedRate(new PeriodicAuditor(dataManager, PAYMENT_TIMEOUT_MS),
                CHECK_INTERVAL, CHECK_INTERVAL, TimeUnit.SECONDS);

        System.out.println("Server pornit pe portul " + PORT);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // Acceptam conexiunea. Aceasta linie blocheaza executia pana vine un client.
                Socket clientSocket = serverSocket.accept();

                // Procesare asincrona.
                // Nu procesam cererea aici (pe thread-ul principal), ci o trimitem catre executor.
                // Thread-ul principal se intoarce instant la "accept()" pentru urmatorul client.
                // CompletableFuture.runAsync arunca task-ul in Thread Pool.
                CompletableFuture.runAsync(() -> handleClient(clientSocket, dataManager), executor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Aceasta metoda ruleaza pe un thread din Worker Pool.
    private static void handleClient(Socket socket, DataManager dm) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {

            // Citim tipul cererii trimis de client (RESERVE, PAY, CANCEL).
            String requestType = (String) in.readObject();

            if ("RESERVE".equals(requestType)) {

                // Citim parametrii si apelam logica de rezervare.
                String name = (String) in.readObject();
                String cnp = (String) in.readObject();
                int center = (int) in.readObject();
                int workshop = (int) in.readObject();
                int hour = (int) in.readObject();

                String result = processReservation(dm, name, cnp, center, workshop, hour);
                out.writeObject(result);

            } else if ("PAY".equals(requestType)) {
                String resId = (String) in.readObject();
                String result = processPayment(dm, resId);
                out.writeObject(result);
            } else if ("CANCEL".equals(requestType)) {
                String resId = (String) in.readObject();
                String result = processCancellation(dm, resId);
                out.writeObject(result);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Inchidem socket-ul dupa ce am terminat cu acest client.
            try { socket.close(); } catch (IOException e) {}
        }
    }

    // Logica de rezervare, necesita sincronizare.
    private static String processReservation(DataManager dm, String name, String cnp, int cId, int wId, int hour) {
        // Luam write lock. Nimeni altcineva nu poate scrie sau citi in acest timp.
        dm.rwLock.writeLock().lock();
        try {
            if (hour < 9 || hour > 17) return "FAIL: Centrul inchis";

            // Numaram cate rezervari active (REZERVARE sau PLATITA) exista deja.
            long currentBookings = dm.getReservations().values().stream()
                    .filter(r -> r.centerId == cId && r.workshopId == wId && r.hour == hour)
                    .filter(r -> r.status.equals("REZERVARE") || r.status.equals("PLATITA"))
                    .count();

            // Verificam capacitatea maxima a atelierului.
            int cap = dm.getCenters().get(cId).stream().filter(w -> w.id == wId).findFirst().get().capacity;

            if (currentBookings < cap) {
                // Daca e loc, cream rezervarea.
                double price = dm.getCenters().get(cId).stream().filter(w -> w.id == wId).findFirst().get().price;
                Reservation r = new Reservation(name, cnp, cId, wId, hour, price);
                dm.getReservations().put(r.id, r);
                dm.logReservation(r);
                return "SUCCESS:" + r.id;
            } else {
                return "FAIL: Capacitate depasita";
            }
        } finally {
            // Eliberam lock-ul in finally, ca sa nu blocam serverul daca apare o eroare.
            dm.rwLock.writeLock().unlock();
        }
    }

    // Logica de plata.
    private static String processPayment(DataManager dm, String resId) {
        dm.rwLock.writeLock().lock();
        try {
            Reservation r = dm.getReservations().get(resId);
            // Putem plati doar daca statusul e inca REZERVARE.
            if (r != null && "REZERVARE".equals(r.status)) {
                // Verificam daca a trecut timpul limita (15s).
                if (System.currentTimeMillis() - r.timestamp > PAYMENT_TIMEOUT_MS) {
                    return "FAIL: Rezervare expirata";
                }

                // Confirmam plata.
                r.status = "PLATITA";

                // Adaugam tranzactia de incasare.
                Transaction t = new Transaction(r.id, "INCASARE", r.cost);
                dm.getTransactions().add(t);
                dm.logReservation(r);
                dm.logTransaction(t);
                return "PAID";
            }
            return "FAIL: Rezervare inexistenta sau deja procesata";
        } finally {
            dm.rwLock.writeLock().unlock();
        }
    }

    // Logica de anulare (ramburs).
    private static String processCancellation(DataManager dm, String resId) {
        dm.rwLock.writeLock().lock();
        try {
            Reservation r = dm.getReservations().get(resId);
            // Putem anula doar ce a fost deja PLATIT.
            if (r != null && "PLATITA".equals(r.status)) {
                r.status = "ANULATA";

                // Facem tranzactie cu suma negativa (bani inapoi).
                Transaction t = new Transaction(r.id, "RAMBURS", -r.cost);
                dm.getTransactions().add(t);
                dm.logReservation(r);
                dm.logTransaction(t);
                return "CANCELLED";
            }
            return "FAIL: Nu se poate anula";
        } finally {
            dm.rwLock.writeLock().unlock();
        }
    }
}