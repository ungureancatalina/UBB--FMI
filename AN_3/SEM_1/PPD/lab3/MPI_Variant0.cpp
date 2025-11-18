#include <iostream>
#include <vector>
#include <fstream>
#include <algorithm>
#include <chrono>
#include "Logger.h"

using namespace std;

// Citeste un numar mare din fisier.
vector<unsigned char> readNumber(const string& filename) {
    ifstream file(filename);

    int numDigits;
    file >> numDigits; // citim cate cifre are numarul

    vector<unsigned char> number(numDigits);

    // Citim cifrele din fisier in ordine inversa
    // deoarece pentru calcul cifra 0 trebuie sa fie cea mai putin semnificativa
    for (int i = numDigits - 1; i >= 0; --i) {
        int digit;
        file >> digit;
        number[i] = digit;
    }

    return number;
}

// Aduna doua numere mari reprezentate ca vectori de cifre.
vector<unsigned char> addLargeNumbers(const vector<unsigned char>& num1,
                                      const vector<unsigned char>& num2)
{
    int maxSize = max(num1.size(), num2.size());

    // +1 pentru cazul in care apare carry final
    vector<unsigned char> result(maxSize + 1, 0);
    unsigned char carry = 0;

    // Parcurgem cifrele de la cea mai putin semnificativa la cea mai semnificativa
    for (int i = 0; i < maxSize; ++i) {

        unsigned char a = (i < num1.size()) ? num1[i] : 0;
        unsigned char b = (i < num2.size()) ? num2[i] : 0;

        unsigned char sum = a + b + carry;

        result[i] = sum % 10;   // cifra curenta
        carry = sum / 10;       // carry pentru pozitia urmatoare
    }

    // Daca dupa ultima adunare mai exista carry, il punem in vector
    if (carry) result[maxSize] = carry;

    // Eliminam zerourile nesemnificative din varf (doar daca avem mai multe cifre)
    while (result.size() > 1 && result.back() == 0)
        result.pop_back();

    return result;
}

// Scrie numarul rezultat in fisier in format:
void writeNumberToFile(const vector<unsigned char>& number, const string& filename) {
    ofstream file(filename);

    // scriem cate cifre are numarul
    file << number.size() << "\n";

    // scriem cifrele de la cea mai semnificativa la cea mai putin semnificativa
    for (int i = number.size() - 1; i >= 0; --i)
        file << (int)number[i] << " ";

    file << endl;
}

// Rularea secventiala cu 10 repetari pentru a obtine timpul mediu de executie.
void runSequential() {
    int runs = 10;

    vector<unsigned char> lastSum; // salvam rezultatul ultimei rulari

    long long avgNs = averageExecutionTime(runs, [&]() {

        auto num1 = readNumber("Numar1.txt");
        auto num2 = readNumber("Numar2.txt");
        auto sum = addLargeNumbers(num1, num2);

        // salvam rezultatul ultimei rulari pentru a-l scrie in fisier
        lastSum = sum;
    });

    cout << "Timp mediu: " << avgNs << " ns" << endl;

    // scriem in fisier rezultatul final
    writeNumberToFile(lastSum, "Numar3_Secvential.txt");

    // citim numarul de cifre pentru log
    int N1 = 0, N2 = 0;
    ifstream f1("Numar1.txt"); f1 >> N1; f1.close();
    ifstream f2("Numar2.txt"); f2 >> N2; f2.close();

    // adaugam linie in Results.txt (1 proces pentru varianta secventiala)
    logResult("Varianta 0 ", N1, N2, 1, avgNs);
}
