package lab.server;

import lab.model.Reservation;
import lab.model.Transaction;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class PeriodicAuditor implements Runnable {
    private final DataManager dm;
    private final long timeoutMs;

    public PeriodicAuditor(DataManager dm, long timeoutMs) {
        this.dm = dm;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public void run() {

        // Luam Write Lock pe tot DataManager-ul.
        // Asta inseamna ca in timp ce ruleaza verificarea (cativa ms),
        // niciun client nu poate rezerva sau plati.
        // Acest lucru asigura "Snapshot Isolation" - auditorul vede o stare consistenta a datelor.
        dm.rwLock.writeLock().lock();

        try (PrintWriter report = new PrintWriter(new FileWriter("raport_auditor.txt", true))) {
            long now = System.currentTimeMillis();
            report.println("--- VERIFICARE " + new Date() + " ---");

            // Verificare si Expirare automata
            for (Reservation r : dm.getReservations().values()) {
                if ("REZERVARE".equals(r.status)) {
                    // Daca au trecut cele 15 secunde si nu e platita.
                    if (now - r.timestamp > timeoutMs) {
                        r.status = "EXPIRATA";
                        // Generam o tranzactie de tip anulare cu suma 0.
                        Transaction t = new Transaction(r.id, "ANULARE_TIMEOUT", 0.0);
                        dm.getTransactions().add(t);
                        dm.logReservation(r);
                        dm.logTransaction(t);
                        report.println("Expirat rezervare: " + r.id);
                    }
                }
            }

            // Verificare Consistenta Financiara
            // Calculam cati bani ar trebui sa avem (suma rezervarilor PLATITE).
            double expectedTotal = dm.getReservations().values().stream()
                    .filter(r -> "PLATITA".equals(r.status))
                    .mapToDouble(r -> r.cost)
                    .sum();

            // Calculam cati bani avem efectiv in registrul de tranzactii.
            double actualTotal = dm.getTransactions().stream()
                    .mapToDouble(t -> t.amount)
                    .sum();

            // Daca e diferenta avem o problema grava de sincronizare.
            report.printf("Sold Total Asteptat: %.2f | Sold Tranzactii: %.2f%n", expectedTotal, actualTotal);
            if (Math.abs(expectedTotal - actualTotal) > 0.01) {
                report.println("ERROARE: Inconsistenta financiara detectata");
            }

            // Verificare Suprapuneri (Overbooking)
            Map<String, Integer> counts = new HashMap<>();
            // Numaram rezervarile pentru fiecare combinatie Centru-Atelier-Ora
            for (Reservation r : dm.getReservations().values()) {
                if ("PLATITA".equals(r.status)) {
                    String key = r.centerId + "-" + r.workshopId + "-" + r.hour;
                    counts.put(key, counts.getOrDefault(key, 0) + 1);
                }
            }

            // Comparam numaratoarea cu capacitatea maxima
            for (Map.Entry<String, Integer> entry : counts.entrySet()) {
                String[] parts = entry.getKey().split("-");
                int cId = Integer.parseInt(parts[0]);
                int wId = Integer.parseInt(parts[1]);
                int count = entry.getValue();

                int cap = dm.getCenters().get(cId).stream()
                        .filter(w -> w.id == wId).findFirst().get().capacity;

                if (count > cap) {
                    report.printf("EROARE SUPRAPUNERE: C%d W%d Ora %s are %d rezervari (Cap: %d)%n",
                            cId, wId, parts[2], count, cap);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Eliberam lock-ul pentru a lasa serverul sa proceseze din nou clienti.
            dm.rwLock.writeLock().unlock();
        }
    }
}