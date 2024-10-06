#pragma once
#include <utility>
#include <vector>
#include <string>
#include <ostream>
#include <fstream>
#include <unordered_map>
#include <random>
#include "domain.h"

using std::vector;
using std::ostream;
using std::string;

class ExceptieRepo
{
private:
    string mesaj;
public:
    ExceptieRepo(string mesaj) :mesaj{ mesaj } {};
    string get_mesaj() 
    {
        return this->mesaj;
    }
};

class FilmRepo 
{
private:
    vector <Film> filme;
public:
    FilmRepo() = default;
    FilmRepo(const FilmRepo& f) = delete;

    virtual void adauga(const Film& f2);

    virtual void sterge(string titlu);

    virtual void modifica(const Film& f2);

    const Film& cauta(string titlu);

    bool exista(const Film& f);

    const vector<Film>& getAll_filme();

    vector <Film> getAll() const
    {
        return filme;
    }
};

class FilmRepoFile : public FilmRepo {
private:
    string fisier;
    void loadFromFile();
    void writeToFile();

public:
    explicit FilmRepoFile(string fisier) : FilmRepo(), fisier{ fisier } {
        loadFromFile();
    }

    void adauga(const Film& f) override;

    void sterge(string titlu) override;

    void modifica(const Film& f) override;
};
