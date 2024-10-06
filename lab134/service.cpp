#include <string>
#include <vector>
#include <algorithm>
#include <fstream>
#include "service.h"
using namespace std;

void FilmServ::adauga( string titlu,  string gen,  string actor, int an)
{
    Film f{ titlu, gen, actor, an };
    valid.validare(f);
    repo.adauga(f);
    undo_actiune.push_back(std::make_unique<undo_adauga>(repo, f));
}

void FilmServ::modifica(const string& titlu, const string& gen, const string& actor,const int& an)
{
    Film fv = repo.cauta(titlu);
    Film f{ titlu, gen, actor, an };
    valid.validare(f);
    repo.modifica(f);
    undo_actiune.push_back(std::make_unique<undo_modifica>(repo, fv, f));
}

void FilmServ::sterge(const std::string& titlu)
{
    Film f{ titlu, "gen", "actor", 10 };
    valid.validare(f);
    Film f2 = repo.cauta(titlu);
    repo.sterge(titlu);
    undo_actiune.push_back(std::make_unique<undo_sterge>(repo, f2));
}

Film FilmServ::cauta(const string& titlu)
{
    Film f{ titlu, "gen", "actor", 10 };
    valid.validare(f);
    return repo.cauta(titlu);
}

vector <Film> FilmServ::filtrare_titlu(string titlu)
{
    const vector<Film>& filme = getAll();
    vector<Film> filtrate;
    std::copy_if(filme.begin(), filme.end(), back_inserter(filtrate), [titlu](const Film& f)
        {
            return f.get_titlu() == titlu;
        });
    return (filtrate);
}

vector <Film> FilmServ::filtrare_an(int an)
{
    const vector<Film>& filme = getAll();
    vector<Film> filtrate;
    std::copy_if(filme.begin(), filme.end(), back_inserter(filtrate), [an](const Film& f)
        {
            return f.get_an() == an;
        });
    return (filtrate);
}

vector<Film> FilmServ::sortare_titlu()
{
    vector<Film> sortedCopy{ repo.getAll_filme() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_titlu);
    return sortedCopy;
}

vector<Film> FilmServ::sortare_actor()
{
    vector<Film> sortedCopy{ repo.getAll_filme() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_actor);
    return sortedCopy;
}

vector<Film> FilmServ::sortare_an_gen()
{
    vector<Film> sortedCopy{ repo.getAll_filme() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_an_gen);
    return sortedCopy;
}

void FilmServ::cos_adauga(string titlu)
{
    const auto& oferta = repo.cauta(titlu);
    cos_curent.adauga_film_cos(oferta);
}

int FilmServ::cos_adauga_random(int nr)
{
    cos_curent.adauga_random(this->getAll(), nr);
    return cos_curent.getAll_cos().size();
}

const vector<Film>& FilmServ::getAll_cos() {
    return cos_curent.getAll_cos();
}

void FilmServ::cos_sterge(string titlu) 
{
    cos_curent.sterge_film_cos(titlu);
}

int FilmServ::nr_filme_cos(string gen)
{
    vector<Film> Copy{ repo.getAll() };
    int nr = std::count_if(Copy.begin(), Copy.end(), [gen](const Film& f) {
        return f.get_gen() == gen;
        });
    return nr;
}

void FilmServ::cos_export(string fisier)
{
    cos_curent.exporta_cos(fisier);
}

unordered_map<string, int> FilmServ::frecvente_titlu()
{
    unordered_map<string, int> map;
    vector<Film> v = this->getAll();
    for (const auto& i : v) {
        if (map.find(i.get_titlu()) != map.end())
            map[i.get_titlu()]++;
        else
            map[i.get_titlu()] = 1;
    }
    return map;
}

void FilmServ::undo()
{
    if (undo_actiune.empty())
        throw ExceptieRepo("Nu mai exista operatii");
    undo_actiune.back()->doUndo();
    undo_actiune.pop_back();
}

bool FilmServ::cmp_titlu(const Film& a, const Film& b)
{
    return a.get_titlu() < b.get_titlu();
}

bool FilmServ::cmp_actor(const Film& a, const Film& b)
{
    return a.get_actor() < b.get_actor();
}

bool FilmServ::cmp_an_gen(const Film& a, const Film& b)
{
    if (a.get_an() == b.get_an())
    {
        return a.get_gen() < b.get_gen();
    }
    return a.get_an() < b.get_an();
}

const vector<Film>& FilmServ::getAll() noexcept {
    return repo.getAll_filme();
}

