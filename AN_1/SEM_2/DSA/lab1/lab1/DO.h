#ifndef DO_H_
#define DO_H_

typedef int TCheie;
typedef int TValoare;

#define NULL_TVALOARE -1

#include <utility>
#include <vector>
typedef std::pair<TCheie, TValoare> TElem;

class Iterator;

typedef bool(*Relatie)(TCheie, TCheie);

class DO {
	friend class Iterator;
private:
	int cp;
	int n;
	TElem* el;
	Relatie rel;
	void redim();

public:

	// constructorul implicit al dictionarului
	DO(Relatie r);

	// adauga o pereche (cheie, valoare) in dictionar
	//daca exista deja cheia in dictionar, inlocuieste valoarea asociata cheii si returneaza vechea valoare
	// daca nu exista cheia, adauga perechea si returneaza null: NULL_TVALOARE
	TValoare adauga(TCheie c, TValoare v);

	//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null: NULL_TVALOARE
	TValoare cauta(TCheie c) const;


	//sterge o cheie si returneaza valoarea asociata (daca exista) sau null: NULL_TVALOARE
	TValoare sterge(TCheie c);

	//returneaza numarul de perechi (cheie, valoare) din dictionar
	int dim() const;

	//verifica daca dictionarul e vid
	bool vid() const;

	// se returneaza iterator pe dictionar
	// iteratorul va returna perechile in ordine dupa relatia de ordine (pe cheie)
	Iterator iterator() const;

	// înlocuiește valoarea mapate în prezent cheii k cu valoarea valoareNoua, dar numai în cazul în care valoarea curentă este egală cu valoareVeche. 
	// dacă valoarea curentă nu este valoareVeche sau în cazul în care k nu este în cheie a dicționarului, dicționarul nu este modificat 
	void inlocuieste(TCheie k, TValoare valoareVeche, TValoare valoareNoua);

	// destructorul dictionarului
	~DO();

};

#endif //DICTORDO_DO_H