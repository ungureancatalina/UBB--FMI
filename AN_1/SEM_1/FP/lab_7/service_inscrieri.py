from repository.repository_inscrieri import RepositoryInscrieri
from repository.repository_evenimente import RepositoryEvenimente
from repository.repository_persoane import RepositoryPersoane
from domain.validare import Validator
from domain.lucru_persoana import Persoana
from domain.lucru_eveniment import Eveniment
from domain.lucru_inscrieri import Inscriere
from utils.sortare import *
import datetime

def cmp_descriere(element):
    '''
    Functie ce returneaza descrierea aflata pe poz 5 a elementului din lista
    :param: element
    :type: list
    :return: valoarea de pe poz 5
    :rtype: str
    '''
    return element[5]

def cmp_timp(element):
    '''
    Functie ce returneaza timpul aflat pe poz 4 a elementului din lista
    :param: element
    :type: list
    :return: valoarea de pe poz 4
    :rtype: int
    '''
    return element[4]

def cmp_data(element):
    '''
    Functie ce returneaza data aflata pe poz 3-2-1 a elementului din lista
    :param: element
    :type: list
    :return: data elementului
    :rtype: date
    '''
    data=datetime.date(element[3],element[2],element[1])
    return data

class ServiceInscrieri:
    def __init__(self,validator:Validator,repository:RepositoryInscrieri,lista_evenimente:RepositoryEvenimente,lista_persoane:RepositoryPersoane):
        self.__repo = repository
        self.__validator = validator
        self.__lista_evenimente =lista_evenimente
        self.__lista_persoane=lista_persoane
        
    def adauga_inscriere(self,persoanaID,evenimentID):
        '''
        Functie ce adauga pentru id-ul persoanei id-ul unui eveniment
        :param: personID
        :type: int
        :param: evenimentID
        :type: int
        :return: -
        '''
        self.__validator.validare_id(persoanaID)
        self.__validator.validare_id(evenimentID)
        inscriere=Inscriere(persoanaID,evenimentID)
        self.__repo.adauga(inscriere)
        
    def adauga_by_default_inscrieri(self):  
        '''
        Functie ce adauga cateva inscrieri in lista
        :param: none
        :return: none dar adauga mai multe inscrieri in lista
        '''
        self.__repo.adauga_default() 
        
    def get_lista_inscrieri(self):
        '''
        Functie ce returneaza lista de inscrieri
        :param: none
        :return: lista de inscrieri
        :rtype: list
        '''
        return self.__repo.get_lista_inscrieri()
    
    def get_evenimentele_persoanei(self,ID:int):
        '''
        Functie ce returneaza lista de evenimente ale unei persoane
        :param: ID
        :type: int
        :return: lista de evenimente ale unei persoane
        :rtype: list
        '''
        self.__validator.validare_id(ID)
        return self.__repo.get_evenimentele_persoanei(ID)

    def get_persoanele_evenimentului(self,ID:int):
        '''
        Functie ce returneaza lista de persoane ale unui eveniment
        :param: ID
        :type: int
        :return: lista de persoane ale unui eveniment
        :rtype: list
        '''
        self.__validator.validare_id(ID)
        return self.__repo.get_persoanele_evenimentului(ID)
    
    def evenimente_ordonate_descriere(self,persoanaID:int):
        """
        Creeaza lista de evenimente la care participa o persoana ordonate dupa descriere
        :param: personID
        :type: int
        :return: lista de evenimente ale persoanei
        :rtype: list
        """
        self.__validator.validare_id(persoanaID)
        evenimentele=[]
        lista_principala=[]
        evenimentele=self.get_evenimentele_persoanei(persoanaID)
        for index in range(len(evenimentele)):
            eveniment=self.__lista_evenimente.cauta_eveniment(evenimentele[index])
            lista_principala.append([eveniment.get_id(),eveniment.get_zi(),eveniment.get_luna(),eveniment.get_an(),eveniment.get_timp(),eveniment.get_descriere()])
        insertion_sort(lista_principala,key=cmp_descriere,reverse=False)
        return lista_principala
        # lista_evenimentele_persoanei=[]
        # for index in range(len(evenimentele)):
            # eveniment=self.__lista_evenimente.cauta_eveniment(evenimentele[index])
            # lista_evenimentele_persoanei.append(eveniment)
        # lista_sortata= sorted(lista_evenimentele_persoanei, key=lambda eveniment:eveniment.get_descriere())
        # return lista_sortata
    
    def evenimente_ordonate_data(self,persoanaID:int):
        """
        Creeaza lista de evenimente la care participa o persoana ordonate dupa data
        :param: personID
        :type: int
        :return: lista de evenimente ale persoanei
        :rtype: list
        """
        self.__validator.validare_id(persoanaID)
        evenimentele=[]
        lista_principala=[]
        evenimentele=self.get_evenimentele_persoanei(persoanaID)
        for index in range(len(evenimentele)):
            eveniment=self.__lista_evenimente.cauta_eveniment(evenimentele[index])
            lista_principala.append([eveniment.get_id(),eveniment.get_zi(),eveniment.get_luna(),eveniment.get_an(),eveniment.get_timp(),eveniment.get_descriere()])
        comb_sort(lista_principala,key=cmp_data,reverse=False)
        return lista_principala
        # lista_evenimentele_persoanei=[]
        # for index in range(len(evenimentele)):
        #    eveniment=self.__lista_evenimente.cauta_eveniment(evenimentele[index])
        #    lista_evenimentele_persoanei.append(eveniment)
        # for index1 in range(len(lista_evenimentele_persoanei)-1):
        #     eveniment1=lista_evenimentele_persoanei[index1]
        #     for index2 in range(index1,len(lista_evenimentele_persoanei)):
        #         eveniment2=lista_evenimentele_persoanei[index2]
        #         mai_mare=0
        #         if eveniment1.get_an()>=eveniment2.get_an():
        #             if eveniment1.get_luna()>=eveniment2.get_luna():
        #                 if eveniment1.get_zi()>eveniment2.get_zi():
        #                     mai_mare=1
        #         if mai_mare==1:
        #             aux=lista_evenimentele_persoanei[index1]
        #             lista_evenimentele_persoanei[index1]=lista_evenimentele_persoanei[index2]
        #             lista_evenimentele_persoanei[index2]=aux
        # return lista_evenimentele_persoanei
        
    def evenimente_ordonate_timp_descriere(self,persoanaID:int):
        """
        Creeaza lista de evenimente la care participa o persoana ordonate dupa timp,iar apoi dupa descriere
        :param: personID
        :type: int
        :return: lista de evenimente ale persoanei
        :rtype: list
        """
        self.__validator.validare_id(persoanaID)
        evenimentele=[]
        lista_principala=[]
        evenimentele=self.get_evenimentele_persoanei(persoanaID)
        for index in range(len(evenimentele)):
            eveniment=self.__lista_evenimente.cauta_eveniment(evenimentele[index])
            lista_principala.append(eveniment)
        two_sorts(lista_principala,key=lambda eveniment:(eveniment.get_timp(),eveniment.get_descriere()),reverse=False)
        return lista_principala
    
    def cauta_persoana_lista_persoane(self,persoanele:list,persoana_curenta:int):
        """
        Cauta id-ul unei persoane in lista de persoane
        :param: persoanele
        :type: list
        :param: persoana_curenta
        :type: int
        :return: -;
        :rtype:
        """
        for index in persoanele:
            if index==persoana_curenta:
                return 1
        return 0
    
    
    # COMPLEXITATE
    # notam n=len(evenimentele)
    # Best Case: Theta(1) primul element este cel cautat
    # Worst Case: Theta(n) niciun element nu este cel cautat
    # Avarage Case: Theta(n) rezultatul Suma de la i=1 la n din P(i)*E(i) este (n+1)/2
    # Overall Case: O(n)
    def cauta_eveniment_lista_evenimente(self,evenimentele:list,eveniment_curent:int):
        """
        Cauta id-ul unui eveniment in lista de evenimente
        :param: evenimentele
        :type: list
        :param: eveniment_curent
        :type: int
        :return: -;
        :rtype:
        """
        for index in evenimentele:
            if index==eveniment_curent:
                return 1
        return 0
    
    def persoane_inscrise_multe_evenimente(self):
        """
        Afiseaza lista de persoane care participa la mai multe evenimente
        :param: none
        :return: -;
        :rtype:
        """
        max=0
        persoanele=[]
        lista_inscrieri=self.get_lista_inscrieri()
        for inscriere in lista_inscrieri:
            evenimentele=[]
            evenimentele=self.get_evenimentele_persoanei(inscriere.get_id_persoana())
            if len(evenimentele)>max:
                max=len(evenimentele)
        for inscriere in lista_inscrieri:
            evenimentele=[]
            evenimentele=self.get_evenimentele_persoanei(inscriere.get_id_persoana())
            if len(evenimentele)==max and self.cauta_persoana_lista_persoane(persoanele,inscriere.get_id_persoana())==0:
                persoanele.append(inscriere.get_id_persoana())
        return persoanele
    
    def evenimente_multi_participanti(self):
        """
        Afiseaza lista de evenimente la care participa cele mai multe persoane
        :param: none
        :return: -;
        :rtype:
        """
        max=0
        evenimentele=[]
        lista_inscrieri=self.get_lista_inscrieri()
        for inscriere in lista_inscrieri:
            persoanele=[]
            persoanele=self.get_persoanele_evenimentului(inscriere.get_id_eveniment())
            if len(persoanele)>max:
                max=len(persoanele)
        for inscriere in lista_inscrieri:
            persoanele=[]
            persoanele=self.get_persoanele_evenimentului(inscriere.get_id_eveniment())
            if len(persoanele)==max and self.cauta_eveniment_lista_evenimente(evenimentele,inscriere.get_id_eveniment())==0:
                evenimentele.append(inscriere.get_id_eveniment())
        return evenimentele
    
    def persoane_inscrise_la_n_evenimente(self,n_evenimente:int):
        """
        Afiseaza lista de persoane care participa la mai multe evenimente
        :param: none
        :return: -;
        :rtype:
        """
        list_persoane=[]
        persoanele=[0]*(len(self.__lista_persoane.get_lista_persoane())+1)
        lista_inscrieri=self.get_lista_inscrieri()
        for inscriere in lista_inscrieri:
            if persoanele[inscriere.get_id_persoana()]==0:
                lista_evenimente_pers=self.get_evenimentele_persoanei(inscriere.get_id_persoana())
                persoanele[inscriere.get_id_persoana()]=1
                if len(lista_evenimente_pers)==n_evenimente:
                    list_persoane.append(inscriere.get_id_persoana())
        return list_persoane