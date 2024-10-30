class Validare:
    def __init__(self):
        pass
    
    def validare_cafea(self, cafea):
        eroare=[]
        if cafea.get_nume() == '':
            eroare.append('Numele trebuie sa contina caractere. ')
        elif cafea.get_tara_origine() == '':
            eroare.append('Tara trebuie sa contina caractere. ')
        elif cafea.get_pret() <=0:
            eroare.append('Pretul trebuie sa fie pozitiv. ')
        if len(eroare)>0:
            raise ValueError(eroare)