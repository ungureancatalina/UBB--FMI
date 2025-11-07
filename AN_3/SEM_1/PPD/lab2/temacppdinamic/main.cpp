#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include "Sequential.h"
#include "HorizontalAllocatedThreads.h"
using namespace std;
ofstream logFile;

// Dimensiuni si parametri
int N, M, n, p;
int** matrix;      // Matricea originala
int conv[3][3];    // Nucleul de convolutie

// Citim parametrii din fisier
void readValues(ifstream& fin) { fin >> N >> M >> n >> p; }

// Citim matricea originala
void readOriginalMatrix(ifstream& fin) {
    for(int i=0; i<N; i++)
        for(int j=0; j<M; j++)
            fin >> matrix[i][j];
}

// Citim nucleul de convolutie
void readConvolutionMatrix(ifstream& fin) {
    for(int i=0;i<n;i++)
        for(int j=0;j<n;j++)
            fin >> conv[i][j];
}

// Generam fisier cu date random
void generateFile(int N, int M, int n, int p){
    srand(time(0));
    ofstream fout("date.txt");
    fout << N << " " << M << " " << n << " " << p << "\n";

    // Generam matricea originala
    for(int i=0;i<N;i++){
        for(int j=0;j<M;j++)
            fout << (rand()%21+40) << " ";
        fout << "\n";
    }

    // Generam nucleul de convolutie
    for(int i=0;i<n;i++){
        for(int j=0;j<n;j++)
            fout << (rand()%2) << " ";
        fout << "\n";
    }
    cout << "Fisierul date.txt a fost generat!\n";
}

// Generam fisier doar daca nu exista sau daca dimensiunile difera
void generateFileIfNeeded(int N,int M,int n,int p){
    ifstream fin("date.txt");
    if(fin.is_open()){
        int oldN, oldM, oldn, oldp;
        fin >> oldN >> oldM >> oldn >> oldp;
        if(oldN==N && oldM==M && oldn==n)
            return;
    }
    generateFile(N,M,n,p);
}

void allocateMatrices() {
    matrix = new int*[N];
    for (int i = 0; i < N; i++)
        matrix[i] = new int[M];
}


void freeMatrices() {
    for (int i = 0; i < N; i++)
        delete[] matrix[i];
    delete[] matrix;
}


// Scriem rezultatele intr-un fisier
void writeToFile(int** res, const string& name, int rows, int cols){
    ofstream fout("output_" + name + ".txt");
    for(int i=0;i<rows;i++){
        for(int j=0;j<cols;j++)
            fout << res[i][j] << " ";
        fout << "\n";
    }
}

// Comparam doua fisiere (pentru verificarea corectitudinii)
bool compareFiles(const string& f1, const string& f2) {
    ifstream s1(f1);
    ifstream s2(f2);

    if (!s1.is_open() || !s2.is_open())
        return false;

    int a, b;
    while (s1 >> a && s2 >> b) {
        if (a != b) return false;
    }

    return !(s1 >> a) && !(s2 >> b);
}

void testAll() {
    long long seqTimes[10]{}, horTimes[10]{};

    for (int run = 0; run < 10; run++) {
        // copiem matricea pentru secvential
        int** seqMat = new int*[N];
        for (int i = 0; i < N; i++) {
            seqMat[i] = new int[M];
            for (int j = 0; j < M; j++)
                seqMat[i][j] = matrix[i][j];
        }

        // rulare secventiala
        auto start = chrono::high_resolution_clock::now();
        Sequential seq(N, M, n, seqMat, conv);
        seq.run();
        auto end = chrono::high_resolution_clock::now();
        seqTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();

        // rulare orizontala 
        int** horMat = new int*[N];
        for (int i = 0; i < N; i++) {
            horMat[i] = new int[M];
            for (int j = 0; j < M; j++)
                horMat[i][j] = matrix[i][j];
        }

        start = chrono::high_resolution_clock::now();
        HorizontalAllocatedThreads hor(N, M, n, p, horMat, conv);
        hor.run();
        end = chrono::high_resolution_clock::now();
        horTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end - start).count();

        // verificare corectitudine
        bool ok = true;
        for (int i = 0; i < N && ok; i++)
            for (int j = 0; j < M; j++)
                if (seqMat[i][j] != horMat[i][j])
                    ok = false;

        //cout << "Rulare #" << run + 1 << " -> Verificare: " << (ok ? "CORECTA" : "GRESITA") << endl;

        for (int i = 0; i < N; i++) {
            delete[] seqMat[i];
            delete[] horMat[i];
        }
        delete[] seqMat;
        delete[] horMat;
    }

    auto avg = [](long long arr[]) {
        long long s = 0; for (int i = 0; i < 10; i++) s += arr[i];
        return s / 10;
    };

    cout << "\nN=" << N << " M=" << M << " n=" << n << endl;
    cout << "Secvential: " << avg(seqTimes) << " ns" << endl;
    cout << p << " - Orizontal: " << avg(horTimes) << " ns" << endl;

    logFile << "\nN=" << N << " M=" << M << " n=" << n << endl;
    logFile << "Secvential: " << avg(seqTimes) << " ns" << endl;
    logFile << p << " - Orizontal: " << avg(horTimes) << " ns" << endl;
}


int main(){
    logFile.open("rezultate.txt", ios::app);
    if(!logFile.is_open()){
        cerr << "Eroare la deschiderea fisierului rezultate.txt\n";
        return 1;
    }

    // Generam fisier cu date daca este nevoie
    generateFileIfNeeded(10000,10000,3,2);
    ifstream fin("date.txt");
    readValues(fin);
    allocateMatrices();
    readOriginalMatrix(fin);
    readConvolutionMatrix(fin);
    fin.close();


    // Testam diferite valori pentru numarul de thread-uri
    int threadCounts[] = {2,4,8,16};
    for(int t:threadCounts){
        p=t;
        testAll();
    }

    // Eliberam memoria
    freeMatrices();
    logFile.close();
    return 0;
}
