from domain.lucru_eveniment import Eveniment   
from domain.validare import Validator
from repository.repository_evenimente import RepositoryEvenimente,FileRepositoryEvenimente

class ServiceEvenimente:
    def __init__(self,validator:Validator, repository: RepositoryEvenimente):
        self.__repo = repository
        self.__validator = validator

    def get_lista_evenimente(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return self.__repo.get_lista_evenimente()

    def adauga_eveniment(self, ID:int, data_zi:int, data_luna:int, data_an:int, timp:int, descriere:str):
        '''
        Functie ce adauga un eveniment in lista
        :param: ID
        :type: int
        :param: data_zi
        :type: int
        :param: data_luna
        :type: int
        :param: data_an
        :type: int
        :param: timp
        :type: int
        :param: descriere
        :type: string
        :return: none dar adauga evenimentul in lista
        '''
        eveniment=Eveniment(ID,data_zi,data_luna,data_an,timp,descriere)
        self.__validator.validare_eveniment(eveniment)
        self.__repo.adauga(eveniment)
        
    def adauga_by_default_evenimente(self):  
        '''
        Functie ce adauga cateva evenimente in lista
        :param: none
        :return: none dar adauga mai multe evenimente in lista
        '''
        FileRepositoryEvenimente.adauga_default(self)

    def sterge_eveniment(self, ID:int):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: none dar lista de evenimente se modifica prin stergerea evenimentului cu ID-ul dat
        '''
        self.__validator.validare_id(ID)
        self.__repo.sterge(ID)

    def modifica_eveniment(self, ID:int, data_zi_nou:int, data_luna_nou:int, data_an_nou:int, timp_nou:int, descriere_nou:str):
        '''
        Functie ce modifica o persoana din lista
        :param: ID
        :type: int
        :param: data_zi_nou
        :type: int
        :param: data_luna_nou
        :type: int
        :param: data_an_nou
        :type: int
        :param: timp_nou
        :type: int
        :param: descriere_nou
        :type: string
        :return: none dar lista de perevenimentesoane se modifica prin modificarea evenimentului cu ID-ul dat
        '''
        self.__validator.validare_id(ID)
        self.__repo.modifica(ID,data_zi_nou,data_luna_nou,data_an_nou,timp_nou,descriere_nou)
                
    def cauta_eveniment(self, ID:int):
        '''
        Functie ce afiseaza daca exista acest eveniment din lista
        :param: ID
        :type: int
        :return: eveniment
        :rtype: Eveniment
        '''
        self.__validator.validare_id(ID)
        return self.__repo.cauta_eveniment(ID) 
    
