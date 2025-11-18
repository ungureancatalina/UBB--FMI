#include <iostream>
#include <fstream>
#include <string>
#include <vector>

using namespace std;

// Funcție care compara conținutul a doua fișiere, token cu token
bool filesAreIdenticalTokens(const string& f1, const string& f2) {
    ifstream in1(f1), in2(f2);
    if (!in1.is_open() || !in2.is_open()) {
        cerr << "Nu pot deschide unul dintre fisiere: "
             << f1 << " sau " << f2 << endl;
        return false;
    }

    while (true) {
        int a, b;
        bool ok1 = (bool)(in1 >> a);
        bool ok2 = (bool)(in2 >> b);

        if (!ok1 && !ok2) {
            return true; // ambele s-au terminat simultan
        }
        if (ok1 != ok2) {
            return false; // unul s-a terminat mai devreme
        }
        if (a != b) {
            return false; // valorile difera
        }
    }
}

// Functie principala de comparare automata
void runCompareFiles() {
    struct Pair {
        string file1;
        string file2;
        string label;
    };

    // Toate perechile de comparat
    vector<Pair> pairs = {
            {"Numar3_Secvential.txt", "Numar3_MPI_V1.txt", "Secvential vs MPI_V1"},
            {"Numar3_Secvential.txt", "Numar3_MPI_V2.txt", "Secvential vs MPI_V2"},
            {"Numar3_Secvential.txt", "Numar3_MPI_V3.txt", "Secvential vs MPI_V3"}
    };


    for (const auto& p : pairs) {
        //cout << "\nCompar " << p.label << " ... ";

        bool identical = filesAreIdenticalTokens(p.file1, p.file2);
        if (identical)
            cout << "Fisierele sunt identice.\n";
        else
            cout << "Fisierele sunt diferite!\n";
    }

}
