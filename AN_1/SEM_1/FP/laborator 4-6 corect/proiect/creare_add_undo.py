def validare_date_introduse(tranzactie: dict):
    """
    Verificam o tranzactie
    :param tranzactie: tranzactie de validat
    :type tranzactie: dict
    :return: corectitudinea datelor introduse
    :rtype: int
    """
    errors = []
    if get_ziua(tranzactie)>31 or get_ziua(tranzactie)<0:
        errors.append("Ziua poate fi doar intre 1-31.")
    if get_luna(tranzactie)>12 or get_luna(tranzactie)<0:
        errors.append("Luna poate fi doar intre 1-12.")
    if get_anul(tranzactie)>2023 or get_anul(tranzactie)<=0:
        errors.append("Anul poate fi doar intre 0-2023.")
    if get_tipul(tranzactie)!='intrare' and get_tipul(tranzactie)!='iesire':
        errors.append("Tipul poate fi doar intrare sau iesire.")
    if len(errors) > 0:
        error_string = '\n'.join(errors)
        raise ValueError(error_string)

def creare_tranzactie(tranzactie_detalii: str) -> dict:
    """
    Creeaza o tranzactie
    :param tranzactie_detalii: string-ul care reprezinta tranzactia
    :type tranzactie_detalii: str
    :return: tranzactia creata
    :rtype: dict
    """
    ziua, suma, tip = tranzactie_detalii.split(',')
    ziua = ziua.strip()
    suma = suma.strip()
    tip = tip.strip()
    tip.lower()
    zi, luna, an = ziua.split('/')
    zi=int(zi)
    an=int(an)
    luna=int(luna)
    suma=float(suma)
    tranzactie_dict = {'data_zi': zi, 'data_luna': luna, 'data_an': an, 'suma': suma, 'tip': tip}
    return tranzactie_dict

def adaugare_lista_tranzactie_noua(lista_tranzactii: list,tranzactie_detalii:str,undo_list: list):
    """
    Citeste, creeaza si adauga o tranzactie in lista de tranzactii
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tranzactie_detalii: string-ul care reprezinta tranzactia
    :type tranzactie_detalii: str
    :return: -; adauga tranzactia in lista
    :rtype:
    """
    tranzactie = creare_tranzactie(tranzactie_detalii)
    try:
        validare_date_introduse(tranzactie)
        status_curent_lista_undo(lista_tranzactii,undo_list)
        add_tranzactie(lista_tranzactii, tranzactie) 
    except ValueError as exc:
        print("EROARE:",str(exc))    
        
def add_tranzactie(lista_tranzactii: list, tranzactie: dict) -> None:
    """
    Adauga o tranzactie la finalul listei
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tranzactie: tranzactie de adaugat
    :type tranzactie: dict
    :return: -; modifica lista prin adaugarea la sfarsit a tranzactiei
    :rtype:
    """
    lista_tranzactii.append(tranzactie)  
    
def schimbare_tranzactie(lista_tranzactii: list, tranzactie_veche: dict, tranzactie_noua: dict):
    """
    Actualizeaza tranzactia veche cu cea noua
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tranzactie_veche: tranzactie veche
    :type tranzactie_veche: dict
    :param tranzactie_noua: tranzactie noua
    :type tranzactie_noua: dict
    :return: -; modifica lista prin actualizarea tranzactiei
    :rtype:
    """
    for tranzactie in lista_tranzactii:
        if tranzactie==tranzactie_veche:
            tranzactie['data_zi']=get_ziua(tranzactie_noua)
            tranzactie['data_luna']=get_luna(tranzactie_noua)
            tranzactie['data_an']=get_anul(tranzactie_noua)
            tranzactie['suma']=get_suma(tranzactie_noua)
            tranzactie['tip']=get_tipul(tranzactie_noua)   
    
def actualizare_tranzactie_data(lista_tranzactii: list,tranzactie_detalii_vechi:str,tranzactie_detalii_noi:str):
    """
    Citeste si actualizeaza o tranzactie din lista de tranzactii
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param tranzactie_detalii_vechi: string-ul care reprezinta tranzactia veche
    :type tranzactie_detalii_vechi: str
    :param tranzactie_detalii_noi: string-ul care reprezinta tranzactia noua
    :type tranzactie_detalii_noi: str
    :return: -; actualizeaza tranzactia din lista
    :rtype:
    """
    tranzactie_veche = creare_tranzactie(tranzactie_detalii_vechi)
    tranzactie_noua = creare_tranzactie(tranzactie_detalii_noi)
    try:
        validare_date_introduse(tranzactie_noua)
        schimbare_tranzactie(lista_tranzactii,tranzactie_veche,tranzactie_noua)
    except ValueError as exc:
        print("EROARE:",str(exc))    
    
def copiere_lista(lista_tranzactii: list):
    """
    Face o copie "deep" a listei date de tranzactii
    :param lista_tranzactii: lista de dictionare
    :type lista_tranzactii: list
    :return: copia listei de tranzactii
    """
    lista_copiata = []
    for tranzactie in lista_tranzactii:
        tranzactie_noua = {}
        for cheie, valoare in tranzactie.items():
            tranzactie_noua[cheie] = valoare
        lista_copiata.append(tranzactie_noua)
    return lista_copiata    
 
def status_curent_lista_undo(lista_tranzactii: list,undo_list: list):
    """
    Adauga statusul curent al listei in lista de undo
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :param undo_list: lista de undo
    :type undo_list: list
    :return: -; lista de undo se modifica prin adaugarea starii curente a listei la sfarsit
    """
    undo_list.append(copiere_lista(lista_tranzactii)) 
    
def undo(lista_tranzactii: list,undo_list: list):
    """
    Anuleaza ultima operatie
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :return: lista actualizata in urma modificarii undo
    :raises ValueError daca nu se mai poate face undo
    """
    if len(undo_list)>0:
        lista_anterioara = undo_list.pop()
        lista_tranzactii=copiere_lista(lista_anterioara)
        return lista_tranzactii
    else:
        raise ValueError("Nu se mai poate face undo.")
    
def undo_actiune(lista_tranzactii: list,undo_list: list):
    """
    Verifica daca se poate face undo
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :return: lista dupa undo
    """
    try:
        lista_de_undo=undo(lista_tranzactii,undo_list)
        return lista_de_undo
    except ValueError as exc:
        print("EROARE:",str(exc))   

def get_ziua(tranzactie):        
    return tranzactie['data_zi']

def get_luna(tranzactie):
    return tranzactie['data_luna']

def get_anul(tranzactie):
    return tranzactie['data_an']

def get_suma(tranzactie):
    return tranzactie['suma']

def get_tipul(tranzactie):
    return tranzactie['tip']    
    
def testare_creare_tranzactie():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(type(tranzactie_curenta)==dict)
    assert(get_tipul(tranzactie_curenta)=="intrare")
    assert(get_ziua(tranzactie_curenta)==10)
    assert(get_luna(tranzactie_curenta)==8)
    assert(get_anul(tranzactie_curenta)==2023)
    assert(get_suma(tranzactie_curenta)==100)
testare_creare_tranzactie()        
    
def testare_add_tranzactie():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    tranzactie_lista = []
    add_tranzactie(tranzactie_lista, tranzactie_curenta)
    assert(len(tranzactie_lista)==1)
testare_add_tranzactie()    
    
def testare_schimbare_tranzactie():
    tranzactie_veche_detalii='10/8/2023,100,intrare'
    tranzactie_veche=creare_tranzactie(tranzactie_veche_detalii)
    tranzactie_lista = []
    add_tranzactie(tranzactie_lista,tranzactie_veche)
    tranzactie_noua_detalii='1/8/2020,10,intrare'
    tranzactie_noua=creare_tranzactie(tranzactie_noua_detalii)
    schimbare_tranzactie(tranzactie_lista,tranzactie_veche,tranzactie_noua)
    assert(tranzactie_veche=={'data_zi': 1, 'data_luna': 8, 'data_an': 2020, 'suma': 10.0, 'tip': 'intrare'})
testare_schimbare_tranzactie()        
    
def testare_actualizare_tranzactie_data():
    tranzactie_veche_detalii='10/8/2023,100,intrare'
    tranzactie_noua_detalii='1/8/2020,10,intrare'
    tranzactie_lista = []
    undo_list=[]
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_veche_detalii,undo_list)
    actualizare_tranzactie_data(tranzactie_lista,tranzactie_veche_detalii,tranzactie_noua_detalii)
    assert(tranzactie_lista==[{'data_zi': 1, 'data_luna': 8, 'data_an': 2020, 'suma': 10.0, 'tip': 'intrare'}])
testare_actualizare_tranzactie_data()    
    
def testare_copiere_lista():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    tranzactie_lista = []
    copie_tranzactie = []
    add_tranzactie(tranzactie_lista, tranzactie_curenta)
    copie_tranzactie=copiere_lista(tranzactie_lista)
    assert(copie_tranzactie==tranzactie_lista)
testare_copiere_lista()

def test_undo_actiune():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_lista = []
    undo_list=[]
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    assert (len(tranzactie_lista) == 1)
    lista_de_undo=undo_actiune(tranzactie_lista,undo_list)
    assert (len(tranzactie_lista) == 0)
    tranzactie_noua_detalii='1/9/2021,100,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    tranzactie_noua_detalii='7/3/2022,50,iesire'
    adaugare_lista_tranzactie_noua(tranzactie_lista,tranzactie_noua_detalii,undo_list)
    assert (len(tranzactie_lista) == 2)
    lista_de_undo=undo_actiune(tranzactie_lista,undo_list)
    assert (len(tranzactie_lista) == 1)
    lista_de_undo=undo_actiune(tranzactie_lista,undo_list)
    assert (len(tranzactie_lista) == 0)
    try:
        lista_de_undo=undo_actiune(tranzactie_lista,undo_list)
        assert False
    except:
        assert True
        
def test_validare_date_introduse():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    try:
        validare_date_introduse(tranzactie_curenta)
        assert True
    except ValueError:
        assert False
    tranzactie_noua_detalii='100/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    try:
        validare_date_introduse(tranzactie_curenta)
        assert False
    except ValueError:
        assert True
    tranzactie_noua_detalii='10/80/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    try:
        validare_date_introduse(tranzactie_curenta)
        assert False
    except ValueError:
        assert True
    tranzactie_noua_detalii='10/8/20230,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    try:
        validare_date_introduse(tranzactie_curenta)
        assert False
    except ValueError:
        assert True
    tranzactie_noua_detalii='10/8/2023,100,intrareeeee'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    try:
        validare_date_introduse(tranzactie_curenta)
        assert False
    except ValueError:
        assert True