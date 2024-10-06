#include "TestScurt.h"
#include <assert.h>
#include "Matrice.h"
#include <iostream>

using namespace std;
#define NULL_TELEMENT 0

void testAll() { 
	Matrice m(4, 4);
	assert(m.nrLinii() == 4);
	assert(m.nrColoane() == 4);
	m.modifica(1, 1, 5);
	assert(m.element(1, 1) == 5);
	m.modifica(1, 1, 6);
	assert(m.element(1, 2) == NULL_TELEMENT);
}
