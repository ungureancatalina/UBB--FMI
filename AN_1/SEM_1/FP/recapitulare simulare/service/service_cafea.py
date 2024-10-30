from repository.repository_cafea import RepositoryCafeaFisier
from domain.lucru_cafea import Cafea
from domain.validator import Validare

class ServiceCafea:
    def __init__(self,lista_cafele:RepositoryCafeaFisier,validare:Validare):
        self.__lista_cafele=lista_cafele
        self.__validare=validare
        
    def get_lista(self):
        return self.__lista_cafele.get_lista()
    
    def adauga_cafea(self,nume:str,tara_origine:str,pret:float):
        cafea=Cafea(nume,tara_origine,pret)
        self.__validare.validare_cafea(cafea)
        self.__lista_cafele.adauga_cafea(cafea)
        
    def adauga_by_default_cafea(self):
        self.__lista_cafele.adauga_by_default_cafea()
    
    def sterge_cafea(self,pret:float):
        self.__lista_cafele.sterge_cafea(pret)
        
    def cauta_cafea(self,nume:str):
        for cafea in self.__lista_cafele.get_lista():
            if cafea.get_nume()==nume:
                return cafea
        return None   
        
    def sorteaza_cafea(self): 
        lista=self.__lista_cafele.get_lista()
        lista_sortata=sorted(lista, key=lambda cafea: cafea.get_tara_origine())
        return lista_sortata
        
    def raport_cafea(self):            
        ...