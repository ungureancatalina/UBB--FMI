#ifndef HORIZONTAL_ALLOCATED_THREADS_H
#define HORIZONTAL_ALLOCATED_THREADS_H

#define MAX 10005

class HorizontalAllocatedThreads {
private:
    int N, M, n, p;
    int (*bordered)[MAX];
    int (*conv)[6];

    // fiecare thread va prelucra un set de randuri
    void worker(int startRow, int endRow, int result[MAX][MAX]);

public:
    HorizontalAllocatedThreads(int N, int M, int n, int p, int (*bordered)[MAX], int (*conv)[6]);

    // ruleaza convolutia paralel pe linii, scriind in result
    void run(int result[MAX][MAX]);
};

#endif
