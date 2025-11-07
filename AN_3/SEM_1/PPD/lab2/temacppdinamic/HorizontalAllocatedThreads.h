#ifndef HORIZONTAL_ALLOCATED_THREADS_H
#define HORIZONTAL_ALLOCATED_THREADS_H

#include <thread>
#include <vector>
using namespace std;

// Distributie pe linii, in-place, doar vectori O(n)
class HorizontalAllocatedThreads {
private:
    int N, M, n, p;
    int** matrix;
    int (*conv)[3];

    void worker(int startRow, int endRow);

public:
    HorizontalAllocatedThreads(int N, int M, int n, int p, int** matrix, int (*conv)[3]);
    void run();
};

#endif
