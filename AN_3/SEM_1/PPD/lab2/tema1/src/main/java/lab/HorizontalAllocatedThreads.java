package lab;

public class HorizontalAllocatedThreads {
    int N, M, n, p;
    int[][] matrix;
    int[][] conv;

    // Constructor
    public HorizontalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] conv) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.matrix = matrix;
        this.conv = conv;
    }

    // Ruleaza filtrarea folosind p thread-uri
    public void run() throws InterruptedException {
        Thread[] threads = new Thread[p];
        int rowsPerThread = N / p; // cate linii proceseaza fiecare thread
        int extra = N % p;         // daca nu se imparte perfect
        int start = 0;

        // crearea și pornirea thread-urilor
        for (int t = 0; t < p; t++) {
            int end = start + rowsPerThread + (extra-- > 0 ? 1 : 0);
            threads[t] = new Thread(new Worker(start, end)); // fiecare thread proceseaza un bloc de linii
            threads[t].start();
            start = end;
        }

        // asteptam toate thread-urile sa termine
        for (Thread t : threads) t.join();
    }

    // Fiecare thread aplica filtrul pe liniile sale
    private class Worker implements Runnable {
        int startRow, endRow;

        Worker(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        // se face filtrarea efectiva.
        @Override
        public void run() {
            int border = n / 2;
            int[] tempRow = new int[M]; // linie temporara
            int from = Math.max(0, startRow - border);
            int to = Math.min(N, endRow + border);

            // Parcurgem liniile alocate thread-ului
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < M; j++) {
                    int sum = 0;

                    // Aplicam filtrul pe vecinii pixelului
                    for (int x = -border; x <= border; x++) {
                        int row = i + x;
                        if (row < from) row = from;
                        if (row >= to) row = to - 1;

                        for (int y = -border; y <= border; y++) {
                            int col = j + y;
                            if (col < 0) col = 0;
                            if (col >= M) col = M - 1;

                            sum += matrix[row][col] * conv[x + border][y + border];
                        }
                    }
                    tempRow[j] = sum;
                }

                // Copiem rezultatul in matrice dupa ce linia e completa
                System.arraycopy(tempRow, 0, matrix[i], 0, M);
            }
        }
    }
}
