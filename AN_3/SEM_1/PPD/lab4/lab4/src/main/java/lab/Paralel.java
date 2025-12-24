package lab;

import java.io.IOException;

public class Paralel {

    public static long runParallel(String[] files, int p, int p_r, String outputFile)
            throws IOException, InterruptedException {

        if (p_r <= 0 || p_r >= p) {
            throw new IllegalArgumentException("Trebuie 0 < p_r < p");
        }

        int p_w = p - p_r;

        //creem coada si lista
        ThreadSafeGradeList list = new ThreadSafeGradeList();
        GradeQueue queue = new GradeQueue(p_r);

        long t0 = System.nanoTime();

        // pornim producatorii
        ReaderThread[] readers = new ReaderThread[p_r];
        for (int i = 0; i < p_r; i++) {
            readers[i] = new ReaderThread(queue, files, i, p_r);
            readers[i].start();
        }

        // pornim consumatorii
        WorkerThread[] workers = new WorkerThread[p_w];
        for (int i = 0; i < p_w; i++) {
            workers[i] = new WorkerThread(queue, list);
            workers[i].start();
        }

        // asteptam sa termine toti
        for (ReaderThread r : readers) {
            r.join();
        }
        for (WorkerThread w : workers) {
            w.join();
        }

        // main-thread scrie lista in fisier
        list.writeToFile(outputFile);

        long t1 = System.nanoTime();
        return t1 - t0;
    }
}
