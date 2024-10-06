#include "cosfilm.h"
#include "observer.h"
#include <algorithm>
using std::shuffle;
#include <fstream>

void CosFilm::adauga_film_cos(const Film& f)
{
    vector<Film> film = this->getAll_cos();
    for (auto elem : film)
        if (f.get_titlu() == elem.get_titlu())
            throw ExceptieRepo("Exista deja");
    this->cos.push_back(f);
    notify();
}

void CosFilm::adauga_random(vector<Film> filme, int numar)
{
    shuffle(filme.begin(), filme.end(), std::default_random_engine(std::random_device{}()));
    while (cos.size() < numar && filme.size() > 0) 
    {
        if (gasit(filme, cos) == true) 
        {
            cos.push_back(filme.back());
        }
        filme.pop_back();
    }
    notify();
}

bool CosFilm::gasit(vector<Film> film, vector<Film> cos)
{
    for (auto f : cos)
        if (f.get_titlu() == film.back().get_titlu())
            return false;
    return true;
}

const vector<Film>& CosFilm::getAll_cos()
{
    return this->cos;
}

void CosFilm::sterge_film_cos(string titlu)
{
    int i = 0;
    while (i < this->cos.size() && this->cos.at(i).get_titlu() != titlu) 
    {
        i++;
    }
    if (i < this->cos.size()) 
        this->cos.erase(this->cos.begin() + i);
    else 
        throw ExceptieRepo("Nu exista filmul");
    notify();
}

void CosFilm::golire_cos()
{
    this->cos.clear();
    notify();
}

void CosFilm::exporta_cos(string fisier)
{
    std::ofstream fout(fisier);
    for (auto& f : cos)
        fout << f.get_titlu() << ":" << f.get_gen() << ":" << f.get_actor() << ":" << f.get_an() << std::endl;
    fout.close();
}