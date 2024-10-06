class Validator:
    def __init__(self):
        pass

    def validare_persoana(self, persoana):
        '''
        Functie ce valideaza o persoana
        :param: persoana - obiect de tip Persoana
        :return: -
        '''
        erorrs = []
        if int(persoana.get_id()) <= 0:
            erorrs.append('ID-ul persoanei trebuie sa fie mai mare decat 0')
        if persoana.get_nume() == '':
            erorrs.append('Numele persoanei trebuie sa contina caractere')
        if persoana.get_adresa() == '':
            erorrs.append('Adresa persoanei trebuie sa contina caractere')
        if len(erorrs) > 0:
            raise ValueError(erorrs)
        
    def validare_eveniment(self, eveniment):
        '''
        Functie ce valideaza un eveniment
        :param: eveniment - obiect de tip Eveniment
        :return: -
        '''
        erorrs = []
        if int(eveniment.get_id()) <= 0:
            erorrs.append('ID-ul evenimentului trebuie sa fie mai mare decat 0')
        if int(eveniment.get_zi()) < 1 or int(eveniment.get_zi()) > 31:
            erorrs.append('Ziua evenimentului trebuie sa fie cuprinsa intre 1 si 31')
        if int(eveniment.get_luna()) < 1 or int(eveniment.get_luna()) > 12:
            erorrs.append('Luna evenimentului trebuie sa fie cuprinsa intre 1 si 12')
        if int(eveniment.get_an()) < 0 or int(eveniment.get_an()) > 2023:
            erorrs.append('Anul evenimentului trebuie sa fie cuprinsa intre 1 si 2023')
        if int(eveniment.get_timp()) < 0 or int(eveniment.get_timp()) > 24:
            erorrs.append('Timpul evenimentului trebuie sa fie cuprins intre 0 si 24 ore')
        if eveniment.get_descriere() == '':
            erorrs.append('Descrierea evenimentului trebuie sa contina caractere')
        if len(erorrs) > 0:
            raise ValueError(erorrs)
        
    def validare_id(self,id):
        '''
        Functie ce valideaza un id al unei persoane sau eveniment
        :param: id 
        :type: int
        :return: -
        '''
        erorrs = []
        id=int(id)
        if id < 0:
            erorrs.append('ID-ul trebuie sa fie mai mare decat 0')
        if len(erorrs) > 0:
            raise ValueError(erorrs)
