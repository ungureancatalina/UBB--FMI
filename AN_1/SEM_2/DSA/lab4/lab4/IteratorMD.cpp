#include "IteratorMD.h"
#include "MD.h"
#include <stdexcept>

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
IteratorMD::IteratorMD(const MD& c) : md(c) {
    prim();
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
void IteratorMD::prim() {
    index_cheie = 0;
    nod_curent = nullptr;
    for (; index_cheie < md.m; index_cheie++) {
        // Găsește prima cheie din tabelă care are asociat cel puțin un nod în lista sa de dispersie
        if (md.tabela_dispersie[index_cheie] != nullptr) {
            nod_curent = md.tabela_dispersie[index_cheie];
            break;
        }
    }
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
void IteratorMD::urmator() {
    if (!valid()) {
        throw std::runtime_error("Iterator invalid");
    }
    nod_curent = nod_curent->next;
    // Dacă nodul curent a ajuns la finalul listei de dispersie, se caută următoarea cheie
    while (nod_curent == nullptr && index_cheie < md.m - 1) {
        index_cheie++;
        nod_curent = md.tabela_dispersie[index_cheie];
    }
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool IteratorMD::valid() const {
    return nod_curent != nullptr;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
TElem IteratorMD::element() const {
    if (!valid()) {
        throw std::runtime_error("Iterator invalid");
    }
    return { nod_curent->cheie, nod_curent->valoare };
}
