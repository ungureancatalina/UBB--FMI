package lab;

// Clasa pentru calcul secvential al convolutiei
public class Sequential {
    int N, M, n;         // dimensiuni matrice: N x M, dimensiune convolutie n x n
    int[][] matrix;      // matricea bordata
    int[][] conv;        // matricea de convolutie

    public Sequential(int N, int M, int n, int[][] matrix, int[][] conv) {
        this.N = N;
        this.M = M;
        this.n = n;
        this.matrix = matrix;
        this.conv = conv;
    }

    // Calcul convolutie secvential
    public int[][] run() {
        int[][] result = new int[N][M];
        int border = n / 2;

        // parcurgem matricea bordata (inclusiv bordura)
        for (int i = border; i < N + border; i++) {
            for (int j = border; j < M + border; j++) {
                int sum = 0;
                // aplicam matricea de convolutie pe zona n x n
                for (int x = 0; x < n; x++)
                    for (int y = 0; y < n; y++)
                        sum += matrix[i - border + x][j - border + y] * conv[x][y];
                // salvam in matricea rezultat
                result[i - border][j - border] = sum;
            }
        }
        return result;
    }
}
