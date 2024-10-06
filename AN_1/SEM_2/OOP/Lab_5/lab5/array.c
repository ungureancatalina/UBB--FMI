#pragma once
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include "array.h"

int get_Vector_Size(Vector* vec)
{
    return vec->size;
}

Telem* get_Vector_Array(Vector* vec)
{
    return vec->v;
}

Vector* create_vector(DestroyFct f)
{
    Vector* vec = (Vector*)malloc(sizeof(Vector));
    if (vec == NULL)
        return NULL;
    vec->size = 0;
    vec->cap = 1;
    vec->v = (Telem*)malloc(vec->cap * sizeof(Telem));
    vec->dfnc = f;
    return vec;
}

void destroy_vector(Vector* vec)
{
    for (int i = 0; i < get_Vector_Size(vec); i++)
        vec->dfnc(vec->v[i]);
    vec->size = 0;
    free(vec->v);
    free(vec);
}

Telem get_vector_element(Vector* vec, int poz)
{
    return vec->v[poz];
}

Telem set_vector_element(Vector* vec, int poz, Telem el)
{
    Telem schimbat = vec->v[poz];
    vec->v[poz] = el;
    return schimbat;
}

void add_vector(Vector* vec, Telem elem)
{
    if (vec->size >= vec->cap)
    {
        int new_cap = vec->cap * 2;
        Telem* aux = malloc(sizeof(Telem) * new_cap);
        for (int i = 0; i < vec->size; i++)
            aux[i] = vec->v[i];
        free(vec->v);
        vec->v = aux;
        vec->cap = new_cap;
    }
    vec->v[vec->size] = elem;
    vec->size++;
}

Telem remove_vector_element(Vector* vec, int poz)
{
    Telem elem = vec->v[poz];
    for (int i = poz; i < vec->size - 1; i++)
    {
        vec->v[i] = vec->v[i + 1];
    }
    vec->size--;
    return elem;
}

Vector* copyList(Vector* vec, CopyFct copyFct)
{
    Vector* rez = create_vector(vec->dfnc);
    for (int i = 0; i < get_Vector_Size(vec); i++)
    {
        Telem new_vec = get_vector_element(vec, i);
        add_vector(rez, copyFct(new_vec));
    }
    return rez;
}

void test_create_vector()
{
    Vector* v = create_vector(delete_Oferta);
    assert(v->size == 0);
    destroy_vector(v);
}

void test_add_vector()
{
    Vector* v = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    Oferta* of2 = create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
    add_vector(v, of1);
    add_vector(v, of2);
    assert(v->size == 2);
    Oferta* o = get_vector_element(v, 0);
    assert(strcmp(o->tip, "teren") == 0);
    assert(o->pret==350);
    destroy_vector(v);
}

void test_copyList() 
{
    Vector* v = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    Oferta* of2 = create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
    add_vector(v, of1);
    add_vector(v, of2);
    assert(v->size == 2);
    Vector* v2 = copyList(v, copyOferta);
    assert(v2->size == 2);
    Oferta* o = get_vector_element(v2, 0);
    assert(strcmp(o->tip, "teren") == 0);
    destroy_vector(v);
    destroy_vector(v2);
}

void test_get()
{
    Vector* v = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    add_vector(v, of1);
    //Oferta* of2 = get_Vector_Array(v);
    //assert(of1->pret== of2->pret);
}

void test_remove_vector_element() 
{
    Vector* v = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    Oferta* of2 = create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
    add_vector(v, of1);
    add_vector(v, of2);
    assert(v->size == 2);
    Oferta* o = remove_vector_element(v, 0);
    assert(strcmp(o->tip, "teren") == 0);
    assert(o->pret == 350);
    delete_Oferta(o);
    assert(v->size == 1);
    destroy_vector(v);
}

void testSet() {
    Vector* v = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    add_vector(v, of1);
    assert(v->size == 1);
    Oferta* of2 = set_vector_element(v, 0, create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 550));
    Oferta* o = get_vector_element(v, 0);
    assert(strcmp(o->tip, "apartament") == 0);
    assert(o->pret == 550);
    delete_Oferta(of2);
    destroy_vector(v);

}

void test_destroy_vector() 
{
    Vector* v1 = create_vector(delete_Oferta);
    Oferta* of1 = create_Oferta("teren", 52, "Sibiu,str.Luncii,nr.14", 350);
    Vector* v2 = create_vector(delete_Oferta);
    Oferta* of2 = create_Oferta("apartament", 25, "Cluj,str.Libertatii,nr.1", 550);
    add_vector(v1, of1);
    add_vector(v2, of2);
    assert(v1->size == 1);
    assert(v2->size == 1);
    Vector* undo = create_vector(destroy_vector);
    add_vector(undo, v1);
    assert(undo->size == 1);
    add_vector(undo, v2);
    assert(undo->size == 2);
    destroy_vector(undo);
}

void test_array()
{
    test_create_vector();
    test_destroy_vector();
    testSet();
    test_get();
    test_remove_vector_element();
    test_copyList();
    test_add_vector();
}