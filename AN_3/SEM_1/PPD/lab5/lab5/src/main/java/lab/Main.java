package lab;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main{

    private static String[] generateRandomInputFiles(int numStudents,
                                                     int numFiles,
                                                     int minNotesPerFile) throws IOException {
        Random r = new Random();
        String[] files = new String[numFiles];

        for (int f = 0; f < numFiles; f++) {
            String file = "proiect" + (f + 1) + ".txt";
            files[f] = file;

            try (PrintWriter pw = new PrintWriter(file)) {
                int notesCount = minNotesPerFile + r.nextInt(50); // 80–130
                for (int i = 0; i < notesCount; i++) {
                    int id = 1 + r.nextInt(numStudents);
                    int grade = r.nextInt(11);
                    pw.println("(" + id + ", " + grade + ")");
                }
            }

            //System.out.println("Creat fisier: " + file);
        }
        return files;
    }


    public static boolean compareFiles(String f1, String f2) throws IOException {
        try (var br1 = new java.io.BufferedReader(new java.io.FileReader(f1));
             var br2 = new java.io.BufferedReader(new java.io.FileReader(f2))) {

            String line1, line2;
            while (true) {
                line1 = br1.readLine();
                line2 = br2.readLine();

                if (line1 == null && line2 == null) return true;
                if (line1 == null || line2 == null) return false;
                if (!line1.trim().equals(line2.trim())) return false;
            }
        }
    }

    public static long runParallel(String[] files,
                                       int p_r,
                                       int p_w,
                                       int queueCapacity,
                                       String outputFile) throws InterruptedException, IOException {

        int numFiles = files.length;

        BoundedGradeQueue queue = new BoundedGradeQueue(queueCapacity, numFiles);
        FineGrainedGradeList list = new FineGrainedGradeList();

        WorkerAddThread[] workers = new WorkerAddThread[p_w];
        for (int i = 0; i < p_w; i++) {
            workers[i] = new WorkerAddThread(queue, list);
            workers[i].start();
        }

        long t0 = System.nanoTime();

        ExecutorService executor = Executors.newFixedThreadPool(p_r);
        for (String file : files) {
            executor.submit(new ReaderTask(queue, file));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        for (WorkerAddThread w : workers) {
            w.join();
        }

        List<GradeRecord> records = list.toRecords();
        LinkedList<GradeRecord> workQueue = new LinkedList<>(records);
        FineGrainedSortedByTotalList sortedList = new FineGrainedSortedByTotalList();

        WorkerSortThread[] sortWorkers = new WorkerSortThread[p_w];
        for (int i = 0; i < p_w; i++) {
            sortWorkers[i] = new WorkerSortThread(workQueue, sortedList);
            sortWorkers[i].start();
        }
        for (WorkerSortThread w : sortWorkers) {
            w.join();
        }

        long t1 = System.nanoTime();

        sortedList.writeToFile(outputFile);

        return t1 - t0;
    }

    public static void main(String[] args) throws Exception {

        int numStudents = 200;
        int numFiles = 10;
        int minNotesPerFile = 80;

        String[] files = generateRandomInputFiles(numStudents, numFiles, minNotesPerFile);
        long tSeq = Secvential.runSequential(files, "rez_lab5_secvential.txt");

        System.out.println("Timp secvential = " + tSeq + " ns");

        int p_r = 4;
        int[] p_ws = {2, 4, 8};
        int queueCapacity = 100;
        for (int p_w : p_ws) {

            long sum = 0;
            int okCount = 0;

            for (int run = 1; run <= 10; run++) {

                String out = "rezultat.txt";

                long tPar = runParallel(files, p_r, p_w, queueCapacity, out);
                sum += tPar;

                boolean eq = compareFiles("rez_lab5_secvential.txt", out);
                if (eq) okCount++;
            }

            long avg = sum / 10;
            double speedup = (double) tSeq / avg;

            System.out.println("\nTimp mediu paralel p_w=" + p_w + " = " + avg + " ns");
        }
    }
}

