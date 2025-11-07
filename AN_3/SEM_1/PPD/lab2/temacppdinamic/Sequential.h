#ifndef SEQUENTIAL_H
#define SEQUENTIAL_H

class Sequential {
private:
    int N, M, n;
    int** matrix;
    int (*conv)[3];

public:
    Sequential(int N, int M, int n, int** matrix, int (*conv)[3]);
    void run();
};

#endif
