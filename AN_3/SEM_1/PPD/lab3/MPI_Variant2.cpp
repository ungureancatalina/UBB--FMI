#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <algorithm>
#include "Logger.h"

using namespace std;

// Citeste un numar mare din fisier.
vector<unsigned char> readNumber2(const string& file, int& n) {
    ifstream fin(file);
    fin >> n;
    vector<unsigned char> v(n);

    for (int i = n - 1; i >= 0; i--) {
        int d; fin >> d;
        v[i] = d;
    }
    fin.close();
    return v;
}

// Varianta 2 MPI – foloseste modelul Scatter/Gather.
void runVariant2() {
    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);  // id-ul procesului curent
    MPI_Comm_size(MPI_COMM_WORLD, &size);  // numarul total de procese

    int runs = 10;
    vector<unsigned char> lastResult; // rezultatul ultimei rulari (doar la rank 0)

    long long avgNs = averageExecutionTime(runs, [&]() {

        vector<unsigned char> num1, num2;
        int N = 0;

        // Rank 0 citeste numerele, le face egale ca lungime si completeaza cu 0
        // ca sa fie divizibile cu numarul de procese.
        if (rank == 0) {
            int n1, n2;
            num1 = readNumber2("Numar1.txt", n1);
            num2 = readNumber2("Numar2.txt", n2);

            N = max(n1, n2);

            // facem N divizibil cu nr. de procese (conditie pentru Scatter)
            if (N % size != 0)
                N = ((N / size) + 1) * size;

            num1.resize(N, 0);
            num2.resize(N, 0);
        }

        // Trimitem tuturor valoarea N
        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);

        if (N == 0) {
            MPI_Barrier(MPI_COMM_WORLD);
            return;
        }

        int chunk = N / size;  // cate cifre primeste fiecare proces

        // Bufferele locale ale fiecarui proces
        vector<unsigned char> local1(chunk), local2(chunk), localSum(chunk);

        // MPI_Scatter distribuie automat cate "chunk" cifre fiecarui proces
        MPI_Scatter(num1.data(), chunk, MPI_UNSIGNED_CHAR,
                    local1.data(), chunk, MPI_UNSIGNED_CHAR,
                    0, MPI_COMM_WORLD);

        MPI_Scatter(num2.data(), chunk, MPI_UNSIGNED_CHAR,
                    local2.data(), chunk, MPI_UNSIGNED_CHAR,
                    0, MPI_COMM_WORLD);

        // Fiecare proces face suma locala pe segmentul primit.
        // Carry-ul se transmite de la rank i la rank i+1.
        unsigned char carryIn = 0;

        // Procesele rank > 0 asteapta carry-ul de la procesul precedent
        if (rank > 0) {
            MPI_Recv(&carryIn, 1, MPI_UNSIGNED_CHAR,
                     rank - 1, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
        }

        unsigned char carry = carryIn;

        // calculam cifra cu cifra suma partiala
        for (int i = 0; i < chunk; i++) {
            int s = local1[i] + local2[i] + carry;
            localSum[i] = (unsigned char)(s % 10);
            carry = (unsigned char)(s / 10);
        }

        // Procesul urmator primeste carry-ul nostru.
        // Ultimul proces trimite carry-ul final la rank 0.
        if (rank < size - 1) {
            MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, rank + 1, 0, MPI_COMM_WORLD);
        } else {
            MPI_Send(&carry, 1, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD);
        }

        // MPI_Gather colecteaza toate sumele partiale la rank 0
        vector<unsigned char> total;
        if (rank == 0)
            total.resize(N);

        MPI_Gather(localSum.data(), chunk, MPI_UNSIGNED_CHAR,
                   total.data(), chunk, MPI_UNSIGNED_CHAR,
                   0, MPI_COMM_WORLD);

        // Rank 0 finalizeaza rezultatul: primeste carry final, elimina zerouri,
        // si pastreaza rezultatul ultimei rulari.
        if (rank == 0) {

            unsigned char finalCarry = 0;
            MPI_Recv(&finalCarry, 1, MPI_UNSIGNED_CHAR,
                     size - 1, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            // daca a ramas un carry final, il adaugam in vector
            if (finalCarry > 0)
                total.push_back(finalCarry);

            // eliminam zerourile nesemnificative
            while (total.size() > 1 && total.back() == 0)
                total.pop_back();

            lastResult = total;
        }

        MPI_Barrier(MPI_COMM_WORLD);
    });

    // Dupa 10 rulari, rank 0 scrie rezultatul final si logheaza timpul mediu
    if (rank == 0) {
        cout << "Timp mediu : " << avgNs << " ns" << endl;

        // Scriem in fisier
        ofstream fout("Numar3_MPI_V2.txt");
        int len = (int)lastResult.size();
        fout << len << "\n";
        for (int i = len - 1; i >= 0; --i)
            fout << (int)lastResult[i] << " ";
        fout << "\n";
        fout.close();

        // log
        int N1 = 0, N2 = 0;
        ifstream f1("Numar1.txt"); f1 >> N1; f1.close();
        ifstream f2("Numar2.txt"); f2 >> N2; f2.close();

        logResult("Varianta 2 ", N1, N2, size, avgNs);
    }
}
