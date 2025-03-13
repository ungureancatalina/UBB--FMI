#Considerându-se o matrice cu n x m elemente întregi și o listă cu perechi formate din coordonatele a 2 căsuțe din matrice
#((p,q) și (r,s)), să se calculeze suma elementelor din sub-matricile identificate de fieare pereche.
#De ex, pt matricea
# [[0, 2, 5, 4, 1],
# [4, 8, 2, 3, 7],
# [6, 3, 4, 6, 2],
# [7, 3, 1, 8, 3],
# [1, 5, 7, 9, 4]]
#și lista de perechi ((1, 1) și (3, 3)), ((2, 2) și (4, 4)), suma elementelor din prima sub-matrice este 38,
#iar suma elementelor din a 2-a sub-matrice este 44.

#Complexitate Spatiu: O(nr), Timp: O(nr*dim) nr=len(l), dim=dimensiunea submatricei
# Intrare: matrice de dimensiune n x m, lista de perechi de coordonate
# Iesire: lista cu sumele fiecarui sub-matrice
def suma_sub_matrice(m,l):
    lista=[]
    for k in l:
        sum=0
        for i in range(k[0][0],k[1][0]+1):
            for j in range(k[0][1],k[1][1]+1):
                sum=sum+m[i][j]
        lista.append(sum)
    return lista


def preprocesare(matrice):
    n, m = len(matrice), len(matrice[0])
    prefix = [[0] * (m + 1) for _ in range(n + 1)]

    for i in range(n):
        for j in range(m):
            prefix[i + 1][j + 1] = matrice[i][j] + prefix[i][j + 1] + prefix[i + 1][j] - prefix[i][j]

    return prefix


#Complexitate Spatiu: O(n*m), Timp: O(n∗m)+O(q)
def gpt(prefix, p, q, r, s):
    return prefix[r+1][s+1] - prefix[p][s+1] - prefix[r+1][q] + prefix[p][q]


def test():
    m= [[0, 2, 5, 4, 1],[4, 8, 2, 3, 7],[6, 3, 4, 6, 2],[7, 3, 1, 8, 3],[1, 5, 7, 9, 4]]
    l=[((1,1),(3,3)),((2, 2), (4, 4))]
    rez=suma_sub_matrice(m,l)
    assert rez==[38,44]

test()