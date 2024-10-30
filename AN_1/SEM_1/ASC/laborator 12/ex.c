/*++
Se dau un sir care contine n reprezentari binare pe 8 biti date ca sir de caractere.
Sa se obtina un sir care contine numerele corespunzatoare reprezentarilor binare.
Exemplu:
Se da: '10100111b', '01100011b', '110b', '101011b'
Se stocheaza: 10100111b, 01100011b, 110b, 101011b
--*/

#include <stdio.h>
#include <string.h>

extern void ex(char c);

int main()
{
    // declarare numarul de siruri citite
    int n = 0;
    printf("n=");
    scanf("%d", &n);
    
    int numar = 0;
    int cifra = 0;
    char a[100] = "";
    
    // afisam mesajul
    printf("Sirul final este: ");
    
    // se prelucreaza n siruri
    for (int i=1;i<=n;i++)
        
        // declarare variabile
        numar = 0;
        cifra = 0;

        // citire de la tastatura sirul a
        printf("a=");
        scanf("%s", &a);
        
        for (int j=0;j<strlen(a);j++)
            // apelam functia scrisa in limbaj de asamblare
            cifra = ex(a[j]);
            numar=numar*10+cifra;
        
        // afisam valoarea calculata de functie
        printf("%d", numar);
        
    return 0;
}