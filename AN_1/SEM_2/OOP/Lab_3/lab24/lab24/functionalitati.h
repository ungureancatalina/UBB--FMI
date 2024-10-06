#pragma once
#include <stdio.h>
#include "Lista.h"

void adauga(MyList* lista_participanti, char* nume, char* prenume, int scor[], int nr_scor);
/*
Adauga un nou participant la lista initiala
Parametrii: lista_participanti-lista de participanti existenta,
nume-numele participantului ce va fi adaugat, prenume-prenumele participantului ce va fi adaugat,
scor-lista de scoruri ale participantului ce va fi adaugat, nr_scor-nr de scoruri ale participantului ce va fi adaugat
*/

int cauta(MyList* lista_participanti, char* cheie_nume);
/*
Cauta un participant in lista initiala
Parametrii: lista_participanti-lista de participanti existentaa,
cheie_nume-numele participantului ce va fi cautat
Returneaza pozitia din lista unde va fi gasit participantul, sau -1 daca nu exista
*/

p actualizeaza(MyList* lista_participanti, char* cheie_nume, char* nume, char* prenume, int scor[], int nr_scor);
/*
Modifica detaliile unui participant din lista initiala
Parametrii: lista_participanti-lista de participanti existenta,
cheie_nume-numele participantului ce va fi cautat pentru a fi modificat
nume-numele participantului nou, prenume-prenumele participantului nou,
scor-lista de scoruri ale participantului noua, nr_scor-nr de scoruri ale participantului nou
Returneaza 1 daca exista participantul ce va fi actualizat sau 0 daca nu exista
*/

int sterge(MyList* lista_participanti, char* cheie_nume);
/*
Sterge un participant din lista initiala
Parametrii: lista_participanti-lista de participanti existenta,
cheie_nume-numele participantului ce va fi cautat pentru a fi sters
Returneaza 1 daca exista participantul ce va fi sters sau 0 daca nu exista
*/

float medie_scor(MyList* lista_participanti, int pozitie);
/*
Calculeaza media aritmetica a scorurilor unui participant
Parametrii: lista_participanti-lista de participanti existenta,
pozitie-pozitia participantului ce va fi cautat pentru a-i fi calculata media
Returneaza media aritmetica
*/

typedef int(*functie_compara)(p* p1, p* p2);

int compara_nume(p* p1, p* p2);
/*
Compara numele participantilor
Parametrii: p1- participantul 1, p2- participantul 2
Returneaza 0 daca numele primului participant e mai mare decat al celui de al doilea, iar 1 altfel
*/

void sort(MyList* lista_participanti, functie_compara cmp);
/*
Sorteaza participantii din lista initiala dupa nume crescator
Parametrii: lista_participanti-lista de participanti existenta, cmp- functia de comparare
*/

MyList sortare_nume(MyList* lista_participanti);
/*
Sorteaza participantii din lista initiala dupa nume crescator
Parametrii: lista_participanti-lista de participanti existenta
Returneaza o noua lista sortata
*/

MyList sortare_scor(MyList* lista_participanti);
/*
Sorteaza participantii din lista initiala dupa media scorurilor crescator
Parametrii: lista_participanti-lista de participanti existenta
Returneaza o noua lista sortata
*/

MyList filtrare_scor(MyList* lista_participanti);
/*
Verifica daca media scorurilor unui participant este mai mare ca 5
Parametrii: lista_participanti-lista de participanti existenta
Returneaza o noua lista ce contine doar paeticipantii filtrati
*/