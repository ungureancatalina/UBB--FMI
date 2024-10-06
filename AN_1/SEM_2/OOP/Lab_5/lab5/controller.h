#pragma once
#include "array.h"
#include "validator.h"
typedef struct {
    Vector* repo;
    Vector* undo;
}Controller;

typedef int (*CompareFct)(void* el1, void* el2);

Controller create_Controller();

void destroy_Controller(Controller* controller);

void test_transform_chars_to_integer();

/*
//Functie care primeste ca parametru un sir de caractere
//Functia returneaza numarul intreg reprezentat in scrierea normala a sirului de caractere;
*/
int transform_chars_to_integer(const char* amount_text);


//Functie primeste ca parametru controller si sirurile de caractere: tip, suprafata, adresa, pret:
//Functia returneaza un sir de caractere
//Functia returneaza OK daca s-a putut adauga oferta cu datele respective
//Functia returneaza EROARE daca nu au fost introduse corect;
int add_oferta(Controller* controller, char* tip, int suprafata, char* adresa, int pret);


//Functie primeste ca parametru controller si sirurile de caractere: id, tip, suprafata, adresa, pret:
//Functia returneaza un sir de caractere
//Functia returneaza OK daca s-a putut edita oferta cu datele respective
//Functia returneaza EROARE daca nu au fost introduse corect;
int edit(Controller* controller, int id, char* tip, int suprafata, char* adresa, int pret);


//Functie primeste ca parametru controller si un sir de caractere(id oferta)
//Functia returneaza un sir de caractere
//Functia returneaza OK daca s-a putut sterge oferta cu id-ul respectiv
//Functia returneaza EROARE daca nu exista
int remove_of(Controller* controller, int id);

int compare_Oferta_by_price(Oferta* of1, Oferta* of2);

int compare_Oferta_by_type(Oferta* of1, Oferta* of2);

void sort(Vector* v, CompareFct cmpF);

void sort2(Vector* v, CompareFct cmpF, CompareFct cmpF2);

//Functia primeste ca parametru controller
//Functia returneaza un array de oferte sortate dupa pret
Vector* sort_by_price(Controller* controller);

//Functia primeste ca parametru controller
//Functia returneaza un array de oferte sortate dupa tip
Vector* sort_by_tip(Controller* controller);

//Functia primeste ca parametru controller, un tip dupa care se filtreaza
//Size-ul care se intoarce ca parametru reprezinta numarul de oferte care respecta tipul dat
//Functia returneaza un array de oferte sortate descendent dupa tip
Vector* filter_by_tip(Controller* controller, char* tip);

//Functia primeste ca parametru un controller, un sir de caractere care reprezinta valoare minima ca text
//alt sir de caractere care reprezinta valoarea maxima ca text si un size care se va intoarce reprezentant 
//cate elemente au respectat filtrarea dupa suprafata in [min, max]
Vector* filter_by_suprafata(Controller* controller, char* mini_txt, char* maxi_txt);

////Functia primeste ca parametru un controller, un sir de caractere care reprezinta valoare minima ca text
//alt sir de caractere care reprezinta valoarea maxima ca text si un size care se va intoarce reprezentant 
//cate elemente au respectat filtrarea dupa pret in [min, max]
Vector* filter_by_pret(Controller* controller, char* mini_txt, char* maxi_txt);

//Functia primeste ca parametru un controller, un multiplu care este un nr intreg la
//care va trebui sa se imparta pretul
Vector* filter_by_pret_multiplu(Controller* controller, char* multiplu);

int undo(Controller* controller);

Vector* sort_by_tip_price(Controller* controller);

void test_controller();