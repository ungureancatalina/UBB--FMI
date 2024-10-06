from service.service_persoane import ServicePersoane
from service.service_evenimente import ServiceEvenimente
from service.service_inscrieri import ServiceInscrieri
from utils.utils_fisiere import elimina_continut_fisier

class Consola:
    def __init__(self,lista_persoane:ServicePersoane, lista_evenimente:ServiceEvenimente, lista_inscrieri:ServiceInscrieri):
        self.__lista_persoane = lista_persoane
        self.__lista_evenimente =lista_evenimente
        self.__lista_inscrieri =lista_inscrieri
    
    def __print_meniu(self):
        """
        Afiseaza meniul aplicatiei
        :param: none
        :return: -;
        :rtype:
        """
        print('Optiunea 1: adaugă lista de persoane')
        print('Optiunea 2: adaugă lista de evenimente')
        print('Optiunea 3: șterge lista de persoane')
        print('Optiunea 4: șterge lista de evenimente')
        print('Optiunea 5: modifică lista de persoane')
        print('Optiunea 6: modifică lista de evenimente')
        print('Optiunea 7: căutare persoane')
        print('Optiunea 8: căutare evenimente')
        print('Optiunea 9: Înscriere persoană la eveniment')
        print('Optiunea 10: afisare lista de evenimente la care participă o persoană ordonat alfabetic după descriere')
        print('Optiunea 11: afisare lista de evenimente la care participă o persoană ordonat alfabetic după data')
        print('Optiunea 12: afisare persoane participante la cele mai multe evenimente ')
        print('Optiunea 13: afisare primele 20 la suta evenimente cu cei mai mulți participanți (descriere, număr participanți)')
        print('Optiunea 14. afisare persoane participante la un nr finit de evenimente')
        print('Optiunea 15. afisare lista de evenimente la care participă o persoană ordonat alfabetic după timp, iar apoi dupa descriere')
        print('Optiunea 16. adauga by default persoane, evenimente si inscrieri')
        print('Optiunea 17. adauga random persoane')
        print('Optiunea 18. Iesire din aplicatie')
            
    def __print_lista_evenimente(self, lista_evenimente):
        """
        Afiseaza lista de evenimente 
        :param lista_evenimente: lista de evenimente
        :type lista_evenimente: list
        :return: -;
        :rtype:
        """
        for eveniment in lista_evenimente:
            print(eveniment)
        
    def __print_lista_persoane(self, lista_persoane):
        """
        Afiseaza lista de persoane 
        :param lista_persoane: lista de persoane
        :type lista_persoane: list
        :return: -;
        :rtype:
        """
        for persoana in lista_persoane:
            print(persoana)
            
    def __print_lista_inscrieri(self, lista_inscrieri):
        """
        Afiseaza lista de inscrieri 
        :param lista_inscrieri: lista de inscrieri
        :type lista_inscrieri: list
        :return: -;
        :rtype:
        """
        for inscriere in lista_inscrieri: 
            persoana=self.__lista_persoane.cauta_persoana(inscriere.get_id_persoana())
            eveniment=self.__lista_evenimente.cauta_eveniment(inscriere.get_id_eveniment())
            print(persoana,' --- ',eveniment)
            
    def __print_persoana(self, persoana):
        """
        Afiseaza persoana
        :param persoana: persoana
        :type persoana: Persoana
        :return: -;
        :rtype:
        """
        print(persoana)
        
    def __print_eveniment(self, eveniment):
        """
        Afiseaza evenimentul
        :param eveniment: evenimentul
        :type eveniment: Eveniment
        :return: -;
        :rtype:
        """
        print(eveniment)
            
    def __adauga_persoane_ui(self):
        """
        Adauga persoana la lista de persoane 
        :param: none
        :return: -;
        :rtype:
        """
        personID=int(input('Introduceti Id-ul persoanei: '))
        nume=input('Introduceti numele persoanei: ')
        adresa=input('Introduceti adresa persoanei: ')
        try:
            self.__lista_persoane.adauga_persoana(personID,nume,adresa)
            print("Dupa adaugare, lista de persoane va fi:")
            self.__print_lista_persoane(self.__lista_persoane.get_lista_persoane())
        except ValueError as e:
            print('EROARE:' + str(e))
                
    def __adauga_evenimente_ui(self):
        """
        Adauga eveniment la lista de evenimente 
        :param: none
        :return: -;
        :rtype:
        """
        ID=int(input('Introduceti Id-ul evenimentului: '))
        data=input('Introduceti data evenimentului: ')
        timp=int(input('Introduceti timpul evenimentului: '))
        descriere=input('Introduceti descrierea evenimentului: ')
        zi, luna, an = data.split('/')
        zi=int(zi)
        luna=int(luna)
        an=int(an)
        try:
            self.__lista_evenimente.adauga_eveniment(ID,zi,luna,an,timp,descriere)
            print("Dupa adaugare, lista de evenimente va fi:")
            self.__print_lista_evenimente(self.__lista_evenimente.get_lista_evenimente())
        except ValueError as e:
            print('EROARE:' + str(e))    
            
    def __sterge_persoana_ui(self):  
        """
        Sterge persoana din lista de persoane 
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti ID-ul persoanei ce va fi stearsa din lista: '))
        try:
            self.__lista_persoane.sterge_persoana(persoanaID)
            print("Dupa stergere, lista de persoane va fi:")
            self.__print_lista_persoane(self.__lista_persoane.get_lista_persoane())
        except IndexError as e:
            print('EROARE: ' + str(e))       
            
    def __sterge_eveniment_ui(self):  
        """
        Sterge eveniment din lista de evenimente 
        :param: none
        :return: -;
        :rtype:
        """
        evenimentID=int(input('Introduceti ID-ul evenimentului ce va fi sters din lista: '))
        try:
            self.__lista_evenimente.sterge_eveniment(evenimentID)
            print("Dupa stergere, lista de evenimente va fi:")
            self.__print_lista_evenimente(self.__lista_evenimente.get_lista_evenimente())
        except IndexError as e:
            print('EROARE: ' + str(e))
                
    def __modifica_persoana_ui(self):  
        """
        Modifica lista de persoane 
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei actual: '))
        nume_nou=input('Introduceti numele persoanei nou: ')
        adresa_nou=input('Introduceti adresa persoanei noua: ')
        try:
            self.__lista_persoane.modifica_persoana(persoanaID,nume_nou,adresa_nou)
            print("Dupa modificare, lista de persoane va fi:")
            self.__print_lista_persoane(self.__lista_persoane.get_lista_persoane())
        except IndexError as e:
            print('EROARE: ' + str(e))
                
    def __modifica_eveniment_ui(self):  
        """
        Modifica lista de evenimente 
        :param: none
        :return: -;
        :rtype:
        """
        evenimentID=int(input('Introduceti ID-ul actual: '))
        data_nou=input('Introduceti data evenimentului noua: ')
        timp_nou=int(input('Introduceti timpul evenimentului nou: '))
        descriere_nou=input('Introduceti descrierea evenimentului noua: ')
        zi_nou, luna_nou, an_nou = data_nou.split('/')
        zi_nou=int(zi_nou)
        luna_nou=int(luna_nou)
        an_nou=int(an_nou)
        try:
            self.__lista_evenimente.modifica_eveniment(evenimentID,zi_nou,luna_nou,an_nou,timp_nou,descriere_nou)
            print("Dupa modificare, lista de evenimente va fi:")
            self.__print_lista_evenimente(self.__lista_evenimente.get_lista_evenimente())
        except IndexError as e:
            print('EROARE: ' + str(e))
            
    def __cauta_persoana_ui(self):
        """
        Cauta o persoana in lista de persoane 
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei actual: '))
        try:
            persoana_gasita=self.__lista_persoane.cauta_persoana(persoanaID)
            print("Persoana este: ")
            self.__print_persoana(persoana_gasita)
        except IndexError as e:
            print('EROARE: ' + str(e))
            
    def __cauta_eveniment_ui(self):
        """
        Cauta un eveniment in lista de evenimente 
        :param: none
        :return: -;
        :rtype:
        """
        evenimentID=int(input('Introduceti ID-ul actual: '))
        try:
            evenimentul_gasit=self.__lista_evenimente.cauta_eveniment(evenimentID)
            print("Evenimentul este: ")
            self.__print_eveniment(evenimentul_gasit)
        except IndexError as e:
            print('EROARE: ' + str(e))
            
    def __inscriere_ui(self):
        """
        Inscrie o persoana la un eveniment 
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei: '))
        evenimentID=int(input('Introduceti ID-ul evenimentului: '))
        self.__lista_inscrieri.adauga_inscriere(persoanaID,evenimentID)
        self.__print_lista_inscrieri(self.__lista_inscrieri.get_lista_inscrieri())
        
    def __evenimente_inscrieri_ordonate_descriere(self):
        """
        Afiseaza lista de evenimente la care participa o persoana ordonate dupa descriere
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei: '))
        persoana=self.__lista_persoane.cauta_persoana(persoanaID)
        print('Persoana este ',persoana, ' iar filmele acesteia sunt: ')
        lista_evenimentele_persoanei=self.__lista_inscrieri.evenimente_ordonate_descriere(persoanaID)
        for eveniment in lista_evenimentele_persoanei:
            print(eveniment)
            
    def __evenimente_inscrieri_ordonate_data(self):
        """
        Afiseaza lista de evenimente la care participa o persoana ordonate dupa data
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei: '))
        persoana=self.__lista_persoane.cauta_persoana(persoanaID)
        print('Persoana este ',persoana, ' iar filmele acesteia sunt: ')
        lista_evenimentele_persoanei=self.__lista_inscrieri.evenimente_ordonate_data(persoanaID)
        for eveniment in lista_evenimentele_persoanei:
            print(eveniment)        
            
    def __persoane_inscrise_multe_evenimente_ui(self):
        """
        Afiseaza lista de persoane care participa la mai multe evenimente
        :param: none
        :return: -;
        :rtype:
        """
        persoane=[]
        persoane=self.__lista_inscrieri.persoane_inscrise_multe_evenimente()
        for id in persoane:
            pers=self.__lista_persoane.cauta_persoana(id)
            print(pers)
            
    def __persoane_inscrise_la_n_evenimente_ui(self):
        """
        Afiseaza lista de persoane care participa la mai multe evenimente
        :param: none
        :return: -;
        :rtype:
        """
        nr_evenimente=int(input('Introduceti un nr natural n,ce va reprezenta nr de evenimente'))
        persoane=[]
        persoane=self.__lista_inscrieri.persoane_inscrise_la_n_evenimente(nr_evenimente)
        for id in persoane:
            pers=self.__lista_persoane.cauta_persoana(id)
            print(pers)        
            
    def __20_evenimente_multi_participanti_ui(self):
        """
        Afiseaza lista de evenimente la care participa cele mai multe persoane(20%)
        :param: none
        :return: -;
        :rtype:
        """
        evenimente=[]
        evenimente=self.__lista_inscrieri.evenimente_multi_participanti()
        lungimea=len(evenimente)/5
        pozitie_curenta=0
        for id in evenimente:
            if pozitie_curenta<=lungimea:
                ev=self.__lista_evenimente.cauta_eveniment(id)
                print(ev.get_descriere())
            pozitie_curenta+=1
            
    def __adauga_random_ui(self):
        '''
        Functie ce adauga cateva persoane random in lista
        :param: none
        :return: none dar adauga mai multe persoane random in lista
        '''
        numar=int(input('Introduceti nr de persoane ce doriti sa fie adaugate'))
        i=0
        self.__lista_persoane.adauga_random(numar,i)
        print("Dupa adaugare, lista de persoane va fi:")
        self.__print_lista_persoane(self.__lista_persoane.get_lista_persoane())
        
    def __adauga_default_ui(self):
        '''
        Functie ce adauga cateva persoane,evenimente si inscrieri in liste
        :param: none
        :return: none dar adauga mai multe persoane,evenimente si inscrieri in liste
        '''
        self.__lista_persoane.adauga_by_default_persoane()
        print("Dupa adaugare, lista de persoane va fi:")
        self.__print_lista_persoane(self.__lista_persoane.get_lista_persoane())
        self.__lista_evenimente.adauga_by_default_evenimente()
        print("Dupa adaugare, lista de evenimente va fi:")
        self.__print_lista_evenimente(self.__lista_evenimente.get_lista_evenimente())
        self.__lista_inscrieri.adauga_by_default_inscrieri()
        print("Dupa adaugare, lista de inscrieri va fi:")
        self.__print_lista_inscrieri(self.__lista_inscrieri.get_lista_inscrieri())
        
    def __doua_sortari(self):
        """
        Afiseaza lista de evenimente la care participa o persoana ordonate dupa timp, iar apoi dupa descriere
        :param: none
        :return: -;
        :rtype:
        """
        persoanaID=int(input('Introduceti Id-ul persoanei: '))
        persoana=self.__lista_persoane.cauta_persoana(persoanaID)
        print('Persoana este ',persoana, ' iar filmele acesteia sunt: ')
        lista_evenimentele_persoanei=self.__lista_inscrieri.evenimente_ordonate_timp_descriere(persoanaID)
        for eveniment in lista_evenimentele_persoanei:
            print(eveniment)        
        
    def start_program(self):
        while True:
            self.__print_meniu()
            option=input('Introduceti optiunea dorita (1-16) : ')
            option=option.strip()
            option=int(option)
            match option:
                case 1:
                    self.__adauga_persoane_ui()
                case 2:
                    self.__adauga_evenimente_ui()
                case 3:
                    self.__sterge_persoana_ui()
                case 4:
                    self.__sterge_eveniment_ui()
                case 5:
                    self.__modifica_persoana_ui()
                case 6:
                    self.__modifica_eveniment_ui()
                case 7:
                    self.__cauta_persoana_ui()
                case 8:
                    self.__cauta_eveniment_ui()
                case 9:
                    self.__inscriere_ui()
                case 10:
                    self.__evenimente_inscrieri_ordonate_descriere()
                case 11:
                    self.__evenimente_inscrieri_ordonate_data()
                case 12:
                    self.__persoane_inscrise_multe_evenimente_ui()
                case 13:
                    self.__20_evenimente_multi_participanti_ui()
                case 14:
                    self.__persoane_inscrise_la_n_evenimente_ui()
                case 15:
                    self.__doua_sortari()
                case 16:
                    self.__adauga_default_ui()
                    elimina_continut_fisier('data/persoane.txt')
                    elimina_continut_fisier('data/evenimente.txt')
                case 17:
                    self.__adauga_random_ui()
                    elimina_continut_fisier('data/persoane.txt')
                    elimina_continut_fisier('data/evenimente.txt')
                case 18:
                    elimina_continut_fisier('data/persoane.txt')
                    elimina_continut_fisier('data/evenimente.txt')
                    break
                case _:
                    print('Nu exista aceasta optiune')
                    break