#include <mpi.h>
#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <algorithm>
#include "Logger.h"

using namespace std;

// Functie care citeste un numar mare din fisier.
vector<unsigned char> readNumber3(const string& filename, int& numDigits) {
    ifstream fin(filename);
    if (!fin.is_open()) {
        cerr << "Nu pot deschide fisierul: " << filename << endl;
        numDigits = 0;
        return {};
    }

    fin >> numDigits;
    vector<unsigned char> number(numDigits);

    // salvam invers pentru a avea ordinea corecta
    for (int i = numDigits - 1; i >= 0; --i) {
        int digit;
        fin >> digit;
        number[i] = (unsigned char)digit;
    }

    fin.close();
    return number;
}

// Scrie un vector de cifre intr-un fisier.
// Este eliminat automat orice zero nesemnificativ.
void writeNumber(const vector<unsigned char>& digits, const string& filename) {
    ofstream fout(filename);
    if (!fout.is_open()) {
        cerr << "Nu pot deschide fisierul pentru scriere: " << filename << endl;
        return;
    }

    // Eliminam zerourile nesemnificative din coada vectorului
    int last = (int)digits.size() - 1;
    while (last > 0 && digits[last] == 0)
        --last;

    int length = last + 1;

    fout << length << "\n";

    for (int i = last; i >= 0; --i)
        fout << (int)digits[i] << " ";

    fout << "\n";
    fout.close();
}

// VARIANTA 3 – Comunicare MPI asincorona (Isend / Irecv)
void runVariant3() {
    int rank, size;
    MPI_Comm_rank(MPI_COMM_WORLD, &rank);   // id proces
    MPI_Comm_size(MPI_COMM_WORLD, &size);   // numar procese

    if (size < 2) {
        if (rank == 0)
            cerr << "Trebuie cel putin 2 procese.\n";
        return;
    }

    int runs = 10;
    vector<unsigned char> lastResult;

    long long avgNs = averageExecutionTime(runs, [&]() {

        auto startTime = chrono::high_resolution_clock::now();

        int N = 0;
        vector<unsigned char> num1, num2;

        // Rank 0 citeste numerele si le completeaza astfel incat sa aiba
        // aceeasi lungime.
        if (rank == 0) {
            int n1 = 0, n2 = 0;
            num1 = readNumber3("Numar1.txt", n1);
            num2 = readNumber3("Numar2.txt", n2);
            N = max(n1, n2);
            num1.resize(N, 0);
            num2.resize(N, 0);
        }

        // Trimitem N tuturor proceselor
        MPI_Bcast(&N, 1, MPI_INT, 0, MPI_COMM_WORLD);
        if (N == 0) {
            MPI_Barrier(MPI_COMM_WORLD);
            return;
        }

        int workers = size - 1;

        // Impartim cifrele astfel incat workerii 1..size-1 sa primeasca segmente
        // aproximativ egale.
        int base = N / workers;
        int extra = N % workers;

        // Functii lambda pentru calculul segmentelor
        auto getStart = [&](int r) {
            return (r - 1) * base + min(r - 1, extra);
        };

        auto getCount = [&](int r) {
            return base + (r <= extra ? 1 : 0);
        };

        vector<unsigned char> resultDigits;
        unsigned char finalCarry = 0;

        // Procesul 0
        if (rank == 0) {
            resultDigits.assign(N + 1, 0);

            vector<MPI_Request> sendReqs;
            sendReqs.reserve(2 * workers);

            // Trimitem asincron fiecarui worker segmentele proprii local1 si local2.
            for (int r = 1; r <= workers; ++r) {
                int len = getCount(r);
                int startIdx = getStart(r);
                if (len <= 0) continue;

                MPI_Request req1, req2;
                MPI_Isend(num1.data() + startIdx, len, MPI_UNSIGNED_CHAR,
                          r, 10, MPI_COMM_WORLD, &req1);
                MPI_Isend(num2.data() + startIdx, len, MPI_UNSIGNED_CHAR,
                          r, 11, MPI_COMM_WORLD, &req2);

                sendReqs.push_back(req1);
                sendReqs.push_back(req2);
            }

            // Pregatim recv asincron pentru rezultatele partiale
            vector<MPI_Request> recvResultReqs;
            recvResultReqs.reserve(workers);

            for (int r = 1; r <= workers; ++r) {
                int len = getCount(r);
                int startIdx = getStart(r);
                if (len <= 0) continue;

                MPI_Request req;
                MPI_Irecv(resultDigits.data() + startIdx, len, MPI_UNSIGNED_CHAR,
                          r, 40, MPI_COMM_WORLD, &req);
                recvResultReqs.push_back(req);
            }

            // Primim carry final de la ultimul worker
            MPI_Request recvCarryReq;
            MPI_Irecv(&finalCarry, 1, MPI_UNSIGNED_CHAR,
                      workers, 30, MPI_COMM_WORLD, &recvCarryReq);

            // Asteptam finalizarea tuturor comunicarilor asincrone
            if (!sendReqs.empty())
                MPI_Waitall((int)sendReqs.size(), sendReqs.data(), MPI_STATUSES_IGNORE);
            if (!recvResultReqs.empty())
                MPI_Waitall((int)recvResultReqs.size(), recvResultReqs.data(), MPI_STATUSES_IGNORE);

            MPI_Wait(&recvCarryReq, MPI_STATUS_IGNORE);

            // Daca exista carry final, il punem la finalul vectorului
            if (finalCarry > 0)
                resultDigits[N] = finalCarry;

            lastResult = resultDigits; // salvam ultima runda
        }

            // Workerii
        else {
            int len = getCount(rank);
            int startIdx = getStart(rank);

            if (len > 0) {
                vector<unsigned char> local1(len), local2(len), localSum(len);

                // Primim asincron datele de la rank 0
                MPI_Request reqData[2];
                MPI_Irecv(local1.data(), len, MPI_UNSIGNED_CHAR,
                          0, 10, MPI_COMM_WORLD, &reqData[0]);
                MPI_Irecv(local2.data(), len, MPI_UNSIGNED_CHAR,
                          0, 11, MPI_COMM_WORLD, &reqData[1]);

                // Primul worker are carry initial = 0
                unsigned char carryIn = 0;
                MPI_Request carryReq;
                bool hasCarryReq = false;

                if (rank > 1) {
                    // workerii >1 primesc carry asincron de la workerul precedent
                    MPI_Irecv(&carryIn, 1, MPI_UNSIGNED_CHAR,
                              rank - 1, 20, MPI_COMM_WORLD, &carryReq);
                    hasCarryReq = true;
                }

                // Asteptam toate datele: num1, num2 si carry
                if (hasCarryReq) {
                    MPI_Request allReqs[3] = { reqData[0], reqData[1], carryReq };
                    MPI_Waitall(3, allReqs, MPI_STATUSES_IGNORE);
                } else {
                    MPI_Waitall(2, reqData, MPI_STATUSES_IGNORE);
                }

                // Calculam suma partiala
                unsigned char carryOut = 0;
                for (int i = 0; i < len; ++i) {
                    int s = local1[i] + local2[i] + carryIn;
                    localSum[i] = (unsigned char)(s % 10);
                    carryIn = (unsigned char)(s / 10);
                }
                carryOut = carryIn;

                // Trimitem carry-ul mai departe, asincron
                MPI_Request sendCarryReq;
                if (rank < workers)
                    MPI_Isend(&carryOut, 1, MPI_UNSIGNED_CHAR,
                              rank + 1, 20, MPI_COMM_WORLD, &sendCarryReq);
                else
                    MPI_Isend(&carryOut, 1, MPI_UNSIGNED_CHAR,
                              0, 30, MPI_COMM_WORLD, &sendCarryReq);

                // Trimitem rezultatul partial la procesul 0
                MPI_Request sendResultReq;
                MPI_Isend(localSum.data(), len, MPI_UNSIGNED_CHAR,
                          0, 40, MPI_COMM_WORLD, &sendResultReq);

                // Asteptam finalizarea trimiterilor
                MPI_Wait(&sendResultReq, MPI_STATUS_IGNORE);
                MPI_Wait(&sendCarryReq, MPI_STATUS_IGNORE);
            }
        }

        MPI_Barrier(MPI_COMM_WORLD);
        auto endTime = chrono::high_resolution_clock::now();
        auto elapsed = chrono::duration_cast<chrono::nanoseconds>(endTime - startTime).count();
    });

    // Dupa cele 10 rulari, procesul 0 scrie rezultatul final si logheaza timpul
    if (rank == 0) {
        cout << "Timp mediu : " << avgNs << " ns" << endl;

        writeNumber(lastResult, "Numar3_MPI_V3.txt");

        int N1 = 0, N2 = 0;
        ifstream f1("Numar1.txt"); f1 >> N1; f1.close();
        ifstream f2("Numar2.txt"); f2 >> N2; f2.close();

        logResult("Varianta 3 ", N1, N2, size, avgNs);
    }
}
