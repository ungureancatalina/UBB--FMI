#include "teste.h"
#include "validator.h"
#include <assert.h>
#include <sstream>
#include <iostream>

using std::ostream;
using std::cout;
using std::stringstream;

void test_adauga()
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
}

void test_cauta()
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    auto f = repo.cauta("Titanic");
    assert(f.get_titlu() == "Titanic");
    assert(f.get_gen() == "dragoste");
    assert(f.get_actor() == "Leonardo DiCaprio");
    assert(f.get_an() == 1997);
    try
    {
        repo.cauta("Fast and Furious");
    }
    catch (ExceptieRepo& ex)
    {
        stringstream os;
        os << ex;
        assert(os.str().find("exista") >= 0);
        assert(true);
    }
}

void test_sterge()
{
    FilmRepo repo;
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    Film f = Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997);
    repo.sterge(f);
    assert(repo.getAll().size() == 0);
}

void test_getAll()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos};
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
    vector <Film> lista = serv.getAll();
    assert(lista.size() == 2);
}

void test_adauga2()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    assert(repo.getAll().size() == 0);
    serv.adauga("Titanic", "dragoste", "Leonardo DiCaprio", 1997);
    assert(repo.getAll().size() == 1);
    serv.adauga("Fast and Furious", "actiune", "Vin Diesel", 2001);
    assert(repo.getAll().size() == 2);
}

void test_modifica()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    serv.modifica("Titanic", "Fast and Furious", "actiune", "Vin Diesel", 2001);
    Film f = repo.cauta("Fast and Furious");
    assert(f.get_titlu() == "Fast and Furious");
    assert(f.get_actor() == "Vin Diesel");
}

void test_sterge2()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos};
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    serv.sterge("Titanic");
    assert(repo.getAll().size() == 0);
}

void test_cauta2()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    bool rezultat = serv.cauta("Titanic");
    assert(rezultat == true);
    bool rezultat2 = serv.cauta("nimic");
    assert(rezultat2 == false);
    try
    {
        repo.adauga(Film("a", "b", "c", -1));
    }
    catch (const Exceptie&)
    {
        assert(true);
    }
}

void test_filtrare_titlu()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 2);
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 3);
    vector<Film> lista = serv.filtrare_titlu("Titanic");
    assert(lista.size() == 1);
    assert(lista[0].get_an() == 1997);
    vector <Film> lista2 = serv.filtrare_titlu("Titlu");
    assert(lista2.size() == 0);
}

void test_filtrare_an()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 2);
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    assert(repo.getAll().size() == 3);
    vector <Film> lista = serv.filtrare_an(1997);
    assert(lista.size() == 2);
    assert(lista[0].get_titlu() == "Titanic");
}

void test_sortare_titlu() {
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    auto sortedList = serv.sortare_titlu();
    assert(sortedList.size() == 3);
    assert(sortedList[0].get_titlu() == "Fast and Furious");
    assert(sortedList[1].get_titlu() == "Fast and Furious 2");
    assert(sortedList[2].get_titlu() == "Titanic");
}

void test_sortare_actor() {
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    auto sortedList = serv.sortare_actor();
    assert(sortedList.size() == 3);
    assert(sortedList[0].get_actor() == "Leonardo DiCaprio");
    assert(sortedList[1].get_actor() == "Paul Walker");
    assert(sortedList[2].get_actor() == "Vin Diesel");
}

void test_sortare_an_gen() {
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Fast and Furious", "actiune", "Paul Walker", 2003));
    repo.adauga(Film("Titanic", "dragoste", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious 2", "actiune", "Vin Diesel", 1997));
    auto sortedList = serv.sortare_an_gen();
    assert(sortedList.size() == 3);
    assert(sortedList[0].get_an() == 1997);
    assert(sortedList[1].get_an() == 1997);
    assert(sortedList[2].get_an() == 2003);
}

void test_domain()
{
    Film f("Titanic", "drama", "Leonardo DiCaprio", 1997);
    assert(f.get_titlu() == "Titanic");
    assert(f.get_gen() == "drama");
    assert(f.get_actor() == "Leonardo DiCaprio");
    assert(f.get_an() == 1997);
    Film f2;
    assert(f2.get_titlu() == "");
    assert(f2.get_gen() == "");
    assert(f2.get_actor() == "");
}

void test_repo()
{
    FilmRepo repo;
    Film f("Titanic", "drama", "Leonardo DiCaprio", 1997);
    repo.adauga(f);
    Film f2("Fast and Furious", "actiune", "Paul Walker", 2003);
    repo.adauga(f2);
    const vector<Film>& lista = repo.getAll();
    assert(lista.size() == 2);
    const Film& gasit = repo.cauta("Titanic");
    assert(gasit.get_titlu() == "Titanic");
    try {
        repo.cauta("nimic");
    }
    catch (ExceptieRepo& ex)
    {
        stringstream os;
        os << ex;
        assert(os.str().find("exista") >= 0);
        assert(true);
    }
}

void test_validare()
{
    Validator validator;
    try
    {
        validator.validare("a", "drama", "Leonardo DiCaprio", 1997);
        assert(false);
    }
    catch (Exceptie& e)
    {
        assert(e.get_mesaj() == "Titlul nu poate fi vid");
    }
    try
    {
        validator.validare("Titanic", "drama", "b", 1997);
        assert(false);
    }
    catch (Exceptie& e)
    {
        assert(e.get_mesaj() == "Numele actorului nu poate fi vid");
    }
    try
    {
        validator.validare("Titanic", "drama", "Leonardo DiCaprio", -1997);
        assert(false);
    }
    catch (Exceptie& e)
    {
        assert(e.get_mesaj() == "Anul nu poate fi mai mic ca 0");
    }
    try
    {
        validator.validare("Titanic", "c", "Leonardo DiCaprio", 1997);
        assert(false);
    }
    catch (Exceptie& e)
    {
        assert(e.get_mesaj() == "Genul filmului nu poate fi vid");
    }
}
void test_cos()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ service{ repo, valid, cos };
    service.adauga("Titanic", "drama", "Leonardo DiCaprio", 1997);
    service.adauga("Fast and Furious", "action", "Vin Diesel", 2001);
    service.adauga("Inception", "sci-fi", "Leonardo DiCaprio", 2010);
    service.cos_adauga("Titanic");
    assert(service.getAll_cos().size() == 1);
    try {
        service.cos_adauga("Avatar");
        assert(false); 
    }
    catch (ExceptieRepo& ex) {
        assert(true);
    }
    assert(service.getAll_cos().size() == 1); 

}

void test_raport()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ service{ repo, valid, cos };
    service.adauga("Titanic", "drama", "Leonardo DiCaprio", 1997);
    service.adauga("Fast and Furious", "action", "Vin Diesel", 2001);
    service.adauga("Inception", "sci-fi", "Leonardo DiCaprio", 2010);
    auto map = service.frecvente_titlu();
    assert(map.size() == 3);
}

void test_cos_adauga_film_cos() {
    CosFilme cos;
    assert(cos.getAll_cos().empty());
    Film film("Titanic", "drama", "Leonardo DiCaprio", 1997);
    cos.adauga_film_cos(film);
    assert(cos.getAll_cos().size() == 1); 
}

void test_cos_adauga_random() {
    CosFilme cos;
    assert(cos.getAll_cos().empty());
    vector<Film> oferte = {
        Film("Titanic", "drama", "Leonardo DiCaprio", 1997),
        Film("Fast and Furious", "action", "Vin Diesel", 2001),
        Film("Inception", "thriller", "Leonardo DiCaprio", 2010)
    };
    cos.adauga_random(oferte, 2);
    assert(cos.getAll_cos().size() == 2); 
}

void test_cos_getAll_cos() {
    CosFilme cos;
    assert(cos.getAll_cos().empty());
    vector<Film> oferte = {
        Film("Titanic", "drama", "Leonardo DiCaprio", 1997),
        Film("Fast and Furious", "action", "Vin Diesel", 2001)
    };
    cos.adauga_random(oferte, 2);
    const vector<Film>& cosFilme = cos.getAll_cos();
    assert(cosFilme.size() == 2);
    assert(find_if(cosFilme.begin(), cosFilme.end(), [](const Film& f) { return f.get_titlu() == "Titanic"; }) != cosFilme.end());
    assert(find_if(cosFilme.begin(), cosFilme.end(), [](const Film& f) { return f.get_titlu() == "Fast and Furious"; }) != cosFilme.end());

}

void test_cos_sterge_film_cos() {
    CosFilme cos;
    assert(cos.getAll_cos().empty()); 
    vector<Film> oferte = {
        Film("Titanic", "drama", "Leonardo DiCaprio", 1997),
        Film("Fast and Furious", "action", "Vin Diesel", 2001)
    };
    cos.adauga_random(oferte, 2);
    cos.sterge_film_cos();
    assert(cos.getAll_cos().empty()); 
}

void test_cos_adauga()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious", "action", "Vin Diesel", 2001));
    repo.adauga(Film("Inception", "sci-fi", "Leonardo DiCaprio", 2010));
    serv.cos_adauga("Titanic");
    assert(serv.getAll_cos().size() == 1);
    try {
        serv.cos_adauga("Avatar");
        assert(false); 
    }
    catch (ExceptieRepo& ex) {
        assert(true);
    }
    assert(serv.getAll_cos().size() == 1); 
}

void test_cos_adauga_random2()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious", "action", "Vin Diesel", 2001));
    repo.adauga(Film("Inception", "sci-fi", "Leonardo DiCaprio", 2010));
    int initialSize = serv.getAll_cos().size();
    serv.cos_adauga_random(2);
    assert(serv.getAll_cos().size() == initialSize + 2);
}

void test_cos_export()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Fast and Furious", "action", "Vin Diesel", 2001));
    repo.adauga(Film("Inception", "sci-fi", "Leonardo DiCaprio", 2010));
    serv.cos_adauga("Titanic");
    serv.cos_adauga("Fast and Furious");
    string filename = "cos_export.html";
    serv.cos_export(filename);
    ifstream file(filename);
    assert(file.good());
}

void test_cos_sterge() 
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    serv.adauga("Titanic", "drama", "Leonardo DiCaprio", 1997);
    serv.adauga("Fast and Furious", "action", "Vin Diesel", 2001);
    serv.adauga("Inception", "sci-fi", "Leonardo DiCaprio", 2010);
    assert(serv.getAll_cos().empty());
    serv.cos_sterge(); 
    assert(serv.getAll_cos().empty());
}

void test_undo()
{
    FilmRepo repo;
    Validator valid;
    CosFilme cos;
    FilmServ serv{ repo, valid, cos };
    serv.adauga("Titanic", "drama", "Leonardo DiCaprio", 1997);
    serv.adauga("Fast and Furious", "actiune", "Vin Diesel", 2001);
    serv.modifica("Titanic", "Fast and Furious", "actiune", "Vin Diesel", 2001);
    serv.sterge("Fast and Furious");
    serv.undo();
    assert(repo.getAll().size() == 2);
    serv.undo();
    assert(repo.getAll().size() == 2); 
    serv.undo();
    assert(repo.getAll().size() == 1); 
}

void test_writeToFile() {
    string filename = "C:/Users/ungur/OneDrive/Desktop/SEM_2/oop/lab/89/lab_8_9/test2.txt";
    FilmRepoFile repo(filename);
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    repo.adauga(Film("Inception", "SF", "Leonardo DiCaprio", 2010));
    repo.adauga(Film("The Shawshank Redemption", "drama", "Tim Robbins", 1994));
    repo.writeToFile();
    ifstream file(filename);
    assert(file.is_open());
    vector<string> lines;
    string line;
    while (getline(file, line)) {
        lines.push_back(line);
    }
    assert(lines.size() == 12); 
    file.close();
}

void test_adauga3()
{
    RepoNou repo;
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
}

void test_sterge3()
{
    RepoNou repo;
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    Film f = Film("Titanic", "drama", "Leonardo DiCaprio", 1997);
    repo.sterge(f);
    assert(repo.getAll().size() == 0);
}

void test_cauta3()
{
    RepoNou repo;
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    auto f = repo.cauta("Titanic");
    assert(f.get_titlu() == "Titanic");
    assert(f.get_gen() == "drama");
    assert(f.get_actor() == "Leonardo DiCaprio");
    assert(f.get_an() == 1997);

    try
    {
        repo.cauta("Fast and Furious");
    }
    catch (Exceptie& ex)
    {
        assert(true);
    }
}

void test_getAll3()
{
    RepoNou repo;
    repo.adauga(Film("Titanic", "drama", "Leonardo DiCaprio", 1997));
    assert(repo.getAll().size() == 1);
    repo.adauga(Film("Fast and Furious", "actiune", "Vin Diesel", 2001));
    assert(repo.getAll().size() == 2);
}

void teste()
{
    test_adauga();
    test_cauta();
    test_sterge();
    test_getAll();
    test_adauga2();
    test_sterge2();
    test_cauta2();
    test_modifica();
    test_filtrare_titlu();
    test_filtrare_an();
    test_repo();
    test_sortare_titlu();
    test_sortare_actor();
    test_sortare_an_gen();
    test_domain();
    test_validare();
    test_cos();
    test_raport();
    test_cos_adauga_film_cos();
    test_cos_adauga_random();
    test_cos_getAll_cos();
    test_cos_sterge_film_cos();
    test_cos_adauga();
    test_cos_adauga_random2();
    test_cos_export();
    test_cos_sterge();
    test_undo();
    test_cauta3();
    test_adauga3();
    test_sterge3();
    test_getAll3();
    //test_writeToFile();
}