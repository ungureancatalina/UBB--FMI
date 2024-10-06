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
	// Dac� elementul este mai mare sau egal cu elementul din nodul curent ?i dreapta este liber�
	if (rel(e[primul].element, elem) && dreapta[primul] == -1) {
		dreapta[primul] = i; return;
	}
	// Dac� elementul este mai mic dec�t elementul din nodul curent ?i stanga este liber�
	if (!rel(e[primul].element, elem) && stanga[primul] == -1) {
		stanga[primul] = i; return;
	}
	// Dac� elementul este mai mare sau egal cu elementul din nodul curent ?i dreapta nu este liber�
	if (rel(e[primul].element, elem) && dreapta[primul] != -1) {
		adauga2(elem, dreapta[primul], i);
	}
	// Dac� elementul este mai mic dec�t elementul din nodul curent ?i stanga nu este liber�
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
	// Dac� s-a reu?it alocarea
	if (i != -1) {
		this->e[i].element = e;
		stanga[i] = -1;
		dreapta[i] = -1;
	}
	// Dac� colec?ia este goal�
	if (prim == -1) {
		prim = 0;
		j = prim;
	}
	// Dac� s-a realizat o ad�ugare efectiv�
	if (i != j) {
		// Aplic� func?ia recursiv� pentru a insera elementul �n colec?ie
		adauga2(e, j, i);
	}
	nr++;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
bool Colectie::sterge2(int i, TElem elem) {
	
	TElem minim;
	// Dac� nodul curent este inexistent, returneaz� false
	if (i == -1) {
		return false;
	}
	// Dac� elementul este mai mic dec�t elementul din nodul curent ?i acesta nu este nodul c�utat
	if (rel(elem, e[i].element) && e[i].element != elem) {
		return sterge2(stanga[i], elem);
	}
	// Dac� elementul este mai mare dec�t elementul din nodul curent ?i acesta nu este nodul c�utat
	else {
		if (rel(e[i].element, elem) && e[i].element != elem) {
			return sterge2(dreapta[i], elem);
		}
		// Dac� nodul curent con?ine elementul c�utat
		else {
			if (e[i].element == elem) {
				// Dac� nodul curent are ambii fii
				if (stanga[i] != -1 && dreapta[i] != -1) {
					// �nlocuie?te elementul din nodul curent cu minimul g�sit
					minim = mini(dreapta[i]);
					e[i].element = minim;
					// ?terge elementul minim din ramura dreapt�
					return sterge2(dreapta[i], e[i].element);
				}
				// Dac� nodul curent are un singur fiu sau este frunza, elimin� - l direct
				else {
					minim = i;
					dealoca(minim);
				}
				return true;
			}
			// Nodul c�utat nu a fost g�sit �n subarborii nodului curent
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
	// Dac� colec?ia este goal�, nu se poate ?terge nimic.
	if (nr == 0) {
		return false;
	}
	// �ncearc� s� ?tearg� elementul dat.
	if (sterge2(prim, e)) {
		nr--;
		return true;
	}
	return false;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
bool Colectie::cauta2(int i, TElem elem)const {
	// Verific� dac� elementul din nodul curent este cel c�utat
	if (e[i].element == elem) {
		return true;
	}
	// Caut� �n continuare pe ramura dreapt� dac� rela?ia de ordine este �ndeplinit� ?i exist� un fiu drept
	if (rel(e[i].element, elem) && dreapta[i] != -1) {
		return cauta2(dreapta[i], elem);
	}
	// Caut� �n continuare pe ramura st�ng� dac� rela?ia de ordine nu este �ndeplinit� ?i exist� un fiu st�ng
	if (!rel(e[i].element, elem) && stanga[i] != -1) {
		return cauta2(stanga[i], elem);
	}
	// Elementul nu a fost g�sit �n subarborele nodului curent
	return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
bool Colectie::cauta(TElem elem) const {
	// Dac� colec?ia este goal�, returneaz� fals
	if (nr == 0) {
		return false;
	}
	// Caut� elementul �ncep�nd de la primul nod
	return cauta2(prim, elem);
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
int Colectie::apar(int i, TElem elem)const {
	
	int nr = 0;
	// Dac� nodul curent nu exist�, returneaz� 0
	if (i == -1) {
		return 0;
	}
	// Dac� elementul din nodul curent este cel c�utat, incrementeaz� num�rul de apari?ii
	if (e[i].element == elem) {
		nr++;
	}
	// Num�r� apari?iile �n continuare
	nr += apar(stanga[i], elem);
	nr += apar(dreapta[i], elem);
	return nr;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
int Colectie::nrAparitii(TElem elem) const {
	// Dac� colec?ia este goal�, returneaz� 0
	if (nr == 0) {
		return 0;
	}
	int nr = 0;
	// Calculeaz� num�rul de apari?ii �ncep�nd de la primul nod
	nr += apar(prim, elem);
	return nr;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
TElem Colectie::mini(int i) {
	// Dac� nodul curent este frunza, returneaz� elementul s�u
	if (stanga[i] == -1) {
		return e[i].element;
	}
	// Continu� c�utarea minimului �n subarborele st�ng
	return mini(stanga[i]);
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
int Colectie::dim2(int i) const {
	// Dac� nodul curent nu exist�, returneaz� 0
	if (i == -1) {
		return 0;
	}
	// Dimensiunea subarborelui este 1 (nodul curent) plus dimensiunile subarborilor s�i
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
	// Returneaz� num�rul de elemente din colec?ie.
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
	// Verific� dac� colec?ia nu con?ine niciun element.
	if (nr == 0) {
		return true;
	}
	return false;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
IteratorColectie Colectie::iterator() const {
	// Returneaz� un iterator pentru colec?ia curent�.
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
	// Returneaz� primul nod liber din colec?ie.
	int i = primLiber;
	primLiber = stanga[primLiber];
	return i;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)
void Colectie::dealoca(int i) {
	// Updateaz� leg�turile pentru a elimina nodul dat din colec?ie.
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
	// Dac� nodul eliminat este primul, actualizeaz� indicele primului ?i primLiber.
	if (i == prim) {
		prim = dreapta[i];
		primLiber = i;
		stanga[primLiber] = nr;
	}
	// Altfel, actualizeaz� primLiber ?i leg�tura st�nga pentru nodul eliminat.
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
	// Verific�m dac� colec?ia este vid�
	if (nr == 0) {
		return -1;
	}
	// Ini?ializ�m valoarea minim� ?i maxim� cu primul element din colec?ie
	int minim = e[prim].element;
	int maxim = e[prim].element;
	// Parcurgem colec?ia pentru a g�si minimul ?i maximul
	for (int i = prim; i != -1; i = dreapta[i]) {
		if (e[i].element < minim) {
			minim = e[i].element;
		}
		if (e[i].element > maxim) {
			maxim = e[i].element;
		}
	}
	// Return�m diferen?a dintre maxim ?i minim
	return maxim - minim;
}

//subalgoritm diferenta(colectie)
//		dac� colec?ia este vid�
//			returneaz� - 1
//		altfel
//			minim < -primul element din colec?ie
//			maxim < -primul element din colec?ie
//			pentru fiecare element din colec?ie �ncep�nd de la al doilea
//				dac� elementul este mai mic dec�t minimul
//					minim < -elementul
//				altfel dac� elementul este mai mare dec�t maximul
//					maxim < -elementul
//				sf�r?it dac�
//			sf�r?it pentru
//		returneaz� maxim - minim
//sf�r?it subalgoritm