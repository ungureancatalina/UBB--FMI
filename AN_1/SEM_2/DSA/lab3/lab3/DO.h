#ifndef DO_H_
#define DO_H_

#include <utility>

using namespace std;

typedef int TCheie;
typedef int TValoare;
typedef std::pair<TCheie, TValoare> TElem;
typedef bool(*Relatie)(TCheie, TCheie);
#define NULL_TVALOARE -1

class Iterator;

struct Nod {
    TCheie cheie;
    TValoare valoare;
    int next;
    int prev;

    // Constructorii structurii Nod
    Nod() : cheie(0), valoare(0), next(-1), prev(-1) {}
    Nod(TCheie c, TValoare v, int n = -1, int p = -1) : cheie(c), valoare(v), next(n), prev(p) {}
};

class DO {
    friend class Iterator;
private:
    Nod* elemente;
    int head;
    int tail;
    int primLiber;
    int capacitate;
    Relatie rel;

    void initSpatiuLiber();
    int aloca();
    void dealoca(int i);
    void redimensioneaza();

public:
    DO(Relatie r);
    ~DO();

    TValoare adauga(TCheie c, TValoare v);
    TValoare cauta(TCheie c) const;
    TValoare sterge(TCheie c);
    int dim() const;
    bool vid() const;
    Iterator iterator() const;
    void inlocuieste(TCheie k, TValoare valoareVeche, TValoare valoareNoua);
};

#endif
