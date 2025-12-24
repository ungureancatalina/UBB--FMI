package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Secvential {

    public static long runSequential(String[] files, String outputFile) throws IOException {

        // Folosim lista fine-grain și la secvențial
        FineGrainedGradeList list = new FineGrainedGradeList();

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

                    String[] parts = line.split("\\s+");
                    if (parts.length < 2) continue;

                    int id = Integer.parseInt(parts[0]);
                    int grade = Integer.parseInt(parts[1]);

                    list.addOrUpdate(id, grade);
                }
            }
        }

        // lista sortata (descrescator)
        var sortedList = new FineGrainedSortedByTotalList();

        for (GradeRecord rec : list.toRecords()) {
            sortedList.insertSorted(rec.id, rec.grade);
        }

        // Scriem rezultatul final
        sortedList.writeToFile(outputFile);

        long t1 = System.nanoTime();
        return t1 - t0;
    }
}
