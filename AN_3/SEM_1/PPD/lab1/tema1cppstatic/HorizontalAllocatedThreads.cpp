#include "HorizontalAllocatedThreads.h"
#include <thread>
#include <vector>
using namespace std;

HorizontalAllocatedThreads::HorizontalAllocatedThreads(int N, int M, int n, int p, int (*bordered)[MAX], int (*conv)[6]) {
    this->N = N;
    this->M = M;
    this->n = n;
    this->p = p;
    this->bordered = bordered;
    this->conv = conv;
}

// Fiecare thread proceseaza un set de randuri
void HorizontalAllocatedThreads::worker(int startRow, int endRow, int result[MAX][MAX]) {
    int border = n / 2;

    for (int i = startRow; i < endRow; i++) {
        for (int j = 0; j < M; j++) {
            int sum = 0;
            for (int x = 0; x < n; x++) {
                for (int y = 0; y < n; y++) {
                    sum += bordered[i + x][j + y] * conv[x][y];
                }
            }
            result[i][j] = sum;
        }
    }
}

// Ruleaza convolutia folosind p threaduri (pe orizontala)
void HorizontalAllocatedThreads::run(int result[MAX][MAX]) {
    vector<thread> threads;
    int rowsPerThread = N / p;
    int start = 0;

    for (int i = 0; i < p; i++) {
        int end = (i == p - 1) ? N : start + rowsPerThread;
        threads.emplace_back(&HorizontalAllocatedThreads::worker, this, start, end, result);
        start = end;
    }

    for (auto &t : threads)
        t.join();
}
