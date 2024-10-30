from lucru_cu_liste_de_tranzactii import afisare_tranzactii_suma_data,afisare_tranzactii_suma_si_ziua_data,afisare_tranzactii_tip_dat,suma_tranzactii_tip_dat,soldul_contului_ziua_data,afisare_tranzactii_tip_dat_ordonat_dupa_suma,stergere_tranzactii_ziua_data,stergere_tranzactii_perioada_data,stergere_tranzactii_tip_dat,eliminare_tranzactii_tip_dat,eliminare_tranzactii_suma_data_tip_dat
from interfata import citire_detalii_tranzactie,citire_ziua_data,citire_tip_dat,citire_suma_data,printare_lista_tranzactii,printare_tranzactie,printare_suma_totala,printare_sold_curent 
from creare_add_undo import creare_tranzactie,add_tranzactie,actualizare_tranzactie_data,adaugare_lista_tranzactie_noua,undo_actiune
from string_de_optiuni import citire_optiune

def print_meniu():
    print('Optiunea 1: adaugă tranzacție (se dă ziua, suma, tipul)')
    print('Optiunea 2: actualizare tranzacție (se dă ziua, suma, tipul)')
    print('Optiunea 3: Șterge toate tranzacțiile de la ziua specificată')
    print('Optiunea 4: Șterge tranzacțiile dintr-o perioadă dată (se dă ziua de început și sfârșit)')
    print('Optiunea 5: Șterge toate tranzacțiile de un anumit tip')
    print('Optiunea 6: Tipărește tranzacțiile cu sume mai mari decât o sumă dată')
    print('Optiunea 7: Tipărește toate tranzacțiile efectuate înainte de o zi și mai mari decât o sumă (se dă suma și ziua)')
    print('Optiunea 8: Tipărește tranzacțiile de un anumit tip')
    print('Optiunea 9: Suma totală a tranzacțiilor de un anumit tip')
    print('Optiunea 10: Soldul contului la o dată specificată')
    print('Optiunea 11: Tipărește toate tranzacțiile de un anumit tip ordonat după sumă')
    print('Optiunea 12: Elimină toate tranzacțiile de un anumit tip')
    print('Optiunea 13: Elimină toate tranzacțiile mai mici decât o sumă dată care au tipul specificat')
    print('Optiunea 14: Reface ultima operație (lista de tranzacții revine la ce exista înainte de ultima operație care a modificat lista)')
    print('Optiunea 15: iesire din aplicatie')
    
def functionalitatea1():  
    tranzactie_detalii=citire_detalii_tranzactie()
    adaugare_lista_tranzactie_noua(lista_tranzactii,tranzactie_detalii,undo_list)
    printare_lista_tranzactii(lista_tranzactii)  
    
def functionalitatea2():
    tranzactie_detalii_vechi=citire_detalii_tranzactie()
    tranzactie_detalii_noi=citire_detalii_tranzactie()
    actualizare_tranzactie_data(lista_tranzactii,tranzactie_detalii_vechi,tranzactie_detalii_noi)
    printare_lista_tranzactii(lista_tranzactii)
    
def functionalitatea3():
    ziua_data=citire_ziua_data()
    stergere_tranzactii_ziua_data(lista_tranzactii,ziua_data,undo_list)
    printare_lista_tranzactii(lista_tranzactii)
    
def functionalitatea4():
    ziua_de_inceput=citire_ziua_data()
    ziua_de_sfarsit=citire_ziua_data()
    stergere_tranzactii_perioada_data(lista_tranzactii,ziua_de_inceput,ziua_de_sfarsit,undo_list)
    printare_lista_tranzactii(lista_tranzactii)
    
def functionalitatea5():
    tip_dat=citire_tip_dat()
    stergere_tranzactii_tip_dat(lista_tranzactii,tip_dat,undo_list)
    printare_lista_tranzactii(lista_tranzactii)
    
def functionalitatea6():
    suma_data=citire_suma_data()
    lista_noua=[]
    lista_noua=afisare_tranzactii_suma_data(lista_tranzactii,suma_data,undo_list)
    printare_lista_tranzactii(lista_noua)
    
def functionalitatea7():
    suma_data=citire_suma_data()
    ziua_data=citire_ziua_data()
    lista_noua=[]
    lista_noua=afisare_tranzactii_suma_si_ziua_data(lista_tranzactii,suma_data,ziua_data,undo_list)
    printare_lista_tranzactii(lista_noua)
    
def functionalitatea8():
    tip_dat=citire_tip_dat()
    lista_noua=[]
    lista_noua=afisare_tranzactii_tip_dat(lista_tranzactii,tip_dat,undo_list)
    printare_lista_tranzactii(lista_noua)
    
def functionalitatea9():
    tip_dat=citire_tip_dat()
    suma_totala=suma_tranzactii_tip_dat(lista_tranzactii,tip_dat)
    printare_suma_totala(suma_totala)
    
def functionalitatea10():
    ziua_data=citire_ziua_data()
    sold_curent=soldul_contului_ziua_data(lista_tranzactii,ziua_data)
    printare_sold_curent(sold_curent)
    
def functionalitatea11():
    tip_dat=citire_tip_dat()
    lista_ordonata=[]
    lista_ordonata=afisare_tranzactii_tip_dat_ordonat_dupa_suma(lista_tranzactii,tip_dat,undo_list)
    printare_lista_tranzactii(lista_ordonata)
    
def functionalitatea12():
    tip_dat=citire_tip_dat()
    eliminare_tranzactii_tip_dat(lista_tranzactii,tip_dat,undo_list)
    printare_lista_tranzactii(lista_tranzactii)
    
def functionalitatea13():
    tip_dat=citire_tip_dat()
    suma_data=citire_suma_data()
    eliminare_tranzactii_suma_data_tip_dat(lista_tranzactii,tip_dat,suma_data,undo_list)
    printare_lista_tranzactii(lista_tranzactii)
            
def functionalitatea14():
    lista_de_undo=undo_actiune(lista_tranzactii,undo_list)        
    printare_lista_tranzactii(lista_de_undo)
    
lista_tranzactii = []
undo_list = []  
def start_program():
    while True:
        print_meniu()
        option=input('Introduceti optiunea dorita (1-15) : ')
        option=option.strip()
        option=int(option)
        match option:
            case 1:
                functionalitatea1()
            case 2:
                functionalitatea2()
            case 3:
                functionalitatea3()
            case 4:
                functionalitatea4()
            case 5:
                functionalitatea5()
            case 6:
                functionalitatea6()
            case 7:
                functionalitatea7()
            case 8:
                functionalitatea8()
            case 9:
                functionalitatea9()
            case 10:
                functionalitatea10()
            case 11:
                functionalitatea11()
            case 12:
                functionalitatea12()
            case 13:
                functionalitatea13()
            case 14:
                functionalitatea14()
            case 15:
                break
            case _:
                print('Nu exista aceasta optiune')
                break
