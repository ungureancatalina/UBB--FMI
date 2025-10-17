#ifndef VERTICAL_ALLOCATED_THREADS_H
#define VERTICAL_ALLOCATED_THREADS_H

class VerticalAllocatedThreads {
private:
    int N, M, n, p;
    int** bordered;
    int (*conv)[6];

    void worker(int startCol, int endCol, int** result);

public:
    VerticalAllocatedThreads(int N, int M, int n, int p, int** bordered, int (*conv)[6]);
    void run(int** result);
};

#endif
