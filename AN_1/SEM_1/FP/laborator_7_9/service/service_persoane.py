from domain.lucru_persoana import Persoana
from domain.validare import Validator
from repository.repository_persoane import RepositoryPersoane,FileRepositoryPersoane
import random,string

class ServicePersoane:
    def __init__(self,validator:Validator, repository: RepositoryPersoane):
        self.__repo = repository
        self.__validator = validator
        
    def get_lista_persoane(self):
        '''
        Functie ce returneaza lista de persoane
        :param: none
        :return: lista de persoane
        :rtype: list
        '''
        return self.__repo.get_lista_persoane()

    def adauga_persoana(self, personID:int, nume:str, adresa:str):
        '''
        Functie ce adauga o persoana in lista
        :param: personID
        :type: int
        :param: nume
        :type: string
        :param: adresa
        :type: string 
        :return: none dar adauga persoana in lista
        '''
        persoana=Persoana(personID,nume,adresa)
        self.__validator.validare_persoana(persoana)
        self.__repo.adauga(persoana)
        
    def adauga_by_default_persoane(self):
        '''
        Functie ce adauga cateva persoane in lista
        :param: none
        :return: none dar adauga mai multe persoane in lista
        '''
        FileRepositoryPersoane.adauga_default(self)

    # RECURSIV
    def adauga_random(self,numar: int,i:int):
        '''
        Functie ce adauga cateva persoane random in lista
        :param: none
        :return: none dar adauga mai multe persoane random in lista
        '''
        if i<numar:
            Id_random=''.join([str(random.randint(0,9)) for _ in range(2)])
            nume_1 =random.choice(string.ascii_uppercase)
            nume_2=''.join([str(random.choice(string.ascii_lowercase)) for _ in range(6)])
            nume_random= nume_1+nume_2
            adresa_1=random.choice(string.ascii_uppercase)
            adresa_2=''.join([str(random.choice(string.ascii_lowercase)) for _ in range(5)])
            adresa_random= 'Strada '+adresa_1+adresa_2
            persoana= Persoana(Id_random,nume_random,adresa_random)
            self.__repo.adauga(persoana)
            return self.adauga_random(numar,i+1)
        return

    def sterge_persoana(self, personID:int):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: none dar lista de persoane se modifica prin stergerea persoanei cu personID-ul dat
        '''
        self.__validator.validare_id(personID)
        self.__repo.sterge(personID)

    def modifica_persoana(self,personID:int, nume_nou:str, adresa_nou:str):
        '''
        Functie ce modifica o persoana din lista
        :param: personID
        :type: int
        :param: nume_nou
        :type: string
        :param: adresa_noua
        :type: string
        :return: none dar lista de persoane se modifica prin modificarea persoanei cu personID-ul dat
        '''
        self.__validator.validare_id(personID)
        self.__repo.modifica(personID,nume_nou,adresa_nou)
        
    def cauta_persoana(self, personID:int):
        '''
        Functie ce afiseaza daca exista aceasta persoana din lista
        :param: personID
        :type: int
        :return: persoana
        '''
        self.__validator.validare_id(personID)
        return self.__repo.cauta_persoana(personID)

