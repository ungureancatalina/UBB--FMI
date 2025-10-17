#ifndef HORIZONTAL_ALLOCATED_THREADS_H
#define HORIZONTAL_ALLOCATED_THREADS_H

#define MAX 1010

class HorizontalAllocatedThreads {
private:
    int N, M, n, p;
    int** bordered;
    int (*conv)[6];

    void worker(int startRow, int endRow, int** result);

public:
    HorizontalAllocatedThreads(int N, int M, int n, int p, int** bordered, int (*conv)[6]);
    void run(int** result);
};

#endif
