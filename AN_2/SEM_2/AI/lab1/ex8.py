#Să se genereze toate numerele (în reprezentare binară) cuprinse între 1 și n.
#De ex. dacă n = 4, numerele sunt: 1, 10, 11, 100.

#Complexitate Spatiu: O(n), Timp: O(n*log n)
# Intrare: un intreg n
# Iesire: o lista cu reprezentarile binare ale numerelor de la 1 la n
def binar(n):
    rez=[]
    for i in range(1,n+1):
        rez.append(int(bin(i)[2:]))
    return rez

#Complexitate Spatiu: O(1), Timp: O(n*log n)
def gpt(n):
    return [int(format(i, 'b')) for i in range(1, n + 1)]

def test():
    rez=binar(4)
    assert rez==[1, 10, 11, 100]
    rez=binar(7)
    assert rez==[1, 10, 11, 100, 101, 110, 111]

test()

