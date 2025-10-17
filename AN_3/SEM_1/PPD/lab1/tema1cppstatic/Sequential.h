#ifndef SEQUENTIAL_H
#define SEQUENTIAL_H

#define MAX 101

class Sequential {
private:
    int N, M, n;
    int (*bordered)[MAX];
    int (*conv)[6];

public:
    Sequential(int N, int M, int n, int (*bordered)[MAX], int (*conv)[6]);
    void run(int result[MAX][MAX]);
};

#endif
