package lab.client;

import java.io.*;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class ClientSimulator {
    public static void main(String[] args) throws InterruptedException {
        int numClients = 20;
        Thread[] clients = new Thread[numClients];

        // Cream 20 de thread-uri separate, fiecare simuland un om diferit.
        for (int i = 0; i < numClients; i++) {
            final int id = i;
            clients[i] = new Thread(() -> runClientScenario(id));
            clients[i].start();
        }

        // Asteptam ca toate cele 20 de thread-uri sa termine executia.
        for (Thread t : clients) t.join();
    }

    private static void runClientScenario(int clientId) {
        Random rand = new Random();

        // Clientul va trimite cereri timp de 120 de secunde.
        long endTime = System.currentTimeMillis() + 120000;

        while (System.currentTimeMillis() < endTime) {
            try {
                // Generam date aleatoare pentru rezervare.
                int cId = rand.nextInt(3) + 1;
                int wId = rand.nextInt(4) + 1;
                int hour = 9 + rand.nextInt(9);

                // Trimite cerere de REZERVARE
                CompletableFuture<String> reserveFuture = sendRequestAsync("RESERVE", "Client" + clientId, "CNP" + clientId, cId, wId, hour);

                String resId = reserveFuture.get();

                if (resId != null && resId.startsWith("SUCCESS")) {
                    String actualId = resId.split(":")[1];
                    System.out.println("Client " + clientId + " a rezervat: " + actualId);

                    // Simulam comportament uman aleatoriu
                    int action = rand.nextInt(10);

                    if (action < 6) {
                        // 60% sansa: Plateste rapid (in 2 secunde Succes)
                        Thread.sleep(2000);
                        String payRes = sendRequestAsync("PAY", actualId).get();
                        System.out.println("Client " + clientId + " plata: " + payRes);

                        // Dupa plata, poate se razgandeste si anuleaza.
                        if (rand.nextBoolean()) {
                            Thread.sleep(3000);
                            sendRequestAsync("CANCEL", actualId).get();
                        }

                    } else if (action < 8) {
                        // 20% sansa: Plateste PREA TARZIU (in 16 secunde Fail, expirat)
                        // Aici testam daca Auditorul sterge rezervarea.
                        Thread.sleep(16000);
                        String payRes = sendRequestAsync("PAY", actualId).get();
                        System.out.println("Client " + clientId + " plata tarzie: " + payRes);
                    }
                    // Restul 20%: Nu plateste deloc (rezervarea va expira singura).
                }

                // Asteapta putin inainte de urmatoarea incercare.
                Thread.sleep(2000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Metoda generica pentru trimiterea cererilor catre server.
    private static CompletableFuture<String> sendRequestAsync(Object... args) {
        // Executa codul de retea pe un alt thread pentru a nu bloca executia.
        return CompletableFuture.supplyAsync(() -> {
            try (Socket s = new Socket("localhost", 8888);
                 ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {

                // Trimitem toate datele cererii catre server (serializare).
                for (Object arg : args) out.writeObject(arg);

                // Asteptam si returnam raspunsul venit de la server.
                return (String) in.readObject();
            } catch (Exception e) {
                return "ERROR";
            }
        });
    }

}