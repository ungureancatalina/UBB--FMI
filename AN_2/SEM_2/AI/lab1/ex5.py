#Pentru un șir cu n elemente care conține valori din mulțimea {1, 2, ..., n - 1}
#astfel încât o singură valoare se repetă de două ori, să se identifice acea valoare care se repetă.
#De ex. în șirul [1,2,3,4,2] valoarea 2 apare de două ori.

#Complexitate Spatiu: O(n), Timp: O(n)
# Intrare: lista de numere intregi
# Iesire: valoarea care se repeta
def dublura(v):
    frecv={}
    for nr in v:
        if nr in frecv:
            return nr
        else:
            frecv[nr]=1
    return 0

#Complexitate Spatiu: O(1), Timp: O(n)
def gpt(lst):
    xor_all = 0
    n = len(lst) - 1
    for num in lst:
        xor_all ^= num
    for i in range(1, n + 1):
        xor_all ^= i
    return xor_all

def test():
    rez=dublura([1,2,3,4,2])
    assert rez==2
    rez=dublura([1,1])
    assert rez==1

test()

