#pragma once
#include <functional>
#include <vector>
#include <memory>
#include <unordered_map>
#include <string>

#include "domain.h"
#include "repository.h"
#include "validator.h"
#include "cosfilm.h"
#include "undo.h"

using std::vector;
using std::function;
using std::string;


class FilmServ
{
private:
    RepoAbstract& repo;
    Validator& valid;
    CosFilme& cos_curent;
    vector<std::unique_ptr<Undo>> undo_actiune;

    static bool cmp_titlu(const Film& a, const Film& b) 
    {
        return a.get_titlu() < b.get_titlu();
    }

    static bool cmp_actor(const Film& a, const Film& b) 
    {
        return a.get_actor() < b.get_actor();
    }

    static bool cmp_an_gen(const Film& a, const Film& b) 
    {
        if (a.get_an() == b.get_an()) 
        {
            return a.get_gen() < b.get_gen();
        }
        return a.get_an() < b.get_an();
    }
public:

    FilmServ(class RepoAbstract& repo, Validator& valid, CosFilme &cos_filme) :repo{ repo }, valid{ valid }, cos_curent{ cos_filme }
    {
    }
    FilmServ() = delete;
    FilmServ(const FilmServ& fs) = delete;
    void adauga(const string& titlu, const string& gen, const string& actor, int an);
    void modifica(const string& titlu_vechi, const string& titlu, const string& gen, const string& actor, int an);
    void sterge(const string& titlu);
    bool cauta(string titlu);
    vector <Film> filtrare_titlu(const string& titlu);
    vector <Film> filtrare_an(int an);
    
    void cos_adauga(const string& titlu);
    int cos_adauga_random(int nr);
    const vector<Film>& getAll_cos();
    void cos_sterge();
    void cos_export(const string& filename);
    vector <Film>& getAll() noexcept
    {
        return repo.getAll();
    }
    vector<Film> sortare_titlu();
    vector<Film> sortare_actor();
    vector<Film> sortare_an_gen();
    unordered_map<string, int> frecvente_titlu();
    void undo();
};

