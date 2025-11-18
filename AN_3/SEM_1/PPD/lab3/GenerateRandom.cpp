#include <iostream>
#include <fstream>
#include <vector>
#include <cstdlib>
#include <ctime>

using namespace std;

// Genereaza un numar cu n cifre in fisierul dat
// Prima cifra este nenula (1..9), restul 0..9
void generateNumber(const string& filename, int n) {
    ofstream fout(filename);
    if (!fout.is_open()) {
        cerr << "Nu pot deschide fisierul: " << filename << endl;
        return;
    }

    if (n <= 0) {
        cerr << "Numarul de cifre trebuie sa fie pozitiv.\n";
        fout << 1 << "\n0\n";
        return;
    }

    vector<int> digits(n);
    digits[0] = rand() % 9 + 1; // prima cifra nenula
    for (int i = 1; i < n; ++i) {
        digits[i] = rand() % 10;
    }

    fout << n << "\n";
    for (int i = 0; i < n; ++i) {
        fout << digits[i] << " ";
    }
    fout << "\n";
    fout.close();
}

int runGenerateRandom() {
    srand((unsigned int)time(nullptr));

    int n1, n2;
    cout << "Introdu N1 (numar de cifre pentru Numar1): ";
    cin >> n1;
    cout << "Introdu N2 (numar de cifre pentru Numar2): ";
    cin >> n2;

    generateNumber("Numar1.txt", n1);
    generateNumber("Numar2.txt", n2);

    cout << "Au fost generate fisierle Numar1.txt si Numar2.txt.\n";
    return 0;
}
