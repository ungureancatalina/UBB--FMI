#Să se determine cuvintele unui text care apar exact o singură dată în acel text.
#De ex. cuvintele care apar o singură dată în ”ana are ana are mere
#rosii ana" sunt: 'mere' și 'rosii'.

from collections import Counter


#Complexitate Spatiu: O(n), Timp: O(n)
# Intrare: string text ce contine cuvinte separate prin spatiu
# Iesire: un string cu cuvintele care apar o singura data
def apar_o_data(text):
    frecv={}
    textfinal=""
    elemente=text.split()
    for el in elemente:
        el=el.lower()
        if el in frecv:
            frecv[el]+=1
        else:
            frecv[el]=1
    for cuv in frecv:
        if frecv[cuv]==1:
            textfinal=textfinal+cuv+" "
    textfinal=textfinal[:-1].strip()
    return textfinal

#Complexitate Spatiu: O(n), Timp: O(n)
def gpt(text):
    counter = Counter(text.lower().split())
    return [word for word, count in counter.items() if count == 1]

def test():
    rez=apar_o_data("ana are ana are mere rosii ana")
    assert rez=="mere rosii"
    rez = apar_o_data("ana are ana are ana")
    assert rez == ""

test()