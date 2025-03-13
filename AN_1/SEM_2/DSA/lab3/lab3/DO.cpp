#include "DO.h"
#include "Iterator.h"
#include <exception>

using namespace std;

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

// Constructorul pt DO
DO::DO(Relatie r) : rel(r) 
{
    capacitate = 10;
    elemente = new Nod[capacitate];
    head = -1;
    tail = -1;
    primLiber = 0;
    initSpatiuLiber();
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)

// Destructorul pt DO
DO::~DO() 
{
    delete[] elemente;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

// Inițializare spațiu liber în vectorul de noduri. 
void DO::initSpatiuLiber() 
{
    // Asociază fiecare nod liber cu următorul nod liber din listă.
    for (int i = 0; i < capacitate - 1; ++i) {
        elemente[i].next = i + 1;
    }
    elemente[capacitate - 1].next = -1;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1) amortizat, Complexitate Spatiu: O(1)

// Alocă un nod liber din vectorul de noduri pentru a fi utilizat.
int DO::aloca() 
{
    // Verifică dacă există noduri libere, și, în caz negativ, redimensionează vectorul de noduri folosind redimensioneaza().
    if (primLiber == -1) 
    {
        redimensioneaza();
    }
    int nou = primLiber;
    primLiber = elemente[primLiber].next;
    elemente[nou].next = -1;
    elemente[nou].prev = -1;
    return nou;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1) amortizat, Complexitate Spatiu: O(1)

// Dealocă un nod din vectorul de noduri, lăsându-l disponibil pentru reutilizare.
void DO::dealoca(int i) 
{
    // Adaugă nodul indicat de indicele i la lista de noduri libere.
    elemente[i].next = primLiber;
    primLiber = i;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(n), Complexitate Spatiu: O(n)

// Redimensionează vectorul de noduri, dublând capacitatea acestuia.
void DO::redimensioneaza() 
{
    // Copiază elementele existente în noul vector.
    Nod* elemente_noi = new Nod[capacitate * 2];
    for (int i = 0; i < capacitate; ++i) {
        elemente_noi[i] = elemente[i];
    }
    // Inițializează lista de noduri libere în noul vector.
    for (int i = capacitate; i < capacitate * 2 - 1; ++i) {
        elemente_noi[i].next = i + 1;
    }
    // Actualizează variabilele de control capacitate și primLiber.
    elemente_noi[capacitate * 2 - 1].next = -1;
    primLiber = capacitate;
    capacitate *= 2;
    delete[] elemente;
    elemente = elemente_noi;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

// Adaugă o pereche cheie-valoare în dictionar.
TValoare DO::adauga(TCheie c, TValoare v) 
{
    int curent = head;
    int anterior = -1;

    // Se parcurge lista dublu înlănțuită până la găsirea poziției unde trebuie adăugat elementul
    // Dacă cheia este deja în dictionar, se actualizează valoarea și se returnează valoarea veche
    while (curent != -1 && rel(elemente[curent].cheie, c)) {
        if (elemente[curent].cheie == c) {
            TValoare veche = elemente[curent].valoare;
            elemente[curent].valoare = v;
            return veche;
        }
        anterior = curent;
        curent = elemente[curent].next;
    }

    // Se alocă un nou nod din lista de noduri libere
    int nou = aloca();
    elemente[nou] = Nod(c, v);

    // Se actualizează legăturile dintre noduri pentru a introduce noul nod în listă
    if (anterior == -1) {
        // Dacă adăugarea se face la începutul listei
        elemente[nou].next = head;
        if (head != -1) {
            elemente[head].prev = nou;
        }
        head = nou;
    }
    else {
        // Altfel, adăugarea se face în interiorul listei
        elemente[nou].next = elemente[anterior].next;
        elemente[nou].prev = anterior;
        elemente[anterior].next = nou;
        if (elemente[nou].next != -1) 
        {
            elemente[elemente[nou].next].prev = nou;
        }
    }

    // Dacă noul nod este adăugat în coadă, se actualizează și coada listei
    if (elemente[nou].next == -1) {
        tail = nou;
    }

    return NULL_TVALOARE;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

// Căutarea unei chei în dictionar
TValoare DO::cauta(TCheie c) const
{
    int curent = head;

    // Se parcurge lista dublu înlănțuită pentru a găsi cheia specificată
    while (curent != -1) {
        if (rel(elemente[curent].cheie, c)) {
            // Utilizează relația pentru a verifica dacă cheia curentă trebuie căutată
            if (elemente[curent].cheie == c) {
                return elemente[curent].valoare;
            }
        }
        else {
            // Dacă cheia curentă este mai mare decât cheia căutată, iar lista este ordonată crescător,
            // atunci cheia căutată nu există în listă
            return NULL_TVALOARE;
        }
        curent = elemente[curent].next;
    }
    // Dacă cheia nu este găsită, se returnează valoarea implicită pentru o cheie inexistentă
    return NULL_TVALOARE;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)
TValoare DO::sterge(TCheie c)
{
    int curent = head;
    int anterior = -1;

    // Se parcurge lista dublu înlănțuită pentru a găsi nodul corespunzător cheii specificate
    while (curent != -1 && rel(elemente[curent].cheie, c)) {
        if (elemente[curent].cheie == c) {
            // Utilizează relația pentru a verifica dacă cheia curentă trebuie ștearsă
            // Dacă da, se efectuează ștergerea
            if (curent == head) {
                head = elemente[curent].next;
                if (head != -1) {
                    elemente[head].prev = -1;
                }
            }
            else {
                elemente[anterior].next = elemente[curent].next;
                if (elemente[curent].next != -1) {
                    elemente[elemente[curent].next].prev = anterior;
                }
            }
            // Se păstrează valoarea asociată cheii care va fi ștearsă
            TValoare valoareStearsa = elemente[curent].valoare;

            // Dacă nodul șters este și coada listei, se actualizează coada
            if (curent == tail) {
                tail = anterior;
            }

            // Se dealocă memoria alocată pentru nodul șters
            dealoca(curent);
            // Se returnează valoarea asociată cheii șterse
            return valoareStearsa;
        }
        anterior = curent;
        curent = elemente[curent].next;
    }

    // Dacă cheia nu este găsită, se returnează valoarea implicită pentru o cheie inexistentă
    return NULL_TVALOARE;
}

// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

// Returnează numărul de perechi cheie-valoare din dictionar.
int DO::dim() const {
    int dimensiune = 0;
    int curent = head;
    while (curent != -1) {
        dimensiune++;
        curent = elemente[curent].next;
    }
    return dimensiune;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)

//Verifică dacă dictionarul este gol.
bool DO::vid() const {
    return head == -1;
}

// Best Case = Average Case = Worst Case
// Complexitate Timp: O(1), Complexitate Spatiu: O(1)

//Creează și returnează un iterator pentru dictionar, inițializat la prima pereche cheie-valoare.
Iterator DO::iterator() const {
    return Iterator(*this);
}



// subalgoritm inlocuieste(d, k, valoareVeche, valoareNoua)
//      pentru fiecare element i din d
//          dacă d.el[i].cheie = k și d.el[i].valoare = valoareVeche
//               d.el[i].valoare = valoareNoua
//              returnează
//          sfârșit dacă
//      sfârșit pentru
// sfârșit subalgoritm


// Best Case: O(1), Average Case: O(n), Worst Case: O(n)
// Complexitate Timp: O(n), Complexitate Spatiu: O(1)

//Înlocuiește valoarea mapate
void DO::inlocuieste(TCheie k, TValoare valoareVeche, TValoare valoareNoua)
{
    int curent = head;
    // Se parcurge lista dublu înlănțuită pentru a găsi cheia specificată
    while (curent != -1) {
        if (rel(elemente[curent].cheie, k)) {
            // Utilizează relația pentru a verifica dacă cheia curentă trebuie căutată
            if (elemente[curent].cheie == k && elemente[curent].valoare == valoareVeche) {
                // Dacă valoarea asociată cheii este valoareaVeche, se înlocuiește cu valoareaNoua
                elemente[curent].valoare = valoareNoua;
                return;
            }
        }
        else {
            // Dacă cheia curentă este mai mare decât cheia căutată, iar lista este ordonată crescător,
            // atunci cheia căutată nu există în listă
            return;
        }
        curent = elemente[curent].next;
    }
}