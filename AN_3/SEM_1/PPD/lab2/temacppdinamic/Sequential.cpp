#include "HorizontalAllocatedThreads.h"
#include <algorithm>
using namespace std;

// Constructor
HorizontalAllocatedThreads::HorizontalAllocatedThreads(int N, int M, int n, int p, int** matrix, int (*conv)[3]) {
    this->N = N;
    this->M = M;
    this->n = n;
    this->p = p;
    this->matrix = matrix;
    this->conv = conv;
}

// Functie worker - proceseaza un set de linii
void HorizontalAllocatedThreads::worker(int startRow, int endRow) {
    int border = n / 2;
    int* tempRow = new int[M];  // linie temporara

    for (int i = startRow; i < endRow; i++) {
        for (int j = 0; j < M; j++) {
            int sum = 0;

            // aplicam filtrul pe vecini
            for (int x = -border; x <= border; x++) {
                int r = i + x;
                if (r < 0) r = 0;
                if (r >= N) r = N - 1;

                for (int y = -border; y <= border; y++) {
                    int c = j + y;
                    if (c < 0) c = 0;
                    if (c >= M) c = M - 1;

                    sum += matrix[r][c] * conv[x + border][y + border];
                }
            }
            tempRow[j] = sum;
        }

        // copiem rezultatul in matrice
        copy(tempRow, tempRow + M, matrix[i]);
    }

    delete[] tempRow; // eliberam memoria
}

// Ruleaza filtrarea folosind p thread-uri
void HorizontalAllocatedThreads::run() {
    vector<thread> threads;
    int rowsPerThread = N / p;  // cate linii proceseaza fiecare thread
    int extra = N % p;          // daca nu se imparte perfect
    int start = 0;

    // pornim thread-urile
    for (int i = 0; i < p; i++) {
        int end = start + rowsPerThread + (extra-- > 0 ? 1 : 0);
        threads.emplace_back(&HorizontalAllocatedThreads::worker, this, start, end);
        start = end;
    }

    // asteptam sa se termine toate
    for (auto& t : threads)
        t.join();
}
