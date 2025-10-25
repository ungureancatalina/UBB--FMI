#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <thread>
#include <cstring>
#include <cstdlib>
#include <ctime>
#include "Sequential.h"
#include "HorizontalAllocatedThreads.h"
#include "VerticalAllocatedThreads.h"
using namespace std;
ofstream logFile;

// limita maxima pentru matrici mari static global
#define MAX 10005

// matrici mari globale restul locale
int N, M, n, p;
int matrix[MAX][MAX];     // matrice originala
int bordered[MAX][MAX];   // matrice cu padding
int conv[6][6];           // nucleu convolutie

// Citire si generare date
void readValues(ifstream& fin) { fin >> N >> M >> n >> p; }

void readOriginalMatrix(ifstream& fin) {
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            fin >> matrix[i][j];
}

void readConvolutionMatrix(ifstream& fin) {
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            fin >> conv[i][j];
}

// Generare fisier date random
void generateFile(int N, int M, int n, int p) {
    srand(time(0));
    ofstream fout("date.txt");
    fout << N << " " << M << " " << n << " " << p << "\n";
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < M; j++)
            fout << (rand() % 21 + 40) << " ";
        fout << "\n";
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++)
            fout << (rand() % 2) << " ";
        fout << "\n";
    }
    fout.close();
    cout << "Fisierul date.txt a fost generat!\n";
}

// Genereaza doar daca lipseste sau nu corespunde
void generateFileIfNeeded(int N, int M, int n, int p) {
    ifstream fin("date.txt");
    if (fin.is_open()) {
        int oldN, oldM, oldn, oldp;
        fin >> oldN >> oldM >> oldn >> oldp;
        fin.close();
        if (oldN == N && oldM == M && oldn == n) {
            cout << "Fisierul date.txt exista deja si coincide.\n";
            return;
        }
    }
    generateFile(N, M, n, p);
}

// Adauga padding in jurul matricii
void borderMatrix() {
    int border = n / 2;
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            bordered[i + border][j + border] = matrix[i][j];

    for (int i = 0; i < border; i++) {
        for (int j = 0; j < M + 2 * border; j++)
            bordered[i][j] = bordered[border][j];
        for (int j = 0; j < M + 2 * border; j++)
            bordered[N + border + i][j] = bordered[N + border - 1][j];
    }

    for (int i = 0; i < N + 2 * border; i++) {
        for (int j = 0; j < border; j++) {
            bordered[i][j] = bordered[i][border];
            bordered[i][M + border + j] = bordered[i][M + border - 1];
        }
    }
}

// Testare pentru toate implementarile
void testAll() {
    long long seqTimes[10]{}, horTimes[10]{}, verTimes[10]{};

    // matrice de rezultat locala — doar una o data in memorie
    static int result[MAX][MAX];

    for (int run = 0; run < 10; run++) {
        // secvential
        auto start = chrono::high_resolution_clock::now();
        Sequential seq(N, M, n, bordered, conv);
        seq.run(result);
        auto end = chrono::high_resolution_clock::now();
        seqTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();

        // orizontal
        start = chrono::high_resolution_clock::now();
        HorizontalAllocatedThreads hor(N, M, n, p, bordered, conv);
        hor.run(result);
        end = chrono::high_resolution_clock::now();
        horTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();

        // vertical
        start = chrono::high_resolution_clock::now();
        VerticalAllocatedThreads ver(N, M, n, p, bordered, conv);
        ver.run(result);
        end = chrono::high_resolution_clock::now();
        verTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
    }

    auto avg = [](long long arr[]) {
        long long s = 0; for (int i = 0; i < 10; i++) s += arr[i]; return s / 10;
    };

    cout << "\n-----------------------------------------\n";
    cout << "N=" << N << " M=" << M << " n=" << n << "\n";
    cout << "Secvential: " << avg(seqTimes) << " ns\n";
    cout << p << " - vertical (coloane): " << avg(verTimes) << " ns\n";
    cout << p << " - orizontal (randuri): " << avg(horTimes) << " ns\n";
    cout << "-----------------------------------------\n";

    logFile << "\n-----------------------------------------\n";
    logFile << "N=" << N << " M=" << M << " n=" << n << "\n";
    logFile << "Secvential: " << avg(seqTimes) << " ns\n";
    logFile << p << " - vertical: " << avg(verTimes) << " ns\n";
    logFile << p << " - orizontal: " << avg(horTimes) << " ns\n";
    logFile << "-----------------------------------------\n";
    logFile.flush();
}

int main() {
    logFile.open("rezultate.txt", ios::app);
    if(!logFile.is_open()){
        cerr << "Eroare la deschiderea fisierului rezultate.txt\n";
        return 1;
    }

    int testN = 10000;
    int testM = 10000;
    int testn = 5;
    int testp = 2;

    generateFileIfNeeded(testN, testM, testn, testp);

    ifstream fin("date.txt");
    readValues(fin);
    readOriginalMatrix(fin);
    readConvolutionMatrix(fin);
    fin.close();

    borderMatrix();

    int threadCounts[] = {2, 4, 8, 16};
    for (int t : threadCounts) {
        p = t;
        testAll();
    }

    logFile.close();
    cout << "\nRezultatele au fost scrise si in fisierul rezultate.txt\n";
    return 0;
}
