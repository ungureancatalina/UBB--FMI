from domain.lucru_cafea import Cafea

class RepositoryCafea:
    def __init__(self):
        self.__lista_cafele=[]
        
    def get_lista(self):
        return self.__lista_cafele
    
    def adauga_cafea(self,cafea:Cafea):
        if self.cauta_cafea(cafea.get_nume()) is not(None):
            raise ValueError('Exista deja acest tip de cafea')
        self.__lista_cafele.append(cafea)
        
    def adauga_by_default_cafea(self):
        cafea=Cafea('a','aa',10)
        self.__lista_cafele.append(cafea)
        cafea=Cafea('b','bb',20)
        self.__lista_cafele.append(cafea)
        cafea=Cafea('c','cc',30)
        self.__lista_cafele.append(cafea)
        cafea=Cafea('d','dd',40)
        self.__lista_cafele.append(cafea)
        cafea=Cafea('e','ee',50)
        self.__lista_cafele.append(cafea)
        
    def sterge_cafea(self,pret:float):
        cafea=self.cauta_cafea_pret(pret)
        if cafea is None:
            raise ValueError('Nu exista acest tip de cafea')
        self.__lista_cafele.remove(cafea)
            
    def cauta_cafea_pret(self,pret:float):    
        for cafea in self.__lista_cafele:
            if cafea.get_pret()==pret:
                return cafea
        return None
        
    def cauta_cafea(self,nume:str):    
        for cafea in self.__lista_cafele:
            if cafea.get_nume()==nume:
                return cafea
        return None
            
class RepositoryCafeaFisier:
    def __init__(self,filename):
        RepositoryCafea.__init__(self)
        self.__filename=filename
        self.incarca_fisier()
    
    def incarca_fisier(self):
        with open(self.__filename,"r") as f:
            lines=f.readlines()
            for line in lines:
                if line=="\n":
                    break
                elemente = line.split(',')
                elemente = [element.strip() for element in elemente]
                nume = elemente[0]
                tara_origine = elemente[1]
                pret = int(elemente[2])
                cafea =Cafea(nume,tara_origine,pret)
                RepositoryCafea.adauga_cafea(self,cafea)
                
    def elimina_fisier(self):
        with open(self.__filename,'w') as f:
            pass
        
    def salvare_fisier(self):
        with open(self.__filename,"w") as f:
            lista=RepositoryCafea.get_lista(self)
            for cafea in lista:
                elemente = [cafea.get_nume(),cafea.get_tara_origine(),cafea.get_pret()]
                elemente = [str(element) for element in elemente]
                line = ', '.join(elemente) + '\n'
                f.write(line)
    
    def get_lista(self):
        return RepositoryCafea.get_lista(self)
    
    def adauga_cafea(self,cafea:Cafea):
        RepositoryCafea.adauga_cafea(self,cafea)
        self.salvare_fisier()
        
    def adauga_by_default_cafea(self):
        RepositoryCafea.adauga_by_default_cafea(self)
        self.salvare_fisier()
        
    def sterge_cafea(self,pret:float):
        RepositoryCafea.sterge_cafea(self,pret)
        self.salvare_fisier()
        
    def cauta_cafea_pret(self,pret:float):    
        return RepositoryCafea.cauta_cafea_pret(self,pret)
        
    def cauta_cafea(self,nume:str):    
        return RepositoryCafea.cauta_cafea(self,nume)
        
        