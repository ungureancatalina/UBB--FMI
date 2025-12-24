package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderTask implements Runnable {

    private final BoundedGradeQueue queue;
    private final String filename;

    public ReaderTask(BoundedGradeQueue queue, String filename) {
        this.queue = queue;
        this.filename = filename;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                line = line.replace("(", "")
                        .replace(")", "")
                        .replace(",", " ");
                String[] parts = line.split("\\s+");
                if (parts.length < 2) continue;

                int id = Integer.parseInt(parts[0]);
                int grade = Integer.parseInt(parts[1]);

                queue.put(new GradeRecord(id, grade)); // poate bloca daca e full
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Eroare ReaderTask pe " + filename + ": " + e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            queue.producerDone();
        }
    }
}
