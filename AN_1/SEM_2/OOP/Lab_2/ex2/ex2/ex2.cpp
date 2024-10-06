//2. Genereaza primele n (n natural dat) numere prime.

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
Genereaza primele n numere prime
:pre conditie: n sa fie de tip int pozitiv
:post conditie: se afiseaza daca exista sau nu numere valide
*/
{
    int x = 2,nr = 0,ok = 1;
    printf("Primele n numere prime sunt: ");
    while(ok==1)
    {
        if (nr == n)
            ok = 0;
        else
        { 
            if (prim(x) == 1)
            {
                printf("%d", x);
                printf(" ");
                nr++;
            }
            if (x == 2)
                x += 1;
            else
                x += 2;
        }
    }
    if (nr == 0)
        printf("nu exista");
    printf("\n");
}


int main()
{
    int n = 0;
    printf("Introduceti un numar n: \n");
    scanf("%d", &n);
    int ok = 1, optiunea = 0;
    while (ok == 1)
    {
        printf("Introduceti o optiune: \n");
        scanf("%d", &optiunea);
        switch (optiunea)
        {
        case 1:
            verificare(n);
            break;
        default:
            ok = 0;
            break;
        }
    }
    return 0;
}