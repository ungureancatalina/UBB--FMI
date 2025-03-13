#include "Matrice.h"
#include <stdexcept>


//subalgoritm iterator()
//return IteratorMatrice(*this)
//sfârșit subalgoritm

IteratorMatrice Matrice::iterator() const
{
    return IteratorMatrice(*this);
}

//subalgoritm urmator(it)
//    coloana < -coloana + 1
//    dacă linie < matrice.nrLinii() și coloana >= matrice.nrColoane()
//        coloana < -0
//        linie < -linie + 1
//    sfârșit dacă
//sfârșit subalgoritm

void IteratorMatrice::urmator()
{
    coloana++; 
    while (linie < matrice.nrLinii() && coloana >= matrice.nrColoane())
    {
        coloana = 0;
        linie++;
    }
}

//subalgoritm valid()
//    return linie < matrice.nrLinii() și coloana < matrice.nrColoane()
//sfârșit subalgoritm

bool IteratorMatrice::valid() const
{
    return linie < matrice.nrLinii() && coloana < matrice.nrColoane();
}

//subalgoritm element2()
//    dacă nu valid()
//        aruncă o excepție std::out_of_range cu mesajul "Iteratorul nu este valid."
//    altfel
//        return matrice.element(linie, coloana)
//    sfârșit dacă
//sfârșit subalgoritm

TElem IteratorMatrice::element2() const
{
    if (!valid())
    {
        throw std::out_of_range("Iteratorul nu este valid.");
    }
    return matrice.element(linie, coloana);
}


//BC: O(1), daca pozitia e invalida sau daca nodul cautat este primul
//WC: O(n), daca nodul cautat este ultimul sau nu exista, unde n este nr de elemente din lista
//AC: O(n)
//Complexitate Timp : O(n)
//Complexitate Spatiu : O(1)
TElem Matrice::element(int i, int j) const 
{
    //verifică dacă poziția (i, j) este validă
    if (i < 0 || j < 0 || i >= Linii || j >= Coloane) 
    {
        throw std::invalid_argument("Invalid position");
    }

    //un pointer către primul nod din listă
    Node* current = head;

    //parcurge lista până când ajunge la final sau găsește nodul căutat
    while (current) 
    {
        if (current->linie == i && current->coloana == j) 
        {
            return current->valoare;
        }
        current = current->next;
    }

    //dacă nu a găsit elementul la poziția dată
    return 0; 
}


//BC=WC=AC: O(1)
//Complexitate Timp : O(1)
//Complexitate Spatiu : O(1)
int Matrice::nrLinii() const 
{
    return Linii;
}


//BC=WC=AC: O(1)
//Complexitate Timp : O(1)
//Complexitate Spatiu : O(1)
int Matrice::nrColoane() const 
{
    return Coloane;
}


//BC: O(1), daca pozitia e invalida sau daca nodul cautat este primul
//WC: O(n), daca nodul modificat este ultimul sau nu exista in lista
//AC: O(n)
//Complexitate Timp : O(n)
//Complexitate Spatiu : O(1)
TElem Matrice::modifica(int i, int j, TElem e) 
{
    //verifică dacă poziția (i, j) este validă
    if (i < 0 || j < 0 || i >= Linii || j >= Coloane) 
    {
        throw std::invalid_argument("Invalid position");
    }
    // elimina valoarea 0 din matricea rara
    if (e == 0) {
        // caută nodul care corespunde poziției (i, j) și îl elimină, dacă există
        Node* prev = nullptr;
        Node* current = head;

        while (current && (current->linie < i || (current->linie == i && current->coloana < j)))
        {
            prev = current;
            current = current->next;
        }

        if (current && current->linie == i && current->coloana == j)
        {
            // elimină nodul curent
            if (!prev)
            {
                head = current->next;
                delete current;
            }
            else
            {
                prev->next = current->next;
                delete current;
            }
            return 0; 
        }
        return 0; 
    }
    else
    {
        //doi pointeri, unul pentru nodul curent și altul pentru nodul precedent
        Node* prev = nullptr;
        Node* current = head;

        //parcurge lista până când ajunge la final sau găsește poziția unde trebuie inserat sau modificat elementul
        while (current && (current->linie < i || (current->linie == i && current->coloana < j)))
        {
            prev = current;
            current = current->next;
        }

        //dacă nodul curent corespunde poziției (i, j) și trebuie modificat
        if (current && current->linie == i && current->coloana == j)
        {
            TElem oldValue = current->valoare;
            current->valoare = e;
            return oldValue;
        }
        //dacă trebuie adăugat un nod nou în listă pentru poziția (i, j)
        else
        {
            Node* newNode = new Node(i, j, e);
            //dacă nu există un nod precedent, adaugă noul nod la începutul liste
            if (!prev)
            {
                newNode->next = head;
                head = newNode;
            }
            //adaugă noul nod între nodul precedent și nodul curent
            else
            {
                newNode->next = current;
                prev->next = newNode;
            }
            return 0;
        }
    }
    
}
