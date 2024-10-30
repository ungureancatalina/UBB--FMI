from domain.lucru_eveniment import Eveniment


class RepositoryEvenimente:
    def __init__(self):
        self.__lista_evenimente= []
      
    def get_lista_evenimente(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return self.__lista_evenimente  
      
    def cauta_eveniment(self, ID:int):
        '''
        Functie ce afiseaza daca exista acest eveniment din lista
        :param: ID
        :type: int
        :return: eveniment
        '''
        for eveniment in self.__lista_evenimente:
            if eveniment.get_id()==ID:
                return eveniment
        return None    
        
    def adauga(self, eveniment:Eveniment):
        '''
        Functie ce adauga un eveniment din lista
        :param: eveniment
        :type: Eveniment
        :return: -; lista de evenimente se modifica prin adaugarea la sfarsit a evenimentului dat
        :rtype: -;
        :raises: ValueError daca exista deja eveniment cu id-ul dat
        '''
        eveniment_gasit= self.cauta_eveniment(eveniment.get_id())
        if eveniment_gasit is not(None):
            raise ValueError("Exista deja evenimentul cu acest id.")
        self.__lista_evenimente.append(eveniment)
        
    def sterge(self, ID:int):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: -; lista de evenimente se modifica prin stergerea evenimentului dat
        :rtype: -;
        :raises: ValueError daca nu exista eveniment cu id-ul dat
        '''
        eveniment_gasit= self.cauta_eveniment(ID)
        if eveniment_gasit is None:
            raise ValueError("Nu exista eveniment cu acest id.")
        self.__lista_evenimente.remove(eveniment_gasit)
        
    def modifica(self,ID:int, data_zi_nou:int, data_luna_nou:int, data_an_nou:int, timp_nou:int, descriere_nou:str):
        '''
        Functie ce modifica un eveniment din lista
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
        :return: -; lista de evenimente se modifica prin schimbarea datelor evenimentului
        :rtype: -;
        :raises: ValueError daca nu exista eveniment cu id-ul dat
        '''
        eveniment_gasit= self.cauta_eveniment(ID)
        if eveniment_gasit is None:
            raise ValueError("Nu exista eveniment cu acest id.")
        eveniment_gasit.set_zi(data_zi_nou)
        eveniment_gasit.set_luna(data_luna_nou)
        eveniment_gasit.set_an(data_an_nou)
        eveniment_gasit.set_timp(timp_nou)
        eveniment_gasit.set_descriere(descriere_nou)
        
       
class FileRepositoryEvenimente(RepositoryEvenimente):
    def __init__(self, filename):
       RepositoryEvenimente.__init__(self)
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
                ID = int(elements[0])
                data=elements[1]
                zi,luna,an = data.split('/')
                timp = int(elements[2])
                descriere= elements[3]
                eveniment = Eveniment(ID, zi, luna, an, timp, descriere)
                RepositoryEvenimente.adauga(self,eveniment)
                
    def __salvare_fisier(self):
        with open(self.__filename,"w") as f:
            lista_evenimente=RepositoryEvenimente.get_lista_evenimente(self)
            for eveniment in lista_evenimente:
                data=[eveniment.get_zi(),eveniment.get_luna(),eveniment.get_an()]
                data=[str(data_detalii) for data_detalii in data]
                date='/'.join(data)
                elements = [eveniment.get_id(),date, eveniment.get_timp(), eveniment.get_descriere()]
                elements = [str(element) for element in elements]
                line = ', '.join(elements) + '\n'
                f.write(line)
                
    def adauga(self,eveniment):
        '''
        Functie ce adauga un eveniment din lista
        :param: eveniment
        :type: Eveniment
        :return: -; lista de evenimente se modifica prin adaugarea la sfarsit a evenimentului dat
        :rtype: -;
        '''
        RepositoryEvenimente.adauga(self,eveniment)
        self.__salvare_fisier()
        
    def adauga_default(self):
        '''
        Functie ce adauga cateva evenimente in lista
        :param: none
        :return: none dar adauga mai multe evenimente in lista
        '''
        self.adauga_eveniment(1, 10, 2, 2020, 2, 'Boardgames')
        self.adauga_eveniment(2, 5, 3, 2023, 4, 'Meeting')
        self.adauga_eveniment(3, 9, 12, 2021, 3, 'Petrecere')
        self.adauga_eveniment(4, 3, 7, 2022, 6, 'Targ')
        self.adauga_eveniment(5, 8, 10, 2020, 3, 'Cinema')
        
    def sterge(self, ID:int):
        '''
        Functie ce sterge un eveniment din lista
        :param: ID
        :type: int
        :return: -; lista de evenimente se modifica prin stergerea evenimentului dat
        :rtype: -;
        '''
        RepositoryEvenimente.sterge(self,ID)
        self.__salvare_fisier()
        
    def modifica(self,ID:int, data_zi_nou:int, data_luna_nou:int, data_an_nou:int, timp_nou:int, descriere_nou:str):
        '''
        Functie ce modifica un eveniment din lista
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
        :return: -; lista de evenimente se modifica prin schimbarea datelor evenimentului
        :rtype: -;
        '''
        RepositoryEvenimente.modifica(self,ID,data_zi_nou,data_luna_nou,data_an_nou,timp_nou,descriere_nou)
        self.__salvare_fisier()
        
    def get_lista_persoane(self):
        '''
        Functie ce returneaza lista de evenimente
        :param: none
        :return: lista de evenimente
        :rtype: list
        '''
        return RepositoryEvenimente.get_lista_evenimente(self)  
      
    def cauta_eveniment(self, ID:int):
        '''
        Functie ce afiseaza daca exista acest eveniment din lista
        :param: ID
        :type: int
        :return: eveniment
        '''
        return RepositoryEvenimente.cauta_eveniment(self,ID)