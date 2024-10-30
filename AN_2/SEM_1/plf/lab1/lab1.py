
# 1. a. Să se substituie toate aparițiile unui element dintr-o listă cu o altă
#  listă.
#  b. Să se determine elementul de pe poziția a n-a unei liste.

def substituire(lista,element,lista_inlocuire):
    if len(lista)==0:
        return lista
    else:
        if lista[0]==element:
            return lista_inlocuire+ substituire(lista[1:], element, lista_inlocuire) 
        else:
            return [lista[0]] + substituire(lista[1:], element, lista_inlocuire) 
    
def gasire(lista, n,index):
    if index==n:
        return lista[0]
    else:
        if index>n:
            return -1
        else:
            return gasire(lista[1:],n,index+1)
        
lista=[1,3,4,3,7,5,3]
element=3
lista_inlocuitoare=[0,9]
index=1
n=4
lista2=[]

lista2=substituire(lista,element,lista_inlocuitoare)
nr=gasire(lista,n,index)
print(lista)
print(lista2)
print(nr)