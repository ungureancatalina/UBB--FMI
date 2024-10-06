#pragma once
#include "participant.h"

typedef p ElemType;

typedef struct
{
	ElemType* participant;
	int nr_participanti;
	int maxim;
}MyList;

MyList createEmpty();
/*
Creeaza o lista vida noua ce contine participantii,nr de participanti si nr maxim de participanti existenti
*/

void destroy(MyList* l);
/*
Distruge lista si elibereaza memoria alocata
Parametrii: l-lista de participanti existenta
*/

int size(MyList* l);
/*
Afiseaza lungimea listei
Parametrii: l-lista de participanti existenta
Returneaza lungimea listei de participanti
*/

void add(MyList* l, ElemType elem);
/*
Adauga la lista de participanti un nou participant
Parametrii: l-lista de participanti existenta,elem- participantul ce va fi adaugat
*/

ElemType delete(MyList* l, int pozitie);
/*
Sterge din lista de participanti un participant
Parametrii: l-lista de participanti existenta,elem- participantul ce va fi sters
Returneaza participantul sters
*/


ElemType get(MyList* l, int pozitie);
/*
Alege din lista de participanti participantul de la pozitia data
Parametrii: l-lista de participanti existenta,elem- participantul ce va fi adaugat
Returneaza participantul gasit
*/


//MyList copyList(MyList* l);
