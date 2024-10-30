from domain.validare import Validator
from service.service_persoane import ServicePersoane
from service.service_evenimente import ServiceEvenimente
from service.service_inscrieri import ServiceInscrieri
from repository.repository_evenimente import FileRepositoryEvenimente
from repository.repository_persoane import FileRepositoryPersoane
from repository.repository_inscrieri import RepositoryInscrieri
from ui.consola import Consola
from tests.testare_service import *
from tests.testare_pers_ev import *
from tests.testare_repo import *
from tests.testare_sortari import *

validator = Validator()
repo_persoana =FileRepositoryPersoane('data/persoane.txt')
repo_eveniment= FileRepositoryEvenimente('data/evenimente.txt')
lista_persoane= ServicePersoane(validator,repo_persoana)
lista_evenimente= ServiceEvenimente(validator,repo_eveniment)
repo_inscrieri=RepositoryInscrieri(lista_persoane,lista_evenimente)
lista_inscrieri=ServiceInscrieri(validator,repo_inscrieri,lista_evenimente,lista_persoane)
fisiere_test=TesteFisier()
fisiere_test.run_all_test()

consola = Consola(lista_persoane,lista_evenimente,lista_inscrieri)
consola.start_program()
