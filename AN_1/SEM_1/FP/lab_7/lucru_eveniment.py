class Eveniment:
    def __init__(self, ID, data_zi, data_luna, data_an , timp, descriere):
        self.ID = ID
        self.zi = data_zi
        self.luna = data_luna
        self.an = data_an
        self.timp = timp
        self.descriere = descriere

    def get_id(self):
        '''
        Functie ce returneaza id-ul unui eveniment
        :param: none
        :return: id-ul unui eveniment
        :rtype: int
        '''
        return self.ID
    
    def get_zi(self):
        '''
        Functie ce returneaza ziua unui eveniment
        :param: none
        :return: ziua unui eveniment
        :rtype: int
        '''
        return self.zi
    
    def get_luna(self):
        '''
        Functie ce returneaza luna unui eveniment
        :param: none
        :return: luna unui eveniment
        :rtype: int
        '''
        return self.luna
    
    def get_an(self):
        '''
        Functie ce returneaza anul unui eveniment
        :param: none
        :return: anul unui eveniment
        :rtype: int
        '''
        return self.an
    
    def get_data(self):
        '''
        Functie ce returneaza data unui eveniment
        :param: none
        :return: data unui eveniment
        :rtype: int
        '''
        return str(self.zi) + '/' + str(self.luna) + '/' + str(self.an)
    
    def get_timp(self):
        '''
        Functie ce returneaza timpul unui eveniment
        :param: none
        :return: timpul unui eveniment
        :rtype: int
        '''
        return self.timp
    
    def get_descriere(self):
        '''
        Functie ce returneaza descrierea unui eveniment
        :param: none
        :return: descrierea unui eveniment
        :rtype: string
        '''
        return self.descriere
    
    def set_zi(self, zi_noua):
        '''
        Functie ce modifica ziua unui eveniment
        :param: none
        :return: none dar schimba ziua unui eveniment
        :rtype: string
        '''
        self.zi = zi_noua

    def set_luna(self, luna_noua):
        '''
        Functie ce modifica luna unui eveniment
        :param: none
        :return: none dar schimba luna unui eveniment
        :rtype: string
        '''
        self.luna = luna_noua

    def set_an(self, an_nou):
        '''
        Functie ce modifica anul unui eveniment
        :param: none
        :return: none dar schimba anul unui eveniment
        :rtype: string
        '''
        self.an = an_nou

    def set_timp(self, timp_nou):
        '''
        Functie ce modifica timpul unui eveniment
        :param: none
        :return: none dar schimba timpul unui eveniment
        :rtype: string
        '''
        self.timp = timp_nou

    def set_descriere(self, descriere_noua):
        '''
        Functie ce modifica descrierea unui eveniment
        :param: none
        :return: none dar schimba descrierea unui eveniment
        :rtype: string
        '''
        self.descriere = descriere_noua
        
    def __str__(self):
        return str(self.get_id()) + ' ~ ' + self.get_data() + ' ~ ' + str(self.get_timp()) + ' ~ ' + self.get_descriere()
    
    def __eq__(self, other):
        return self.ID == other.get_id() and self.zi == other.get_zi() and self.luna == other.get_luna() and self.an == other.get_an() and self.timp == other.get_timp() and self.descriere == other.get_descriere()

