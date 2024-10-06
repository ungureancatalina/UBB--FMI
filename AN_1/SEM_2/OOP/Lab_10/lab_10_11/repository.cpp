#include "repository.h"
#include <iostream>
#include <sstream>
#include <exception>
#include <functional>
#include <algorithm>
#include <fstream>

using std::ostream;
using std::stringstream;
using std::string;
using std::exception;


void FilmRepo::adauga(const Film& f2)
{
    filme.push_back(f2);
}

void FilmRepo::sterge(const Film& f2)
{
    int i = 0;
    while (i < this->filme.size())
    {
        if (filme.at(i).get_titlu() == f2.get_titlu())
        {
            this->filme.erase(this->filme.begin() + i);
            break;
        }
        i++;
    }
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

void FilmRepoFile::loadFromFile()
{
    std::ifstream in(fisier);
    if (!in.is_open()) throw ExceptieRepo("Nu s-a realizat deschiderea fisierului");
    while (!in.eof()) {
        string titlu, gen, actor;
        int an;
        if (in.eof()) break;
        in >> titlu;
        if (in.eof()) break;
        in >> gen;
        if (in.eof()) break;
        in >> actor;
        if (in.eof()) break;
        in >> an;
        Film f{ titlu, gen, actor, an };
        FilmRepo::adauga(f);
    }
    in.close();
}

void FilmRepoFile::writeToFile()
{
    std::ofstream out(fisier);
    if (!out.is_open()) throw ExceptieRepo("Nu s-a realizat deschiderea fisierului");
    for (auto& f : FilmRepo::getAll()) {
        out << f.get_titlu();
        out << std::endl;
        out << f.get_gen();
        out << std::endl;
        out << f.get_actor();
        out << std::endl;
        out << f.get_an();
        out << std::endl;
    }
    out.close();
}


ostream& operator<<(ostream& out, const ExceptieRepo& ex)
{
    out << ex.mesaj;
    return out;
}