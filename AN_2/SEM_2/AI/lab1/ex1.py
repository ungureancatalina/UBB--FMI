#Să se determine ultimul (din punct de vedere alfabetic) cuvânt care poate apărea într-un text care
#conține mai multe cuvinte separate prin ” ” (spațiu).
#De ex. ultimul (dpdv alfabetic) cuvânt din ”Ana are mere rosii si galbene” este cuvântul "si".
from statistics import multimode

#Complexitate Spatiu: O(n), Timp:O(n)
# Intrare: string text ce contine cuvinte separate prin spatiu
# Iesire: string ce are ultimul cuv in ordine alfabetica
def ultimul_cuvant(text):
    elemente=text.split()
    ultimul=elemente[0].lower()
    for el in elemente:
        el=el.lower()
        if el>ultimul:
            ultimul=el
    return ultimul

#Complexitate Spatiu: O(n), Timp:O(n)
def gpt(text):
    return max(text.split(), key=str.lower) if text.strip() else None


def test():
    rez=ultimul_cuvant("Ana are mere rosii si galbene")
    assert rez=="si"
    rez = ultimul_cuvant("Zebra merge la mare da e in zadarnic")
    assert rez == "zebra"

test()


