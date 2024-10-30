def citire_detalii_tranzactie():
    tranzactie_detalii = input('Introduceti detaliile tranzactiei separate prin , (ziua,suma,tipul), iar ziua separata prin / (zi,luna,an): ')
    return tranzactie_detalii

def citire_ziua_data():
    ziua_data=input('Introduceti un string ce reprezinta ziua, separat prin / (zi,luna,an): ')
    ziua_data.strip()
    return ziua_data

def citire_tip_dat():
    tip_dat=input('Alegeti dintre string-urile intrare si iesire: ')
    tip_dat.strip()
    tip_dat.lower()
    return tip_dat
    
def citire_suma_data():
    suma_data=input('Introduceti un numar real ce va reprezenta suma data: ')
    suma_data.strip()
    suma_data=float(suma_data)
    return suma_data

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

def printare_lista_tranzactii(lista_tranzactii: list):
    """
    Afiseaza tranzactiile din lista
    :param lista_tranzactii: lista de tranzactii
    :type lista_tranzactii: list
    :return: -; printeaza tranzactiile din lista
    :rtype:
    """
    for tranzactie in lista_tranzactii:
        print('Data: ',get_ziua(tranzactie),'/',get_luna(tranzactie),'/',get_anul(tranzactie),'   Suma: ',get_suma(tranzactie),'  Tipul: ',get_tipul(tranzactie))

def printare_tranzactie(tranzactie: dict):
    """
    Afiseaza una dintre tranzactiile din lista 
    :param tranzactie: tranzactia curenta
    :type tranzactie: dict
    :return: -; afiseaza o tranzactie valida
    :rtype:
    """
    print('Data: ',get_ziua(tranzactie),'/',get_luna(tranzactie),'/',get_anul(tranzactie),'   Suma: ',get_suma(tranzactie),'  Tipul: ',get_tipul(tranzactie))
    
def printare_suma_totala(suma_totala: float):
    """
    Afiseaza suma tranzactiilor ce au tipul dat
    :param suma_totala: suma pana in momentul adaugarii
    :type suma_totala: float
    :return: -; afiseaza suma totala a tranzactiilor ce respecta conditia
    :rtype:
    """
    print('Suma totala este: ',suma_totala)    
    
def printare_sold_curent(sold_curent: float):
    """
    Afiseaza soldul tranzactiei din ziua data
    :param sold_curent: soldul final al tranzactiilor
    :type sold_curent: float
    :return: -; afiseaza soldul tranzactiei ce respecta conditia
    :rtype:
    """
    print('Soldul este: ',sold_curent)  
