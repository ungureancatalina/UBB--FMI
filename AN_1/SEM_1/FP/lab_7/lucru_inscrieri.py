class Inscriere:
    def __init__(self, ID_persoana, ID_eveniment):
        self.ID_persoana = ID_persoana
        self.ID_eveniment = ID_eveniment

    def get_id_persoana(self):
        '''
        Functie ce returneaza id-ul unei persoane
        :param: none
        :return: id-ul unei persoane
        :rtype: int
        '''
        return self.ID_persoana
    
    def get_id_eveniment(self):
        '''
        Functie ce returneaza id-ul unui eveniment
        :param: none
        :return: id-ul unui eveniment
        :rtype: int
        '''
        return self.ID_eveniment
    
    def __str__(self):
        return str(self.get_id_persoana() + ' --- ' + self.get_id_eveniment())
    


