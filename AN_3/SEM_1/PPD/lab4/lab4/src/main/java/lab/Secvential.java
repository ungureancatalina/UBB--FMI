package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Secvential {

    public static long runSequential(String[] files, String outputFile) throws IOException {
        ThreadSafeGradeList list = new ThreadSafeGradeList();

        long t0 = System.nanoTime();

        // Citim pe rand din fiecare fisier
        for (String file : files) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty()) continue;

                    // (ID, NotaP)
                    line = line.replace("(", "")
                            .replace(")", "")
                            .replace(",", " ");
                    String[] parts = line.trim().split("\\s+");
                    if (parts.length < 2) continue;

                    int id = Integer.parseInt(parts[0]);
                    int grade = Integer.parseInt(parts[1]);

                    //pt fiecare record adaugam sau actualizam in lista
                    list.addOrUpdate(id, grade);
                }
            }
        }

        // Scriem rezultatul in fisierul rezultat
        list.writeToFile(outputFile);

        long t1 = System.nanoTime();
        return t1 - t0;
    }
}
