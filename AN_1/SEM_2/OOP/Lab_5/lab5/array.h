#pragma once
#include "domain.h"

typedef void* Telem;
typedef Telem(*CopyFct)(Telem);
typedef void (*DestroyFct)(Telem);

typedef struct {
    int size;
    int cap;
    Telem* v;
    DestroyFct dfnc;
}Vector;

Vector* create_vector(DestroyFct f);

void destroy_vector(Vector* vec);

Telem get_vector_element(Vector* vec, int poz);

int get_Vector_Size(Vector* vec);

Telem* get_Vector_Array(Vector* vec);

Telem set_vector_element(Vector* vec, int poz, Telem el);

void add_vector(Vector* vec, Telem elem);

Telem remove_vector_element(Vector* vec, int poz);

Vector* copyList(Vector* vec, CopyFct copyFct);

void test_array();
