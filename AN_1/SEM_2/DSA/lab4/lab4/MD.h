#pragma once
#include <vector>
#include <utility>

using namespace std;

typedef int TCheie;
typedef int TValoare;
typedef std::pair<TCheie, TValoare> TElem;

class Nod {
public:
    TCheie cheie;
    TValoare valoare;
    Nod* next;

    Nod(TCheie c, TValoare v) : cheie(c), valoare(v), next(nullptr) {}
};

class IteratorMD;


class MD {
    friend class IteratorMD;

private:
    int m;  // capacitatea tabelei de dispersie
    int n;  // numãrul total de elemente din dic?ionar
    Nod** tabela_dispersie;  // tabela de dispersie

    int dispersie(TCheie c) const;

public:
    // constructorul implicit al MultiDictionarului
    MD();

    // adauga o pereche (cheie, valoare) in MD    
    void adauga(TCheie c, TValoare v);

    // cauta o cheie si returneaza vectorul de valori asociate
    vector<TValoare> cauta(TCheie c) const;

    // sterge o cheie si o valoare 
    // returneaza adevarat daca s-a gasit cheia si valoarea de sters
    bool sterge(TCheie c, TValoare v);

    // returneaza numarul de perechi (cheie, valoare) din MD 
    int dim() const;

    // verifica daca MultiDictionarul e vid 
    bool vid() const;

    // se returneaza iterator pe MD
    IteratorMD iterator() const;

    // returneaza un vector cu toate cheile din MD
    vector<TCheie> multimeaCheilor() const;

    // destructorul MultiDictionarului    
    ~MD();
};
