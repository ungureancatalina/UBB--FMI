#include <string.h>
#include <stdlib.h>
#include <assert.h>
#include "domain.h"
#include "array.h"

Oferta* create_Oferta(char* tip, int suprafata, char* adresa, int pret)
{
    char* ok;
    Oferta* oferta = (Oferta*)malloc(sizeof(Oferta));
    if (oferta == NULL)
        return NULL;
    int nr = (int)strlen(tip) + 1;
    oferta->tip = (char*)malloc(nr * sizeof(char));
    if(oferta->tip!=NULL)
        ok=strcpy(oferta->tip, tip);
    nr = (int)strlen(adresa) + 1;
    oferta->adresa = (char*)malloc(nr * sizeof(char));
    if (oferta->adresa != NULL)
        ok=strcpy(oferta->adresa, adresa);
    oferta->pret = pret;
    oferta->suprafata = suprafata;
    return oferta;
}

void delete_Oferta(Oferta* oferta)
{
    free(oferta->tip);
    free(oferta->adresa);
    free(oferta);
}

Oferta* copyOferta(Oferta* oferta)
{
    return create_Oferta(oferta->tip, oferta->suprafata, oferta->adresa, oferta->pret);
}

void test_creare_distrugere()
{
    Oferta* o = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    assert(strcmp(o->tip, "teren") == 0);
    assert(o->pret == 350);
    delete_Oferta(o);
}

void test_domain()
{
    test_creare_distrugere();
}