from domain.validare import Validator
from domain.lucru_persoana import Persoana
from domain.lucru_eveniment import Eveniment

def test_create_persoana():
    persoana = Persoana(1, 'Ana', 'Strada Plopilor')
    assert (persoana.get_id() == 1)
    assert (persoana.get_nume() == 'Ana')
    assert (persoana.get_adresa() =='Strada Plopilor')
    persoana.set_nume('Maria')
    assert (persoana.get_nume()== 'Maria')

def test_persoane_egale():
    persoana1 = Persoana(1,'Ana', 'Strada Plopilor')
    persoana2 = Persoana(1,'Ana', 'Strada Plopilor')
    assert (persoana1 == persoana2)
    persoana1 = Persoana(1,'Ana', 'Strada Plopilor')
    persoana2 = Persoana(1,'Maria', 'Strada Plopilor')
    assert (persoana1 != persoana2)

def test_validare_persoana():
    validator = Validator()
    persoana1 = Persoana(1,'Ana', 'Strada Plopilor')
    try:
        validator.validare_persoana(persoana1)
        assert True
    except ValueError:
        assert False
    persoana1 = Persoana(0,'Ana', 'Strada Plopilor')
    try:
        validator.validare_persoana(persoana1)
        assert False
    except ValueError:
        assert True
    persoana1 = Persoana(1,'', 'Strada Plopilor')
    try:
        validator.validare_persoana(persoana1)
        assert False
    except ValueError:
        assert True
    persoana1 = Persoana(1,'Ana', '')
    try:
        validator.validare_persoana(persoana1)
        assert False
    except ValueError:
        assert True

def test_create_eveniment():
    eveniment = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    assert (eveniment.get_id() == 1)
    assert (eveniment.get_zi() == 2)
    assert (eveniment.get_luna() == 3)
    assert (eveniment.get_an() == 2023)
    assert (eveniment.get_timp() == 4)
    assert (eveniment.get_descriere() =='Boardgames')
    eveniment.set_an(2021)
    assert (eveniment.get_an()== 2021)

def test_evenimente_egale():
    eveniment1 = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    eveniment2 = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    assert (eveniment1 == eveniment2)
    eveniment1 = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    eveniment2 = Eveniment(1, 2, 3, 2023, 5, 'Boardgames')
    assert (eveniment1 != eveniment2)

def test_validare_eveniment():
    validator = Validator()
    eveniment1 = Eveniment(1, 2, 3, 2023, 4, 'Boardgames')
    try:
        validator.validare_eveniment(eveniment1)
        assert True
    except ValueError:
        assert False
    eveniment1 = Eveniment(0, 2, 3, 2023, 4, 'Boardgames')
    try:
        validator.validare_eveniment(eveniment1)
        assert False
    except ValueError:
        assert True
    eveniment1 = Eveniment(1, 52, 3, 2023, 4, 'Boardgames')
    try:
        validator.validare_eveniment(eveniment1)
        assert False
    except ValueError:
        assert True
    eveniment1 = Eveniment(1, 2, 13, 2023, 4, 'Boardgames')
    try:
        validator.validare_eveniment(eveniment1)
        assert False
    except ValueError:
        assert True
    eveniment1 = Eveniment(1, 2, 3, 2026, 4, 'Boardgames')
    try:
        validator.validare_eveniment(eveniment1)
        assert False
    except ValueError:
        assert True    
    eveniment1 = Eveniment(1, 2, 3, 2026, 4, '')
    try:
        validator.validare_eveniment(eveniment1)
        assert False
    except ValueError:
        assert True        

test_create_eveniment()
test_create_persoana()
test_evenimente_egale()
test_persoane_egale()
test_validare_eveniment()
test_validare_persoana()
