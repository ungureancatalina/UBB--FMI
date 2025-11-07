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

// Worker function (procesare pe blocuri de linii)
void HorizontalAllocatedThreads::worker(int startRow, int endRow) {
    int border = n / 2;
    int* tempRow = new int[M];

    for (int i = startRow; i < endRow; i++) {
        for (int j = 0; j < M; j++) {
            int sum = 0;

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

        // suprascriem linia in matrice (in-place)
        copy(tempRow, tempRow + M, matrix[i]);
    }

    delete[] tempRow;
}

// Ruleaza filtrarea cu p threaduri
void HorizontalAllocatedThreads::run() {
    vector<thread> threads;
    int rowsPerThread = N / p;
    int extra = N % p;
    int start = 0;

    for (int i = 0; i < p; i++) {
        int end = start + rowsPerThread + (extra-- > 0 ? 1 : 0);
        threads.emplace_back(&HorizontalAllocatedThreads::worker, this, start, end);
        start = end;
    }

    for (auto& t : threads)
        t.join();
}
