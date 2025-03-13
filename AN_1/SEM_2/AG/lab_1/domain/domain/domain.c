#include <stdio.h>
#include <string.h>
#include "functionalitate.h"
#include "teste.h"
#include "validator.h" 

void testare()
{
	test_adaugare();
	test_stergere();
	test_actualizare();
	test_filtrare();
	test_sortare_scor();
	test_sortare_nume();
}

void adauga_ui(struct p participant[20], int* nr_participant)
/*
Adauga un nou participant la lista initiala,iar inainte il valideaza si afiseaza lista finala
Parametrii: participant-lista de participanti existenta, nr_participant-nr de participanti din lista,
*/
{
	char nume[25], prenume[25];
	int scor[11] = { 0 };
	int nr_scor = 0;
	printf("Introduceti un nume: \n");
	scanf("%s", nume);
	printf("Introduceti un prenume: \n");
	scanf("%s", prenume);
	printf("Introduceti un numar de scoruri: \n");
	scanf("%d", &nr_scor);
	printf("Introduceti scorurile: \n");
	for (int i = 0; i < nr_scor; i++)
		scanf("%d", &scor[i]);
	int corect1 = validare1(nume, prenume);
	int corect2 = validare2(scor, nr_scor);
	if (corect1 == 1 && corect2 == 1)
		adauga(participant, nr_participant, nume, prenume, scor, nr_scor);
	printf("Participantii sunt: \n");
	for (int i = 0; i < *nr_participant; i++)
	{
		printf("%s ", participant[i].nume);
		printf("%s ", participant[i].prenume);
		for (int j = 0; j < 11; j++)
			if (participant[i].scor[j] > 0)
				printf("%d ", participant[i].scor[j]);
		printf(" \n");
	}
}

void actualizeaza_ui(struct p participant[20], int* nr_participant)
/*
Actualizeaza un participant din lista initiala,iar inainte il valideaza si afiseaza lista finala
Parametrii: participant-lista de participanti existenta, nr_participant-nr de participanti din lista,
*/
{
	char cheie_nume[25];
	char nume[25], prenume[25];
	int scor[11] = { 0 };
	int nr_scor = 0;
	printf("Introduceti numele participantului ce doriti a fi actualizat: \n");
	scanf("%s", cheie_nume);
	printf("Introduceti un nume nou: \n");
	scanf("%s", nume);
	printf("Introduceti un prenume nou: \n");
	scanf("%s", prenume);
	printf("Introduceti un numar de scoruri nou: \n");
	scanf("%d", &nr_scor);
	printf("Introduceti scorurile noi: \n");
	for (int i = 0; i < nr_scor; i++)
		scanf("%d", &scor[i]);
	int corect1 = validare1(nume, prenume);
	int corect2 = validare2(scor, nr_scor);
	if (corect1 == 1 && corect2 == 1)
		actualizeaza(participant, nr_participant, cheie_nume, nume, prenume, scor, nr_scor);
	printf("Participantii sunt: \n");
	for (int i = 0; i < *nr_participant; i++)
	{
		printf("%s ", participant[i].nume);
		printf("%s ", participant[i].prenume);
		for (int j = 0; j < 11; j++)
			if (participant[i].scor[j] > 0)
				printf("%d ", participant[i].scor[j]);
		printf(" \n");
	}
}

void sterge_ui(struct p participant[20], int* nr_participant)
/*
Sterge un participant din lista initiala si afiseaza lista finala
Parametrii: participant-lista de participanti existenta, nr_participant-nr de participanti din lista,
*/
{
	char cheie_nume[25];
	printf("Introduceti numele participantului ce doriti a fi sters: \n");
	scanf("%s", cheie_nume);
	sterge(participant, nr_participant, cheie_nume);
	printf("Participantii sunt: \n");
	for (int i = 0; i < *nr_participant; i++)
	{
		printf("%s ", participant[i].nume);
		printf("%s ", participant[i].prenume);
		for (int j = 0; j < 11; j++)
			if (participant[i].scor[j] > 0)
				printf("%d ", participant[i].scor[j]);
		printf(" \n");
	}
}

void sortare_ui(struct p participant[20], int* nr_participant)
/*
Sorteaza participantii din lista initiala dupa nume sau dupa media scorurilor crescator si afiseaza lista finala
Parametrii: participant-lista de participanti existenta, nr_participant-nr de participanti din lista,
*/
{
	int optiunea = 0;
	printf("1: sortare dupa nume.    2:sortare dupa scor. \n");
	printf("Ce optiune alegeti? \n");
	scanf("%d", &optiunea);
	switch (optiunea)
	{
	case 1:
	{
		sortare_nume(participant, nr_participant);
		break;
	}
	case 2:
	{
		sortare_scor(participant, nr_participant);
		break;
	}
	}
	printf("Participantii sunt: \n");
	for (int i = 0; i < *nr_participant; i++)
	{
		printf("%s ", participant[i].nume);
		printf("%s ", participant[i].prenume);
		for (int j = 0; j < 11; j++)
			if (participant[i].scor[j] > 0)
				printf("%d ", participant[i].scor[j]);
		printf(" \n");
	}
}

void filtrare_ui(struct p participant[20], int* nr_participant)
/*
Filtreaza participantii din lista initiala dupa media scorurilor peste 5 si afiseaza lista finala
Parametrii: participant-lista de participanti existenta, nr_participant-nr de participanti din lista,
*/
{
	printf("Participantii cu scor mai mare decat 5 sunt: \n");
	int v[50];
	filtrare_scor(participant, nr_participant, v);
	for (int i = 0; i < *nr_participant; i++)
		if (v[i] == 1)
		{
			printf("%s ", participant[i].nume);
			printf("%s ", participant[i].prenume);
			for (int j = 0; j < 11; j++)
				if (participant[i].scor[j] > 0)
					printf("%d ", participant[i].scor[j]);
			printf(" \n");
		}
}

void meniu()
/*  La concursul anual de programare, comitetul de evaluare inregistreaza intr - o lista scorul
obtinut de fiecare participant.Despre fiecare participant se inregistreaza urmatoarele
informatii : Nume, Prenume, Scor.Fiecare participant trebuie sa rezolve 10 probleme, maximul
de puncte pe fiecare problema este 10.*/
{
	printf("Optiunile sunt: \n");
	printf("1.Adauga participanti \n");
	printf("2.Actualizare participant existent \n");
	printf("3.Sterge participanti \n");
	printf("4.Permite afisearea participantilor filtrat dupa o proprietate(cei care au un scor mai mic decat o valoare data,cei cu nume incepand cu o litera data, etc \n");
	printf("5.Permite sortarea participantilor dupa : nume sau dupa scor(crescator / descrescator) \n");
	printf("6.Iesire \n");
}

void start()
/*
Se incepe programul iar utilizatorul alege optiunile dorite
*/
{
	int ok = 1;
	struct p participant[20];
	int nr_participant = 0;
	while (ok == 1)
	{
		int optiune = 0;
		meniu();
		printf("Ce optiune alegeti? \n");
		scanf("%d", &optiune);
		getchar();
		switch (optiune)
		{
		case 1:
		{
			adauga_ui(participant, &nr_participant);
			break;
		}
		case 2:
		{
			actualizeaza_ui(participant, &nr_participant);
			break;
		}
		case 3:
		{
			sterge_ui(participant, &nr_participant);
			break;
		}
		case 4:
		{
			filtrare_ui(participant, &nr_participant);
			break;
		}
		case 5:
		{
			sortare_ui(participant, &nr_participant);
			break;
		}
		case 6:
		{
			break;
		}
		default:
		{
			ok = 0;
			break;
		}
		}
	}
}

int main()
{
	testare();
	start();
	return 0;
}
