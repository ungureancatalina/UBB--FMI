#include "Logger.h"
#include <fstream>
#include <iostream>
#include <chrono>

using namespace std;

void logResult(const string& variantName, int N1, int N2, int processes, long long timeNs) {
    ofstream fout("Results.txt", ios::app); // append mode
    if (!fout.is_open()) {
        cerr << "Eroare la deschiderea Results.txt pentru scriere!" << endl;
        return;
    }

    // Scriem data și ora rulării
    auto now = chrono::system_clock::to_time_t(chrono::system_clock::now());
    fout << "----------------------------------------\n";
    fout << "Data rulare: " << ctime(&now);
    fout << "Varianta: " << variantName << "\n";
    fout << "N1=" << N1 << " | N2=" << N2 << " | Procese=" << processes
         << " | Timp total (ns): " << timeNs << "\n";
    fout.close();
}

long long averageExecutionTime(int runs, const std::function<void()>& func) {
    using namespace std::chrono;
    long long total = 0;

    for (int i = 0; i < runs; i++) {
        auto start = high_resolution_clock::now();
        func(); // execută funcția testată
        auto end = high_resolution_clock::now();
        total += duration_cast<nanoseconds>(end - start).count();
    }

    return total / runs; // media în nanosecunde
}
