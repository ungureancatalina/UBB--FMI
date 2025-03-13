#Să se determine produsul scalar a doi vectori rari care conțin numere reale.
# Un vector este rar atunci când conține multe elemente nule. Vectorii pot avea oricâte dimensiuni.
# De ex. produsul scalar a 2 vectori unisimensionali [1,0,2,0,3] și [1,2,0,3,1] este 4.

#Complexitate Spatiu: O(1), Timp:O(n)
# Intrare: doi vectori de numere reale
# Iesire: un numar real care reprezinta produsul scalar
def produs_scalar(v1, v2):
    rez=0
    for el1,el2 in zip(v1,v2):
        rez=rez+el1*el2
    return rez

#Complexitate Spatiu: O(k), Timp:O(k), unde k<=n
def gpt(v1, v2):
    d1 = {i: v for i, v in enumerate(v1) if v}
    d2 = {i: v for i, v in enumerate(v2) if v}
    return sum(d1[i] * d2[i] for i in d1 if i in d2)


def test():
    rez=produs_scalar([1,3,4,0,2],[2,0,0,4,6])
    assert rez==14
    rez=produs_scalar([1,0],[2,0,0,4,6])
    assert rez==2
test()