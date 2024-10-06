#pragma once
#include "domain.h"
void test_validation();
void test_transform_chars_to_integer();

//Functia validate_option primeste ca parametru un string si verifica daca numarul apartine intervalului [1, 9];
int validate_option(char* option, int st, int dr);

//Functia check_number primeste ca parametru un caracter si verifica daca este numar->returneaza 1 daca e, si 0 in caz contrar;
int check_number(char ch);

//Functia count_sep primeste ca parametru un sir de caractere si returneaza o valoare int
//Functia returneaza cate caractere de ',' sunt in string
int count_sep(const char* string);

//Functia check_ok_adresss primeste ca parametru un sir de caractere si returneaza o valoare int(-1, 0 sau 1)
//Functia returneaza -1 daca nu sunt oferite desulte informatii despre adresa separate prin ,
//Functia returneaza 0 daca nu contine macar o cifra sa indice numarul adresei;
//Functia returneaza 1 daca stringul are cel putin 2 ',' si are cel putin 1 numar;
int check_ok_adress(const char* string);

//Functia check_equ_string primeste ca parametru doua siruri de caractere si returneaza o valoare int(0, 1)
//Functia returneaza 0 daca stringurile au lungimi diferite
//Functia returneaza 0 daca sunt diferite
//Functia returneaza 1 daca cele 2 stringuri sunt identice
int check_equ_string(const char* string1, const char* string2);

//Functia primeste ca parametru un sir de caractere
//Functia returneaza 1 daca sunt doar cifre in string
//Functia returneaza 0 daca aparte un caracter care nu e cifra in string;
int check_number_string(const char* string);

int validare_oferta(Oferta* o);