#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include "Logger.h"

using namespace std;

// Citeste un numar mare din fisier.
vector<unsigned char> readNumber1(const string& filename, int& n) {
    ifstream fin(filename);
    fin >> n;                 // citim cate cifre are numarul
    vector<unsigned char> v(n);

    // citim cifrele invers pentru operatii rapide
    for (int i = n - 1; i >= 0; i--) {
        int d; fin >> d;
        v[i] = d;
    }
    return v;
}

// Scrie numarul complet in fisier:
void writeFullNumberV1(const vector<unsigned char>& digits, const string& filename) {
    ofstream fout(filename);
    if (!fout.is_open()) {
        cerr << "Nu pot deschide fisierul pentru scriere: " << filename << endl;
        return;
    }

    // eliminam zerourile nesemnificative de la final (pozitia cea mai mare)
    int last = (int)digits.size() - 1;
    while (last > 0 && digits[last] == 0)
        last--;

    int length = last + 1;
    fout << length << "\n";

    // scriem cifrele in ordine descrescatoare a indexului
    for (int i = last; i >= 0; --i)
        fout << (int)digits[i] << " ";

    fout << "\n";
    fout.close();
}


// Varianta 1 MPI – comunicare STANDARD (MPI_Send / MPI_Recv)
void runVariant1() {
    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);   // id-ul procesului curent
    MPI_Comm_size(MPI_COMM_WORLD, &size);   // numarul total de procese

    const string outFile = "Numar3_MPI_V1.txt";
    int runs = 10;

    vector<unsigned char> lastResult;

    long long avgNs = averageExecutionTime(runs, [&]() {

        int n1 = 0, n2 = 0;
        int N = 0;
        vector<unsigned char> num1, num2;

        // Procesul 0 citeste numerele, le face egale ca lungime si le pregateste
        if (rank == 0) {
            num1 = readNumber1("Numar1.txt", n1);
            num2 = readNumber1("Numar2.txt", n2);

            N = max(n1, n2);   // lungimea maxima
            num1.resize(N, 0);
            num2.resize(N, 0);
        }

        // trimitem tuturor lungimea N
        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);

        if (N == 0) { MPI_Barrier(MPI_COMM_WORLD); return; }

        int workers = size - 1;                       // procesele 1..size-1
        int perProcess = (N + workers - 1) / workers; // impartim in bucati egale

        unsigned char finalCarry = 0;                 // carry de la ultimul worker

        // Procesul 0 trimite segmentele catre workeri
        if (rank == 0) {
            int sent = 0;

            for (int id = 1; id < size; ++id) {
                int offset = sent;   // unde incepe segmentul
                int count = 0;

                if (sent < N)
                    count = min(perProcess, N - sent);

                // trimitem offset si dimensiunea segmentului
                MPI_Send(&offset, 1, MPI_INT, id, 9, MPI_COMM_WORLD);
                MPI_Send(&count, 1, MPI_INT, id, 0, MPI_COMM_WORLD);

                // trimitem cifre doar daca are ceva de procesat
                if (count > 0) {
                    MPI_Send(num1.data() + offset, count, MPI_UNSIGNED_CHAR, id, 1, MPI_COMM_WORLD);
                    MPI_Send(num2.data() + offset, count, MPI_UNSIGNED_CHAR, id, 2, MPI_COMM_WORLD);
                }

                sent += count;
            }
        }

            // Workerii aduna cifrele si trimit carry-ul spre urmatorul worker
        else {
            int offset, count;

            MPI_Recv(&offset, 1, MPI_INT, 0, 9, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
            MPI_Recv(&count, 1, MPI_INT, 0, 0, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            if (count > 0) {

                vector<unsigned char> a(count), b(count), sum(count);

                // primim cele doua segmente
                MPI_Recv(a.data(), count, MPI_UNSIGNED_CHAR, 0, 1, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                MPI_Recv(b.data(), count, MPI_UNSIGNED_CHAR, 0, 2, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                // carry-ul de la procesul anterior
                unsigned char carryIn = 0;
                if (rank > 1)
                    MPI_Recv(&carryIn, 1, MPI_UNSIGNED_CHAR, rank - 1, 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                // adunam cifra cu cifra
                for (int i = 0; i < count; i++) {
                    int s = a[i] + b[i] + carryIn;
                    sum[i] = (unsigned char)(s % 10);
                    carryIn = (unsigned char)(s / 10);
                }
                unsigned char carryOut = carryIn;

                // trimitem carry-ul mai departe
                if (rank < size - 1)
                    MPI_Send(&carryOut, 1, MPI_UNSIGNED_CHAR, rank + 1, 5, MPI_COMM_WORLD);
                else
                    MPI_Send(&carryOut, 1, MPI_UNSIGNED_CHAR, 0, 6, MPI_COMM_WORLD);

                // trimitem segmentul rezultat la rank 0
                MPI_Send(&offset, 1, MPI_INT, 0, 9, MPI_COMM_WORLD);
                MPI_Send(&count, 1, MPI_INT, 0, 3, MPI_COMM_WORLD);
                MPI_Send(sum.data(), count, MPI_UNSIGNED_CHAR, 0, 4, MPI_COMM_WORLD);
            }

                // Workerii fara date trebuie totusi sa primeasca si sa trimita carry
            else {
                if (rank > 1) {
                    unsigned char carryIn = 0;
                    MPI_Recv(&carryIn, 1, MPI_UNSIGNED_CHAR, rank - 1, 5, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                    if (rank < size - 1)
                        MPI_Send(&carryIn, 1, MPI_UNSIGNED_CHAR, rank + 1, 5, MPI_COMM_WORLD);
                    else
                        MPI_Send(&carryIn, 1, MPI_UNSIGNED_CHAR, 0, 6, MPI_COMM_WORLD);
                }
            }
        }

        // Procesul 0 reasambleaza numarul final din segmentele primite
        if (rank == 0) {
            vector<unsigned char> fullResult(N, 0);

            // primim toate segmentele partiale
            for (int id = 1; id < size; ++id) {
                int offset, count;
                MPI_Recv(&offset, 1, MPI_INT, id, 9, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                MPI_Recv(&count, 1, MPI_INT, id, 3, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

                if (count > 0) {
                    vector<unsigned char> partial(count);
                    MPI_Recv(partial.data(), count, MPI_UNSIGNED_CHAR, id, 4, MPI_COMM_WORLD, MPI_STATUS_IGNORE);
                    for (int k = 0; k < count; ++k)
                        fullResult[offset + k] = partial[k];
                }
            }

            // primim carry-ul final de la ultimul proces
            MPI_Recv(&finalCarry, 1, MPI_UNSIGNED_CHAR, size - 1, 6, MPI_COMM_WORLD, MPI_STATUS_IGNORE);

            if (finalCarry > 0)
                fullResult.push_back(finalCarry);

            lastResult = fullResult;
        }

        MPI_Barrier(MPI_COMM_WORLD);
    });

    // Rank 0 afiseaza timpul mediu si scrie rezultatul complet
    if (rank == 0) {
        cout << "Timp mediu : " << avgNs << " ns" << endl;

        writeFullNumberV1(lastResult, outFile);

        int N1 = 0, N2 = 0;
        ifstream f1("Numar1.txt"); f1 >> N1; f1.close();
        ifstream f2("Numar2.txt"); f2 >> N2; f2.close();

        logResult("Varianta 1 ", N1, N2, size, avgNs);
    }
}
