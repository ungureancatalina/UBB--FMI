#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "functionalitati.h"
#include "teste.h"
#include "participant.h"
#include "Lista.h"

int citire;

void testare()
{
	test_adaugare();
	test_stergere();
	test_actualizare();
	test_filtrare();
	test_sortare_scor();
	test_sortare_nume();
	test_cauta();
	test_validare1();
	test_validare2();
	test_add();
	test_createEmpty();
	test_creeaza();
	test_distruge();
	test_delete();
}

void nr_eroare(int ok)
{
	if (ok == 2)
		printf("Numele nu poate fi vid. \n");
	else if (ok == 3)
		printf("Numele este prea scurt. \n");
	else if (ok == 4)
		printf("Prenumele nu poate fi vid. \n");
	else if (ok == 5)
		printf("Prenumele este prea scurt. \n");
	else if (ok == 6)
		printf("Nr de scoruri nu poate fi negativ sau mai mare decat 10. \n");
	else if (ok == 7)
		printf("Scorul trebuie sa ia valori intre 1 si 10. \n");
}

void afisare_participanti(MyList* lista_participanti)
{
	if (lista_participanti->nr_participanti == 0)
		printf("Nu exista participanti");
	else
	{
		printf("Participantii sunt: \n");
		for (int i = 0; i < size(lista_participanti); i++)
		{
			p participant = get(lista_participanti, i);
			printf("%s ", participant.nume);
			printf("%s ", participant.prenume);
			for (int j = 0; j < participant.nr_scor; j++)
				if (participant.scor[j] > 0)
					printf("%d ", participant.scor[j]);
			printf(" \n");
		}
	}
}

void adauga_ui(MyList* lista_participanti)
{
	char nume[25], prenume[25];
	int scor[11] = { 0 };
	int nr_scor = 0;
	printf("Introduceti un nume: \n");
	citire = scanf("%s", nume);
	printf("Introduceti un prenume: \n");
	citire = scanf("%s", prenume);
	printf("Introduceti un numar de scoruri: \n");
	citire = scanf("%d", &nr_scor);
	printf("Introduceti scorurile: \n");
	for (int i = 0; i < nr_scor; i++)
		citire = scanf("%d", &scor[i]);
	int corect1 = validare1(nume, prenume);
	int corect2 = validare2(scor, nr_scor);
	if (corect1 == 1 && corect2 == 1)
		adauga(lista_participanti, nume, prenume, scor, nr_scor);
	else
	{
		nr_eroare(corect1);
		nr_eroare(corect2);
	}
	afisare_participanti(lista_participanti);
}

void actualizeaza_ui(MyList* lista_participanti)
{
	char cheie_nume[25];
	char nume[25], prenume[25];
	int scor[11] = { 0 };
	int nr_scor = 0;
	printf("Introduceti numele participantului ce doriti a fi actualizat: \n");
	citire = scanf("%s", cheie_nume);
	printf("Introduceti un nume: \n");
	citire = scanf("%s", nume);
	printf("Introduceti un prenume: \n");
	citire = scanf("%s", prenume);
	printf("Introduceti un numar de scoruri: \n");
	citire = scanf("%d", &nr_scor);
	printf("Introduceti scorurile: \n");
	for (int i = 0; i < nr_scor; i++)
		citire = scanf("%d", &scor[i]);
	int corect1 = validare1(nume, prenume);
	int corect2 = validare2(scor, nr_scor);
	int ok = 0;
	if (corect1 == 1 && corect2 == 1) {
		p e = actualizeaza(lista_participanti, cheie_nume, nume, prenume, scor, nr_scor);
	}
	else
	{
		nr_eroare(corect1);
		nr_eroare(corect2);
	}
	if (ok == 0)
		printf("Nu exista acest participant ce doriti a fi actualizat. \n");
	else
	{
		afisare_participanti(lista_participanti);
	}
}

void sterge_ui(MyList* lista_participanti)
{
	char cheie_nume[25];
	printf("Introduceti numele participantului ce doriti a fi sters: \n");
	citire = scanf("%s", cheie_nume);
	int ok = sterge(lista_participanti, cheie_nume);
	if (ok == 0)
		printf("Nu exista acest participant ce doriti a fi sters. \n");
	else
	{
		afisare_participanti(lista_participanti);
	}
}

void sortare_ui(MyList* lista_participanti)
{
	int optiunea = 0;
	printf("1: sortare dupa nume.    2:sortare dupa scor. \n");
	printf("Ce optiune alegeti? \n");
	citire = scanf("%d", &optiunea);
	switch (optiunea)
	{
	case 1:
	{
		MyList lista_noua = sortare_nume(lista_participanti);
		afisare_participanti(&lista_noua);
		break;
	}
	case 2:
	{
		MyList lista_noua = sortare_scor(lista_participanti);
		afisare_participanti(&lista_noua);
		break;
	}
	}
}

void filtrare_ui(MyList* lista_participanti)
{
	printf("Participantii cu scor mai mare decat 5 sunt: \n");
	MyList lista_noua = filtrare_scor(lista_participanti);
	afisare_participanti(&lista_noua);
}

void meniu()
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
{
	int ok = 1;
	MyList lista_participanti = createEmpty();
	while (ok == 1)
	{
		int optiune = 0;
		meniu();
		printf("Ce optiune alegeti? \n");
		citire = scanf("%d", &optiune);
		switch (optiune)
		{
		case 1:
		{
			adauga_ui(&lista_participanti);
			break;
		}
		case 2:
		{
			actualizeaza_ui(&lista_participanti);
			break;
		}
		case 3:
		{
			sterge_ui(&lista_participanti);
			break;
		}
		case 4:
		{
			filtrare_ui(&lista_participanti);
			break;
		}
		case 5:
		{
			sortare_ui(&lista_participanti);
			break;
		}
		case 6:
		{
			ok = 0;
			break;
		}
		default:
		{
			break;
		}
		}
	}
	destroy(&lista_participanti);
}

int main()
{
	testare();
	start();
	_CrtDumpMemoryLeaks();
	return 0;
}
