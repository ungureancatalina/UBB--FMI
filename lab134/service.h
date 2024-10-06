#pragma once
#include "domain.h"
#include "repository.h"
#include "cosfilm.h"
#include "validator.h"
#include "undo.h"
#include <string>
#include <functional>
#include <vector>
#include <memory>
#include <unordered_map>

using std::vector;
using std::function;
using std::string;


class FilmServ
{
private:
    FilmRepo& repo;
    Validator& valid;

    CosFilm cos_curent;
    vector<std::unique_ptr<Undo>> undo_actiune;

public:

    FilmServ(class FilmRepo& repo, Validator& valid) :repo{ repo }, valid{ valid }, cos_curent{ repo } {};
    FilmServ(const FilmServ& fs) = delete;
    void adauga( string titlu, string gen, string actor, int an);
    void modifica(const string& titlu, const string& gen, const string& actor,const int& an);
    void sterge(const string& titlu);
    Film cauta(const string& titlu);
    vector <Film> filtrare_titlu(string titlu);
    vector <Film> filtrare_an(int an);

    void cos_adauga(string titlu);
    int cos_adauga_random(int nr);
    const vector<Film>& getAll_cos();
    void cos_sterge(string titlu);
    void goleste_cos()
    {
        cos_curent.golire_cos();
    }
    int nr_filme_cos(string gen);
    void cos_export(string filename);
    CosFilm& get_cos()
    {
        return cos_curent;
    }

    const vector <Film>& getAll() noexcept;

    vector<Film> sortare_titlu();
    vector<Film> sortare_actor();
    vector<Film> sortare_an_gen();
    unordered_map<string, int> frecvente_titlu();
    void undo();
    static bool cmp_titlu(const Film& a, const Film& b);
    static bool cmp_actor(const Film& a, const Film& b);
    static bool cmp_an_gen(const Film& a, const Film& b);
};

