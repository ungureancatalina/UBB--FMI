#Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n).
# De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.

import heapq

#Complexitate Spatiu: O(1), Timp: O(n*log n)
# Intrare: lista de numere intregi, k (intreg)
# Iesire: al k-lea cel mai mare element
def k_elem(v,k):
    v.sort()
    return v[len(v)-k]

#Complexitate Spatiu: O(k), Timp: O(n*log k)
def gpt(lst, k):
    heap = lst[:k]
    heapq.heapify(heap)
    for num in lst[k:]:
        if num > heap[0]:
            heapq.heappushpop(heap, num)
    return heap[0]

def test():
    rez=k_elem([7,4,6,3,9,1],2)
    assert rez==7
    rez=k_elem([6,7,3,4,0,2], 4)
    assert rez == 3

test()