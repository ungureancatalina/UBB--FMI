#include <iostream>
#include <fstream>
#include <vector>
#include <chrono>
#include <cstdlib>
#include <ctime>
#include <fstream>
#include "Sequential.h"
#include "HorizontalAllocatedThreads.h"
#include "VerticalAllocatedThreads.h"
using namespace std;
ofstream logFile;

// Dimensiuni si parametri
int N, M, n, p;
int** matrix;      // Matricea originala
int** bordered;    // Matricea cu padding pentru convolutie
int** resultSeq;   // Rezultat secvential
int** resultHor;   // Rezultat orizontal (pe linii)
int** resultVer;   // Rezultat vertical (pe coloane)
int conv[6][6];    // Nucleul de convolutie

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

// Alocam memorie pentru matrici
void allocateMatrices() {
    matrix = new int*[N];
    bordered = new int*[N + n];
    resultSeq = new int*[N];
    resultHor = new int*[N];
    resultVer = new int*[N];
    for(int i=0;i<N;i++){
        matrix[i] = new int[M];
        resultSeq[i] = new int[M];
        resultHor[i] = new int[M];
        resultVer[i] = new int[M];
    }
    for(int i=0;i<N+n;i++)
        bordered[i] = new int[M+n];
}

// Eliberam memoria matricilor
void freeMatrices() {
    for(int i=0;i<N;i++){
        delete[] matrix[i];
        delete[] resultSeq[i];
        delete[] resultHor[i];
        delete[] resultVer[i];
    }
    for(int i=0;i<N+n;i++)
        delete[] bordered[i];
    delete[] matrix;
    delete[] bordered;
    delete[] resultSeq;
    delete[] resultHor;
    delete[] resultVer;
}

// Adaugam padding (border) in jurul matricii
void borderMatrix(){
    int border = n/2;

    // Copiem matricea originala in centrul matricii cu padding
    for(int i=0;i<N;i++)
        for(int j=0;j<M;j++)
            bordered[i+border][j+border] = matrix[i][j];

    // Copiem marginile sus si jos
    for(int i=0;i<border;i++){
        for(int j=0;j<M+2*border;j++){
            bordered[i][j] = bordered[border][j];
            bordered[N+border+i][j] = bordered[N+border-1][j];
        }
    }
    // Copiem marginile stanga si dreapta
    for(int i=0;i<N+2*border;i++){
        for(int j=0;j<border;j++){
            bordered[i][j] = bordered[i][border];
            bordered[i][M+border+j] = bordered[i][M+border-1];
        }
    }
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

// Testam toate implementările
void testAll(){
    long long seqTimes[10]{}, horTimes[10]{}, verTimes[10]{};
    for(int run=0;run<10;run++){
        //cout << "\nRulare #" << run + 1 << "\n";

        // Masuram timpul pentru secvential
        auto start = chrono::high_resolution_clock::now();
        Sequential seq(N,M,n,bordered,conv);
        seq.run(resultSeq);
        auto end = chrono::high_resolution_clock::now();
        seqTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end-start).count();
        //writeToFile(resultSeq, "sequential", N, M);

        // Masuram timpul pentru orizontal (pe linii)
        start = chrono::high_resolution_clock::now();
        HorizontalAllocatedThreads hor(N,M,n,p,bordered,conv);
        hor.run(resultHor);
        end = chrono::high_resolution_clock::now();
        horTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end-start).count();
        //writeToFile(resultHor, "horizontal", N, M);

        // Masuram timpul pentru vertical (pe coloane)
        start = chrono::high_resolution_clock::now();
        VerticalAllocatedThreads ver(N,M,n,p,bordered,conv);
        ver.run(resultVer);
        end = chrono::high_resolution_clock::now();
        verTimes[run] = chrono::duration_cast<chrono::nanoseconds>(end-start).count();
        //writeToFile(resultVer, "vertical", N, M);

        //cout << "Verificare orizontala: " << (compareFiles("output_sequential.txt", "output_horizontal.txt") ? "CORECTA" : "GRESITA") << "\n";
        //cout << "Verificare verticala:  " << (compareFiles("output_sequential.txt", "output_vertical.txt") ? "CORECTA" : "GRESITA") << "\n";
    }

    // Calculam media timpurilor
    auto avg = [](long long arr[]){long long s=0;for(int i=0;i<10;i++)s+=arr[i];return s/10;};
    cout << "\nN=" << N << " M=" << M << " n=" << n << "\n";
    cout << "Secvential: " << avg(seqTimes) << " ns\n";
    cout << p << " - vertical: " << avg(verTimes) << " ns\n";
    cout << p << " - orizontal: " << avg(horTimes) << " ns\n";

    logFile << "\nN=" << N << " M=" << M << " n=" << n << "\n";
    logFile << "Secvential: " << avg(seqTimes) << " ns\n";
    logFile << p << " - vertical: " << avg(verTimes) << " ns\n";
    logFile << p << " - orizontal: " << avg(horTimes) << " ns\n";
}

int main(){
    logFile.open("rezultate.txt", ios::app);
    if(!logFile.is_open()){
        cerr << "Eroare la deschiderea fisierului rezultate.txt\n";
        return 1;
    }

    // Generam fisier cu date daca este nevoie
    generateFileIfNeeded(10000,10000,5,2);
    ifstream fin("date.txt");
    readValues(fin);
    allocateMatrices();
    readOriginalMatrix(fin);
    readConvolutionMatrix(fin);
    fin.close();

    // Adaugam padding la matrice
    borderMatrix();

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
