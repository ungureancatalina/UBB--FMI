#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include "functionalitati.h"
#include "participant.h"
#include "Lista.h"

void test_creeaza()
{
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(strcmp(part.nume, "Pop") == 0);
	assert(strcmp(part.prenume, "Ana") == 0);
	assert(part.scor[3] == 9);
	assert(part.nr_scor == 4);
	distruge_participant(&part);
}

void test_distruge()
{
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	assert(part.scor[3] == 0);
	assert(part.nr_scor == 0);
}

void test_createEmpty()
{
	MyList lista_part = createEmpty();
	assert(lista_part.nr_participanti == 0);
	destroy(&lista_part);
}

void test_add()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	add(&lista_part, part);
	assert(lista_part.nr_participanti == 1);
	destroy(&lista_part);
	assert(lista_part.nr_participanti == 0);
}

void test_delete()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	add(&lista_part, part);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 10, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	add(&lista_part, part);
	distruge_participant(&part);
	assert(lista_part.nr_participanti == 2);
	delete(&lista_part, 1);
	assert(lista_part.nr_participanti == 1);
	delete(&lista_part, 1);
	assert(lista_part.nr_participanti == 0);
	delete(&lista_part, 0);
	destroy(&lista_part);
}

void test_adaugare()
{
	MyList lista_part = createEmpty();
	lista_part.maxim = 1;
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	assert(strcmp(part.nume, "Pop") == 0);
	assert(strcmp(part.prenume, "Ana") == 0);
	assert(part.scor[3] == 9);
	distruge_participant(&part);
	char nume3[15] = "Pop", prenume3[15] = "Ana";
	int scor3[10] = { 0 }, nr_scor3 = 4;
	scor3[0] = 3, scor3[1] = 7, scor3[2] = 2, scor3[3] = 9;
	part = creare_participanti(nume3, prenume3, scor3, nr_scor3);
	adauga(&lista_part, nume3, prenume3, scor3, nr_scor3);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 10, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	assert(strcmp(part.nume, "Dia") == 0);
	assert(strcmp(part.prenume, "Maria") == 0);
	assert(part.scor[3] == 6);
	distruge_participant(&part);
	destroy(&lista_part);
}

void test_stergere()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part2 = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	part2 = creare_participanti(nume, prenume, scor, nr_scor);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part2);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 10, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	p part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	distruge_participant(&part);
	assert(lista_part.nr_participanti == 2);
	sterge(&lista_part, nume2);
	assert(lista_part.nr_participanti == 1);
	sterge(&lista_part, nume);
	assert(lista_part.nr_participanti == 0);
	destroy(&lista_part);
}

void test_actualizare()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	char nume2[15] = "Dia";
	p particip;
	particip = actualizeaza(&lista_part, nume, nume2, prenume, scor, nr_scor);
	assert(strcmp(get(&lista_part, 0).nume, "Dia") == 0);
	assert(strcmp(get(&lista_part, 0).nume, "Pop") != 0);
	assert(strcmp(get(&lista_part, 0).prenume, "Ana") == 0);
	particip = actualizeaza(&lista_part, nume, nume2, prenume, scor, nr_scor);
	assert(strcmp(get(&lista_part, 0).nume, "Dia") == 0);
	assert(strcmp(get(&lista_part, 0).nume, "Pop") != 0);
	assert(strcmp(get(&lista_part, 0).prenume, "Ana") == 0);
	destroy(&lista_part);
}

void test_cauta()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 10, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	distruge_participant(&part);
	int poz = -1;
	poz = cauta(&lista_part, "Aba");
	assert(poz == -1);
	poz = cauta(&lista_part, "Dia");
	assert(poz == 1);
	destroy(&lista_part);
}

void test_filtrare()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 0, scor[1] = 5, scor[2] = 7, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 1, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	distruge_participant(&part);
	assert(lista_part.nr_participanti == 2);
	MyList lista_noua = filtrare_scor(&lista_part);
	assert(size(&lista_noua) == 1);
	destroy(&lista_noua);
	destroy(&lista_part);
}

void test_sortare_scor()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 1, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	distruge_participant(&part);
	MyList lista_noua = sortare_scor(&lista_part);
	assert(strcmp(get(&lista_noua, 0).nume, "Dia") == 0);
	assert(strcmp(get(&lista_noua, 0).nume, "Aba") != 0);
	assert(strcmp(get(&lista_noua, 1).nume, "Pop") == 0);
	assert(strcmp(get(&lista_noua, 1).nume, "SFc") != 0);
	destroy(&lista_part);
}

void test_sortare_nume()
{
	MyList lista_part = createEmpty();
	char nume[15] = "Pop", prenume[15] = "Ana";
	int scor[10] = { 0 }, nr_scor = 4;
	scor[0] = 1, scor[1] = 5, scor[2] = 4, scor[3] = 9;
	p part = creare_participanti(nume, prenume, scor, nr_scor);
	assert(lista_part.nr_participanti == 0);
	adauga(&lista_part, nume, prenume, scor, nr_scor);
	distruge_participant(&part);
	char nume2[15] = "Dia", prenume2[15] = "Maria";
	int scor2[10] = { 0 }, nr_scor2 = 4;
	scor2[0] = 1, scor2[1] = 5, scor2[2] = 1, scor2[3] = 6;
	part = creare_participanti(nume2, prenume2, scor2, nr_scor2);
	adauga(&lista_part, nume2, prenume2, scor2, nr_scor2);
	distruge_participant(&part);
	MyList lista_noua = sortare_nume(&lista_part);
	assert(strcmp(get(&lista_noua, 0).nume, "Dia") == 0);
	assert(strcmp(get(&lista_noua, 0).nume, "Aba") != 0);
	assert(strcmp(get(&lista_noua, 1).nume, "Pop") == 0);
	assert(strcmp(get(&lista_noua, 1).nume, "SFc") != 0);
	destroy(&lista_part);
}

void test_validare1()
{
	char nume[15] = "Pop", prenume[15] = "Ana";
	char nume2[15] = "D", prenume2[15] = "Maria";
	char nume3[15] = " ", prenume3[15] = " ";
	int ok = validare1(nume, prenume);
	assert(ok == 1);
	int ok2 = validare1(nume2, prenume2);
	assert(ok2 == 3);
	int ok3 = validare1(nume3, prenume3);
	assert(ok3 == 5);
}

void test_validare2()
{
	int scor[10] = { 0 }, nr_scor = 2;
	scor[0] = 1, scor[1] = 3;
	int scor2[10] = { 0 }, nr_scor2 = 15;
	int scor3[10] = { 0 }, nr_scor3 = 4;
	scor2[0] = 10, scor2[1] = -5, scor2[2] = 100, scor2[3] = 6;
	int ok = validare2(scor, nr_scor);
	assert(ok == 1);
	int ok1 = validare2(scor2, nr_scor2);
	assert(ok1 == 7);
	int ok2 = validare2(scor3, nr_scor3);
	assert(ok2 == 7);

}