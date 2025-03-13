#Pentru un șir cu n numere întregi care conține și duplicate, să se determine elementul
#majoritar (care apare de mai mult de n / 2 ori). De ex. 2 este elementul
#majoritar în șirul [2,8,7,2,2,5,2,3,1,2,2].

#Complexitate Spatiu: O(n), Timp: O(n)
# Intrare: lista de numere intregi
# Iesire: elementul majoritar
def majoritar(v,n):
    frecv = {}
    maj=v[0]
    for nr in v:
        if nr in frecv:
            frecv[nr] += 1
        else:
            frecv[nr] = 1
        if frecv[nr] > n/2 and nr>maj:
            maj=nr
    return maj

#Complexitate Spatiu: O(1), Timp: O(n)
def gpt(lst):
    candidat, count = None, 0
    for num in lst:
        if count == 0:
            candidat = num
        count += (1 if num == candidat else -1)
    return candidat if lst.count(candidat) > len(lst) // 2 else None


def test():
    rez=majoritar([2,8,7,2,2,5,2,3,1,2,2],11)
    assert rez==2
    rez=majoritar([2,8,7,3,0,8,8,8,8],9)
    assert rez==8

test()