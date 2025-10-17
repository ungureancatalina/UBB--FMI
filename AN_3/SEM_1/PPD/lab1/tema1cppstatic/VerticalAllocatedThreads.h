#ifndef VERTICAL_ALLOCATED_THREADS_H
#define VERTICAL_ALLOCATED_THREADS_H

#define MAX 101

class VerticalAllocatedThreads {
private:
    int N, M, n, p;
    int (*bordered)[MAX];
    int (*conv)[6];

    // Fiecare thread proceseaza un set de coloane
    void worker(int startCol, int endCol, int result[MAX][MAX]);

public:
    VerticalAllocatedThreads(int N, int M, int n, int p, int (*bordered)[MAX], int (*conv)[6]);

    // rulează convolutia paralel pe coloane, scriind in result
    void run(int result[MAX][MAX]);
};

#endif // VERTICAL_ALLOCATED_THREADS_H
