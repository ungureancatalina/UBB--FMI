#include "HorizontalAllocatedThreads.h"
#include <thread>
#include <vector>
using namespace std;

HorizontalAllocatedThreads::HorizontalAllocatedThreads(int N, int M, int n, int p, int** bordered, int (*conv)[6]) {
    this->N = N;
    this->M = M;
    this->n = n;
    this->p = p;
    this->bordered = bordered;
    this->conv = conv;
}

// Functie worker care proceseaza un interval de linii
void HorizontalAllocatedThreads::worker(int startRow, int endRow, int** result) {
    int border = n / 2;

    // Parcurgem liniile alocate acestui thread
    for (int i = startRow; i < endRow; i++) {
        for (int j = 0; j < M; j++) {
            int sum = 0;

            // Aplicam convolutia pentru fiecare element
            for (int x = 0; x < n; x++)
                for (int y = 0; y < n; y++)
                    sum += bordered[i + x][j + y] * conv[x][y];
            result[i][j] = sum;
        }
    }
}

// Functie care porneste thread-urile si le aloca pe linii
void HorizontalAllocatedThreads::run(int** result) {
    vector<thread> threads;
    int rowsPerThread = N / p;
    int start = 0;

    // Cream thread-urile si le atribuim intervale de linii
    for (int i = 0; i < p; i++) {
        int end = (i == p - 1) ? N : start + rowsPerThread;
        threads.emplace_back(&HorizontalAllocatedThreads::worker, this, start, end, result);
        start = end;
    }

    // Asteptam ca toate thread-urile sa termine
    for (auto &t : threads)
        t.join();
}
