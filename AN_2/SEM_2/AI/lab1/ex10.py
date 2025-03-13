#Considerându-se o matrice cu n x m elemente binare (0 sau 1) sortate crescător pe linii,
#să se identifice indexul liniei careconține cele mai multe elemente de 1.
#De ex. în matricea
#[[0,0,0,1,1],
#[0,1,1,1,1],
#[0,0,1,1,1]]
#a doua linie conține cele mai multe elemente 1.

# Complexitate Spatiu: O(1), Timp: O(n+m)
# Intrare: matrice binara de dimensiune n x m, sortata crescator pe linii
# Iesire: indexul liniei care contine cele mai multe elemente 1
def elemente_1(matr):
    n=len(matr)
    m=len(matr[0])
    maxim=0
    poz=0
    j = m - 1
    for i in range(0,n):
        while j>=0 and matr[i][j]!=0:
            j-=1
        if m-j-1>maxim:
            maxim=m-j-1
            poz=i
    return poz+1

# Complexitate Spatiu: O(1), Timp: O(n*log m)
def gpt(matrix):
    rows = len(matrix)
    cols = len(matrix[0])
    max_ones = 0
    row_index = -1
    for i in range(rows):
        left, right = 0, cols - 1
        while left <= right:
            mid = (left + right) // 2
            if matrix[i][mid] == 1:
                right = mid - 1
            else:
                left = mid + 1
        num_ones = cols - left
        if num_ones > max_ones:
            max_ones = num_ones
            row_index = i
    return row_index


def test():
    m=[[0,0,0,1,1],[0,1,1,1,1],[0,0,1,1,1]]
    rez=elemente_1(m)
    assert rez==2
    m = [[0, 0, 0, 1, 1], [0, 0, 0, 0, 1], [0, 0, 1, 1, 1]]
    rez = elemente_1(m)
    assert rez == 3

test()

