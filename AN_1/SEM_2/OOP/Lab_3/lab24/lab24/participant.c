#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include "participant.h"

int validare1(char* nume, char* prenume)
{
	int ok = 0;
	if (strcmp(nume, " ") == 0)
	{
		ok = 2;
	}
	if (strlen(nume) < 3)
	{
		ok = 3;
	}
	if (strcmp(prenume, " ") == 0)
	{
		ok = 4;
	}
	if (strlen(prenume) < 3)
	{
		ok = 5;
	}
	if (ok != 0)
		return ok;
	else
		return 1;
}

int validare2(int scor[], int nr_scor)
{
	int ok = 0;
	if (nr_scor < 0 || nr_scor > 10)
	{
		ok = 6;
	}
	for (int i = 0; i < nr_scor; i++)
		if (scor[i] < 1 || scor[i]>10)
			ok = 7;
	if (ok > 0)
		return ok;
	return 1;
}

p creare_participanti(char* nume, char* prenume, int scor[], int nr_scor)
{
	p particip;
	int nr_caractere = (int)(strlen(nume) + 1);
	particip.nume = malloc(nr_caractere * sizeof(char));
	if (particip.nume != NULL)
		strcpy(particip.nume, nume);
	int nr_caractere2 = (int)(strlen(prenume) + 1);
	particip.prenume = malloc(nr_caractere2 * sizeof(char));
	if (particip.prenume != NULL)
		strcpy(particip.prenume, prenume);
	particip.nr_scor = nr_scor;
	for (int i = 0; i < nr_scor; i++)
		particip.scor[i] = scor[i];
	return particip;
}

void distruge_participant(p* participant)
{
	free(participant->nume);
	free(participant->prenume);
	for (int i = 0; i < participant->nr_scor; i++)
		participant->scor[i] = 0;
	participant->nr_scor = 0;
}