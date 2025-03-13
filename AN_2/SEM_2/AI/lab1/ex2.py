#Să se determine distanța Euclideană între două locații identificate
#prin perechi de numere. De ex. distanța între (1,5) și (4,1) este 5.0
import math

#Complexitate Spatiu: O(1), Timp:O(1)
# Intrare: doi vectori de numere reale, pereche1 si pereche2
# Iesire: un numar real care reprezinta distanta Euclidiana dintre cele doua puncte
def distanta(pereche1,pereche2):
    difx=pereche2[0]-pereche1[0]
    dify=pereche2[1]-pereche1[1]
    dist=math.sqrt(difx*difx+dify*dify)
    return dist

#Complexitate Spatiu: O(1), Timp:O(1)
def gpt(punct1, punct2):
    return math.hypot(punct2[0] - punct1[0], punct2[1] - punct1[1])

def test():
    rez=distanta((1,5),(4,1))
    assert rez==5.0
    rez = distanta((2,0), (2,3))
    assert rez == 3.0

test()