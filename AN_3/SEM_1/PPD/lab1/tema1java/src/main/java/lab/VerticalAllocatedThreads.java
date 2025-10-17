package lab;

// Clasa pentru paralelizare pe verticala (mai multe coloane / thread)
public class VerticalAllocatedThreads {
    int N, M, n, p;
    int[][] matrix, conv, result;

    public VerticalAllocatedThreads(int N, int M, int n, int p, int[][] matrix, int[][] conv) {
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
        int colsPerThread = M / p;       // impartirea coloanelor
        int extra = M % p;               // coloane ramase
        int start = 0;

        // cream threaduri
        for (int i = 0; i < p; i++) {
            int end = start + colsPerThread + (extra-- > 0 ? 1 : 0);
            threads[i] = new Worker(start, end);
            threads[i].start();
            start = end;
        }

        // asteptam threadurile sa termine
        for (Thread t : threads) t.join();
        return result;
    }

    // Thread pentru un bloc de coloane
    class Worker extends Thread {
        int startCol, endCol;
        public Worker(int startCol, int endCol) {
            this.startCol = startCol;
            this.endCol = endCol;
        }

        public void run() {
            int border = n / 2;
            for (int i = border; i < N + border; i++) {
                for (int j = startCol + border; j < endCol + border; j++) {
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