#include "repository.h"
#include <iostream>
#include <sstream>
#include <exception>
#include <functional>
#include <algorithm>
#include <fstream>

using namespace std;

void FilmRepo::adauga(const Film& f2)
{
    if (exista(f2))
    {
        throw ExceptieRepo("Exista deja");
    }
    filme.push_back(f2);
}

void FilmRepo::sterge(string titlu)
{
    int i = 0;
    while (i < this->filme.size() && this->filme.at(i).get_titlu() != titlu)
    {
        i++;
    }
    if (i < this->filme.size())
        this->filme.erase(this->filme.begin() + i);
    else
        throw ExceptieRepo("Nu exista");
}

const Film& FilmRepo::cauta(string titlu)
{
    auto f = std::find_if(this->filme.begin(), this->filme.end(), [=](const Film& f2)
        {
            return (f2.get_titlu() == titlu);
        });
    if (f != this->filme.end())
        return (*f);
    else
        throw ExceptieRepo("Nu exista acest film");
}

void FilmRepo::modifica(const Film& f)
{
    int i = 0;
    while (i < this->filme.size() && !(this->filme.at(i).get_titlu() == f.get_titlu()))
        i++;
    if (i < this->filme.size())
    {
        this->filme.at(i).set_actor(f.get_actor());
        this->filme.at(i).set_gen(f.get_gen());
        this->filme.at(i).set_an(f.get_an());
    }
    else
        throw ExceptieRepo("Nu exista");
}

const vector<Film>& FilmRepo::getAll_filme()
{
    return this->filme;
}

bool FilmRepo::exista(const Film& f)
{
    try
    {
        cauta(f.get_titlu());
        return true;
    }
    catch (ExceptieRepo&)
    {
        return false;
    }
}

void FilmRepoFile::loadFromFile()
{
    ifstream f(this->fisier);
    if (!f.is_open()) {
        throw ExceptieRepo("Nu se poate deschide " + fisier);
    }
    string line;
    while (getline(f, line))
    {
        string titlu, gen, actor;
        int an = 0;

        stringstream linestream(line);
        string current_item;
        int item_no = 0;
        while (getline(linestream, current_item, ','))
        {
                if (item_no == 0) titlu = current_item;
                if (item_no == 1) gen = current_item;
                if (item_no == 2) actor = current_item;
                if (item_no == 3) an = stoi(current_item);
                item_no++;
        }
        Film film{ titlu, gen, actor, an };
        FilmRepo::adauga(film);
    }
    f.close();
}

void FilmRepoFile::writeToFile()
{
    ofstream g(this->fisier);
    if (!g.is_open())
        throw ExceptieRepo("Nu se poate deschide " + fisier);
    for (auto& f : getAll_filme()) {
        g << f.get_titlu() << "," << f.get_gen() << "," << f.get_actor() << "," << f.get_an() << endl;
    }
    g.close();
}

void FilmRepoFile::adauga(const Film& f)
{
    FilmRepo::adauga(f);
    writeToFile();
}

void FilmRepoFile::modifica(const Film& f)
{
    FilmRepo::modifica(f); 
    writeToFile();
}

void FilmRepoFile::sterge(string titlu)
{
    FilmRepo::sterge(titlu);
    writeToFile();
}
