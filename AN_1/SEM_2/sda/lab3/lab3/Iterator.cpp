#include "Iterator.h"
#include <stdexcept>

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
Iterator::Iterator(const DO& d) : dict(d) {
    this->curent = dict.head;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void Iterator::prim() {
    curent = dict.head;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void Iterator::urmator() {
    if (!valid()) {
        throw out_of_range("Iteratorul nu este valid.");
    }
    curent = dict.elemente[curent].next;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool Iterator::valid() const {
    return curent != -1;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
TElem Iterator::element() const {
    if (!valid()) {
        throw out_of_range("Iteratorul nu este valid.");
    }
    return make_pair(dict.elemente[curent].cheie, dict.elemente[curent].valoare);
}
