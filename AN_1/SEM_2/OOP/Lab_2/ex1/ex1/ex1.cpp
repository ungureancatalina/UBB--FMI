#include <stdio.h>

int prim(int numar)
/*
Verifica numerul daca este prim
:pre conditie: numar sa fie de tip int pozitiv
:post conditie: se returneaza 1 daca e prim sau 0 altfel
*/
{
    int nr_div = 0;
    for (int i = 1; i <= numar; i++)
    {
        if (numar % i == 0)
            nr_div += 1;
    }
    if (nr_div == 2)
        return 1;
    else
        return 0;
}

void verificare(int n)
/*
Verifica numerele mai mici decat n si le afiseaza pe cele prime
:pre conditie: n sa fie de tip int pozitiv
:post conditie: se afiseaza daca exista sau nu numere valide
*/
{
    int x = 0;
    printf("Numerele prime pana la n sunt: ");
    int ok = 0;
    for (int i = 2; i <= n; i++)
    {
        x = prim(i);
        if (x == 1)
        {
            printf(" %d", i);
            ok = 1;
        }
    }
    if (ok == 0)
        printf("nu exista");
    printf("\n");
}


int main()
{
    printf("Meniul este: \n");
    printf("1: Genereaza numere prime mai mici decat un numar natural dat. \n");
    printf("2: Iesire \n");
    int n = 0;
    int ok = 1, optiunea = 0;
    while (ok == 1)
    {
        printf("Introduceti o optiune: \n");
        scanf("%d", &optiunea);
        switch (optiunea)
        {
        case 1:
            printf("Introduceti un numar n: \n");
            scanf("%d", &n);
            verificare(n);
            break;
        default:
            ok = 0;
            break;
        }
    }
    return 0;
}