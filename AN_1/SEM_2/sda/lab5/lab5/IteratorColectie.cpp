#include "IteratorColectie.h"
#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	curent = c.prim;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
TElem IteratorColectie::element() {
	// Parcurge colec?ia �n ordine st�nga-dreapta ?i adaug� nodurile �ntr-o stiv�.
	while (curent != -1) {
		s.push(curent);
		curent = col.stanga[curent];
	}
	// Extrage ultimul nod din stiv� (�n ordine invers� fa?� de parcurgerea st�nga-dreapta).
	curent = s.top();
	s.pop();
	return col.e[curent].element;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool IteratorColectie::valid() const {

	return curent != -1 || !s.empty();
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void IteratorColectie::urmator() {

	curent = col.dreapta[curent];
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void IteratorColectie::prim() {

	curent = col.prim;
}