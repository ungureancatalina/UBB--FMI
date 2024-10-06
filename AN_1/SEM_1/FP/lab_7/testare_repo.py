from domain.validare import Validator
from repository.repository_evenimente import RepositoryEvenimente,FileRepositoryEvenimente
from repository.repository_persoane import RepositoryPersoane,FileRepositoryPersoane
from repository.repository_inscrieri import RepositoryInscrieri
from domain.lucru_persoana import Persoana
from domain.lucru_eveniment import Eveniment
from domain.lucru_inscrieri import Inscriere
from utils.utils_fisiere import *
import unittest

def test_adauga_persoana():
    validator = Validator()
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    test_lista_persoane = lista_persoane.get_lista_persoane()
    assert (len(test_lista_persoane) == 1)
    try:
        persoana=Persoana(1,'', 'Strada Plopilor')
        lista_persoane.adauga(persoana)
        assert False
    except ValueError:
        assert True

def test_adauga_eveniment():
    validator = Validator()
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    test_lista_evenimente = lista_evenimente.get_lista_evenimente()
    assert (len(test_lista_evenimente) == 1)
    try:
        eveniment=Eveniment(1, 2, 3, 2023, 4, '')
        lista_evenimente.adauga(eveniment)
        assert False
    except ValueError:
        assert True
        
def test_sterge_persoana():
    validator = Validator()
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    lista_persoane.sterge(1)
    assert (len(RepositoryPersoane.get_lista_persoane(lista_persoane)) == 0)
    persoana=Persoana(2,'Maria', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    assert (len(RepositoryPersoane.get_lista_persoane(lista_persoane)) == 1)

def test_sterge_eveniment():
    validator = Validator()
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    lista_evenimente.sterge(1)
    assert (len(RepositoryEvenimente.get_lista_evenimente(lista_evenimente)) == 0)
    eveniment=Eveniment(2, 5, 12, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    assert (len(RepositoryEvenimente.get_lista_evenimente(lista_evenimente)) == 1)

def test_modifica_persoana():
    validator = Validator()
    lista_persoane= RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    lista_persoane = RepositoryPersoane()
    lista_persoane.adauga(persoana)
    lista_persoane.modifica(1,'Maria','Strada Plop')
    pers=Persoana(1,'Maria','Strada Plop')
    assert (lista_persoane.cauta_persoana(1) == pers)

def test_modifica_eveniment():
    validator = Validator()
    lista_evenimente = RepositoryEvenimente()
    eveniment = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    lista_evenimente = RepositoryEvenimente()
    lista_evenimente.adauga(eveniment)
    lista_evenimente.modifica(1,4, 3, 2022, 5, 'Boardgames')
    ev= Eveniment(1,4, 3, 2022, 5, 'Boardgames')
    assert(ev == lista_evenimente.cauta_eveniment(1))

def test_cauta_persoana():
    validator = Validator()
    lista_persoane = RepositoryPersoane()
    persoana_initial=Persoana(1,'Ana', 'Strada Plopilor')
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    persoana=Persoana(2,'Ema', 'Strada Teilor')
    lista_persoane.adauga(persoana)
    persoana=lista_persoane.cauta_persoana(1)
    assert (persoana==persoana_initial)

def test_cauta_eveniment():
    validator = Validator()
    eveniment_initial=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente = RepositoryEvenimente()
    eveniment = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    eveniment = Eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_evenimente.adauga(eveniment)
    eveniment=lista_evenimente.cauta_eveniment(1)
    assert (eveniment==eveniment_initial)
     
def test_cauta_inscriere():
    validator = Validator()
    lista_persoane = RepositoryPersoane()
    persoana=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga(persoana)
    lista_evenimente = RepositoryEvenimente()
    eveniment=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga(eveniment)
    lista_inscrieri= RepositoryInscrieri(lista_persoane,lista_evenimente)
    inscriere=Inscriere(1,1)
    lista_inscrieri.adauga(inscriere)
    assert(lista_inscrieri.cauta_inscriere(1,1)==True)
    assert(lista_inscrieri.cauta_inscriere(1,3)==False)
    
class TesteFisier(unittest.TestCase):
    def setUp(self):
        elimina_continut_fisier("data/persoane.txt")
        
    def test_adauga_persoana_fisier(self):
        test_repo = FileRepositoryPersoane("data/persoane.txt")
        persoana = Persoana(6,'Ema', 'Strada Florilor')
        test_repo.adauga(persoana)
        assert (len(test_repo.get_lista_persoane()) == 1)
        persoana=Persoana(7,'Corina', 'Strada Plopilor')
        test_repo.adauga(persoana)
        assert (len(test_repo.get_lista_persoane()) == 2)
        test_repo.sterge(6)
        test_repo.sterge(7)

    def test_sterge_persoana_fisier(self):
        test_repo = FileRepositoryPersoane("data/persoane.txt")
        persoana = Persoana(6,'Ema', 'Strada Florilor')
        test_repo.adauga(persoana)
        persoana=Persoana(7,'Corina', 'Strada Plopilor')
        test_repo.adauga(persoana)
        test_repo.sterge(6)
        assert (len(test_repo.get_lista_persoane()) == 1)
        assert (test_repo.cauta_persoana(6) is None)
        test_repo.sterge(7)
        
    def test_modifica_persoana_fisier(self):
        test_repo = FileRepositoryPersoane("data/persoane.txt")
        persoana = Persoana(6,'Ema', 'Strada Florilor')
        test_repo.adauga(persoana)
        pers=Persoana(6,'Corina', 'Strada Plopilor')
        test_repo.modifica(6,'Corina', 'Strada Plopilor')
        assert (len(test_repo.get_lista_persoane()) == 1)
        assert (test_repo.cauta_persoana(6) == pers)
        test_repo.sterge(6)
        
    def test_cauta_persoana_fisier(self):
        test_repo = FileRepositoryPersoane("data/persoane.txt")
        persoana = Persoana(6,'Ema', 'Strada Florilor')
        test_repo.adauga(persoana)
        assert (len(test_repo.get_lista_persoane()) == 1)
        assert (test_repo.cauta_persoana(6) == persoana)
        assert (test_repo.cauta_persoana(7) is None)
        test_repo.sterge(6)
        
    def test_adauga_eveniment_fisier(self):
        test_repo = FileRepositoryEvenimente("data/evenimente.txt")
        eveniment = Eveniment(6, 12, 9, 2023, 3, 'Boooks')
        test_repo.adauga(eveniment)
        assert (len(test_repo.get_lista_evenimente()) == 1)
        eveniment=Eveniment(7, 10, 1, 2020, 3, 'Cinema')
        test_repo.adauga(eveniment)
        assert (len(test_repo.get_lista_evenimente()) == 2)
        test_repo.sterge(6)
        test_repo.sterge(7)

    def test_sterge_eveniment_fisier(self):
        test_repo = FileRepositoryEvenimente("data/evenimente.txt")
        eveniment = Eveniment(6, 12, 9, 2023, 3, 'Boooks')
        test_repo.adauga(eveniment)
        eveniment=Eveniment(7, 10, 1, 2020, 3, 'Cinema')
        test_repo.adauga(eveniment)
        test_repo.sterge(6)
        assert (len(test_repo.get_lista_evenimente()) == 1)
        assert (test_repo.cauta_eveniment(6) is None)
        test_repo.sterge(7)
        
    def test_modifica_eveniment_fisier(self):
        test_repo = FileRepositoryEvenimente("data/evenimente.txt")
        eveniment = Eveniment(6, 12, 9, 2023, 3, 'Boooks')
        test_repo.adauga(eveniment)
        ev=Eveniment(6, 10, 1, 2020, 3, 'Cinema')
        test_repo.modifica(6, 10, 1, 2020, 3, 'Cinema')
        assert (len(test_repo.get_lista_evenimente()) == 1)
        assert (test_repo.cauta_eveniment(6) == ev)
        test_repo.sterge(6)
        
    def test_cauta_eveniment_fisier(self):
        test_repo = FileRepositoryEvenimente("data/evenimente.txt")
        eveniment = Eveniment(6, 12, 9, 2023, 3, 'Boooks')
        test_repo.adauga(eveniment)
        assert (len(test_repo.get_lista_evenimente()) == 1)
        assert (test_repo.cauta_eveniment(6) == eveniment)
        assert (test_repo.cauta_eveniment(7) is None)
        test_repo.sterge(6)

    def run_all_test(self):
        self.test_adauga_persoana_fisier()
        self.test_cauta_persoana_fisier()
        self.test_modifica_persoana_fisier()
        self.test_sterge_persoana_fisier()
        self.test_adauga_eveniment_fisier()
        self.test_cauta_eveniment_fisier()
        self.test_modifica_eveniment_fisier()
        self.test_sterge_eveniment_fisier()

test_adauga_persoana()
test_adauga_eveniment()
test_sterge_persoana()
test_sterge_eveniment()
test_modifica_persoana()
test_modifica_eveniment()
test_cauta_persoana()
test_cauta_eveniment()
test_cauta_inscriere()

