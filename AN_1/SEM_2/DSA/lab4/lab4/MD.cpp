#include "MD.h"
#include "IteratorMD.h"
#include <stdexcept>
#include <algorithm>

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
MD::MD() : m(10), n(0) {
    tabela_dispersie = new Nod * [m];
    for (int i = 0; i < m; i++) {
        tabela_dispersie[i] = nullptr;
    }
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int MD::dispersie(TCheie c) const {
    return abs(c) % m;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void MD::adauga(TCheie c, TValoare v) {
    int index = dispersie(c);
    Nod* newNode = new Nod(c, v);
    // Adăugarea noului nod la începutul listei înlănțuite de la poziția index
    newNode->next = tabela_dispersie[index];
    tabela_dispersie[index] = newNode;
    n++;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
vector<TValoare> MD::cauta(TCheie c) const {
    vector<TValoare> valori;
    int index = dispersie(c);
    Nod* node = tabela_dispersie[index];
    // Parcurgerea listei de la poziția index pentru a găsi toate valorile asociate cheii
    while (node != nullptr) {
        if (node->cheie == c) {
            valori.push_back(node->valoare);
        }
        node = node->next;
    }
    return valori;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
bool MD::sterge(TCheie c, TValoare v) {
    int index = dispersie(c);
    Nod* node = tabela_dispersie[index];
    Nod* prev = nullptr;
    // Parcurgerea listei de la poziția index pentru a găsi și șterge perechea cheie-valoare
    while (node != nullptr) {
        if (node->cheie == c && node->valoare == v) {
            // Dacă nodul de șters este primul din listă
            if (prev == nullptr) {
                tabela_dispersie[index] = node->next;
            }
            // Legăm nodul anterior de nodul următor
            else {
                prev->next = node->next;
            }
            delete node;
            n--;
            return true;
        }
        // Avansăm la nodul următor
        prev = node;
        node = node->next;
    }
    return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int MD::dim() const {
    return n;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool MD::vid() const {
    return n == 0;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
IteratorMD MD::iterator() const {
    return IteratorMD(*this);
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
MD::~MD() {
    for (int i = 0; i < m; i++) {
        Nod* node = tabela_dispersie[i];
        while (node != nullptr) {
            Nod* toDelete = node;
            node = node->next;
            delete toDelete;
        }
    }
    delete[] tabela_dispersie;
}

// Best Case: O(n*m), Average Case: O(n*m), Worst Case: O(n*m)
// Complexitate Timp: O(n*m), Complexitate Spatiu: O(n*m)
vector<TCheie> MD::multimeaCheilor() const {
    vector<TCheie> cheiVector;
    for (int i = 0; i < m; i++) {
        // Parcurgem tabela de dispersie
        Nod* node = tabela_dispersie[i];
        while (node != nullptr) {
            // Verificăm dacă cheia există deja în vectorul de chei
            if (find(cheiVector.begin(), cheiVector.end(), node->cheie) == cheiVector.end()) {
                cheiVector.push_back(node->cheie);
            }
            node = node->next;
        }
    }
    return cheiVector;
}

//subalgoritm multimeaCheilor(d)
//    cheiVector < -vector gol
//    pentru fiecare element i din tabela_dispersie atunci
//          nod < -tabela_dispersie[i]
//          cât timp nod != nullptr executa
//                 dacă cheia node->cheie nu există în cheiVector
//                       adaugă node->cheie la cheiVector
//                  sfârșit dacă
//                  nod < -nod->next
//           sfârșit cât timp
//    sfârșit pentru
//    returnează cheiVector
//sfârșit subalgoritm