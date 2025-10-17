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

#define MAX 101

int N, M, n, p;
int matrix[MAX][MAX];
int conv[6][6];
int bordered[MAX][MAX];

// Citire și generare date
void readValues(ifstream& fin) { fin >> N >> M >> n >> p; }

// Citim matricea originala
void readOriginalMatrix(ifstream& fin) {
    for (int i = 0; i < N; i++)
        for (int j = 0; j < M; j++)
            fin >> matrix[i][j];
}

// Citim nucleul de convolutie
void readConvolutionMatrix(ifstream& fin) {
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            fin >> conv[i][j];
}

// Scriem rezultatele intr-un fisier
void writeToFile(int res[MAX][MAX], const string& name, int rows, int cols) {
    ofstream fout("output_" + name + ".txt");
    for (int i = 0; i < rows; i++) {
        for (int j = 0; j < cols; j++)
            fout << res[i][j] << " ";
        fout << "\n";
    }
}

// Generam fisier cu date random
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
    cout << "Fisierul date.txt a fost generat!\n";
}

// Generam fisier doar daca nu exista sau daca dimensiunile difera
void generateFileIfNeeded(int N, int M, int n, int p) {
    ifstream fin("date.txt");
    if (fin.is_open()) {
        int oldN, oldM, oldn, oldp;
        fin >> oldN >> oldM >> oldn >> oldp;
        if (oldN == N && oldM == M && oldn == n) {
            cout << "Fisierul exista deja si coincide.\n";
            return;
        }
    }
    generateFile(N, M, n, p);
}

// Adaugam padding (border) in jurul matricii
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

// Comparam doua fisiere (pentru verificarea corectitudinii)
bool compareFiles(const string& f1, const string& f2) {
    ifstream s1(f1), s2(f2);
    int a, b;
    while (s1 >> a && s2 >> b) {
        if (a != b) return false;
    }
    return !(s1 >> a) && !(s2 >> b);
}


// Testam toate implementările
void testAll() {
    //cout << "--- Rulari multiple (10) ---\n";
    long long seqTimes[10]{}, horTimes[10]{}, verTimes[10]{};

    for (int run = 0; run < 10; run++) {
        //cout << "\nRulare #" << run + 1 << "\n";

        // Masuram timpul pentru secvential
        auto start = chrono::high_resolution_clock::now();
        int seqRes[MAX][MAX];
        Sequential seq(N, M, n, bordered, conv);
        seq.run(seqRes);
        auto end = chrono::high_resolution_clock::now();
        seqTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
        //writeToFile(seqRes, "sequential", N, M);

        // Masuram timpul pentru orizontal (pe linii)
        start = chrono::high_resolution_clock::now();
        int horRes[MAX][MAX];
        HorizontalAllocatedThreads hor(N, M, n, p, bordered, conv);
        hor.run(horRes);
        end = chrono::high_resolution_clock::now();
        horTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
        //writeToFile(horRes, "horizontal", N, M);

        // Masuram timpul pentru vertical (pe coloane)
        start = chrono::high_resolution_clock::now();
        int verRes[MAX][MAX];
        VerticalAllocatedThreads ver(N, M, n, p, bordered, conv);
        ver.run(verRes);
        end = chrono::high_resolution_clock::now();
        verTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();
        //writeToFile(verRes, "vertical", N, M);


        //cout << "Verificare orizontala: " << (compareFiles("output_sequential.txt", "output_horizontal.txt") ? "CORECTA" : "GRESITA") << "\n";
        //cout << "Verificare verticala:  " << (compareFiles("output_sequential.txt", "output_vertical.txt") ? "CORECTA" : "GRESITA") << "\n";
    }

    // Calculam media timpurilor
    auto avg = [](long long arr[]) {
        long long s = 0; for (int i = 0; i < 10; i++) s += arr[i]; return s / 10;
    };

    cout << "N=" << N << " M=" << M << " n=" << n << "\n";
    cout << "Secvential: " << avg(seqTimes) << " ns\n";
    cout << p << " - vertical (coloane): " << avg(verTimes) << " ns\n";
    cout << p << " - orizontal (randuri): " << avg(horTimes) << " ns\n";

    logFile << "\nN=" << N << " M=" << M << " n=" << n << "\n";
    logFile << "Secvential: " << avg(seqTimes) << " ns\n";
    logFile << p << " - vertical: " << avg(verTimes) << " ns\n";
    logFile << p << " - orizontal: " << avg(horTimes) << " ns\n";
}

int main() {
    logFile.open("rezultate.txt", ios::app);
    if(!logFile.is_open()){
        cerr << "Eroare la deschiderea fisierului rezultate.txt\n";
        return 1;
    }

    // Generam fisier cu date daca este nevoie
    generateFileIfNeeded(10, 10, 3, 2);

    ifstream fin("date.txt");
    readValues(fin);
    readOriginalMatrix(fin);
    readConvolutionMatrix(fin);
    fin.close();

    // Adaugam padding la matrice
    borderMatrix();

    // Testam diferite valori pentru numarul de thread-uri
    int threadCounts[] = {2, 4, 8, 16};
    for (int t : threadCounts) {
        p = t;
        testAll();
    }
    // Eliberam memoria
    logFile.close();
    return 0;
}
