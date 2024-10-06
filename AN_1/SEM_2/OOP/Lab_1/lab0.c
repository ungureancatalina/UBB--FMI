#include <stdio.h>

int main() 
{    
    int n=0,suma=0,nr=0;
    printf("Introduceti un numar n: ");
    scanf("%d",&n);
    for (int i=0;i<n;i++)
        {
        scanf("%d",&nr);
        suma+=nr;     
        } 
    printf("Suma numerelor este: %d \n",suma);
    return 0;
}