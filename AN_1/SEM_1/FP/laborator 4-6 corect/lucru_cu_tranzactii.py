from creare_add_undo import adaugare_lista_tranzactie_noua,creare_tranzactie
from interfata import get_ziua,get_luna,get_anul,get_suma,get_tipul

def validare_ziua_introdusa(ziua: str):
    """
    Verificam ziua pentru o tranzactie
    :param tranzactie: tranzactie de validat
    :type tranzactie: str
    :return: corectitudinea datelor introduse
    :rtype: int
    """
    zi, luna, an = ziua.split('/')
    zi=int(zi)
    an=int(an)
    luna=int(luna)
    errors = []
    if zi>31 or zi<0:
        errors.append("Ziua poate fi doar intre 1-31.")
    if luna>12 or luna<0:
        errors.append("Luna poate fi doar intre 1-12.")
    if an>2023 or an<=0:
        errors.append("Anul poate fi doar intre 0-2023.")
    if len(errors) > 0:
        error_string = '\n'.join(errors)
        raise ValueError(error_string)
    
def validare_tipul_introdus(tip: str):
    """
    Verificam tipul pentru o tranzactie
    :param tranzactie: tranzactie de validat
    :type tranzactie: str
    :return: corectitudinea datelor introduse
    :rtype: int
    """
    tip = tip.strip()
    tip.lower()
    errors = []
    if tip!='intrare' and tip!='iesire':
        errors.append("Tipul poate fi doar intrare sau iesire.")
    if len(errors) > 0:
        error_string = '\n'.join(errors)
        raise ValueError(error_string)

def verificare_perioada_data(tranzactie: dict, ziua_de_inceput: str, ziua_de_sfarsit: str):
    """
    Verifica daca tranzactie se afla in perioada dintre cele doua zile citite
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param ziua_de_inceput: ziua de inceput citita de la tastatura
    :type ziua_de_inceput: str
    :param ziua_de_sfarsit: ziua de sfarsit citita de la tastatura
    :type ziua_de_sfarsit: str
    :return: true or false
    :rtype: int
    """
    corect=0
    try:
        validare_ziua_introdusa(ziua_de_inceput)
        validare_ziua_introdusa(ziua_de_sfarsit)
        zi_inceput,luna_inceput,an_inceput=ziua_de_inceput.split('/')
        zi_inceput=int(zi_inceput)
        luna_inceput=int(luna_inceput)
        an_inceput=int(an_inceput)
        zi_sfarsit,luna_sfarsit,an_sfarsit=ziua_de_sfarsit.split('/')
        zi_sfarsit=int(zi_sfarsit)
        luna_sfarsit=int(luna_sfarsit)
        an_sfarsit=int(an_sfarsit)
        if get_anul(tranzactie)>an_inceput and get_anul(tranzactie)<an_sfarsit:
            corect=1
        if get_anul(tranzactie)==an_inceput:
            if get_luna(tranzactie)>luna_inceput:
                corect=1
            else:
                if get_ziua(tranzactie)>zi_inceput:
                    corect=1
        if get_anul(tranzactie)==an_sfarsit:
            if get_luna(tranzactie)<luna_sfarsit:
                corect=1
            else:
                if get_ziua(tranzactie)>zi_sfarsit:
                    corect=1
        return corect
    except ValueError as exc:
        print("EROARE:",str(exc))    

def verificare_ziua_mai_mica(tranzactie: dict, ziua_data: str):
    """
    Verifica daca tranzactie are ziua mai mica decat ziua citita
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param ziua_data: ziua citita de la tastatura
    :type ziua_data: str
    :return: true or false
    :rtype: int
    """
    corect=0
    try:
        validare_ziua_introdusa(ziua_data)
        zi,luna,an=ziua_data.split('/')
        zi=int(zi)
        luna=int(luna)
        an=int(an)
        if get_anul(tranzactie)<an:
            corect=1
        elif get_anul(tranzactie)==an and get_luna(tranzactie)<luna:
            corect=1
        elif get_anul(tranzactie)==an and get_luna(tranzactie) and get_ziua(tranzactie)<zi:
            corect=1
        return corect
    except ValueError as exc:
        print("EROARE:",str(exc))   

def verificare_ziua_data(tranzactie: dict, ziua_data: str):
    """
    Verifica daca tranzactia are aceeasi zi cu ziua data
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param ziua_data: ziua citita de la tastatura
    :type ziua_data: str
    :return: true or false
    :rtype: int
    """
    try:
        validare_ziua_introdusa(ziua_data)
        zi,luna,an=ziua_data.split('/')
        zi=int(zi)
        luna=int(luna)
        an=int(an)
        if get_anul(tranzactie) and get_luna(tranzactie)==luna and get_ziua(tranzactie)==zi:
            return 1
        else:
            return 0
    except ValueError as exc:
        print("EROARE:",str(exc))   

def verificare_tip(tranzactie: dict, tip_dat: str):
    """
    Verifica daca tranzactie are suma mai mare decat suma data
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param tip_dat: tipul citita de la tastatura
    :type tip_dat: str
    :return: true or false
    :rtype: int
    """
    try:
        validare_tipul_introdus(tip_dat)
        if get_tipul(tranzactie)==tip_dat:
            return 1
        else:
            return 0   
    except ValueError as exc:
        print("EROARE:",str(exc))   
    
def verificare_suma_mai_mare(tranzactie: dict, suma_data: float):
    """
    Verifica daca tranzactie are suma mai mare decat suma data
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param suma_data: suma citita de la tastatura
    :type suma_data: float
    :return: true or false
    :rtype: int
    """
    if get_suma(tranzactie)>suma_data:
        return 1
    else:
        return 0    
    
def verificare_suma_mai_mica(tranzactie: dict, suma_data: float):
    """
    Verifica daca tranzactie are suma mai mica decat suma data
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :param suma_data: suma citita de la tastatura
    :type suma_data: float
    :return: true or false
    :rtype: int
    """
    if get_suma(tranzactie)<suma_data:
        return 1
    else:
        return 0  
    
def testare_verificare_perioada_data():
    tranzactie_noua_detalii='10/8/2020,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_perioada_data(tranzactie_curenta,'1/1/2020','31/12/2020')==1)
    tranzactie_noua_detalii='10/8/2021,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_perioada_data(tranzactie_curenta,'1/1/2020','31/12/2020')==0)
testare_verificare_perioada_data()

def testare_verificare_ziua_mai_mica():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    verificare_raspuns=verificare_ziua_mai_mica(tranzactie_curenta,'10/9/2023')
    assert(verificare_raspuns==1)
    verificare_raspuns=verificare_ziua_mai_mica(tranzactie_curenta,'1/5/2023')
    assert(verificare_raspuns==0)
testare_verificare_ziua_mai_mica()

def testare_verificare_ziua_data():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_ziua_data(tranzactie_curenta,'10/8/2023')==1)
    assert(verificare_ziua_data(tranzactie_curenta,'1/8/2023')==0)
testare_verificare_ziua_data()

def testare_verificare_tip():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_tip(tranzactie_curenta,'iesire')==0)
    assert(verificare_tip(tranzactie_curenta,'intrare')==1)
testare_verificare_tip()

def testare_verificare_suma_mai_mare():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_suma_mai_mare(tranzactie_curenta,150)==0)
    assert(verificare_suma_mai_mare(tranzactie_curenta,70)==1)
testare_verificare_suma_mai_mare()

def testare_verificare_suma_mai_mica():
    tranzactie_noua_detalii='10/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(verificare_suma_mai_mica(tranzactie_curenta,150)==1)
    assert(verificare_suma_mai_mica(tranzactie_curenta,70)==0)
testare_verificare_suma_mai_mica()

def validare_date_introduse(tranzactie: dict):
    """
    Verificam o tranzactie
    :param tranzactie: tranzactie de validat
    :type tranzactie: dict
    :return: corectitudinea datelor introduse
    :rtype: int
    """
    if get_ziua(tranzactie)>31 or get_ziua(tranzactie)<=0:
        return 0
    if get_luna(tranzactie)>12 or get_luna(tranzactie)<=0:
        return 0
    if get_anul(tranzactie)>2023 or get_anul(tranzactie)<=0:
        return 0
    if get_tipul(tranzactie)!='intrare' and get_tipul(tranzactie)!='iesire':
        return 0
    return 1   
    
    
def testare_validare_date_introduse():
    tranzactie_noua_detalii='100/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(validare_date_introduse(tranzactie_curenta)==0)
    tranzactie_noua_detalii='1/8/2023,100,intrare'
    tranzactie_curenta=creare_tranzactie(tranzactie_noua_detalii)
    assert(validare_date_introduse(tranzactie_curenta)==1)
testare_validare_date_introduse()   