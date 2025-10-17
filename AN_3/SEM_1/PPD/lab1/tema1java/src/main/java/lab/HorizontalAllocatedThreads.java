package lab;

// Clasa pentru paralelizare pe orizontala (mai multe linii / thread)
public class HorizontalAllocatedThreads {
    int N, M, n, p;
    int[][] matrix, conv, result;

    public HorizontalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] conv) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.p = p;
        this.matrix = matrix;
        this.conv = conv;
    }

    // Functie pentru rulare cu threaduri
    public int[][] run() throws InterruptedException {
        result = new int[N][M];
        Thread[] threads = new Thread[p];
        int rowsPerThread = N / p;       // impartirea liniilor
        int extra = N % p;               // linii ramase
        int start = 0;

        // cream threaduri
        for (int i = 0; i < p; i++) {
            int end = start + rowsPerThread + (extra-- > 0 ? 1 : 0);
            threads[i] = new Worker(start, end);
            threads[i].start();
            start = end;
        }

        // asteptam threadurile sa termine
        for (Thread t : threads) t.join();
        return result;
    }

    // Thread pentru un bloc de linii
    class Worker extends Thread {
        int startRow, endRow;
        public Worker(int startRow, int endRow) {
            this.startRow = startRow;
            this.endRow = endRow;
        }

        public void run() {
            int border = n / 2;
            for (int i = startRow + border; i < endRow + border; i++) {
                for (int j = border; j < M + border; j++) {
                    int sum = 0;
                    for (int x = 0; x < n; x++)
                        for (int y = 0; y < n; y++)
                            sum += matrix[i - border + x][j - border + y] * conv[x][y];
                    result[i - border][j - border] = sum;
                }
            }
        }
    }
}