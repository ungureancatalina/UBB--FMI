from domain.lucru_persoana import Persoana

class RepositoryPersoane:
    def __init__(self):
        self.__lista_persoane= []
      
    def get_lista_persoane(self):
        '''
        Functie ce returneaza lista de persoane
        :param: none
        :return: lista de persoane
        :rtype: list
        '''
        return self.__lista_persoane  
    
    # RECURSIV
    def cauta_recursiv(self,lista,id,n,index):
        if n==index:
            return None
        if n>index and lista[index].get_id()==id:
            return lista[index]
        return self.cauta_recursiv(lista,id,n,index+1)
    
    def cauta_persoana(self, personID:int):
        '''
        Functie ce afiseaza daca exista aceasta persoana din lista
        :param: personID
        :type: int
        :return: persoana
        '''
        return self.cauta_recursiv(self.__lista_persoane,personID,len(self.__lista_persoane),0)
        # for persoana in self.__lista_persoane:
        #     if persoana.get_id()==personID:
        #         return persoana
        # return None    
        
    def adauga(self, persoana:Persoana):
        '''
        Functie ce adauga o persoana din lista
        :param: persoana
        :type: Persoana
        :return: -; lista de persoane se modifica prin adaugarea la sfarsit a persoanei date
        :rtype: -;
        :raises: ValueError daca exista deja persoana cu id-ul dat
        '''
        persoana_gasita= self.cauta_persoana(persoana.get_id())
        if persoana_gasita is not(None):
            raise ValueError("Exista deja persoana cu acest id.")
        self.__lista_persoane.append(persoana)
        
    def sterge(self, personID:int):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: -; lista de persoane se modifica prin stergerea persoanei date
        :rtype: -;
        :raises: ValueError daca nu exista persoana cu id-ul dat
        '''
        persoana_gasita= self.cauta_persoana(personID)
        if persoana_gasita is None:
            raise ValueError("Nu exista persoana cu acest id.")
        self.__lista_persoane.remove(persoana_gasita)
        
    def modifica(self,personID:int, nume_nou:str, adresa_nou:str):
        '''
        Functie ce modifica o persoana din lista
        :param: personID
        :type: int
        :param: nume_nou
        :type: string
        :param: adresa_noua
        :type: string
        :return: -; lista de persoane se modifica prin schimbarea datelor persoanei
        :rtype: -;
        :raises: ValueError daca nu exista persoana cu id-ul dat
        '''
        persoana_gasita= self.cauta_persoana(personID)
        if persoana_gasita is None:
            raise ValueError("Nu exista persoana cu acest id.")
        persoana_gasita.set_nume(nume_nou)
        persoana_gasita.set_adresa(adresa_nou)
        
        
class FileRepositoryPersoane(RepositoryPersoane):
    def __init__(self, filename):
       RepositoryPersoane.__init__(self)
       self.__filename=filename
       self.__incarcare_fisier()
       
    def __incarcare_fisier(self):
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    break
                elements = line.split(',')
                elements = [element.strip() for element in elements]
                personID = int(elements[0])
                nume = elements[1]
                adresa = elements[2]
                persoana =Persoana(personID,nume,adresa)
                RepositoryPersoane.adauga(self,persoana)
                
    def __salvare_fisier(self):
        with open(self.__filename,"w") as f:
            lista_persoane=RepositoryPersoane.get_lista_persoane(self)
            for persoana in lista_persoane:
                elements = [persoana.get_id(), persoana.get_nume(), persoana.get_adresa()]
                elements = [str(element) for element in elements]
                line = ', '.join(elements) + '\n'
                f.write(line)
                
    def adauga_default(self):
        '''
        Functie ce adauga cateva persoane in lista
        :param: none
        :return: none dar adauga mai multe persoane in lista
        '''
        self.adauga_persoana(1, 'Mircea', 'Strada Plopilor')
        self.adauga_persoana(2, 'Ana', 'Strada Teiului')
        self.adauga_persoana(3, 'Maria', 'Strada Iazului')
        self.adauga_persoana(4, 'Andrei', 'Strada Lacului')
        self.adauga_persoana(5, 'Carla', 'Strada Trandafirilor')
                   
    def adauga(self,persoana):
        '''
        Functie ce adauga o persoana din lista
        :param: persoana
        :type: Persoana
        :return: -; lista de persoane se modifica prin adaugarea la sfarsit a persoanei date
        :rtype: -;
        '''
        RepositoryPersoane.adauga(self,persoana)
        self.__salvare_fisier()
        
    def sterge(self, personID:int):
        '''
        Functie ce sterge o persoana din lista
        :param: personID
        :type: int
        :return: -; lista de persoane se modifica prin stergerea persoanei date
        :rtype: -;
        '''
        RepositoryPersoane.sterge(self,personID)
        self.__salvare_fisier()
        
    def modifica(self,personID:int, nume_nou:str, adresa_nou:str):
        '''
        Functie ce modifica o persoana din lista
        :param: personID
        :type: int
        :param: nume_nou
        :type: string
        :param: adresa_nou
        :type: string
        :return: -; lista de persoane se modifica prin schimbarea datelor persoanei
        :rtype: -;
        '''
        RepositoryPersoane.modifica(self,personID,nume_nou,adresa_nou)
        self.__salvare_fisier()
        
    def get_lista_persoane(self):
        '''
        Functie ce returneaza lista de persoane
        :param: none
        :return: lista de persoane
        :rtype: list
        '''
        return RepositoryPersoane.get_lista_persoane(self)  
      
    def cauta_persoana(self, personID:int):
        '''
        Functie ce afiseaza daca exista aceasta persoana din lista
        :param: personID
        :type: int
        :return: persoana
        '''
        return RepositoryPersoane.cauta_persoana(self,personID)