package ro.mpp2024.rest.tests;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class CursaRestTestClient {
    private static final String BASE_URL = "http://localhost:8000/api/curse";

    public static void main(String[] args) throws Exception {
        String json = """
            {
              "nume": "Cursa Simplificata",
              "capacitateMinima": 100,
              "capacitateMaxima": 500
            }
        """;

        int id = sendPost(json);
        Thread.sleep(500);
        sendGet(id);

        String updated = """
            {
              "id": %d,
              "nume": "Cursa Updatata",
              "capacitateMinima": 120,
              "capacitateMaxima": 550
            }
        """.formatted(id);
        sendPut(id, updated);
        sendGet(id);
        sendDelete(id);
    }

    static int sendPost(String jsonInput) throws Exception {
        URL url = new URL(BASE_URL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        int status = con.getResponseCode();
        if (status == 201) {
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            int id = Integer.parseInt(in.readLine());
            System.out.println("Created: ID = " + id);
            return id;
        } else {
            throw new RuntimeException("POST failed: " + status);
        }
    }

    static void sendGet(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        System.out.println("GET status: " + status);
        if (status == 200) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                in.lines().forEach(System.out::println);
            }
        }
    }

    static void sendPut(int id, String jsonInput) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("PUT");
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);

        try (OutputStream os = con.getOutputStream()) {
            os.write(jsonInput.getBytes(StandardCharsets.UTF_8));
        }

        int status = con.getResponseCode();
        System.out.println("PUT status: " + status);
    }

    static void sendDelete(int id) throws Exception {
        URL url = new URL(BASE_URL + "/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("DELETE");

        int status = con.getResponseCode();
        System.out.println("DELETE status: " + status);
    }
}