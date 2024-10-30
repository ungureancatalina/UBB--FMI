class Persoana:
    def __init__(self, personID:int, nume:str, adresa:str):
        self.lista=[personID,nume,adresa]

    def get_id(self):
        '''
        Functie ce returneaza id-ul unei persoane
        :param: none
        :return: id-ul unei persoane
        :rtype: int
        '''
        return self.lista[0]
    
    def get_nume(self):
        '''
        Functie ce returneaza numele unei persoane
        :param: none
        :return: numele unei persoane
        :rtype: string
        '''
        return self.lista[1]
    
    def get_adresa(self):
        '''
        Functie ce returneaza adresa unei persoane
        :param: none
        :return: adresa unei persoane
        :rtype: string
        '''
        return self.lista[2]
    
    def set_nume(self, nume_nou):
        '''
        Functie ce modifica numele unei persoane
        :param: none
        :return: none dar schimba numele unei persoane
        :rtype: string
        '''
        self.lista[1] = nume_nou

    def set_adresa(self, adresa_noua):
        '''
        Functie ce modifica adresa unei persoane
        :param: none
        :return: none dar schimba adresa unei persoane
        :rtype: string
        '''
        self.lista[2] = adresa_noua

    def __str__(self):
        return str(self.get_id()) + ' ~ ' + self.get_nume() + ' ~ ' + self.get_adresa()
    
    def __eq__(self, other):
        return self.lista[0] == other.get_id() and self.lista[1] == other.get_nume() and self.lista[2] == other.get_adresa()