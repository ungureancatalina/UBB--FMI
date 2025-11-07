//#include "VerticalAllocatedThreads.h"
//#include <thread>
//#include <vector>
//using namespace std;
//
//VerticalAllocatedThreads::VerticalAllocatedThreads(int N, int M, int n, int p, int** bordered, int (*conv)[6]) {
//    this->N = N;
//    this->M = M;
//    this->n = n;
//    this->p = p;
//    this->bordered = bordered;
//    this->conv = conv;
//}
//
//void VerticalAllocatedThreads::worker(int startCol, int endCol, int** result) {
//    int border = n / 2;
//    // Parcurgem fiecare linie
//    for (int i = 0; i < N; i++) {
//        // Procesam doar coloanele alocate thread-ului
//        for (int j = startCol; j < endCol; j++) {
//            int sum = 0;
//            // Aplicam convolutia
//            for (int x = 0; x < n; x++)
//                for (int y = 0; y < n; y++)
//                    sum += bordered[i + x][j + y] * conv[x][y];
//            result[i][j] = sum;
//        }
//    }
//}
//
//void VerticalAllocatedThreads::run(int** result) {
//    vector<thread> threads;
//    int colsPerThread = M / p;
//    int start = 0;
//
//    // Cream thread-urile si le atribuim intervale de coloane
//    for (int i = 0; i < p; i++) {
//        int end = (i == p - 1) ? M : start + colsPerThread;
//        threads.emplace_back(&VerticalAllocatedThreads::worker, this, start, end, result);
//        start = end;
//    }
//
//    // Asteptam ca toate thread-urile sa termine
//    for (auto &t : threads)
//        t.join();
//}
