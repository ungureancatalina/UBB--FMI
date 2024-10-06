#pragma once

typedef struct
{
	char* nume;
	char* prenume;
	int scor[11];
	int nr_scor;
}p;

int validare1(char* nume, char* prenume);
/*
Verifica daca numele si prenumele unui participant sunt corecte (fara cifre,cu lungime >=3 si nevide)
Parametrii: sirurile de caractere nume-numele participantului si prenume-prenumele participantului
Returneaza 1 daca cei 2 parametrii sunt valizi,iar 0 altfel
*/

int validare2(int scor[], int nr_scor);
/*
Verifica daca nr de scoruri si scorurile unui participant sunt corecte (intre 1 si 10)
Parametrii: nr_scor-nr de scoruri ale participantului, scor-vector de scoruri
Returneaza 1 daca cei 2 parametrii sunt valizi, iar 0 altfel
*/

p creare_participanti(char* nume, char* prenume, int scor[], int nr_scor);
/*
Creeaza un participant si aloca memoria necesara
Parametrii: sirurile de caractere nume-numele participantului si prenume-prenumele participantului,
nr_scor-nr de scoruri ale participantului, scor-vector de scoruri
Returneaza participantul creat
*/


void distruge_participant(p* participant);
/*
Distruge participantul din lista si elibereaza memoria alocata
Parametrii: participant -participantul dat
*/
