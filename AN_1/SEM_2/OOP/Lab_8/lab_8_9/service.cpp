#include "service.h"

#include <string>
#include <vector>
#include <algorithm>
#include <fstream>

void FilmServ::adauga(const string& titlu, const string& gen, const string& actor, int an)
{
    valid.validare(titlu, gen, actor, an);
    Film f{ titlu, gen, actor, an };
    repo.adauga(f);
    undo_actiune.push_back(std::make_unique<undo_adauga>(repo, f));
}

void FilmServ::modifica(const std::string& titlu_vechi, const std::string& titlu, const std::string& gen, const std::string& actor, int an)
{
    valid.validare(titlu, gen, actor, an);
    Film f = repo.cauta(titlu_vechi);
    repo.sterge(f);
    Film f_nou{ titlu, gen, actor, an };
    repo.adauga(f_nou);
    undo_actiune.push_back(std::make_unique<undo_modifica>(repo, f, f_nou));
}

void FilmServ::sterge(const std::string& titlu)
{
    valid.validare(titlu, "gen", "actor", 2000);
    Film f = repo.cauta(titlu);
    repo.sterge(f);
    undo_actiune.push_back(std::make_unique<undo_sterge>(repo, f));
}

bool FilmServ::cauta(string titlu)
{
    try
    {
        repo.cauta(titlu);
        return true;
    }
    catch (ExceptieRepo&)
    {
        return false;
    }
}

vector <Film> FilmServ::filtrare_titlu(const std::string& titlu)
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
    vector<Film> sortedCopy{ repo.getAll() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_titlu);
    return sortedCopy;
}

vector<Film> FilmServ::sortare_actor() 
{
    vector<Film> sortedCopy{ repo.getAll() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_actor);
    return sortedCopy;
}

vector<Film> FilmServ::sortare_an_gen() 
{
    vector<Film> sortedCopy{ repo.getAll() };
    sort(sortedCopy.begin(), sortedCopy.end(), cmp_an_gen);
    return sortedCopy;
}

void FilmServ::cos_adauga(const string& titlu) 
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

void FilmServ::cos_sterge() {
    cos_curent.sterge_film_cos();
}

void FilmServ::cos_export(const string& filename) {
    if (filename.find(".csv") == string::npos && filename.find(".html") == string::npos) 
    {
        throw Exceptie("Fisierul nu are o extensie valida");
    }
    else 
    {
        ofstream fout(filename);
        if (!fout.is_open()) 
        {
            throw Exceptie("Nu s-a putut deschide fisierul pentru export");
        }
        if (filename.find(".html") != string::npos) 
        {
            fout << "<html>";
            fout << "<style> table {font-family: Arial, sans-serif; border-collapse: collapse; width: 100%;}"
                "td, th {border: 1px solid #dddddd; text-align: left; padding: 8px;} th {background-color: #f2f2f2;}"
                "</style>";
            fout << "<body>";
            fout << "<h1> COS </h1>";
            fout << "<table><tr><th>Titlu</th> <th>Gen</th> <th>Actor</th> <th>An</th></tr>";
            for (auto& f : getAll_cos()) {
                fout << "<tr><td>" << f.get_titlu() << "</td><td>" << f.get_gen() << "</td><td>" << f.get_actor()
                    << "</td><td>" << f.get_an() << "</td></tr>";
            }
            fout << "</table></body>";
            fout << "</html>";
        }
        else 
        {
            for (auto& f : getAll_cos()) 
            {
                fout << f.get_titlu() << "," << f.get_gen() << "," << f.get_actor()
                    << "," << f.get_an() << endl;
            }
        }
        fout.close();
    }
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
        throw Exceptie("Nu mai exista operatii");
    undo_actiune.back()->doUndo();
    undo_actiune.pop_back();
}