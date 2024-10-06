#include "cosfilm.h"

#include <algorithm>
#include <random>

using std::shuffle;

void CosFilme::adauga_film_cos(const Film& o) 
{
    this->cos.push_back(o);
}

void CosFilme::adauga_random(vector<Film> filme, int numar) 
{
    shuffle(filme.begin(), filme.end(), std::default_random_engine(std::random_device{}())); 
    while (cos.size() < numar && !filme.empty()) 
    {
        cos.push_back(filme.back());
        filme.pop_back();
    }
}

const vector<Film>& CosFilme::getAll_cos() 
{
    return this->cos;
}

void CosFilme::sterge_film_cos() 
{
    this->cos.clear();
}