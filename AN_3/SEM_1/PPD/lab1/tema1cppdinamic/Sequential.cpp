#include "Sequential.h"

Sequential::Sequential(int N, int M, int n, int** bordered, int (*conv)[6]) {
    this->N = N;
    this->M = M;
    this->n = n;
    this->bordered = bordered;
    this->conv = conv;
}

void Sequential::run(int** result) {
    int border = n / 2;

    // Parcurgem fiecare element al matricii
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++) {
            int sum = 0;
            // Aplicam convolutia
            for (int x = 0; x < n; x++)
                for (int y = 0; y < n; y++)
                    sum += bordered[i + x][j + y] * conv[x][y];
            result[i][j] = sum;
        }
    }
}
