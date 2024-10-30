from repository.repository_persoane import RepositoryPersoane
from repository.repository_evenimente import RepositoryEvenimente
from domain.lucru_inscrieri import Inscriere

class RepositoryInscrieri:
    def __init__(self,lista_persoane: RepositoryPersoane,lista_evenimente:RepositoryEvenimente):
        self.__lista_inscrieri= []
        self.__lista_persoane = lista_persoane
        self.__lista_evenimente = lista_evenimente
    
    def cauta_persoana(self, personID:int):
        '''
        Functie ce afiseaza daca exista aceasta persoana din lista
        :param: personID
        :type: int
        :return: persoana
        '''
        for persoana in self.__lista_persoane.get_lista_persoane():
            if persoana.get_id()==personID:
                return True
        return False             
    
    def cauta_inscriere(self, personID:int, ID:int):
        '''
        Functie ce afiseaza daca exista aceasta persoana din lista
        :param: personID
        :type: int
        :param: ID
        :type: int
        :return: true or false
        '''
        for inscriere in self.__lista_inscrieri:
            if inscriere.get_id_persoana()==personID and inscriere.get_id_eveniment()==ID:
                return True    
        return False        
    
    def cauta_eveniment(self, ID:int):
        '''
        Functie ce afiseaza daca exista acest eveniment din lista
        :param: ID
        :type: int
        :return: eveniment
        '''
        for eveniment in self.__lista_evenimente.get_lista_evenimente():
            if eveniment.get_id()==ID:
                return True
        return False       
        
    def adauga(self, inscriere:Inscriere):
        '''
        Functie ce adauga pentru id-ul persoanei id-ul unui eveniment
        :param: inscriere
        :type: Inscriere
        :return: -
        '''
        if self.cauta_inscriere(inscriere.get_id_persoana(),inscriere.get_id_eveniment()):
            raise ValueError("Exista deja aceasta inscriere")
        self.__lista_inscrieri.append(inscriere)
        
    def adauga_default(self):
        '''
        Functie ce adauga cateva inscrieri in lista
        :param: none
        :return: none dar adauga mai multe inscrieri in lista
        '''
        inscr=Inscriere(1,3)
        self.adauga(inscr)
        inscr=Inscriere(1,1)
        self.adauga(inscr)
        inscr=Inscriere(1,5)
        self.adauga(inscr)
        inscr=Inscriere(2,1)
        self.adauga(inscr)
        inscr=Inscriere(2,4)
        self.adauga(inscr)
        inscr=Inscriere(3,1)
        self.adauga(inscr)
        inscr=Inscriere(4,5)
        self.adauga(inscr)
        inscr=Inscriere(5,1)
        self.adauga(inscr)
        inscr=Inscriere(5,5)
        self.adauga(inscr)
            
    def get_lista_inscrieri(self):
        '''
        Functie ce returneaza lista de inscrieri
        :param: none
        :return: lista de inscrieri
        :rtype: list
        '''
        return self.__lista_inscrieri
        
    def get_evenimentele_persoanei(self,ID:int):
        '''
        Functie ce returneaza lista de evenimente ale unei persoane
        :param: ID
        :type: int
        :return: lista de evenimente ale unei persoane
        :rtype: list
        '''
        evenimente= []
        for inscriere in self.__lista_inscrieri:
            if inscriere.get_id_persoana()==ID:
                evenimente.append(inscriere.get_id_eveniment())
        return evenimente
        
    def get_persoanele_evenimentului(self,ID:int):
        '''
        Functie ce returneaza lista de persoane ale unui eveniment
        :param: ID
        :type: int
        :return: lista de persoane ale unui eveniment
        :rtype: list
        '''
        persoane= []
        for inscriere in self.__lista_inscrieri:
            if inscriere.get_id_eveniment()==ID:
                persoane.append(inscriere.get_id_persoana())
        return persoane
    
    