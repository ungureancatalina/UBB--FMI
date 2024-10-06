from utils.sortare import *

def test_insertion_sort():
    lista=['ana','maria','bianca','sonia','paula']
    insertion_sort(lista)
    lista2=['ana','bianca','maria','paula','sonia']
    assert(lista[0]==lista2[0])
    assert(lista[1]==lista2[1])
    assert(lista[2]==lista2[2])
    assert(lista[3]==lista2[3])
    assert(lista[4]==lista2[4])
    
def test_comb_sort():
    lista=[10,2,15,4,9]
    comb_sort(lista)
    lista2=[2,4,9,10,15]
    assert(lista[0]==lista2[0])
    assert(lista[1]==lista2[1])
    assert(lista[2]==lista2[2])
    assert(lista[3]==lista2[3])
    assert(lista[4]==lista2[4])   
    
test_insertion_sort()
test_comb_sort()