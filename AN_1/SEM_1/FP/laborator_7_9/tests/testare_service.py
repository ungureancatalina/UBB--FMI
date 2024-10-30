from domain.validare import Validator
from service.service_persoane import ServicePersoane
from service.service_evenimente import ServiceEvenimente
from service.service_inscrieri import ServiceInscrieri
from repository.repository_evenimente import RepositoryEvenimente,FileRepositoryEvenimente
from repository.repository_persoane import RepositoryPersoane,FileRepositoryPersoane
from repository.repository_inscrieri import RepositoryInscrieri
from domain.lucru_persoana import Persoana
from domain.lucru_eveniment import Eveniment
from domain.lucru_inscrieri import Inscriere

def test_adauga_persoana():
    validator = Validator()
    repo = RepositoryPersoane()
    lista_persoane = ServicePersoane(validator,repo)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    test_lista_persoane = lista_persoane.get_lista_persoane()
    assert (len(test_lista_persoane) == 1)
    try:
        lista_persoane.adauga_persoana(1,'', 'Strada Plopilor')
        assert False
    except ValueError:
        assert True

def test_adauga_eveniment():
    validator = Validator()
    repo = RepositoryEvenimente()
    lista_evenimente = ServiceEvenimente(validator,repo)
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    test_lista_evenimente = lista_evenimente.get_lista_evenimente()
    assert (len(test_lista_evenimente) == 1)
    try:
        lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, '')
        assert False
    except ValueError:
        assert True
        
def test_sterge_persoana():
    validator = Validator()
    repo = RepositoryPersoane()
    lista_persoane = ServicePersoane(validator,repo)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.sterge_persoana(1)
    assert (len(ServicePersoane.get_lista_persoane(lista_persoane)) == 0)
    lista_persoane.adauga_persoana(2,'Maria', 'Strada Plopilor')
    assert (len(ServicePersoane.get_lista_persoane(lista_persoane)) == 1)

def test_sterge_eveniment():
    validator = Validator()
    repo = RepositoryEvenimente()
    lista_evenimente = ServiceEvenimente(validator,repo)
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.sterge_eveniment(1)
    assert (len(ServiceEvenimente.get_lista_evenimente(lista_evenimente)) == 0)
    lista_evenimente.adauga_eveniment(2, 5, 12, 2023, 4, 'Boardgames')
    assert (len(ServiceEvenimente.get_lista_evenimente(lista_evenimente)) == 1)

def test_modifica_persoana():
    validator = Validator()
    repo = RepositoryPersoane()
    persoana =Persoana(1,'Maria','Strada Plop')
    lista_persoane = ServicePersoane(validator,repo)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.modifica_persoana(1,'Maria','Strada Plop')
    assert (persoana == lista_persoane.cauta_persoana(1))

def test_modifica_eveniment():
    validator = Validator()
    repo = RepositoryEvenimente()
    eveniment = Eveniment(1,4, 3, 2022, 5, 'Boardgames')
    lista_evenimente = ServiceEvenimente(validator,repo)
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.modifica_eveniment(1,4, 3, 2022, 5, 'Boardgames')
    assert( eveniment == lista_evenimente.cauta_eveniment(1))

def test_cauta_persoana():
    validator = Validator()
    repo = RepositoryPersoane()
    lista_persoane = ServicePersoane(validator,repo)
    persoana_initial=Persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    persoana=lista_persoane.cauta_persoana(1)
    assert (persoana==persoana_initial)

def test_cauta_eveniment():
    validator = Validator()
    repo = RepositoryEvenimente()
    eveniment_initial=Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente = ServiceEvenimente(validator,repo)
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    eveniment=lista_evenimente.cauta_eveniment(1)
    assert (eveniment==eveniment_initial)
    
def test_adauga_inscriere():
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
    assert(inscriere.get_id_persoana()==1 and inscriere.get_id_eveniment()==1)
    assert(len(lista_inscrieri.get_lista_inscrieri())==1)
    
def test_evenimente_ordonate_descriere():
    validator = Validator()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceEvenimente(validator,repo1)
    lista_persoane = ServicePersoane(validator,repo2)
    repo3=RepositoryInscrieri(lista_evenimente,lista_persoane)
    lista_inscrieri=ServiceInscrieri(validator,repo3,lista_evenimente,lista_persoane)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(2,1)
    lista_evenimente_ordonate=lista_inscrieri.evenimente_ordonate_descriere(1)
    # lista_evenimente_ordonate=sort.insertion_sort(lista_inscrieri)
    assert(len(lista_evenimente_ordonate)==2)
    
def test_evenimente_ordonate_data():
    validator = Validator()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceEvenimente(validator,repo1)
    lista_persoane = ServicePersoane(validator,repo2)
    repo3=RepositoryInscrieri(lista_evenimente,lista_persoane)
    lista_inscrieri=ServiceInscrieri(validator,repo3,lista_evenimente,lista_persoane)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(2,1)
    lista_evenimente_ordonate=lista_inscrieri.evenimente_ordonate_data(1)
    assert(len(lista_evenimente_ordonate)==2)
    
def test_persoane_inscrise_multe_evenimente():
    validator = Validator()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceEvenimente(validator,repo1)
    lista_persoane = ServicePersoane(validator,repo2)
    repo3=RepositoryInscrieri(lista_evenimente,lista_persoane)
    lista_inscrieri=ServiceInscrieri(validator,repo3,lista_evenimente,lista_persoane)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(2,1)
    persoanele=lista_inscrieri.persoane_inscrise_multe_evenimente()
    assert(persoanele[0]==1)
    assert(len(persoanele)==1)
    
def test_persoane_inscrise_la_n_evenimente():
    validator= Validator()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceEvenimente(validator,repo1)
    lista_persoane = ServicePersoane(validator,repo2)
    repo3=RepositoryInscrieri(lista_evenimente,lista_persoane)
    lista_inscrieri=ServiceInscrieri(validator,repo3,lista_evenimente,lista_persoane)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(2,1)
    n_evenimente=2
    persoanele=lista_inscrieri.persoane_inscrise_la_n_evenimente(n_evenimente)
    assert(persoanele[0]==1)
    assert(len(persoanele)==1)
    
def test_evenimente_ordonate_timp_descriere():
    validator = Validator()
    repo1 = RepositoryEvenimente()
    repo2 = RepositoryPersoane()
    lista_evenimente = ServiceEvenimente(validator,repo1)
    lista_persoane = ServicePersoane(validator,repo2)
    repo3=RepositoryInscrieri(lista_evenimente,lista_persoane)
    lista_inscrieri=ServiceInscrieri(validator,repo3,lista_evenimente,lista_persoane)
    lista_persoane.adauga_persoana(1,'Ana', 'Strada Plopilor')
    lista_persoane.adauga_persoana(2,'Ema', 'Strada Teilor')
    lista_evenimente.adauga_eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    lista_evenimente.adauga_eveniment(2, 5, 9, 2020, 3, 'Books')
    lista_inscrieri.adauga_inscriere(1,1)
    lista_inscrieri.adauga_inscriere(1,2)
    lista_inscrieri.adauga_inscriere(2,1)
    lista_evenimente_ordonate=lista_inscrieri.evenimente_ordonate_descriere(1)
    assert(len(lista_evenimente_ordonate)==2)
    
test_adauga_eveniment()
test_adauga_persoana()
test_adauga_inscriere()
test_cauta_eveniment()
test_cauta_persoana()
test_modifica_eveniment()
test_modifica_persoana()
test_sterge_eveniment()
test_sterge_persoana()
test_evenimente_ordonate_data()
test_evenimente_ordonate_descriere()
test_persoane_inscrise_multe_evenimente()
test_persoane_inscrise_la_n_evenimente()