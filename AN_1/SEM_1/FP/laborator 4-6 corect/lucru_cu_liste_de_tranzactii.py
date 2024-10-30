from lucru_cu_tranzactii import verificare_ziua_data,verificare_perioada_data,verificare_tip,verificare_suma_mai_mica,verificare_suma_mai_mare,verificare_ziua_mai_mica
from creare_add_undo import adaugare_lista_tranzactie_noua,add_tranzactie,creare_tranzactie,get_ziua,get_luna,get_anul,get_suma,get_tipul,status_curent_lista_undo
    
def stergere_tranzactii_ziua_data(lista_tranzactii: list,ziua_data: str,undo_list: list):
    """
    Cauta tranzactiei ce respecta conditia si o sterge 
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param ziua_data: ziua citita de la tastatura
    :type ziua_data: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: -; Sterge tranzactia 
    :rtype:
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    for tranzactie in lista_tranzactii:
        if verificare_ziua_data(tranzactie,ziua_data):
            lista_tranzactii.remove(tranzactie)
    
def stergere_tranzactii_perioada_data(lista_tranzactii: list,ziua_de_inceput:str,ziua_de_sfarsit:str,undo_list: list):
    """
    Cauta tranzactiei ce respecta conditia si o sterge 
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param ziua_de_inceput: ziua de inceput citita de la tastatura
    :type ziua_de_inceput: str
    :param ziua_de_sfarsit: ziua de sfarsit citita de la tastatura
    :type ziua_de_sfarsit: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: -; Sterge tranzactia 
    :rtype:
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    for tranzactie in lista_tranzactii:
        if verificare_perioada_data(tranzactie,ziua_de_inceput,ziua_de_sfarsit):
            lista_tranzactii.remove(tranzactie) 
            
def stergere_tranzactii_tip_dat(lista_tranzactii: list,tip_dat: str,undo_list: list):
    """
    Cauta tranzactiei ce respecta conditia si o sterge 
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: -; Sterge tranzactia 
    :rtype:
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    for tranzactie in lista_tranzactii:
        if verificare_tip(tranzactie,tip_dat):
            lista_tranzactii.remove(tranzactie)
            
def eliminare_tranzactii_tip_dat(lista_tranzactii: list,tip_dat: str,undo_list: list):
    """
    Cauta tranzactiei ce respecta conditia si o elimina 
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista noua dupa eliminare 
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    for tranzactie in lista_tranzactii:
        if verificare_tip(tranzactie,tip_dat):
            lista_tranzactii.remove(tranzactie)      
            
def eliminare_tranzactii_suma_data_tip_dat(lista_tranzactii: list,tip_dat: str,suma_data: float,undo_list: list):
    """
    Cauta tranzactiei ce respecta conditia si o elimina 
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :param suma_data: suma citita de la tastatura
    :type suma_data: float
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista noua dupa eliminare 
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    for tranzactie in lista_tranzactii:
        if verificare_suma_mai_mica(tranzactie,suma_data) and verificare_tip(tranzactie,tip_dat):
            lista_tranzactii.remove(tranzactie)

def afisare_tranzactii_suma_data(lista_tranzactii: list,suma_data: float,undo_list: list):
    """
    Afiseaza tranzactiile din lista care au suma mai mare decat suma data
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param suma_data: suma citita de la tastatura
    :type suma_data: float
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista de tranzactii ce verifica conditia
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    lista_noua=[]
    for tranzactie in lista_tranzactii:
        if verificare_suma_mai_mare(tranzactie,suma_data):
            add_tranzactie(lista_noua,tranzactie)
    return lista_noua

def afisare_tranzactii_suma_si_ziua_data(lista_tranzactii: list,suma_data:float,ziua_data:str,undo_list: list):
    """
    Afiseaza tranzactiile din lista care au suma mai mare decat suma data si au avut loc intr-o zi mai mica decat o zi data
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param suma_data: suma citita de la tastatura
    :type suma_data: float
    :param ziua_data: ziua citita de la tastatura
    :type ziua_data: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista de tranzactii ce verifica conditia
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    lista_noua=[]
    for tranzactie in lista_tranzactii:
        if verificare_suma_mai_mare(tranzactie,suma_data) and verificare_ziua_mai_mica(tranzactie,ziua_data):
            add_tranzactie(lista_noua,tranzactie)
    return lista_noua
    
def afisare_tranzactii_tip_dat(lista_tranzactii: list,tip_dat:str,undo_list: list):
    """
    Afiseaza tranzactiile din lista care au tipul acelasi cu cel citit
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista de tranzactii ce verifica conditia
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    lista_noua=[]
    for tranzactie in lista_tranzactii:
        if verificare_tip(tranzactie,tip_dat):
             add_tranzactie(lista_noua,tranzactie)
    return lista_noua

def suma_tranzactii_tip_dat(lista_tranzactii: list,tip_dat: str):
    """
    Afiseaza tranzactiile din lista care au tipul acelasi cu cel citit
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :return: suma tranzactiilor ce verifica conditia
    :rtype: float
    """
    suma_totala=0
    for tranzactie in lista_tranzactii:
        if verificare_tip(tranzactie,tip_dat):
            suma_totala+=get_suma(tranzactie)
    return suma_totala

def soldul_contului_ziua_data(lista_tranzactii: list,ziua_data: str):
    """
    Cauta tranzactia ce respecta conditia si afiseaza soldul
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param ziua_data: ziua citita de la tastatura
    :type ziua_data: str
    :return: soldul tranzactiilor ce verifica conditia
    :rtype: float
    """
    sold_curent=0
    for tranzactie in lista_tranzactii:
        if verificare_ziua_data(tranzactie,ziua_data):
            sold_curent+=get_suma(tranzactie)
    return sold_curent
    
def afisare_tranzactii_tip_dat_ordonat_dupa_suma(lista_tranzactii: list,tip_dat: str,undo_list: list):
    """
    Afiseaza tranzactiile ce respecta conditia in ordine dupa suma
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :param undo_list: lista de undo
    :type undo_list: list
    :return: lista de tranzactii ce verifica conditia
    :rtype: list
    """
    status_curent_lista_undo(lista_tranzactii,undo_list)
    lista_noua=[]
    lista_ordonata=[]
    for tranzactie in lista_tranzactii:
        if verificare_tip(tranzactie,tip_dat):
            add_tranzactie(lista_noua,tranzactie)
    lista_ordonata=sorted(lista_noua, key = lambda tranzactie: tranzactie['suma'])
    return lista_ordonata
    
def testare_stergere_tranzactii_ziua_data():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    ziua_data='10/8/2023'
    stergere_tranzactii_ziua_data(tranzactie_lista,ziua_data,undo_list)
    assert(len(tranzactie_lista)==1)
    ziua_data='10/8/2022'
    assert(len(tranzactie_lista)==1)
testare_stergere_tranzactii_ziua_data()

def testare_stergere_tranzactii_perioada_data():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    ziua_de_inceput='1/8/2023'
    ziua_de_sfarsit='31/8/2023'
    stergere_tranzactii_perioada_data(tranzactie_lista,ziua_de_inceput,ziua_de_sfarsit,undo_list)
    assert(len(tranzactie_lista)==1)
testare_stergere_tranzactii_perioada_data()

def testare_stergere_tranzactii_tip_dat():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    stergere_tranzactii_tip_dat(tranzactie_lista,tip_dat,undo_list)
    assert(len(tranzactie_lista)==1)
    tip_dat='intrare'
    assert(len(tranzactie_lista)==1)
testare_stergere_tranzactii_tip_dat()

def testare_eliminare_tranzactii_tip_dat():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    eliminare_tranzactii_tip_dat(tranzactie_lista,tip_dat,undo_list)
    assert(len(tranzactie_lista)==1)
    tip_dat='intrare'
    eliminare_tranzactii_tip_dat(tranzactie_lista,tip_dat,undo_list)
    assert(len(tranzactie_lista)==1)
testare_eliminare_tranzactii_tip_dat()

def testare_eliminare_tranzactii_suma_data_tip_dat():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    suma_data=float(200)
    eliminare_tranzactii_suma_data_tip_dat(tranzactie_lista,tip_dat,suma_data,undo_list)
    assert(len(tranzactie_lista)==1)
    tip_dat='intrare'
    suma_data=float(100)
    eliminare_tranzactii_suma_data_tip_dat(tranzactie_lista,tip_dat,suma_data,undo_list)
    assert(len(tranzactie_lista)==1)
testare_eliminare_tranzactii_suma_data_tip_dat()

def testare_afisare_tranzactii_suma_data():
    tranzactie_lista = []
    undo_list=[]
    lista_noua=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    suma_data=float(70)
    lista_noua=afisare_tranzactii_suma_data(tranzactie_lista,suma_data,undo_list)
    assert(len(lista_noua)==1)
    suma_data=float(120)
    lista_noua=afisare_tranzactii_suma_data(tranzactie_lista,suma_data,undo_list)
    assert(len(lista_noua)==0)
testare_afisare_tranzactii_suma_data()

def testare_afisare_tranzactii_suma_si_ziua_data():
    tranzactie_lista = []
    undo_list=[]
    lista_noua=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    suma_data=float(10)
    ziua_data='1/8/2023'
    lista_noua=afisare_tranzactii_suma_si_ziua_data(tranzactie_lista,suma_data,ziua_data,undo_list)
    assert(len(lista_noua)==1)
    suma_data=float(120)
    ziua_data='1/8/2023'
    lista_noua=afisare_tranzactii_suma_si_ziua_data(tranzactie_lista,suma_data,ziua_data,undo_list)
    assert(len(lista_noua)==0)
testare_afisare_tranzactii_suma_si_ziua_data()

def testare_afisare_tranzactii_tip_dat():
    tranzactie_lista = []
    undo_list=[]
    lista_noua=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    lista_noua=afisare_tranzactii_tip_dat(tranzactie_lista,tip_dat,undo_list)
    assert(len(lista_noua)==1)
testare_afisare_tranzactii_tip_dat()

def testare_suma_tranzactii_tip_dat():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    assert(suma_tranzactii_tip_dat(tranzactie_lista,tip_dat)==float(100))
testare_suma_tranzactii_tip_dat()

def testare_soldul_contului_ziua_data():
    tranzactie_lista = []
    undo_list=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    ziua_data='10/8/2023'
    assert(soldul_contului_ziua_data(tranzactie_lista,ziua_data)==float(100))
    ziua_data='1/8/2023'
    assert(soldul_contului_ziua_data(tranzactie_lista,ziua_data)==float(0))
testare_soldul_contului_ziua_data()

def testare_afisare_tranzactii_tip_dat_ordonat_dupa_suma():
    tranzactie_lista = []
    undo_list=[]
    lista_noua=[]
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='1/5/2023,50,intrare'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tip_dat='intrare'
    lista_noua=afisare_tranzactii_tip_dat_ordonat_dupa_suma(tranzactie_lista,tip_dat,undo_list)
    assert(lista_noua==[{'data_zi': 1, 'data_luna': 5, 'data_an': 2023, 'suma': 50.0, 'tip': 'intrare'},{'data_zi': 10, 'data_luna': 8, 'data_an': 2023, 'suma': 100.0, 'tip': 'intrare'}])
testare_afisare_tranzactii_tip_dat_ordonat_dupa_suma()