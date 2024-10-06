//7. Calculeaza o valoare aproximativa a radacinii patrate a unui numar real pozitiv, cu o precizie data.


#include <stdio.h>

float cautare_binara(int n,int p)
/*
Calculeaza o valoare aproximativa a radacinii patrate a unui numar real pozitiv
:pre conditie: n si p sa fie de tip int pozitiv 
:post conditie: se afiseaza un numar de tip float
*/
{
    int mijloc = n / 2,start = 0,final = n;
    float rezultat = 0;
    while (start <= final) 
    {
        mijloc = (start + final) / 2;
        if (mijloc * mijloc == n) 
        {
            rezultat = mijloc;
            break;
        }
        if (mijloc * mijloc < n) 
        {
            start = mijloc + 1;
            rezultat = mijloc;
        }
        else 
        {
            final = mijloc - 1;
        }
    }
    float aduna = 0.1;
    for (int i = 0; i < p; i++) 
    {
        while (rezultat * rezultat <= n) 
        {
            rezultat += aduna;
        }
        rezultat = rezultat - aduna;
        aduna = aduna / 10;
    }
    return rezultat;
}


int main()
{
    printf("Meniul este: \n");
    printf("1: Calculeaza o valoare aproximativa a radacinii patrate a unui numar real pozitiv, cu o precizie data. \n");
    printf("2: Iesire \n");
    int ok = 1, optiunea = 0;
    int n = 0, p = 0;
    float nr = 0;
    while (ok == 1)
    {
        printf("Introduceti o optiune: \n");
        scanf("%d", &optiunea);
        switch (optiunea)
        {
        case 1:
            printf("Introduceti un numar n: \n");
            scanf("%d", &n);
            printf("Introduceti un numar p care va reprezenta precizia: \n");
            scanf("%d", &p);
            nr = cautare_binara(n,p);
            printf("%f", nr);
            printf("\n");
            break;
        default:
            ok = 0;
            break;
        }
    }
    return 0;
}