from ui.consola import Consola
from repository.repository_cafea import RepositoryCafeaFisier
from service.service_cafea import ServiceCafea
from domain.validator import Validare

validare=Validare()
repo=RepositoryCafeaFisier('data/cafele.txt')
lista_cafele=ServiceCafea(repo,validare)
consola=Consola(lista_cafele)
consola.start_program()