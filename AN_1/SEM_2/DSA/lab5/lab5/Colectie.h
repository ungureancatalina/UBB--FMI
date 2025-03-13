
#pragma once

typedef int TElem;
#define NULL_TELEM 0
#define CAPACITATE 60100
typedef bool(*Relatie)(TElem, TElem);
#include <vector>
using std::vector;

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool relatia(TElem, TElem);

class IteratorColectie;


struct StangaDreapta {
	TElem element;
};


class Colectie {

	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	int cap = CAPACITATE;

	Relatie rel;
	int nr;

	StangaDreapta* e;
	//vectorul static pentru egaturi
	int* stanga, * dreapta;
	//referinta catre primul element al listei
	int prim;
	//referinta catre primul element din lista spatiului liber
	int primLiber;

	//functii pentru alocarea/dealocarea unui spatiu liber
	//se returneaza pozitia unui spatiu liber in lista
	int aloca();
	//dealoca spatiul de indice i
	void dealoca(int i);



public:
	//constructorul implicit
	//void redim();
	Colectie();

	//adauga un element in colectie
	void adauga(TElem e);

	// Func?ie recursivã pentru adãugarea unui element în colec?ie
	void adauga2(TElem elem, int prim, int i);

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	// Func?ie recursivã pentru ?tergerea unui element din colec?ie
	bool sterge2(int i, TElem elem);

	//verifica daca un element se afla in colectie
	bool cauta(TElem elem) const;

	// Func?ie recursivã pentru cãutarea unui element în colec?ie
	bool cauta2(int i, TElem elem)const;

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem) const;

	//intoarce numarul de elemente din colectie;
	int dim() const;

	// Func?ie recursivã pentru determinarea dimensiunii unui subarbore
	int dim2(int i) const;

	// Func?ie recursivã pentru numãrarea apari?iilor unui element în colec?ie
	int apar(int i, TElem elem)const;

	// Func?ie pentru gãsirea minimului într-un subarbore
	TElem mini(int i);

	//verifica daca colectia e vida;
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();

	int diferenta() const;

};
