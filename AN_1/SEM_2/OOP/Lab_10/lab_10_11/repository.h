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
    explicit ExceptieRepo(string m) : mesaj{ std::move(std::move(m)) } {}
    friend ostream& operator<<(ostream& out, const ExceptieRepo& ex);
};
ostream& operator<<(ostream& out, const ExceptieRepo& ex);

class RepoAbstract {
public:
    RepoAbstract() = default;

    RepoAbstract(const RepoAbstract& f) = delete;

    virtual void adauga(const Film& f2) = 0;

    virtual void sterge(const Film& f2) = 0;

    virtual const Film& cauta(string titlu) = 0;

    virtual vector<Film>& getAll() = 0;

};

class FilmRepo : public RepoAbstract
{
private:
    vector <Film> filme;
public:
    FilmRepo() = default;
    FilmRepo(const FilmRepo& f) = delete;

    void adauga(const Film& f2) override;

    void sterge(const Film& f2) override;

    const Film& cauta(string titlu) override;

    vector <Film>& getAll() override
    {
        return filme;
    }
};

class FilmRepoFile : public FilmRepo {
private:
    string fisier;
    void loadFromFile();

public:
    explicit FilmRepoFile(string fisier) : FilmRepo(), fisier{ fisier } {
        loadFromFile();
    }

    void adauga(const Film& f) override {
        FilmRepo::adauga(f);
        writeToFile();
    }

    void sterge(const Film& f) override {
        FilmRepo::sterge(f);
        writeToFile();
    }

    void writeToFile();
};

class RepoNou : public RepoAbstract {
private:
    std::unordered_map<string, Film> filme;

public:
    RepoNou() {};

    RepoNou(const FilmRepo& re) = delete;

    void adauga(const Film& f) override;

    void sterge(const Film& f) override;

    const Film& cauta(string titlu) override;

    vector<Film>& getAll() override;
};