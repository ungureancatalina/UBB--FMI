from service.service_cafea import ServiceCafea
from domain.lucru_cafea import Cafea

class Consola:
    def __init__(self,lista_cafele:ServiceCafea):
        self.__lista_cafele=lista_cafele
    
    def __print_meniu(self):
        print('Optiunea 1. Adauga cafea')
        print('Optiunea 2. Sterge cafea dupa pret')
        print('Optiunea 3. Cauta cafea dupa nume')
        print('Optiunea 4. Sorteaza toate tipurile de cafea alfabetic dupa origine, respectiv dupa pret')
        print('Optiunea 5. Afiseaza tipurile de cafea din tara x cu pretul  <=y')
        print('Optiunea 6. Adauga by default cafea')
        print('Optiunea 7. Iesire din aplicatie')
        
    def __print_lista_cafele(self,lista_cafele):
        for cafea in lista_cafele:
            print(cafea)
            
    def __print_cafea(self,cafea:Cafea):
        print(cafea)
                
    def __adauga_cafea_ui(self):
        nume=input('Introduceti numele cafelei: ')
        tara_origine=input('Introduceti tara de origine a cafelei: ')
        pret=int(input('Introduceti pretul cafelei: '))
        nume=nume.strip()
        tara_origine=tara_origine.strip()
        try:
            self.__lista_cafele.adauga_cafea(nume,tara_origine,pret)
            self.__print_lista_cafele(self.__lista_cafele.get_lista())
        except ValueError as e:
            print(str(e))
        
    def __adauga_by_default_cafea_ui(self):
        self.__lista_cafele.adauga_by_default_cafea()
        self.__print_lista_cafele(self.__lista_cafele.get_lista())
        
    def __sterge_cafea_ui(self):
        pret=int(input('Introduceti pretul cafelei: '))
        try:
            self.__lista_cafele.sterge_cafea(pret)
            self.__print_lista_cafele(self.__lista_cafele.get_lista())
        except ValueError as e:
            print(str(e))
        
    def __cauta_cafea_ui(self):
        nume=input('Introduceti numele cafelei: ')
        cafea=self.__lista_cafele.cauta_cafea(nume)
        self.__print_cafea(cafea)
        
    def __sorteaza_cafea_ui(self): 
        lista= self.__lista_cafele.sorteaza_cafea()
        self.__print_lista_cafele(lista)
        
    def __raport_cafea_ui(self):       
        ...        
        
    def start_program(self):
        while True:
            self.__print_meniu()
            optiunea=int(input('Alegeti una dintre aceste optiuni: '))
            match optiunea:
                case 1:
                    self.__adauga_cafea_ui()
                case 2:
                    self.__sterge_cafea_ui()
                case 3:
                    self.__cauta_cafea_ui()
                case 4:
                    self.__sorteaza_cafea_ui()
                case 5:
                    self.__raport_cafea_ui()
                case 6:
                    self.__adauga_by_default_cafea_ui()
                case 7:
                    break
                case _:
                    print('Nu exista optiunea dorita')
                    break