from creare_add_undo import adaugare_lista_tranzactie_noua,undo_actiune
from interfata import printare_lista_tranzactii
from lucru_cu_liste_de_tranzactii import eliminare_tranzactii_tip_dat,stergere_tranzactii_ziua_data

lista_tranzactii = []
undo_list = []  
stringuri= []

def citire_optiune():
    string_intreg=input('Introduceti optiunile separate prin - iar apoi numele optiunii separate prin : iar datele separate prin ,   ')
    stringuri=string_intreg.split('-')
    for index in stringuri:
        nume_optiune,date=index.split(':')
        nume_optiune.strip()
        date.strip()
        nume_optiune.lower()
        if nume_optiune=='adaugare':
            adaugare_lista_tranzactie_noua(lista_tranzactii,date,undo_list)
            print('Lista noua este ')
            printare_lista_tranzactii(lista_tranzactii)
        elif nume_optiune=='stergere':
            ziua, suma, tip = date.split(',')
            stergere_tranzactii_ziua_data(lista_tranzactii,ziua,undo_list)
            print('Lista noua este ')
            printare_lista_tranzactii(lista_tranzactii)
        elif nume_optiune=='filtrare':
            ziua, suma, tip = date.split(',')
            eliminare_tranzactii_tip_dat(lista_tranzactii,tip,undo_list)
            print('Lista noua este ')
            printare_lista_tranzactii(lista_tranzactii)
        elif nume_optiune=='undo':
            lista_de_undo=undo_actiune(lista_tranzactii,undo_list)
            print('Lista noua este ')
            printare_lista_tranzactii(lista_de_undo)
        else:
            print('Error: date introduse gresit')