#pragma once
#include <stdexcept>

#ifndef MATRICE_H
#define MATRICE_H

#define NULL_TELEMENT 0

typedef int TElem; 

struct Node 
{
    int linie;
    int coloana;
    TElem valoare;
    Node* next;
    Node(int l, int c, TElem v, Node* nxt = nullptr) : linie(l), coloana(c), valoare(v), next(nxt) {}
};

class IteratorMatrice;


class Matrice
{
private:
    friend class IteratorMatrice;
    int Linii;
    int Coloane;
    Node* head;
    
public:
    //constructor
    //se arunca exceptie daca Linii<=0 sau Coloane<=0
    Matrice(int Linii, int Coloane) : Linii(Linii), Coloane(Coloane), head(nullptr) {}

    //destructor
    ~Matrice()
    {
        Node* current = head;
        while (current) {
            Node* next = current->next;
            delete current;
            current = next;
        }
    };

    //returnare element de pe o linie si o coloana
    //se arunca exceptie daca (i,j) nu e pozitie valida in Matrice
    //indicii se considera incepand de la 0
    TElem element(int i, int j) const;

    // returnare numar linii
    int nrLinii() const;

    // returnare numar coloane
    int nrColoane() const;

    // modificare element de pe o linie si o coloana si returnarea vechii valori
    // se arunca exceptie daca (i,j) nu e o pozitie valida in Matrice
    TElem modifica(int i, int j, TElem);

    // returnare un iterator pentru elementele nenule din Matrice
    IteratorMatrice iterator() const;

};

class IteratorMatrice
{
private:
    const Matrice& matrice; 
    int linie; 
    int coloana; 
public:
    IteratorMatrice(const Matrice& matrice) : matrice(matrice), linie(0), coloana(0) {}
    void urmator();
    bool valid() const;
    TElem element2() const;
};

#endif MATRICE_H