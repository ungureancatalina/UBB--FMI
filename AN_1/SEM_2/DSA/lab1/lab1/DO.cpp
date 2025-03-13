#include "Iterator.h"
#include "DO.h"
#include <iostream>
#include <exception>
using namespace std;

/*
  Best Case = Average Case = Worst Case
  Complexitate timp: O(1)
  Complexitate spatiu: O(1)
 */
DO::DO(Relatie rel)
{
	this->cp = 10;
	this->el = new TElem[10];
	this->n = 0;
	this ->rel = rel;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(n)
  Complexitate Spatiu: O(n)
 */
void DO::redim() 
{
	TElem* el_nou = new TElem[this->cp * 2];
	for (int i = 0; i < n; i++) 
	{
		el_nou[i] = el[i];
	}
	cp *= 2;
	delete[] el;
	el = el_nou;
}

/*
  Best Case: O(1) - daca elementul exista deja
  Average Case: O(log(n)) - daca trebuie adaugat
  Worst Case: O(n) - daca nu exista
  Complexitate Timp: O(n)
  Complexitate Spatiu: O(1)
 */
TValoare DO::adauga(TCheie c, TValoare v) 
{
	// verificam daca exista deja valoarea asociata cheii si returneaza valoarea veche
	for (int i = 0; i < n; i++)
	{
		if (el[i].first == c)
		{
			TValoare vechi = el[i].second;
			el[i].second = v;
			return vechi; 
		}
	}
	//redimensionarea
	if (n == cp)
	{
		redim();
	}
	// cautam pozitia pentru cheia noua ce trebuie adaugata si mutam elementele cu o pozitie la dreapta de la pozitie
	int pozitie = 0;
	while (pozitie < n&& rel(el[pozitie].first, c))
	{
		pozitie++;
	}
	for (int i = n; i > pozitie; i--)
	{
		el[i] = el[i - 1];
	}
	// adaugam perechea noua
	el[pozitie] = make_pair(c, v);
	n++;
	return NULL_TVALOARE;
}

/*
  Best Case: O(1) - daca elementul ce trebuie sters se afla la mijloc
  Average Case: O(log(n)) - daca elementul se afla in vector intre inceput si sfarsit
  Worst Case: O(log(n)) - daca nu exista sau se afla la inceput sau la sfarsit
  Complexitate Timp: O(log(n))
  Complexitate Spatiu: O(1)
 */
TValoare DO::cauta(TCheie c) const 
{
	//daca dictionarul e vid
	if (dim()==0)
		return NULL_TVALOARE;
	//cautarea cheii prin cautare binara
	int start = 0, final = dim(), mij;
	while (start != final) 
	{
		mij = (start + final) / 2;
		if (rel(el[mij].first, c) && rel(c, el[mij].first)) 
		{
			return el[mij].second;
		}
		if (rel(c, el[mij].first)) 
		{
			final = mij - 1;
		}
		else 
		{
			start = mij + 1;
		}
		//daca a depasit limita dictionarului
		if (final < 0 || start > dim() - 1)
			return NULL_TVALOARE;
	}
	//verifica cheia de la pozitia start
	if (rel(el[start].first, c) && rel(c, el[start].first))
		return el[start].second;
	//daca nu a gasit cheia
	return NULL_TVALOARE;

}

/*
  Best Case: O(1) - daca elementul ce trebuie sters se afla la mijloc
  Average Case: O(log(n)) - daca elementul  se afla in vector intre inceput si sfarsit
  Worst Case: O(log(n)) - daca nu exista sau se afla la inceput sau la sfarsit
  Complexitate Timp: O(log(n))
  Complexitate Spatiu: O(1)
 */
TValoare DO::sterge(TCheie c) 
{
	//daca dictionarul e vid
	if (dim()==0)
		return NULL_TVALOARE;
	//cautarea cheii prin cautare binara
	int start = 0, final = dim(), mij;
	while (start != final) {
		mij = (start + final) / 2;
		if (rel(el[mij].first, c) && rel(c, el[mij].first)) 
		{
			TValoare vechi = el[mij].second;
			for (int i = mij; i < dim() - 1; i++)
				el[i] = el[i + 1];
			n--;
			return vechi;
		}
		if (rel(c, el[mij].first)) 
		{
			final = mij - 1;
		}
		else {
			start = mij + 1;
		}
		if (final < 0 || start > dim() - 1)
			return NULL_TVALOARE;
	}
	//verifica cheia de la pozitia start
	if (rel(el[start].first, c) && rel(c, el[start].first)) 
	{
		TValoare vechi = el[start].second;
		for (int i = start; i < dim() - 1; i++)
			el[i] = el[i + 1];
		n--;
		return vechi;
	}
	//daca cheia nu e gasita
	return NULL_TVALOARE;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
int DO::dim() const 
{
	return n;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
bool DO::vid() const 
{
	if (dim()==0)
		return true;
	return false;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
Iterator DO::iterator() const 
{
	return Iterator(*this);
}

/*
  Best Case: O(1) - daca elementul ce trebuie inlocuit este primul
  Average Case: O(n)) - daca elementul ce trebuie inlocuit exista
  Worst Case: O(n) - daca nu exista sau este ultimul
  Complexitate Timp: O(n)
  Complexitate Spatiu: O(1)
 */

//PSEUDOCOD
//subalgoritm inlocuieste(d, k, valoareVeche, valoareNoua)
//	pentru i<-1,d.dim executa
//		daca d.el[i].cheie=k si d.el[i].valoare= valoareVeche
//			d.el[i].valoare = valoareNoua
//			returneaza
//		sf daca
//	sf pentru
//sf functie

void DO::inlocuieste(TCheie k, TValoare valoareVeche, TValoare valoareNoua) 
{
	for (int i = 0; i < dim(); i++) 
	{
		if (el[i].first == k && el[i].second == valoareVeche) 
		{
			el[i].second = valoareNoua;
			return;
		}
	}
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
DO::~DO() 
{
	if (el != nullptr) 
	{
		delete[] el;
	}
}

