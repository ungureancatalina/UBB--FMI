#include "Iterator.h"
#include "DO.h"
#include <exception>
#include <stdexcept>
using namespace std;

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
Iterator::Iterator(const DO& d) : dict(d) 
{
    this->curent = 0;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
void Iterator::prim() 
{
    curent = 0;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */

void Iterator::urmator() 
{
    if (valid()) 
        curent++;
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
bool Iterator::valid() const 
{
    return curent < dict.dim();
}

/*
  Best Case = Average Case = Worst Case
  Complexitate Timp: O(1)
  Complexitate Spatiu: O(1)
 */
TElem Iterator::element() const 
{
    if (valid())
        return dict.el[curent];
}


