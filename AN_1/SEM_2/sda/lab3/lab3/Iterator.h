#ifndef ITERATOR_H_
#define ITERATOR_H_

#include "DO.h"

class Iterator {
    friend class DO;
private:
    const DO& dict;
    int curent;

    Iterator(const DO& dictionar);

public:
    void prim();
    void urmator();
    bool valid() const;
    TElem element() const;
};

#endif
