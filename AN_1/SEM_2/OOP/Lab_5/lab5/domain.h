#pragma once
typedef struct {
    char* tip;
    int suprafata;
    char* adresa;
    int pret;
}Oferta;

//Functie care primeste toate datele corecte si creeaza o ofera noua, apoi o returneaza
Oferta* create_Oferta(char* tip, int suprafata, char* adresa, int pret);

//Functie care copiaza oferta curenta
Oferta* copyOferta(Oferta* oferta);

//Functie care primeste ca parametru un pointer void si sterge ce e acolo din memorie;
void delete_Oferta(Oferta* oferta);

//Functie care testeaza domeniul
void test_domain();