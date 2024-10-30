class Cafea:
    def __init__(self,nume:str,tara_origine:str,pret:float):
        self.__nume=nume
        self.__tara_origine=tara_origine
        self.__pret=pret
        
    def get_nume(self):
        return self.__nume
    
    def get_tara_origine(self):
        return self.__tara_origine

    def get_pret(self):
        return self.__pret
    
    def __str__(self):
        return 'Numele: '+ self.get_nume()+' / Tara de origine: '+ self.get_tara_origine()+' / Pretul: '+str(self.get_pret())
    
    def __eq__(self, other):
        return self.get_nume()==other.get_nume() and  self.get_tara_origine()==other.get_tara_origine() and self.get_pret()==other.get_pret()