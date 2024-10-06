#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;


// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool relatia(TElem c1, TElem c2) {
	
	if (c1 <= c2) {
		return true;
	}
	else {
		return false;
	}
}

Colectie::Colectie() {
	rel = relatia;
	nr = 0;
	prim = -1;
	stanga = new int[cap];
	dreapta = new int[cap];
	e = new StangaDreapta[cap];
	for (int i = 0; i < cap - 1; i++)
		stanga[i] = i + 1;
	stanga[cap - 1] = -1;
	primLiber = 0;

}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
void Colectie::adauga2(TElem elem, int primul, int i)
{
	// Dacã elementul este mai mare sau egal cu elementul din nodul curent ?i dreapta este liberã
	if (rel(e[primul].element, elem) && dreapta[primul] == -1) {
		dreapta[primul] = i; return;
	}
	// Dacã elementul este mai mic decât elementul din nodul curent ?i stanga este liberã
	if (!rel(e[primul].element, elem) && stanga[primul] == -1) {
		stanga[primul] = i; return;
	}
	// Dacã elementul este mai mare sau egal cu elementul din nodul curent ?i dreapta nu este liberã
	if (rel(e[primul].element, elem) && dreapta[primul] != -1) {
		adauga2(elem, dreapta[primul], i);
	}
	// Dacã elementul este mai mic decât elementul din nodul curent ?i stanga nu este liberã
	if (!rel(e[primul].element, elem) && stanga[primul] != -1) {
		adauga2(elem, stanga[primul], i);
	}
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void Colectie::adauga(TElem e)
{

	int i = prim, j = prim;
	i = aloca();
	// Dacã s-a reu?it alocarea
	if (i != -1) {
		this->e[i].element = e;
		stanga[i] = -1;
		dreapta[i] = -1;
	}
	// Dacã colec?ia este goalã
	if (prim == -1) {
		prim = 0;
		j = prim;
	}
	// Dacã s-a realizat o adãugare efectivã
	if (i != j) {
		// Aplicã func?ia recursivã pentru a insera elementul în colec?ie
		adauga2(e, j, i);
	}
	nr++;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
bool Colectie::sterge2(int i, TElem elem) {
	
	TElem minim;
	// Dacã nodul curent este inexistent, returneazã false
	if (i == -1) {
		return false;
	}
	// Dacã elementul este mai mic decât elementul din nodul curent ?i acesta nu este nodul cãutat
	if (rel(elem, e[i].element) && e[i].element != elem) {
		return sterge2(stanga[i], elem);
	}
	// Dacã elementul este mai mare decât elementul din nodul curent ?i acesta nu este nodul cãutat
	else {
		if (rel(e[i].element, elem) && e[i].element != elem) {
			return sterge2(dreapta[i], elem);
		}
		// Dacã nodul curent con?ine elementul cãutat
		else {
			if (e[i].element == elem) {
				// Dacã nodul curent are ambii fii
				if (stanga[i] != -1 && dreapta[i] != -1) {
					// Înlocuie?te elementul din nodul curent cu minimul gãsit
					minim = mini(dreapta[i]);
					e[i].element = minim;
					// ?terge elementul minim din ramura dreaptã
					return sterge2(dreapta[i], e[i].element);
				}
				// Dacã nodul curent are un singur fiu sau este frunza, eliminã - l direct
				else {
					minim = i;
					dealoca(minim);
				}
				return true;
			}
			// Nodul cãutat nu a fost gãsit în subarborii nodului curent
			else {
				return false;
			}
		}
		return true;
	}
	return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool Colectie::sterge(TElem e)
{
	// Dacã colec?ia este goalã, nu se poate ?terge nimic.
	if (nr == 0) {
		return false;
	}
	// Încearcã sã ?teargã elementul dat.
	if (sterge2(prim, e)) {
		nr--;
		return true;
	}
	return false;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
bool Colectie::cauta2(int i, TElem elem)const {
	// Verificã dacã elementul din nodul curent este cel cãutat
	if (e[i].element == elem) {
		return true;
	}
	// Cautã în continuare pe ramura dreaptã dacã rela?ia de ordine este îndeplinitã ?i existã un fiu drept
	if (rel(e[i].element, elem) && dreapta[i] != -1) {
		return cauta2(dreapta[i], elem);
	}
	// Cautã în continuare pe ramura stângã dacã rela?ia de ordine nu este îndeplinitã ?i existã un fiu stâng
	if (!rel(e[i].element, elem) && stanga[i] != -1) {
		return cauta2(stanga[i], elem);
	}
	// Elementul nu a fost gãsit în subarborele nodului curent
	return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool Colectie::cauta(TElem elem) const {
	// Dacã colec?ia este goalã, returneazã fals
	if (nr == 0) {
		return false;
	}
	// Cautã elementul începând de la primul nod
	return cauta2(prim, elem);
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
int Colectie::apar(int i, TElem elem)const {
	
	int nr = 0;
	// Dacã nodul curent nu existã, returneazã 0
	if (i == -1) {
		return 0;
	}
	// Dacã elementul din nodul curent este cel cãutat, incrementeazã numãrul de apari?ii
	if (e[i].element == elem) {
		nr++;
	}
	// Numãrã apari?iile în continuare
	nr += apar(stanga[i], elem);
	nr += apar(dreapta[i], elem);
	return nr;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int Colectie::nrAparitii(TElem elem) const {
	// Dacã colec?ia este goalã, returneazã 0
	if (nr == 0) {
		return 0;
	}
	int nr = 0;
	// Calculeazã numãrul de apari?ii începând de la primul nod
	nr += apar(prim, elem);
	return nr;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
TElem Colectie::mini(int i) {
	// Dacã nodul curent este frunza, returneazã elementul sãu
	if (stanga[i] == -1) {
		return e[i].element;
	}
	// Continuã cãutarea minimului în subarborele stâng
	return mini(stanga[i]);
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
int Colectie::dim2(int i) const {
	// Dacã nodul curent nu existã, returneazã 0
	if (i == -1) {
		return 0;
	}
	// Dimensiunea subarborelui este 1 (nodul curent) plus dimensiunile subarborilor sãi
	int d = 1;
	if (stanga[i] != -1) {
		d += dim2(stanga[i]);
	}
	if (dreapta[i] != -1) {
		d += dim2(dreapta[i]);
	}
	return d;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int Colectie::dim() const {
	// Returneazã numãrul de elemente din colec?ie.
	return nr;
	int i = prim, dim = 1;
	if (stanga[i] != -1) {
		dim += dim2(stanga[i]);
	}
	if (dreapta[i] != -1) {
		dim += dim2(dreapta[i]);
	}
	return dim;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool Colectie::vida() const {
	// Verificã dacã colec?ia nu con?ine niciun element.
	if (nr == 0) {
		return true;
	}
	return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
IteratorColectie Colectie::iterator() const {
	// Returneazã un iterator pentru colec?ia curentã.
	return  IteratorColectie(*this);
}

Colectie::~Colectie() {
	
	delete[]e;
	delete[]stanga;
	delete[]dreapta;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int Colectie::aloca() {
	// Returneazã primul nod liber din colec?ie.
	int i = primLiber;
	primLiber = stanga[primLiber];
	return i;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void Colectie::dealoca(int i) {
	// Updateazã legãturile pentru a elimina nodul dat din colec?ie.
	for (int k = 0; k < cap; k++) {
		if (stanga[k] == i) {
			int m = max(stanga[i], dreapta[i]);
			stanga[k] = m;
		}
	}
	for (int k = 0; k < cap; k++) {
		if (dreapta[k] == i) {
			int m = max(stanga[i], dreapta[i]);
			dreapta[k] = m;
		}
	}
	// Dacã nodul eliminat este primul, actualizeazã indicele primului ?i primLiber.
	if (i == prim) {
		prim = dreapta[i];
		primLiber = i;
		stanga[primLiber] = nr;
	}
	// Altfel, actualizeazã primLiber ?i legãtura stânga pentru nodul eliminat.
	else {
		int ant = primLiber;
		primLiber = i;
		stanga[i] = ant;
	}
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
int Colectie::diferenta() const 
{
	// Verificãm dacã colec?ia este vidã
	if (nr == 0) {
		return -1;
	}
	// Ini?ializãm valoarea minimã ?i maximã cu primul element din colec?ie
	int minim = e[prim].element;
	int maxim = e[prim].element;
	// Parcurgem colec?ia pentru a gãsi minimul ?i maximul
	for (int i = prim; i != -1; i = dreapta[i]) {
		if (e[i].element < minim) {
			minim = e[i].element;
		}
		if (e[i].element > maxim) {
			maxim = e[i].element;
		}
	}
	// Returnãm diferen?a dintre maxim ?i minim
	return maxim - minim;
}

//subalgoritm diferenta(colectie)
//		dacã colec?ia este vidã
//			returneazã - 1
//		altfel
//			minim < -primul element din colec?ie
//			maxim < -primul element din colec?ie
//			pentru fiecare element din colec?ie începând de la al doilea
//				dacã elementul este mai mic decât minimul
//					minim < -elementul
//				altfel dacã elementul este mai mare decât maximul
//					maxim < -elementul
//				sfâr?it dacã
//			sfâr?it pentru
//		returneazã maxim - minim
//sfâr?it subalgoritm