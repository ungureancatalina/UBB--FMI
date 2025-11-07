package lab;

public class Sequential {
    int N, M, n;
    int[][] matrix;
    int[][] conv;

    // Constructor
    public Sequential(int N, int M, int n, int[][] matrix, int[][] conv) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.matrix = matrix;
        this.conv = conv;
    }

    // Aplica filtrul de convolutie in mod secvential
    public void run() {
        int border = n / 2;

        // 3 linii temporare pentru calcul
        int[] prevRow = new int[M];
        int[] currRow = new int[M];
        int[] nextRow = new int[M];

        // copiem prima linie pentru initializare
        System.arraycopy(matrix[0], 0, prevRow, 0, M);

        for (int i = 0; i < N; i++) {

            // pregatim linia urmatoare
            if (i < N - 1)
                System.arraycopy(matrix[i + 1], 0, nextRow, 0, M);
            else
                System.arraycopy(matrix[i], 0, nextRow, 0, M);

            for (int j = 0; j < M; j++) {
                int sum = 0;

                // parcurgem vecinii (n x n)
                for (int x = -border; x <= border; x++) {
                    int row = i + x;
                    if (row < 0) row = 0;
                    if (row >= N) row = N - 1;

                    for (int y = -border; y <= border; y++) {
                        int col = j + y;
                        if (col < 0) col = 0;
                        if (col >= M) col = M - 1;

                        int val;
                        // luam valorile din liniile potrivite
                        if (row == i - 1)
                            val = prevRow[col];
                        else if (row == i + 1)
                            val = nextRow[col];
                        else
                            val = matrix[row][col];

                        sum += val * conv[x + border][y + border];
                    }
                }
                currRow[j] = sum; // scriem rezultatul
            }

            // actualizam matricea originala
            System.arraycopy(currRow, 0, matrix[i], 0, M);
            System.arraycopy(matrix[i], 0, prevRow, 0, M);
        }
    }
}
