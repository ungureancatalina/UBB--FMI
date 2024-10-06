#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "functionalitati.h"
#include "participant.h"
#include "Lista.h"

void adauga(MyList* lista_participanti, char* nume, char* prenume, int scor[], int nr_scor)
{
	p particip = creare_participanti(nume, prenume, scor, nr_scor);
	for (int i = 0; i < size(lista_participanti); i++)
	{
		if (strcmp(get(lista_participanti, i).nume, nume) == 0 && strcmp(get(lista_participanti, i).prenume, prenume) == 0)
		{
			p old=actualizeaza(lista_participanti, nume, prenume, nume, scor, nr_scor);
			//distruge_participant(&old);
		}
	}
	add(lista_participanti, particip);
}

int cauta(MyList* lista_participanti, char* cheie_nume)
{
	int pozitie = -1;
	for (int i = 0; i < lista_participanti->nr_participanti; i++)
		if (strcmp(lista_participanti->participant[i].nume, cheie_nume) == 0)
		{
			pozitie = i;
			break;
		}
	return pozitie;
}

p actualizeaza(MyList* lista_participanti, char* cheie_nume, char* nume, char* prenume, int scor[], int nr_scor)
{
	p particip = creare_participanti(nume, prenume, scor, nr_scor);
	int sters = sterge(lista_participanti, cheie_nume);
	if (sters == 0)
	{
		distruge_participant(&particip);
		return particip;
	}
	else
	{
		adauga(lista_participanti, nume, prenume, scor, nr_scor);
		distruge_participant(&particip);
	}
	return particip;
}

int sterge(MyList* lista_participanti, char* cheie_nume)
{
	int pozitie = -1;
	pozitie = cauta(lista_participanti, cheie_nume);
	if (pozitie >= 0)
	{
		p particip = delete(lista_participanti, pozitie);
		distruge_participant(&particip);
		return 1;
	}
	return 0;
}

float medie_scor(MyList* lista_participanti, int pozitie)
{
	float rezultat = 0;
	float suma = 0, nr = 0;
	for (int i = 0; i < lista_participanti->participant[pozitie].nr_scor; i++)
		if (lista_participanti->participant[pozitie].scor[i] > 0)
		{
			suma = suma + lista_participanti->participant[pozitie].scor[i];
			nr++;
		}
	rezultat = suma / nr;
	return rezultat;
}

int compara_nume(p* p1, p* p2)
{
	;	return strcmp(p1->nume, p2->nume);
}

void sort(MyList* lista, functie_compara cmp)
{
	MyList lista_sortata = *lista;
	p particip;
	for (int i = 0; i < size(&lista_sortata) - 1; i++)
		for (int j = i + 1; j < size(&lista_sortata); j++)
			if (cmp(&lista_sortata.participant[i], &lista_sortata.participant[j]) > 0)
			{
				particip = lista_sortata.participant[i];
				lista_sortata.participant[i] = lista_sortata.participant[j];
				lista_sortata.participant[j] = particip;
			}
}

MyList sortare_nume(MyList* lista_participanti)
{
	MyList lista_sortata = *lista_participanti;
	sort(&lista_sortata, compara_nume);
	return lista_sortata;
}

MyList sortare_scor(MyList* lista_participanti)
{
	MyList lista_sortata = *lista_participanti;
	p particip;
	for (int i = 0; i < size(&lista_sortata) - 1; i++)
		for (int j = i + 1; j < size(&lista_sortata); j++)
		{
			float scor_i = 0, scor_j = 0;
			scor_i = medie_scor(lista_participanti, i);
			scor_j = medie_scor(lista_participanti, j);
			if (scor_i > scor_j)
			{
				particip = lista_sortata.participant[i];
				lista_sortata.participant[i] = lista_sortata.participant[j];
				lista_sortata.participant[j] = particip;
			}
		}
	return lista_sortata;
}

MyList filtrare_scor(MyList* lista_participanti)
{
	MyList lista_noua = createEmpty();
	for (int i = 0; i < lista_participanti->nr_participanti; i++)
	{
		p part = get(lista_participanti, i);
		if (medie_scor(lista_participanti, i) > 5)
		{
			add(&lista_noua, creare_participanti(part.nume, part.prenume, part.scor, part.nr_scor));
		}
	}
	return lista_noua;

}