#ifndef SEQUENTIAL_H
#define SEQUENTIAL_H

class Sequential {
private:
    int N, M, n;
    int** bordered;
    int (*conv)[6];

public:
    Sequential(int N, int M, int n, int** bordered, int (*conv)[6]);
    void run(int** result);
};

#endif
