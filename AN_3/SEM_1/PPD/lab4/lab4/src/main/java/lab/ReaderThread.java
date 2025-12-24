package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ReaderThread extends Thread {
    private final GradeQueue queue;
    private final String[] files;
    private final int readerIndex;    // 0 .. p_r-1
    private final int totalReaders;   // p_r

    public ReaderThread(GradeQueue queue, String[] files, int readerIndex, int totalReaders) {
        this.queue = queue;
        this.files = files;
        this.readerIndex = readerIndex;
        this.totalReaders = totalReaders;
    }

    @Override
    public void run() {
        try {
            // impartim fisierele intre readeri prin intercalare:
            for (int i = readerIndex; i < files.length; i += totalReaders) {
                readFile(files[i]);
            }
        } finally {
            // anuntam coada ca acest reader a terminat
            queue.readerDone();
        }
    }

    private void readFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;

                // format: (ID, NotaP)
                line = line.replace("(", "")
                        .replace(")", "")
                        .replace(",", " ");

                String[] parts = line.trim().split("\\s+");
                if (parts.length < 2) continue;

                int id = Integer.parseInt(parts[0]);
                int grade = Integer.parseInt(parts[1]);

                //punem in coada recordul
                queue.put(new GradeRecord(id, grade));
            }
        } catch (IOException e) {
            System.err.println("Eroare la citirea fisierului " + filename + ": " + e.getMessage());
        }
    }
}
